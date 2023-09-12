package miniproject.onlinebookstore.controller;

import miniproject.onlinebookstore.entity.User;
import miniproject.onlinebookstore.exception.IdNotFoundException;
import miniproject.onlinebookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("user/register")
    public ResponseEntity<?> register (@RequestBody User user){
        return new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<?> getById (@PathVariable Long userId) throws IdNotFoundException {
        return ResponseEntity.ok(service.getUser(userId));
    }

}
