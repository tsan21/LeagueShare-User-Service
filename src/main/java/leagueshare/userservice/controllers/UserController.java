package leagueshare.userservice.controllers;

import leagueshare.userservice.entities.User;
import leagueshare.userservice.repos.UserRepo;
import leagueshare.userservice.rmq.MessagingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
public class UserController {

    private final UserRepo userRepo;
    private final RabbitTemplate template;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserRepo userRepo, RabbitTemplate template) {
        this.userRepo = userRepo;
        this.template = template;
    }


    @PostMapping("/")
    public ResponseEntity<?> create() {
        User user = new User("user","user@user.com","user","resu");
        userRepo.save(user);
        template.convertAndSend(MessagingConfig.topicExchangeName, MessagingConfig.routingKey, user);
        log.info("Created new user: " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "test123";
    }

}
