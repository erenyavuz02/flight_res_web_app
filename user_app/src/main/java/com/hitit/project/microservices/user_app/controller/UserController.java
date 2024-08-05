package com.hitit.project.microservices.user_app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitit.project.microservices.user_app.dto.LoginRequest;
import com.hitit.project.microservices.user_app.dto.UserDetails;
import com.hitit.project.microservices.user_app.entity.User;
import com.hitit.project.microservices.user_app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;







@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/trial")
    public String register() {
        return "trial";
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Optional<User> user = userService.findByUsername(loginRequest.getUsername());

        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("username", loginRequest.getUsername());
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }



    @GetMapping("/checkSession")
    public String getMethodName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            return "Logged in"; // Ensure this returns the name of your main page HTML file
        } else {
            return "Not logged in";
        }
    }


    /**
     * This method retrieves a user by their username.
     * 
     * it cannot be accessed by gateway. Only other microservices can access it.
     * 
     * 
     * @param username The username of the user to retrieve.
     */

    @GetMapping("/get")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        Optional<User> userOptional = userService.findByUsername(username);

        return userOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
  
    


    /**
     * This function validates if the sent user information exists
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @return ResponseEntity with the validation result
     */
    @GetMapping("/validate")
    public ResponseEntity<UserDetails> getMethodName(
        @RequestParam String username,
        @RequestParam String password
    ) {

        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password cannot be null");
        }

        
        UserDetails userDetails = userService.getUserDetails(username, password);


        System.out.println(userDetails);

        return ResponseEntity.ok(userDetails);
        // Check if the user is valid
        
    }
    
    
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user){
        try {
            userService.save(user);
            var successResponse = Map.of("message", "User registered successfully!");
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            var errorResponse = Map.of("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    


    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> delete(@RequestParam("userName") String userName) {
        try {
            userService.deleteByUsername(userName);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Deletion failed: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }
}
