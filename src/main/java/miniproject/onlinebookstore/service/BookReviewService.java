package miniproject.onlinebookstore.service;

import miniproject.onlinebookstore.dto.BookReviewDto;
import miniproject.onlinebookstore.dto.BookReviewResponse;
import miniproject.onlinebookstore.entity.BookReview;
import miniproject.onlinebookstore.exception.BookNotAvailableException;
import miniproject.onlinebookstore.exception.IdNotFoundException;
import miniproject.onlinebookstore.repository.BookRepository;
import miniproject.onlinebookstore.repository.BookReviewRepository;
import miniproject.onlinebookstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BookReviewService {
    private final BookReviewRepository repository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public BookReviewService(BookReviewRepository repository, BookRepository bookRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public BookReviewResponse createReview (Long bookId, BookReviewDto bookReviewDto) throws BookNotAvailableException, IdNotFoundException {
        BookReview bookReview = null;
        if (bookRepository.existsById(bookId)){
            if (userRepository.existsById(bookReviewDto.getUserId())){
                if (repository.findByBookIdAndUserId(bookId, bookReviewDto.getUserId()) == null){
                    bookReview = modelMapper.map(bookReviewDto, BookReview.class);
                }else throw new BookNotAvailableException("You have already reviewed.");
            } else throw new IdNotFoundException("User not found.");
        }else throw new IdNotFoundException("Book not found.");
        if (bookReview == null){
            throw new BookNotAvailableException("Review creation failed. Try again.");
        }else return modelMapper.map(bookReview, BookReviewResponse.class);
    }
}
