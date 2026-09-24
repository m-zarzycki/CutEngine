package com.cutengine.service;

import com.cutengine.dto.UserCreateRequest;
import com.cutengine.dto.UserResponse;
import com.cutengine.entity.Role;
import com.cutengine.entity.User;
import com.cutengine.exception.EmailAlreadyExistsException;
import com.cutengine.exception.ResourceNotFoundException;
import com.cutengine.exception.UsernameAlreadyExistsException;
import com.cutengine.mapper.UserMapper;
import com.cutengine.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse register(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(request.username());
        }

        User user = new User(
                request.firstName(),
                request.lastName(),
                request.username(),
                passwordEncoder.encode(request.password()),
                request.email(),
                request.phone(),
                Role.CUSTOMER
        );

        User saved = userRepository.save(user);
        return UserMapper.toResponse(saved);
    }

    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono uzytkownika o ID: " + id));

        return UserMapper.toResponse(user);
    }

    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono uzytkownika o nicku: " + username));

        return UserMapper.toResponse(user);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nie znaleziono uzytkownika o ID: " + id);
        }

        userRepository.deleteById(id);
    }
}
