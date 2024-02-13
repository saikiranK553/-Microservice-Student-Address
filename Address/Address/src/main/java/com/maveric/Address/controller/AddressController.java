package com.maveric.Address.controller;

import com.maveric.Address.model.Request;
import com.maveric.Address.model.Response;
import com.maveric.Address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;

    @PostMapping
    public ResponseEntity<Response> createAddress(@RequestBody Request request){
        Response response=service.saveAddress(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> findAddress(@PathVariable Long id){
        Response response=service.finById(id);
        return ResponseEntity.ok().body(response);
    }
}
