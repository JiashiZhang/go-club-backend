package com.goclub.xian.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 36)
    private String uuid;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String realName;

    private String gender;
    private Date birthDate;

    @Column(length = 18)
    private String idCard;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    private String province;
    private String city;
    private String district;
    private String schoolDistrict;
    private String rankLevel;
    private Integer danLevel;
    private String role;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSchoolDistrictUpdate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private int eloRating;
}
