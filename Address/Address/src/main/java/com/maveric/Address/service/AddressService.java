package com.maveric.Address.service;

import com.maveric.Address.model.Request;
import com.maveric.Address.model.Response;

public interface AddressService {
    Response saveAddress(Request request);
    Response finById(Long id);
}
