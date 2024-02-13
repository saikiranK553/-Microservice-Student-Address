package com.maveric.Address.service;

import com.maveric.Address.entity.Address;
import com.maveric.Address.model.Request;
import com.maveric.Address.model.Response;
import com.maveric.Address.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
    private final AddressRepository addressRepository;
    @Override
    public Response saveAddress(Request request) {
        Address address=new Address();
        address.setState(request.getState());
        address.setStreet(request.getStreet());
        Address savedAddress=addressRepository.save(address);
        return Response.builder().id(savedAddress.getId())
                .street(savedAddress.getStreet()).state(savedAddress.getState()).build();
    }

    @Override
    public Response finById(Long id) {
        Optional<Address> optionalAddress=addressRepository.findById(id);
        return Response.builder()
                .id(optionalAddress.get().getId())
                .street(optionalAddress.get().getStreet())
                .state(optionalAddress.get().getState())
                .build();
    }
}
