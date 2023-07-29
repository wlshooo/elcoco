package com.sku.elcoco.repository.message;

import com.sku.elcoco.domain.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiver_Nickname(String nickname);

    List<Message> findAllBySender_Nickname(String nickname);
}
