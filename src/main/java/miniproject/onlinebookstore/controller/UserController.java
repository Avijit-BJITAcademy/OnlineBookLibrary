package miniproject.onlinebookstore.controller;

import miniproject.onlinebookstore.entity.User;
import miniproject.onlinebookstore.exception.IdNotFoundException;
import miniproject.onlinebookstore.service.BookOperationService;
import miniproject.onlinebookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService service;
    private final BookOperationService bookOperationService;

    public UserController(UserService service, BookOperationService bookOperationService) {
        this.service = service;
        this.bookOperationService = bookOperationService;
    }

    @PostMapping("user/register")
    public ResponseEntity<?> register (@RequestBody User user){
        return new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<?> getById (@PathVariable Long userId) throws IdNotFoundException {
        return ResponseEntity.ok(service.getUser(userId));
    }

    @GetMapping("users/{userId}/history")
    public ResponseEntity<?> getUserHistory(@PathVariable Long userId){
        return ResponseEntity.ok(bookOperationService.getHistoryByUserId(userId));
    }

    @GetMapping("users/{userId}/books")
    public ResponseEntity<?> getBooksByUser (@PathVariable Long userId){
        return new ResponseEntity<>(service.getPreviouslyBorrowedBooksByUser(userId), HttpStatus.OK);
    }

    @GetMapping("users/{userId}/borrowed-book")
    public ResponseEntity<?> getCurrentlyBorrowedBooksByUser (@PathVariable Long userId){
        return new ResponseEntity<>(service.getCurrentlyBorrowedBooksByUser(userId), HttpStatus.OK);
    }

}
