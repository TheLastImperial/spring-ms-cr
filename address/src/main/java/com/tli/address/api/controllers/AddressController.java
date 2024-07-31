package com.tli.address.api.controllers;

import com.tli.address.api.models.request.AddressRequest;
import com.tli.address.api.models.request.UpdateAddressRequest;
import com.tli.address.domain.entities.AddressEntity;
import com.tli.address.services.AddressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@CrossOrigin(originPatterns = "*")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/addresses")
public class AddressController {
    private final AddressService addressService;

    @GetMapping(path = "{id}")
    public ResponseEntity<AddressEntity> show(@Valid @PathVariable Long id){
        return ResponseEntity.ok(addressService.read(id));
    }

    @PostMapping
    public ResponseEntity<AddressEntity> create(@Valid @RequestBody AddressRequest address, UriComponentsBuilder ucb){
        AddressEntity addressSaved = addressService.create(address);
        URI location = ucb
                .path("addresses/{id}")
                .buildAndExpand(addressSaved.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(addressSaved);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AddressEntity> update(@Valid @RequestBody UpdateAddressRequest rq, @PathVariable Long id){
        AddressEntity ad = addressService.update(rq, id);
        return ResponseEntity.ok(ad);
    }
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
