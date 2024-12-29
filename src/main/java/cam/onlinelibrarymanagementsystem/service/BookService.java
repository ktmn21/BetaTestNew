package cam.onlinelibrarymanagementsystem.service;

import cam.onlinelibrarymanagementsystem.exception.ResourceNotFoundException;
import cam.onlinelibrarymanagementsystem.model.Book;
import cam.onlinelibrarymanagementsystem.repository.BookRepository;
import cam.onlinelibrarymanagementsystem.repository.BorrowedBookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BorrowedBookRepository borrowedBookRepository;

    public BookService(BookRepository bookRepository, BorrowedBookRepository borrowedBookRepository) {
        this.bookRepository = bookRepository;
        this.borrowedBookRepository = borrowedBookRepository;
    }

    public Book updateStock(Long id, int stockChange) {
        // Find the book by ID
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));

        // Update the stock
        int updatedStock = book.getStock() + stockChange;
        if (updatedStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        book.setStock(updatedStock);

        // Save the updated book
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    // Method to get available books (books that are not borrowed)
    public List<Book> getAvailableBooks() {
        // Get all books in the library
        List<Book> allBooks = bookRepository.findAll();

        // Get borrowed books - filter those that are already borrowed (not returned)
        List<Long> borrowedBookIds = borrowedBookRepository.findAll()
                .stream()
                .filter(borrowedBook -> {
                    // If the return date is null or after the current date (the book has not been returned or is overdue)
                    LocalDate returnDate = borrowedBook.getReturnDate();
                    return returnDate == null || returnDate.isAfter(LocalDate.now());
                })
                .map(borrowedBook -> borrowedBook.getBook().getId())
                .collect(Collectors.toList());

        // Filter out borrowed books from the list of all books
        List<Book> availableBooks = allBooks.stream()
                .filter(book -> !borrowedBookIds.contains(book.getId()))
                .collect(Collectors.toList());

        return availableBooks;
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with that id not found!"));
    }

    public List<Book> findBooksByTitle(String title){
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> findBooksByAuthor(String author){
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> findByGenre(String genre){
        return bookRepository.findByGenreContainingIgnoreCase(genre);
    }

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);

        if (bookDetails.getTitle() != null && !bookDetails.getTitle().equals(book.getTitle())) {
            book.setTitle(bookDetails.getTitle());
        }

        if (bookDetails.getAuthor() != null && !bookDetails.getAuthor().equals(book.getAuthor())) {
            book.setAuthor(bookDetails.getAuthor());
        }

        if (bookDetails.getGenre() != null && !bookDetails.getGenre().equals(book.getGenre())) {
            book.setGenre(bookDetails.getGenre());
        }

        if (bookDetails.getStock() != null && !bookDetails.getStock().equals(book.getStock())) {
            book.setStock(bookDetails.getStock());
        }

        return bookRepository.save(book);
    }

    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

}
