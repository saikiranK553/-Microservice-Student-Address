package com.maveric.Student.service;

import com.maveric.Student.entity.Student;
import com.maveric.Student.model.AddressResponse;
import com.maveric.Student.model.Request;
import com.maveric.Student.model.Response;
import com.maveric.Student.repository.StudentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final ApiClient apiClient;
    @Override
    public Response saveStudent(Request request) {
        Student student=new Student();
        student.setName(request.getName());
        student.setAddressId(request.getAddressId());
        //AddressResponse response= restTemplate.getForObject("http://localhost:8080/api/address/"+request.getAddressId(), AddressResponse.class);
//        AddressResponse response = webClient.get()
//                .uri("http://localhost:8080/api/address/"+request.getAddressId())
//                .retrieve()
//                .bodyToMono(AddressResponse.class)
//                .block();
        AddressResponse response= apiClient.findAddress(request.getAddressId());

        if(Objects.isNull(response)){
            throw new EntityNotFoundException("no address found with address Id "+request.getAddressId());
        }
        Student savedStudent= studentRepository.save(student);
        return Response.builder()
                .id(savedStudent.getId())
                .name(savedStudent.getName())
                .addressResponse(response)
                .build();
    }
    @CircuitBreaker(name = "${spring.application.name}",fallbackMethod = "getDefaultAddress")
    @Override
    public Response findStudent(Long id) {
        Optional<Student> optionalStudent=studentRepository.findById(id);
        if(!optionalStudent.isPresent()){
            throw new EntityNotFoundException("no student found with student id "+id);
        }
        Student student=optionalStudent.get();
        AddressResponse response= apiClient.findAddress(student.getAddressId());

        if(Objects.isNull(response)){
            throw new EntityNotFoundException("no address found with address Id "+student.getAddressId());
        }
        return Response.builder()
                .id(student.getId())
                .name(student.getName())
                .addressResponse(response)
                .build();
    }

    public Response getDefaultAddress(Long id,Throwable throwable) {
        Optional<Student> optionalStudent=studentRepository.findById(id);
        if(!optionalStudent.isPresent()){
            throw new EntityNotFoundException("no student found with student id "+id);
        }
        Student student=optionalStudent.get();
        AddressResponse response=AddressResponse
                .builder()
                .id(0)
                .street("ramapuram")
                .state("Tamilnadu")
                .build();
        return Response.builder()
                .id(student.getId())
                .name(student.getName())
                .addressResponse(response)
                .build();
    }
}
