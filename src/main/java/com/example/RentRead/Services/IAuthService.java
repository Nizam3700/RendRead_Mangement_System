package com.example.RentRead.Services;

import com.example.RentRead.Exceptions.UserAlreadyExistException;
import com.example.RentRead.Exceptions.UserNotFoundException;
import com.example.RentRead.Exchange.Request.LoginRequest;
import com.example.RentRead.Exchange.Request.Response.RentBookResponse;
import com.example.RentRead.Exchange.Request.UserRegisterRequest;
import com.example.RentRead.Models.User;

public interface IAuthService {
    public User registerUser(UserRegisterRequest request) throws UserAlreadyExistException;

    public String login(LoginRequest request);

    public RentBookResponse getUserById(Long id) throws UserNotFoundException;
}
