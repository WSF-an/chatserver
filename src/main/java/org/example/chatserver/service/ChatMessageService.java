package org.example.chatserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.chatserver.dto.ChatMessage;

import java.util.List;

public interface ChatMessageService extends IService<ChatMessage> {
    /**
     *查找一条记录
     */
    ChatMessage getChatMessage(Long conversationId, Long userId);
    /**
     * 添加一条记录
     */
    ChatMessage addChatMessage(String message, Long conversationId);
    /**
     * 获取用户某ConversationID下的所有消息记录
     */
    List<ChatMessage> getChatMessages(Long conversationId);
}
