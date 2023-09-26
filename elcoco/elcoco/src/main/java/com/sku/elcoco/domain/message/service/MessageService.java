package com.sku.elcoco.domain.message.service;


import com.sku.elcoco.domain.message.dto.MessageRequestDto;

import java.util.List;

public interface MessageService {

    MessageRequestDto.READ createMessage(String email, MessageRequestDto.CREATE create);

    // 받은 편지함 불러오기
    List<MessageRequestDto.READ> getReceivedMessages(String email);

    //받은 편지 삭제
    Long deleteMessageByReceiver(Long messageId, String email);

    //보낸 편지함 불러오기
    List<MessageRequestDto.READ> getSendMessages(String email);

    //보낸 편지 삭제
    Long deleteMessageBySender(Long messageId, String email);


}