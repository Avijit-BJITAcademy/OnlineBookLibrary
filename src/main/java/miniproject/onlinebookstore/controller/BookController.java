package miniproject.onlinebookstore.controller;

import miniproject.onlinebookstore.dto.BookDto;
import miniproject.onlinebookstore.entity.UserHistory;
import miniproject.onlinebookstore.exception.BookNotAvailableException;
import miniproject.onlinebookstore.exception.IdNotFoundException;
import miniproject.onlinebookstore.exception.NoReservationException;
import miniproject.onlinebookstore.service.BookOperationService;
import miniproject.onlinebookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class BookController {
    private final BookService service;
    private final BookOperationService bookOperationService;

    private BookController(BookService service, BookOperationService bookOperationService) {
        this.service = service;
        this.bookOperationService = bookOperationService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody BookDto bookDto){
        return ResponseEntity.ok(service.createBook(bookDto));
    }
    @PutMapping("/update")
    public ResponseEntity<?> update (@RequestBody BookDto bookDto) throws Exception {
        return ResponseEntity.ok(service.update(bookDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete (@RequestParam Long id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll (){
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<UserHistory> borrow (@PathVariable Long bookId, @RequestParam Long userId) throws BookNotAvailableException, IdNotFoundException {
        return new ResponseEntity<>(bookOperationService.borrowBook(bookId, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}/return")
    public ResponseEntity<UserHistory> returnBook (@PathVariable Long bookId) throws BookNotAvailableException, IdNotFoundException {
        return new ResponseEntity<>(bookOperationService.returnBook(bookId), HttpStatus.OK);
    }

    @PostMapping("/{bookId}/reserve")
    public ResponseEntity<UserHistory> reserve (@PathVariable Long bookId, @RequestParam Long userId) throws BookNotAvailableException, IdNotFoundException, NoReservationException {
        return new ResponseEntity<>(bookOperationService.reserve(bookId, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}/cancel-reservation")
    public ResponseEntity<UserHistory> cancelReservation (@PathVariable Long bookId, @RequestParam Long userId) throws BookNotAvailableException, IdNotFoundException, NoReservationException {
        return new ResponseEntity<>(bookOperationService.cancelReservation(bookId, userId), HttpStatus.OK);
    }

}
