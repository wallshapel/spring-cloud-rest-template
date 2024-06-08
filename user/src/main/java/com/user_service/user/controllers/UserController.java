package com.user_service.user.controllers;

import com.user_service.user.entities.User;
import com.user_service.user.models.Car;
import com.user_service.user.models.Moto;
import com.user_service.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<User>> index() {
        List<User> users = userService.index();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<User> show(@PathVariable("id") Long id) {
        User user = userService.show(id);
        if (user == null)
            ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<User> store(@RequestBody User user) {
        User newUser = userService.store(user);
        URI location = URI.create("/users/" + newUser.getId());
        return ResponseEntity.created(location).build(); // Devuelve en los headers una llave llamada Location cuyo valor es la url para acceder al recurso creado
    }

    @GetMapping("cars/{userId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Car>>getCarsByUserId(@PathVariable Long userId) {
        User user = userService.show(userId);
        if (user == null)
            return ResponseEntity.notFound().build();
        List<Car>cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("motos/{userId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Moto>>getMotosByUserId(@PathVariable Long userId) {
        User user = userService.show(userId);
        if (user == null)
            return ResponseEntity.notFound().build();
        List<Moto>motos = userService.getMotos(userId);
        return ResponseEntity.ok(motos);
    }
}
