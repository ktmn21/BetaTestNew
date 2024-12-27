package cam.onlinelibrarymanagementsystem.config.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> demo(){
        return ResponseEntity.ok("Hello from secured url");
    }

    @GetMapping("/admin-only")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("Hello from admin only url");
    }
}
