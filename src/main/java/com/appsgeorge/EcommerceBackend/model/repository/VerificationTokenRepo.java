package com.appsgeorge.EcommerceBackend.model.repository;

import com.appsgeorge.EcommerceBackend.model.LocalUser;
import com.appsgeorge.EcommerceBackend.model.VerificationToken;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface VerificationTokenRepo extends ListCrudRepository<VerificationToken,Long> {


    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser user);
}
