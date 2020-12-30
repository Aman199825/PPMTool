package com.example.demo.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MapErrorValidationService {
    public ResponseEntity<?> mapErrorService(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        if (result.hasErrors()) {
            List<FieldError> errorList = result.getFieldErrors();
            for (FieldError error : errorList) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return  new ResponseEntity<Map<String,String>>(errorMap, HttpStatus.BAD_REQUEST);

        }
            return null;

    }
}
