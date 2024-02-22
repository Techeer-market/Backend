package com.teamjo.techeermarket.domain.chats.entity;

import com.teamjo.techeermarket.global.common.BaseEntity;
import javax.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Chat extends BaseEntity {

  @Id
  @Column(name = "id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  private String senderEmail;
  private String message;


}
