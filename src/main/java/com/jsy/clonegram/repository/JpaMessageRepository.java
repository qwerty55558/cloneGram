package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface JpaMessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM message " +
            "WHERE (receiver_id = :receiverId AND sender_id = :senderId) OR (receiver_id = :senderId AND sender_id = :receiverId) " +
            "ORDER BY message_id DESC",
            nativeQuery = true)
    List<Message> findMessageByReceiverIdAndSenderId(@Param("receiverId") Long receiverId, @Param("senderId") Long senderId);

}
