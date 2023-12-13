package ru.petapp.taskmenagementsystem.taskmenagment.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.petapp.taskmenagementsystem.taskmenagment.dto.LoginDTO;
import ru.petapp.taskmenagementsystem.taskmenagment.dto.UserDTO;
import ru.petapp.taskmenagementsystem.taskmenagment.model.User;
import ru.petapp.taskmenagementsystem.taskmenagment.service.UserService;
import ru.petapp.taskmenagementsystem.taskmenagment.service.userdetails.CustomUserDetailsService;
import ru.petapp.taskmenagementsystem.taskmenagment.utils.JWTTokenUtil;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/users")
@Tag(name = "Пользователи",
       description = "контроллер для работы с пользователями")

@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController extends GenericController<User, UserDTO> {
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenUtil jwtTokenUtil;
    private final UserService userService;

    public UserController(UserService userService,
                          CustomUserDetailsService customUserDetailsService,
                          JWTTokenUtil jwtTokenUtil) {
        super(userService);
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody LoginDTO loginDTO) {
        Map<String, Object> response = new HashMap<>();
        log.info("LoginDTO: {}", loginDTO);
        UserDetails foundUser = customUserDetailsService.loadUserByUsername(loginDTO.getLogin());
        log.info("foundUser, {}", foundUser);
        if (!userService.checkPassword(loginDTO.getPassword(), foundUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка авторизации!\nНеверный пароль");
        }
        String token = jwtTokenUtil.generateToken(foundUser);
        response.put("token", token);
        response.put("username", foundUser.getUsername());
        response.put("authorities", foundUser.getAuthorities());
        return ResponseEntity.ok().body(response);
    }


}
