package com.appsgeorge.EcommerceBackend.api.model.controller.authentication;


import com.appsgeorge.EcommerceBackend.api.model.LoginInfo;
import com.appsgeorge.EcommerceBackend.api.model.LoginResponse;
import com.appsgeorge.EcommerceBackend.api.model.RegistrationInfo;
import com.appsgeorge.EcommerceBackend.exception.UserAlreadyExistsException;
import com.appsgeorge.EcommerceBackend.model.LocalUser;
import com.appsgeorge.EcommerceBackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationInfo registrationInfo){
        try {
            userService.registerUser(registrationInfo);
            ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginInfo loginInfo){
        String jwt = userService.loginUser(loginInfo);

        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            LoginResponse loginResponse = new LoginResponse();

            loginResponse.setJwt(jwt);

            return ResponseEntity.ok(loginResponse);
        }
    }

    @GetMapping("/me")
    public LocalUser getLoggedInUser(@AuthenticationPrincipal LocalUser user){
        return user;

    }

}
