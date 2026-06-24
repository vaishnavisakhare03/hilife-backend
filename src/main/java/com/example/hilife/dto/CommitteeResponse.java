package com.example.hilife.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommitteeResponse {

    private Long id;

    private String position;

    private Long userId;

    private String userName;

    private String contactNumber;

    private Long photoId;

    private String photoUrl;
}