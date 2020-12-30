package com.example.demo.web;

import com.example.demo.Validator.UserValidator;
import com.example.demo.domain.User;
import com.example.demo.payload.LoginRequest;
import com.example.demo.payload.LoginResponse;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.services.MapErrorValidationService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.demo.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    MapErrorValidationService mapErrorValidationService;
    @Autowired
    UserService userService;
    @PostMapping("/post/login")
    public ResponseEntity<?> authenticateUser(@Valid  @ RequestBody  LoginRequest loginRequest,BindingResult result){
        ResponseEntity<?> errorMap=mapErrorValidationService.mapErrorService(result);
        if(errorMap!=null)
            return errorMap;
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=TOKEN_PREFIX+jwtTokenProvider.generateToken(authentication);
        return  ResponseEntity.ok(new LoginResponse(true,jwt));
    }
    @PostMapping("/post/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User newUser, BindingResult result){
        userValidator.validate(newUser,result);
      ResponseEntity<?> errorMap=mapErrorValidationService.mapErrorService(result);
      if(errorMap!=null)
          return errorMap;
      newUser=userService.saveUser(newUser);
      return  new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }
}
