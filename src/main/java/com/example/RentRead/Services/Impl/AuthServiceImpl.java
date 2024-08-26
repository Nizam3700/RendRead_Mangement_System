package com.example.RentRead.Services.Impl;

import com.example.RentRead.Enum.Role;
import com.example.RentRead.Exceptions.UserAlreadyExistException;
import com.example.RentRead.Exceptions.UserNotFoundException;
import com.example.RentRead.Exchange.Request.LoginRequest;
import com.example.RentRead.Exchange.Request.Response.RentBookResponse;
import com.example.RentRead.Exchange.Request.UserRegisterRequest;
import com.example.RentRead.Models.User;
import com.example.RentRead.Respository.UserRepository;
import com.example.RentRead.Services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User registerUser(UserRegisterRequest request) throws UserAlreadyExistException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistException("User is already register");
        }
        if (request.getRole() == null) {
            request.setRole(Role.USER);
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        return userRepository.save(user);

    }


    @Override
    public String login(LoginRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));;
        return "User Logged in successfully!";
    }

    @Override
    public RentBookResponse getUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findUserWithRentedBooksById(id)
                .orElseThrow(() -> new UserNotFoundException("User is not present"));
        return RentBookResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .books(user.getBooks())
                .role(user.getRole())
                .build();
    }
}

