package com.jsy.clonegram.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
public class Notifications {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notifications_id")
    Long Id;
    Long userId;
    String message;
    @Temporal(TemporalType.TIMESTAMP)
    Timestamp readAt;
    byte type;
    Long tid;
}
