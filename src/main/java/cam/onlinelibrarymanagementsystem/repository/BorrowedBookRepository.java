package cam.onlinelibrarymanagementsystem.repository;

import cam.onlinelibrarymanagementsystem.model.BorrowedBook;
import cam.onlinelibrarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findByUser(User user);
}
