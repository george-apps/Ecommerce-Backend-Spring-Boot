package com.appsgeorge.EcommerceBackend.service;


import com.appsgeorge.EcommerceBackend.api.model.LoginInfo;
import com.appsgeorge.EcommerceBackend.api.model.RegistrationInfo;
import com.appsgeorge.EcommerceBackend.exception.UserAlreadyExistsException;
import com.appsgeorge.EcommerceBackend.model.LocalUser;
import com.appsgeorge.EcommerceBackend.model.repository.LocalUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private LocalUserRepo localUserRepo;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private JwtService jwtService;

    public LocalUser registerUser(RegistrationInfo registrationInfo) throws UserAlreadyExistsException {

        if (localUserRepo.findByEmailIgnoreCase(registrationInfo.getEmail()).isPresent() ||
                localUserRepo.findByUsernameIgnoreCase(registrationInfo.getUsername()).isPresent())
            throw new UserAlreadyExistsException();

        LocalUser user = new LocalUser();
        user.setUsername(registrationInfo.getUsername());
        user.setEmail(registrationInfo.getEmail());
        user.setFirstName(registrationInfo.getFirstName());
        user.setLastName(registrationInfo.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationInfo.getPassword()));
        return localUserRepo.save(user);

    }

    public String loginUser(LoginInfo loginInfo){
        Optional<LocalUser> user = localUserRepo.findByUsernameIgnoreCase(loginInfo.getUsername());

        if (user.isPresent()){
            LocalUser localUser = user.get();
            if(encryptionService.verifyPassword(loginInfo.getPassword(),localUser.getPassword())){
                return jwtService.generateJwt(localUser);
            }
        }
        return null;
    }



}
