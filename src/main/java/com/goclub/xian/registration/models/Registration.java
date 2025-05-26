package com.goclub.xian.registration.models;

import com.goclub.xian.tournament.models.Tournament;
import com.goclub.xian.user.models.User;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    private Timestamp registerTime;

    // 新加字段
    private String remark;
}
