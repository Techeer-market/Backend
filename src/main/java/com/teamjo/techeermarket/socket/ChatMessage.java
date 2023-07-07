package com.teamjo.techeermarket.socket;

import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="messages")
public class ChatMessage extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID productUuid;
    private MessageType type;

    private String sender;
    private String message;
    private String time;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;
}
