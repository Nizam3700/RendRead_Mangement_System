package com.example.RentRead.Exchange.Request.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String message;
    private HttpStatus status;
}

