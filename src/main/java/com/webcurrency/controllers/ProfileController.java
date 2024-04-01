package com.webcurrency.controllers;

import com.webcurrency.dto.UserResponse;
import com.webcurrency.services.UserService;
import com.webcurrency.utils.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("profile")
@RequiredArgsConstructor
@Tag(name = "Профиль")
public class ProfileController {
    private final UserService userService;
    private final Converter converter;

    @GetMapping("/{id}")
    @Operation(summary = "Получение данных пользователя")
    public UserResponse getUserData(@PathVariable("id") UUID id) {
        return converter.convertToUserResponse(userService.getById(id));
    }
}
