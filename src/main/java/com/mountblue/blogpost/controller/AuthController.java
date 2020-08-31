package com.mountblue.blogpost.controller;

import com.mountblue.blogpost.dto.LoginDto;
import com.mountblue.blogpost.dto.RegisterDto;
import com.mountblue.blogpost.dto.ResponseStatusDto;
import com.mountblue.blogpost.service.AuthorService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthorService authService;


    @PostMapping("/register")
    public ResponseEntity<ResponseStatusDto> signUp(@RequestBody RegisterDto registerDto) throws JSONException {

       boolean status = authService.register(registerDto);

        ResponseStatusDto responseStatusDto =new ResponseStatusDto();
        if (status==false) responseStatusDto.setStatus("Registration Successful");
        else{
            responseStatusDto.setStatus("User Already Registered");
        }
        return new ResponseEntity(responseStatusDto,HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

}
