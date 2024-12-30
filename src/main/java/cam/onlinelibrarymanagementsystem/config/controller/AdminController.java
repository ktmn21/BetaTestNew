package cam.onlinelibrarymanagementsystem.config.controller;

import cam.onlinelibrarymanagementsystem.model.Book;
import cam.onlinelibrarymanagementsystem.model.BorrowedBook;
import cam.onlinelibrarymanagementsystem.model.User;
import cam.onlinelibrarymanagementsystem.service.BookService;
import cam.onlinelibrarymanagementsystem.service.BorrowedBookService;
import cam.onlinelibrarymanagementsystem.service.UserDetailsServiceImp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;
    private final BorrowedBookService borrowedBookService;
    private final UserDetailsServiceImp userService;

    public AdminController(BookService bookService, BorrowedBookService borrowedBookService, UserDetailsServiceImp userService) {
        this.bookService = bookService;
        this.borrowedBookService = borrowedBookService;
        this.userService = userService;
    }

    // Book Management
    @PostMapping("/book")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/book/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/book/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @PutMapping("/book/stock/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Book updateBookStock(@PathVariable Long id, @RequestParam int stockChange) {
        return bookService.updateStock(id, stockChange);
    }
//eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJrdXRtYW5fbXVrYXJhcG92MjEiLCJpYXQiOjE3MzUzNzA5MjksImV4cCI6MTczNTQ1NzMyOX0.xUsoJ0Z0DveGVMj7109eDIbNLAjxt97BwtZn4wGZuAgWnL_4SXKni5TdtbwLoEAe
    // User Management
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

    // Borrowed Books Management
    @GetMapping("/borrowed-books")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookService.getAllBorrowedBooks();
    }

    @GetMapping("/borrowed-books/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<BorrowedBook> getBorrowedBooksByUser(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return borrowedBookService.getBorrowedBooksByUser(user);
    }

    @GetMapping("/overdue-books")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<BorrowedBook> getOverdueBooks() {
        return borrowedBookService.getOverdueBooks();
    }

    @GetMapping("/get-user-profile/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<User> getUserProfile(@PathVariable Integer userId) {
        return userService.getUserByID(userId);
    }
}
