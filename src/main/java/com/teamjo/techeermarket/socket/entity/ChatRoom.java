package com.teamjo.techeermarket.socket.entity;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@AllArgsConstructor
@Entity
@Setter
@Builder
@NoArgsConstructor
@Table(name="chat_room")
public class ChatRoom extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chatRoomName", unique = true, nullable = false)
    private String chatRoomName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reciver_id")
    private Users reciverId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sender_id")
    private Users senderId;


}

