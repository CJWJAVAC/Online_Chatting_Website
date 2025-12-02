package com.example.chatApp.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

@Getter
@Setter
public class ChatMessage {

//    public enum MessageType{
//        ENTER, TALK;
//    }
//
//    private MessageType type;
//    private String roomId;
//    private String sender;
//    private String message;

    public ChatMessage(){

    }

    @Builder
    public ChatMessage(MessageType type, String roomId, String sender, String message, long userCount){
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.userCount = userCount;
    }

    public enum MessageType{
        ENTER, QUIT, TALK;
    }

//    public enum MessageType {
//        ENTER("ENTER"), TALK("TALK");
//
//        private final String value;
//
//        MessageType(String value) {
//            this.value = value;
//        }
//
//        @JsonValue
//        public String getValue() {
//            return value;
//        }
//
//        @JsonCreator
//        public static MessageType fromValue(String value) {
//            for (MessageType type : MessageType.values()) {
//                if (type.value.equalsIgnoreCase(value)) {
//                    return type;
//                }
//            }
//            throw new IllegalArgumentException("Unknown MessageType: " + value);
//        }
//    }

    private MessageType type; // 메시지 타입
    private String roomId;    // 방번호
    private String sender;    // 메시지 보낸사람
    private String message;   // 메시지
    private long userCount;
}
