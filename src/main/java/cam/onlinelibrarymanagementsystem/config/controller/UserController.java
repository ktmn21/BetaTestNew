package cam.onlinelibrarymanagementsystem.config.controller;

import cam.onlinelibrarymanagementsystem.model.Book;
import cam.onlinelibrarymanagementsystem.model.BorrowedBook;
import cam.onlinelibrarymanagementsystem.model.User;
import cam.onlinelibrarymanagementsystem.service.BookService;
import cam.onlinelibrarymanagementsystem.service.BorrowedBookService;
import cam.onlinelibrarymanagementsystem.service.UserDetailsServiceImp;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDetailsServiceImp userService;
    private final BookService bookService;
    private final BorrowedBookService borrowedBookService;

    public UserController(UserDetailsServiceImp userService, BookService bookService, BorrowedBookService borrowedBookService) {
        this.userService = userService;
        this.bookService = bookService;
        this.borrowedBookService = borrowedBookService;
    }

    @GetMapping("/profile")
    public User getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getUserByUsername(userDetails.getUsername());
    }

    @PutMapping("/profile")
    public User updateUserProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User user) {
        return userService.updateUserProfile(userDetails.getUsername(), user);
    }

    @PostMapping("/borrow/{bookId}")
    public BorrowedBook borrowBook(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long bookId) {
        return borrowedBookService.borrowBook(userService.getUserByUsername(userDetails.getUsername()).getId(), bookId);
    }

    @PostMapping("/return/{borrowedBookId}")
    public void returnBook(@PathVariable Long borrowedBookId) {
        borrowedBookService.returnBook(borrowedBookId);
    }

    @GetMapping("/borrowed")
    public List<BorrowedBook> getBorrowedBooks(@AuthenticationPrincipal UserDetails userDetails) {
        return borrowedBookService.getBorrowedBooksByUser(userService.getUserByUsername(userDetails.getUsername()));
    }

    @GetMapping("/available-books")
    public List<Book> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }

    @GetMapping("/find-books-by-book-title")
    public List<Book> getBooksById(@RequestParam String title){
        return bookService.findBooksByTitle(title);
    }

    @GetMapping("/find-books-by-author")
    public List<Book> getBooksByAuthor(@RequestParam String author){
        return bookService.findBooksByAuthor(author);
    }

    @GetMapping("/find-book-by-genre")
    public List<Book> getBooksByGenre(@RequestParam String genre){
        return bookService.findByGenre(genre);
    }
}
