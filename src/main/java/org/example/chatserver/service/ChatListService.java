package org.example.chatserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.chatserver.dto.ChatList;

import java.util.List;

public interface ChatListService extends IService<ChatList> {
    /**
     * 新建一个会话
     */
    boolean addChatList(Long userId, Long conversationId, String description);

    /**
     * 删除一个会话
     *
     */
    //boolean removeChatList(Long userId, Long conversationId);

    /**
     * 查询某用户下的所有conversationId
     */
    List<ChatList> getChatList(Long userId);


}
