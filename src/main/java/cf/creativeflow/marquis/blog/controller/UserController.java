package cf.creativeflow.marquis.blog.controller;

import cf.creativeflow.marquis.blog.domain.User;
import cf.creativeflow.marquis.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    //temp line
    private ResponseEntityExceptionHandler responseEntityExceptionHandler;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity createNewUser(@Validated @RequestBody User newUser) {

        if (newUser == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(userRepository.save(newUser), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<User> getAllUsers() {

        return new ResponseEntity(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long id) {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(user, HttpStatus.OK);
    }
}
