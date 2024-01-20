package com.teamjo.techeermarket.socket.entity;

import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

//@Getter
//@AllArgsConstructor
//@Entity
//@Setter
//@Builder
//@NoArgsConstructor
//@Table(name="chat_user")
//public class ChatUser {
//
//    @Id
//    @Column(name = "id", unique = true, nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "user_id")
//    private Users users;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "chat_room_id")
//    private ChatRoom chatRoom;
//
//}
