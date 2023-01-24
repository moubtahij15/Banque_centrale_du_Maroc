package com.example.demo.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserInscri {
    private String recruteur;
    private MultipartFile file;
}
