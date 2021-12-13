package leagueshare.userservice.controllers;

import leagueshare.userservice.entities.User;
import leagueshare.userservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
public class UserController {

    private final UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public Iterable<User> all() {
        return userRepo.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<?> create() {
        User user = new User("user","user@user.com","user","resu");
        userRepo.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "test123";
    }

}
