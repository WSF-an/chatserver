package org.example.chatserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.chatserver.dto.ChatList;
import org.example.chatserver.dto.ChatMessage;
import org.example.chatserver.mapper.ChatMessageMapper;
import org.example.chatserver.service.ChatListService;
import org.example.chatserver.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ChatMessageImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {

    @Autowired
    private ChatListService chatListService;

    @Override
    public List<ChatMessage> getChatMessages(Long conversationId) {
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<ChatMessage>();
        queryWrapper.eq("conversation_id", conversationId);
        List<ChatMessage> messages =  this.list(queryWrapper);
        return messages;
    }

    @Override
    public ChatMessage getChatMessage(Long conversationId, Long userId) {
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("conversationID", conversationId);
        queryWrapper.eq("userId", userId);
        ChatMessage chatMessage =  this.baseMapper.selectOne(queryWrapper);
        if(chatMessage == null){
            log.info("数据库无此记录");
        }
        return chatMessage;
    }

    @Override
    public ChatMessage addChatMessage(String message, Long conversationId) {
        String reply = "AI：" + message + "（我已经收到了）";
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setConversationId(conversationId);
        chatMessage.setReply(reply);
        chatMessage.setMessage(message);
        Date time = new Date();
        chatMessage.setTime(time);
        this.save(chatMessage);
        return chatMessage;
    }
}
