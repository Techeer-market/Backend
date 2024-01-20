package com.teamjo.techeermarket.socket.entity;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

//@NoArgsConstructor
@Entity
@Table(name = "chat_room")
@Getter
public class ChatRoom extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_room_name", unique = true)
    private String chatRoomName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Users buyer;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Chat> chats = new ArrayList<>();


    public ChatRoom() {
        this.chatRoomName = UUID.randomUUID().toString();
        System.out.println("Generated UUID: " + this.chatRoomName); //주석처리
    }

    @Builder
    public ChatRoom(Long roomId, String chatRoomName, Products products, Users buyer) {
        this.id = roomId;
        this.chatRoomName = chatRoomName;
        this.products = products;
        this.buyer = buyer;
    }


    /* 비즈니스 로직 */
    public Chat getLastChat(){
        return getChats().get(getChats().size()-1);
    }

}

