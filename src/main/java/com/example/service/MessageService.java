package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;
import java.util.List;

@Service
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    } 

    public Message getMessage(int id) {
        return messageRepository.findByMessageId(id);
    }

    public List<Message> getAllUserMessages(int id) {
        return messageRepository.findByPostedBy(id);
    }

    public Integer deleteMessage(int id){
        Message message = messageRepository.findById(id).orElse(null);
        if(message != null){
            messageRepository.deleteById(id);
            return 1;
        }
        return null;
    }

    public Integer updateMessage(int id, String message){
        Message searchMessage = messageRepository.findById(id).orElse(null);
        if(searchMessage != null){
            searchMessage.setMessageText(message);
            messageRepository.save(searchMessage);
            return 1;
        }
        return null;
    }
}
