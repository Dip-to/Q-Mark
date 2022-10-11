package com.example.q_mark.Model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class ChatMessage {
    public String senderId,receiverId, message, dateTime;
    public Date dateObject;
}
