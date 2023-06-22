package vn.sunbuy.storyapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.sunbuy.storyapi.common.URI;

@RestController
@RequestMapping(URI.ADMIN)
public class AdminController {
    @GetMapping(URI.USERS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsers() {
        // TODO: implement logic to retrieve list of users
        return ResponseEntity.ok("List of users");
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        // TODO: implement logic to retrieve user by ID
        return ResponseEntity.ok("User with ID " + id);
    }
}