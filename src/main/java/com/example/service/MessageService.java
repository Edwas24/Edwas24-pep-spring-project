package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;
    @Autowired
    public MessageService( MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    public boolean isUserInDatabase(Integer posted_by) {
      List<Message> messages = messageRepository.findAllByPostedBy(posted_by);
      return !messages.isEmpty();
  }

    public Message creatMessage(Integer posted_by, String message_text, Long time_posted_epoch) {
        
      if(  !isUserInDatabase(posted_by)){
        return null;
      }
      else {
           
        if (message_text.length() == 0|| message_text.length() > 254) {
          return null;
      }

        
        Message newMessage = new Message( posted_by, message_text, time_posted_epoch);

     
        return messageRepository.save(newMessage);
      }
    }
    public int deleteMessageByMessageId(Integer id) {
      int rows = 0;
      Optional<Message> optionalMessage = messageRepository.findById(id);
  
      if (optionalMessage.isPresent()) {
          messageRepository.deleteById(id);
          rows += 1;
          return rows; 
      } else {
          return rows; 
      }
  }
     public List<Message> getAllmessages(){
            return messageRepository.findAll();
        }
     public List<Message> getAllMessagesByAccountId(Integer accountId) {
          return messageRepository.findAllByPostedBy(accountId);
      }
      public Message getmessageById(Integer id){
        
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }else{
            return null;
        }
    }
    public int updateMessage(Integer id, String replacement){
      int rows = 0;       
      Optional<Message> optionalMessage = messageRepository.findById(id);
      if(optionalMessage.isPresent()){
          Message msg = optionalMessage.get();
          msg.setMessage_text(replacement);
          rows +=1;
          return rows;
      }

      return rows;


      
  }

}
