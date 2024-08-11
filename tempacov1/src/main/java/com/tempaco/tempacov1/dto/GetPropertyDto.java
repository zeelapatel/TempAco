package com.tempaco.tempacov1.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

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
    private int bed;
    private double bath;
    private Date moveInDate;
    private Date moveOutDate;
    private byte[] photo;
}
