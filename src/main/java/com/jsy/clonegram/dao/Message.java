package com.jsy.clonegram.dao;

import com.jsy.clonegram.repository.listener.MessageListener;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@EntityListeners(MessageListener.class)
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    Long Id;
    Long senderId;
    Long receiverId;
    String content;
}

