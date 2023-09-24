package com.appsgeorge.EcommerceBackend.service;


import com.appsgeorge.EcommerceBackend.api.model.LoginInfo;
import com.appsgeorge.EcommerceBackend.api.model.RegistrationInfo;
import com.appsgeorge.EcommerceBackend.exception.EmailFailException;
import com.appsgeorge.EcommerceBackend.exception.UserAlreadyExistsException;
import com.appsgeorge.EcommerceBackend.exception.UserNotVerifiedException;
import com.appsgeorge.EcommerceBackend.model.LocalUser;
import com.appsgeorge.EcommerceBackend.model.VerificationToken;
import com.appsgeorge.EcommerceBackend.model.repository.LocalUserRepo;
import com.appsgeorge.EcommerceBackend.model.repository.VerificationTokenRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private LocalUserRepo localUserRepo;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;

    public LocalUser registerUser(RegistrationInfo registrationInfo) throws UserAlreadyExistsException, EmailFailException {

        if (localUserRepo.findByEmailIgnoreCase(registrationInfo.getEmail()).isPresent() ||
                localUserRepo.findByUsernameIgnoreCase(registrationInfo.getUsername()).isPresent())
            throw new UserAlreadyExistsException();

        LocalUser user = new LocalUser();
        user.setUsername(registrationInfo.getUsername());
        user.setEmail(registrationInfo.getEmail());
        user.setFirstName(registrationInfo.getFirstName());
        user.setLastName(registrationInfo.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationInfo.getPassword()));

        VerificationToken verificationToken = createVerificationToken((user));
        emailService.sendVerificationMailMessage(verificationToken);

        return localUserRepo.save(user);

    }

    private VerificationToken createVerificationToken(LocalUser user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedAtTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    public String loginUser(LoginInfo loginInfo) throws UserNotVerifiedException, EmailFailException {
        Optional<LocalUser> user = localUserRepo.findByUsernameIgnoreCase(loginInfo.getUsername());

        if (user.isPresent()){
            LocalUser localUser = user.get();
            if(encryptionService.verifyPassword(loginInfo.getPassword(),localUser.getPassword())){
                if(localUser.isEmailVerified()){
                    return jwtService.generateJwt(localUser);

                }else {
                    List<VerificationToken> verificationTokens = user.get().getVerificationTokens();
                    boolean resend = verificationTokens.isEmpty() || verificationTokens.get(0).getCreatedAtTimestamp().before(new Timestamp(System.currentTimeMillis() - 60 * 60 * 1000));

                    if (resend){
                        VerificationToken verificationToken = createVerificationToken(localUser);
                        verificationTokenRepo.save(verificationToken);
                        emailService.sendVerificationMailMessage(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    @Transactional
    public boolean verifyUser(String token){
        Optional<VerificationToken> opToken = verificationTokenRepo.findByToken(token);
        if (opToken.isPresent()){
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if(!user.isEmailVerified()){
                user.setEmailVerified(true);
                localUserRepo.save(user);
                verificationTokenRepo.deleteByUser(user);
                return true;
            }
        }
        return false;
    }
}
