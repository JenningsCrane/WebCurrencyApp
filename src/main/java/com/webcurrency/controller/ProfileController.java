package com.webcurrency.controller;

import com.webcurrency.dto.UserResponse;
import com.webcurrency.service.UserService;
import com.webcurrency.utils.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
@RequiredArgsConstructor
@Tag(name = "Профиль")
public class ProfileController {
    private final UserService userService;
    private final Converter converter;

    @GetMapping("/{id}")
    @Operation(summary = "Получение данных пользователя")
    public UserResponse getData(@PathVariable("id") Long id) {
        return converter.convertToUserResponse(userService.getById(id));
    }
}
