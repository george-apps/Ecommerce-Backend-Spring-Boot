package com.appsgeorge.EcommerceBackend.model.repository;

import com.appsgeorge.EcommerceBackend.model.LocalUser;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalUserRepo extends ListCrudRepository<LocalUser,Long> {

    Optional<LocalUser> findByUsernameIgnoreCase(String username);
    Optional<LocalUser> findByEmailIgnoreCase(String email);
}
