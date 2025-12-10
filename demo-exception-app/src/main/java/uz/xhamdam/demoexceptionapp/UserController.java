package uz.xhamdam.demoexceptionapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService svc;

  public UserController(UserService svc) { this.svc = svc; }

  @GetMapping("/{id}")
  public ResponseEntity<String> getUser(@PathVariable long id) {
    String email = svc.getUserEmail(id);
    return ResponseEntity.ok(email);
  }

  @PostMapping("/{id}")
  public ResponseEntity<Void> createUser(@PathVariable long id, @RequestParam String email) {
    svc.createUser(id, email);
    return ResponseEntity.status(201).build();
  }

}
