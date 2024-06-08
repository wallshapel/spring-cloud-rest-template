package com.user_service.user.services;

import com.user_service.user.entities.User;
import com.user_service.user.models.Car;
import com.user_service.user.models.Moto;
import com.user_service.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate; // Necesario para comunicarse con otros microservicios

    // Método para devolver los carros de un usuario por su id
    public List<Car> getCars(Long userId) {
        String url = "http://localhost:8081/api/v1/cars/user/" + userId;
        ResponseEntity<List<Car>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Car>>() {}
        );
        return response.getBody();
    }

    // Método para devolver las motos de un usuario por su id
    public List<Moto> getMotos(Long userId) {
        String url = "http://localhost:8082/api/v1/motos/user/" + userId;
        ResponseEntity<List<Moto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Moto>>() {}
        );
        return response.getBody();
    }

    @Autowired
    private UserRepository userRepository;

    public List<User>index() {
        return userRepository.findAll();
    }

    public User show(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User store(User user) {
        return userRepository.save(user);
    }

}
