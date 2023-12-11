package com.example.repository;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;
public interface MessageRepository  extends JpaRepository<Message, Integer> {

    

    @Query("SELECT m FROM Message m WHERE m.posted_by = :postedBy")
    List<Message> findAllByPostedBy(@Param("postedBy") Integer postedBy);





}
