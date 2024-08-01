package com.hitit.project.microservices.user_app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitit.project.microservices.user_app.dto.LoginRequest;
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
        System.out.println(loginRequest.getUsername());
        User user = userService.findByUsername(loginRequest.getUsername());
        System.out.println(user + "user entered");
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            System.out.println("Login successful!");
            HttpSession session = request.getSession();
            session.setAttribute("user", loginRequest.getUsername());
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password.");
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
    public ResponseEntity<User> getMethodName(@RequestParam String username) {
        
        User user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    
    
    
    @PostMapping({"/register", "/create_user"})
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        try {
            userService.save(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
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
