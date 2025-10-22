package dev.mashni.tarefinhas.controller;

import dev.mashni.tarefinhas.model.User;
import dev.mashni.tarefinhas.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/post/user")
    public ResponseEntity<Void> postUser(@RequestBody User user){
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/user{email}")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email){
        userService.getUserByEmail(email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/user{email}")
    public ResponseEntity<Void> deleteUserByEmail(@RequestParam String email){
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/put/user{email}")
    public ResponseEntity<Void> updateUserByEmail(@RequestParam String email, @RequestBody User user){
        userService.updateUserByEmail(email, user);
        return ResponseEntity.ok().build();
    }

}
