package com.tempaco.tempacov1.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SavePropertyResult {
    private boolean error;
    private String title;
    private String description;
    private String address;
    private String zip;
    private double price;
    private int bed;
    private double bath;
    private Date moveInDate;
    private Date moveOutDate;
    private Long userId;

    private String link;
}
