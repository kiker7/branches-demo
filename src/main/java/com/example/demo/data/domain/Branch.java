package com.example.demo.data.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Branch {

    private Long branchId;

    private String branchName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Date openingTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Date closingTime;

    private List<Service> services;
}
