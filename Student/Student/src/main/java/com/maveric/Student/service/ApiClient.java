package com.maveric.Student.service;

import com.maveric.Student.model.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(url = "http://localhost:8080",value = "DEPARTMENT-SERVICE")
@FeignClient(name = "ADDRESS-SERVICE")

public interface ApiClient {
    @GetMapping("api/address/{id}")
    AddressResponse findAddress(@PathVariable Long id);
}
