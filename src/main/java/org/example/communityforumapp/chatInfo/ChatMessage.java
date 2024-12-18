package org.example.communityforumapp.chatInfo;


import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "chatMessages")
@Data
public class ChatMessage {
    @Id
    private String id;
    private String sender;
    private String text;
    private LocalDateTime time;
    private int likes;
    private final List<ChatMessage> replies = new ArrayList<>();

    // Constructors, Getters, Setters, and ToString



    public void like() {
        this.likes++;
    }

    public void addReply(ChatMessage reply) {
        this.replies.add(reply);
    }

}
