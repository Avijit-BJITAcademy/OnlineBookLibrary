package miniproject.onlinebookstore.repository;

import miniproject.onlinebookstore.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
    BookReview findByBookIdAndUserId (Long bookId, Long userId);
}
