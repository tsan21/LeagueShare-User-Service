package leagueshare.userservice.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import leagueshare.userservice.entities.User;
import leagueshare.userservice.repos.UserRepo;
import leagueshare.userservice.rmq.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.Queue;
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

    @RabbitListener(queuesToDeclare = @Queue(name = "user-queue", durable = "true"))
    public void receiveMsg(String message){
        create(message);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(String message) {
        JsonObject jobj = new Gson().fromJson(message, JsonObject.class);
        String details = jobj.get("details").toString();

        JsonObject detailsJobj = new Gson().fromJson(details, JsonObject.class);
        String userName = detailsJobj.get("username").toString().replaceAll("\"", "");
        String email = detailsJobj.get("email").toString().replaceAll("\"", "");
        String firstName = detailsJobj.get("first_name").toString().replaceAll("\"", "");
        String lastName = detailsJobj.get("last_name").toString().replaceAll("\"", "");
        String keycloakId = jobj.get("userId").toString().replaceAll("\"", "");

        User user = new User(keycloakId,userName,email,firstName,lastName);
        userRepo.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/")
    public Iterable<User> all() {
        return userRepo.findAll();
    }

    @GetMapping("/test")
    public String test() {
        return "test12345";
    }

}
