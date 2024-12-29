package cam.onlinelibrarymanagementsystem.service;

import cam.onlinelibrarymanagementsystem.exception.ResourceNotFoundException;
import cam.onlinelibrarymanagementsystem.model.Book;
import cam.onlinelibrarymanagementsystem.model.BorrowedBook;
import cam.onlinelibrarymanagementsystem.model.User;
import cam.onlinelibrarymanagementsystem.repository.BookRepository;
import cam.onlinelibrarymanagementsystem.repository.BorrowedBookRepository;
import cam.onlinelibrarymanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowedBookService {

    private final BorrowedBookRepository borrowedBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BorrowedBookService(BorrowedBookRepository borrowedBookRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookRepository.findAll();
    }

    public List<BorrowedBook> getBorrowedBooksByUser(User user) {
        return borrowedBookRepository.findByUser(user);
    }

    public BorrowedBook borrowBook(Integer userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        if (book.getStock() <= 0) {
            throw new IllegalStateException("Book is out of stock!");
        }

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setUser(user);
        borrowedBook.setBook(book);
        borrowedBook.setBorrowedDate(LocalDate.now());
        borrowedBook.setDueDate(LocalDate.now().plusDays(14)); // Set a 2-week deadline
        borrowedBook.setReturnDate(null);
        borrowedBook.setReturned(false);

        // Decrease stock
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        return borrowedBookRepository.save(borrowedBook);
    }

    public List<BorrowedBook> getOverdueBooks() {
        return borrowedBookRepository.findAll().stream()
                .filter(borrowedBook -> !borrowedBook.isReturned() && borrowedBook.getDueDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public void returnBook(Long borrowedBookId) {
        BorrowedBook borrowedBook = borrowedBookRepository.findById(borrowedBookId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowed book not found with ID: " + borrowedBookId));

        if (borrowedBook.isReturned()) {
            throw new IllegalStateException("Book is already returned!");
        }

        borrowedBook.setReturned(true);
        borrowedBook.setReturnDate(LocalDate.now());
        // Increase stock
        Book book = borrowedBook.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);

        borrowedBookRepository.save(borrowedBook);
    }
}
