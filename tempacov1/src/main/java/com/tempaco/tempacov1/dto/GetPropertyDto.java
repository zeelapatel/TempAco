package com.tempaco.tempacov1.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class GetPropertyDto {
    private long id;
    private String title;
    private String description;
    private String address;
    private String zip;
    private double price;
    private byte[] photo;
}
