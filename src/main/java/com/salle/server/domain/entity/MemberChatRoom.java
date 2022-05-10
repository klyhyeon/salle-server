package com.salle.server.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MEMBER_CHAT_ROOM")
public class MemberChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberChatRoom")
    private List<ChatMessageLog> chatMessageLogs;

}