package com.sku.elcoco.controller.message;

import com.sku.elcoco.domain.message.dto.MessageRequestDto;
import com.sku.elcoco.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageApiController {

    private final MessageService messageService;


    @PostMapping("/message")
    public ResponseEntity createMessage(@RequestBody MessageRequestDto.CREATE create) {
        log.info("create.receiveNickName ={}",create.getReceiverName());
        log.info("create.sendNickName ={}",create.getSenderName());
        MessageRequestDto.CREATE message = messageService.createMessage(create);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    //받은 편지함 확인
    @GetMapping("/message/received")
    public ResponseEntity getReceivedMessages(@RequestBody MessageRequestDto.READ nickName) {
        List<MessageRequestDto.CREATE> result = messageService.getReceivedMessages(nickName);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //받은 쪽지 삭제
    @DeleteMapping("/message/received/{messageId}")
    public ResponseEntity deleteReceivedMessage(@PathVariable("messageId")Long messageId,
                                                      @RequestBody MessageRequestDto.READ nickName ) {
        Long deleteMessageId = messageService.deleteMessageByReceiver(messageId, nickName);
        return new ResponseEntity<>(deleteMessageId,HttpStatus.OK);
    }

    //보낸 편지함 확인
    @GetMapping("/message/send")
    public ResponseEntity getSendMessage(@RequestBody MessageRequestDto.READ nickName) {
        List<MessageRequestDto.CREATE> result = messageService.getSendMessages(nickName);
        return new ResponseEntity <>(result,HttpStatus.OK);
    }

    //보낸 쪽지 삭제
    @DeleteMapping("/message/send/{messageId}")
    public ResponseEntity deleteSendMessage(@PathVariable("messageId")Long messageId,
                                                  @RequestBody MessageRequestDto.READ nickName ) {
        Long deleteMessageId = messageService.deleteMessageBySender(messageId, nickName);
        return new ResponseEntity<>(deleteMessageId,HttpStatus.OK);
    }

}