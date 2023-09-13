package miniproject.onlinebookstore.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BookReviewDto {
    private String reviewText;
    @Min(value = 0, message = "Can not be lower than 1")
    @Max(value = 10, message = "Can not be higher than 10")
    private int rating;
    private Long userId;
}
