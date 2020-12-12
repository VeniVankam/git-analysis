package com.githubanalysis.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String loginId;
    private String name;
    private String email;
    private String image;
    private String location;
}
