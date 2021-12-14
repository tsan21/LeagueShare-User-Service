package leagueshare.userservice.controllers;

import leagueshare.userservice.entities.User;
import leagueshare.userservice.repos.UserRepo;
import leagueshare.userservice.rmq.MessagingConfig;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
public class UserController {

    private final UserRepo userRepo;
    private final MessagingConfig msgConfig;

    @Autowired
    public UserController(UserRepo userRepo, MessagingConfig msgConfig) {
        this.userRepo = userRepo;
        this.msgConfig = msgConfig;
    }

    @RabbitListener(queues = "user-queue")
    public void receiveMsg(String message){

        System.out.println(message);
        create(message);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(String message) {
        User user = new User("user","user@user.com","user","resu");
        userRepo.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/")
    public Iterable<User> all() {
        return userRepo.findAll();
    }

    @GetMapping("/test")
    public String test() {
        return "test123";
    }

}
