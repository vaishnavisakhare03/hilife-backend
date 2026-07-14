package com.example.hilife.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommitteeResponse {

    private Long id;
    private String position;

    private Long userId;
    private String userName;
    private String contactNumber;

    private Long photoId;
    private String photoUrl;

    private String tower;
    private String flatNumber;
    private String role;
}