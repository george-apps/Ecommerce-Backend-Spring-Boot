package com.appsgeorge.EcommerceBackend.api.model.user;

import com.appsgeorge.EcommerceBackend.model.Address;
import com.appsgeorge.EcommerceBackend.model.LocalUser;
import com.appsgeorge.EcommerceBackend.model.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AddressRepo addressRepo;

    @GetMapping("/{id}/address")
    public ResponseEntity<List<Address>> getAddress(@AuthenticationPrincipal LocalUser user, @PathVariable Long id){
        if (!userHasPermission(user,id))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(addressRepo.findByUser_Id(id));

    }


    @PutMapping("/{id}/address")
    public ResponseEntity<Address> putAddress(@AuthenticationPrincipal LocalUser user,@PathVariable Long id, @RequestBody Address address){
        if (!userHasPermission(user,id))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        address.setId(null);
        LocalUser tmpUser = new LocalUser();
        tmpUser.setId(id);
        address.setUser(tmpUser);
        return ResponseEntity.ok(addressRepo.save(address));

    }

    @PatchMapping("/{userId}/address/{addressId}")
    public ResponseEntity<Address> patchAddress(@AuthenticationPrincipal LocalUser user,@PathVariable Long userId,@RequestBody Address address,@PathVariable Long addressId){
        if (!userHasPermission(user,userId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        if (address.getId() == addressId){
            Optional<Address> originalAddress = addressRepo.findById(addressId);

            if(originalAddress.isPresent()){
                LocalUser originalUser = originalAddress.get().getUser();
                if (originalUser.getId() == userId){
                    address.setUser(originalUser);
                    return ResponseEntity.ok(addressRepo.save(address));
                }
            }
        }

        return ResponseEntity.badRequest().build();

    }

    private boolean userHasPermission(LocalUser user,Long id){
        return user.getId() == id;
    }


}
