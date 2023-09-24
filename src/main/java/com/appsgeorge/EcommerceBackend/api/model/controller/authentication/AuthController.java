package com.appsgeorge.EcommerceBackend.api.model.controller.authentication;


import com.appsgeorge.EcommerceBackend.api.model.LoginInfo;
import com.appsgeorge.EcommerceBackend.api.model.LoginResponse;
import com.appsgeorge.EcommerceBackend.api.model.RegistrationInfo;
import com.appsgeorge.EcommerceBackend.exception.EmailFailException;
import com.appsgeorge.EcommerceBackend.exception.UserAlreadyExistsException;
import com.appsgeorge.EcommerceBackend.exception.UserNotVerifiedException;
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
        } catch (EmailFailException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginInfo loginInfo){
        String jwt = null;
        try {
            jwt = userService.loginUser(loginInfo);
        } catch (UserNotVerifiedException e) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setSuccess(false);
            String reason = "USER_NOT_VERIFIED";

            if(e.isNewEmailSent()){
                reason = "_EMAIL_RESENT";
            }
            loginResponse.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginResponse);
        } catch (EmailFailException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            LoginResponse loginResponse = new LoginResponse();

            loginResponse.setJwt(jwt);
            loginResponse.setSuccess(true);

            return ResponseEntity.ok(loginResponse);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token){
        if (userService.verifyUser(token)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/me")
    public LocalUser getLoggedInUser(@AuthenticationPrincipal LocalUser user){
        return user;

    }

}
