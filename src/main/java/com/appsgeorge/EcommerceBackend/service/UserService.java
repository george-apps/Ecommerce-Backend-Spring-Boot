package com.appsgeorge.EcommerceBackend.service;


import com.appsgeorge.EcommerceBackend.api.model.RegistrationInfo;
import com.appsgeorge.EcommerceBackend.exception.UserAlreadyExistsException;
import com.appsgeorge.EcommerceBackend.model.LocalUser;
import com.appsgeorge.EcommerceBackend.model.repository.LocalUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private LocalUserRepo localUserRepo;

    public LocalUser registerUser(RegistrationInfo registrationInfo) throws UserAlreadyExistsException {

        if (localUserRepo.findByEmailIgnoreCase(registrationInfo.getEmail()).isPresent() ||
                localUserRepo.findByUsernameIgnoreCase(registrationInfo.getUsername()).isPresent())
            throw new UserAlreadyExistsException();

        LocalUser user = new LocalUser();
        user.setUsername(registrationInfo.getUsername());
        user.setEmail(registrationInfo.getEmail());
        user.setFirstName(registrationInfo.getFirstName());
        user.setLastName(registrationInfo.getLastName());
        //TODO: Encrypt password
        user.setPassword(registrationInfo.getPassword());
        return localUserRepo.save(user);

    }
}
