package com.sku.elcoco.service.message;

import com.sku.elcoco.domain.message.dto.MessageRequestDto;

import java.util.List;

public interface MessageService {
    MessageRequestDto.CREATE createMessage(MessageRequestDto.CREATE create);

    // 받은 편지함 불러오기
    List<MessageRequestDto.CREATE> getReceivedMessages(MessageRequestDto.READ read);

    //받은 편지 삭제
    Long deleteMessageByReceiver(Long messageId, MessageRequestDto.READ read);

    //보낸 편지함 불러오기
    List<MessageRequestDto.CREATE> getSendMessages(MessageRequestDto.READ read);

    //보낸 편지 삭제
    Long deleteMessageBySender(Long messageId, MessageRequestDto.READ read);


}
