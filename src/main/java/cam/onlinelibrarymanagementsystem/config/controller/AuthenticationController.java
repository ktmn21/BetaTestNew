package cam.onlinelibrarymanagementsystem.config.controller;

import cam.onlinelibrarymanagementsystem.model.AuthenticationResponce;
import cam.onlinelibrarymanagementsystem.model.User;
import cam.onlinelibrarymanagementsystem.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponce> register(
            @RequestBody User request
            ){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponce> login(
            @RequestBody User request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }


}
