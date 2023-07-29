package com.sku.elcoco.service.message;

import com.sku.elcoco.domain.member.Member;
import com.sku.elcoco.domain.message.Message;
import com.sku.elcoco.domain.message.dto.MessageRequestDto;
import com.sku.elcoco.repository.member.MemberRepository;
import com.sku.elcoco.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public MessageRequestDto.CREATE createMessage(MessageRequestDto.CREATE create) {
        Member receiver = memberRepository.findMemberByNickname(create.getReceiverName()).get();
        Member sender = memberRepository.findMemberByNickname(create.getSenderName()).get();

        Message message = toEntity(create, receiver, sender);
        messageRepository.save(message);

        MessageRequestDto.CREATE resultDto = toDto(message);
        return resultDto;
    }


    // 받은 편지함 불러오기
    // 한 명의 유저가 받은 모든 메시지
    @Transactional(readOnly = true)
    @Override
    public List<MessageRequestDto.CREATE> getReceivedMessages(MessageRequestDto.READ read) {
        Member findMember = memberRepository.findMemberByNickname(read.getNickName()).get();
        List<Message> messages = messageRepository.findAllByReceiver_Nickname(findMember.getNickname());
        List<MessageRequestDto.CREATE> messageDtos = new ArrayList<>();
        for (Message message : messages) {
            if (!message.isDeletedByReceiver()) {
                messageDtos.add(toDto(message));
            }
        }
        return messageDtos;
    }
    // 받은 편지 삭제(상대방으로부터 받은)
    @Transactional
    @Override
    public Long deleteMessageByReceiver(Long messageId, MessageRequestDto.READ read) {
        Message message = messageRepository.findById(messageId).get();
        Member member = memberRepository.findMemberByNickname(read.getNickName()).get();

        if (member.getNickname() == message.getSender().getNickname()) {    //상대방의 닉네임과 messageId 체크 후
            message.deleteByReceiver(); //받은 사람에게 메시지 삭제
        }
        return message.getId();

    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageRequestDto.CREATE> getSendMessages(MessageRequestDto.READ read) {
        Member findMember = memberRepository.findMemberByNickname(read.getNickName()).get();
        List<Message> messages = messageRepository.findAllBySender_Nickname(findMember.getNickname());
        List<MessageRequestDto.CREATE> messageDtos = new ArrayList<>();
        for (Message message : messages) {
            if (!message.isDeletedBySender()) {
                messageDtos.add(toDto(message));
            }
        }
        return messageDtos;
    }

    //보낸 편지 삭제
    @Transactional
    @Override
    public Long deleteMessageBySender(Long messageId, MessageRequestDto.READ read) {
        Message message = messageRepository.findById(messageId).get();
        Member member = memberRepository.findMemberByNickname(read.getNickName()).get();

        if (member.getNickname() == message.getSender().getNickname()) {    //sender의 닉네임과 messageId 체크 후
            message.deleteBySender(); //보낸 사람에게 메시지 삭제
        }
        return message.getId();
    }
    private static Message toEntity(MessageRequestDto.CREATE create, Member receiver, Member sender) {
        return Message.builder()
                .receiver(receiver)
                .sender(sender)
                .title(create.getTitle())
                .content(create.getContent())
                .deletedByReceiver(false)
                .deletedBySender(false)
                .build();
    }

    private static MessageRequestDto.CREATE toDto(Message message) {
        return MessageRequestDto.CREATE.builder()
                .receiverName(message.getReceiver().getNickname())
                .senderName(message.getSender().getNickname())
                .content(message.getContent())
                .title(message.getTitle())
                .build();
    }

}
