package miniproject.onlinebookstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;
    private int publicationYear;

    @Enumerated(EnumType.STRING)
    private BookStatus status; // AVAILABLE, BORROWED, RESERVED}

    private boolean deleted = false;
}
