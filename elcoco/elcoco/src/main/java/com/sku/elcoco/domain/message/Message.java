package com.sku.elcoco.domain.message;

import com.sku.elcoco.domain.member.Member;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", referencedColumnName = "nickname")
    private Member sender;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver", referencedColumnName = "nickname")
    private Member receiver;

    @Column(nullable = false)
    private boolean deletedBySender;

    @Column(nullable = false)
    private boolean deletedByReceiver;
    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "title")
    @NotNull
    private String title;
    @Builder
    public Message(Member sender, Member receiver, boolean deletedBySender, boolean deletedByReceiver, String content, String title) {
        this.sender = sender;
        this.receiver = receiver;
        this.deletedBySender = deletedBySender;
        this.deletedByReceiver = deletedByReceiver;
        this.content = content;
        this.title = title;
    }

    public void deleteBySender() {
        this.deletedBySender = true;
    }

    public void deleteByReceiver() {
        this.deletedByReceiver = true;
    }


}