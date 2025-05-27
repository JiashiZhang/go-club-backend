package com.goclub.xian.user.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, length = 36)
    private String uuid;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "real_name", nullable = false, length = 30)
    private String realName;

    @Column(name = "gender", length = 2)
    private String gender;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "id_card", length = 18)
    private String idCard;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "province", length = 30)
    private String province;

    @Column(name = "city", length = 30)
    private String city;

    @Column(name = "district", length = 30)
    private String district;

    @Column(name = "school_district", length = 30)
    private String schoolDistrict;

    @Column(name = "rank_level", length = 10)
    private String rankLevel; // "5k", "1d"等

    @Column(name = "dan_level")
    private Integer danLevel; // 段位整数，1=一段

    @Column(name = "level_code")
    private Integer levelCode; // 5=五级，-1=一段，-3=三段等，业务自动转化

    @Column(name = "role", length = 20)
    private String role; // "user", "admin"等

    @Column(name = "last_school_district_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSchoolDistrictUpdate;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "elo_rating")
    private int eloRating; // 当前 Elo 分数
}
