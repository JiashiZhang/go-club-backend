package com.goclub.xian.user.models.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String uuid;
    private String username;
    private String realName;
    private String gender;
    private String province;
    private String city;
    private String district;
    private String schoolDistrict;
    private String rankLevel;
    private Integer danLevel;
    private String role;
    // 敏感信息
    private String phone;
    private String email;
    private String idCard;
}
