package com.example.hilife.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommitteeRequest {

    private String position;

    private Long userId;

    private Long photoId;
}