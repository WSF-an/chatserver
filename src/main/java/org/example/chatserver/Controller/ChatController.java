package org.example.chatserver.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.chatserver.dto.ChatList;
import org.example.chatserver.dto.ChatMessage;
import org.example.chatserver.dto.User;
import org.example.chatserver.service.ChatListService;
import org.example.chatserver.service.ChatMessageService;
import org.example.chatserver.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("chat")
@Slf4j
public class ChatController {

    @Autowired
    private Userservice userservice;

    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatListService chatlistservice;

    @PostMapping("/{conversationID}/message")
    public String charAI(@PathVariable Long conversationID,@RequestParam String message, HttpServletRequest request){
        //先校验有无登录
        User user = userservice.getLoginUser(request);
        if(user==null){
            log.info("user is null:用户未登录");
        }
        long userId = user.getId();
        //找到session中存的多会话
        HttpSession session = request.getSession();
        Map<Long, List<String>> multiSessionHistory =
                (Map<Long, List<String>>) session.getAttribute("multi_history");

        if (multiSessionHistory == null) {
            multiSessionHistory = new HashMap<>();
        }


        List<String> history = multiSessionHistory.computeIfAbsent(
                conversationID, k -> new ArrayList<>());

        history.add("用户：" + message);
        // 模拟 AI 回复
        String reply = "AI：" + message + "（我已经收到了）";
        history.add(reply);

        //将数据存入数据库
        chatMessageService.addChatMessage(message,conversationID);

        session.setAttribute("multi_history", multiSessionHistory);
        return reply;

    }
    @GetMapping("/{conversationID}/history")
    public List<ChatMessage> history(@PathVariable Long conversationID,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null){
            log.info("session is null:无会话记录");
        }
        /*Map<Long, List<String>> multiSessionHistory = (Map<Long, List<String>>) session.getAttribute("multi_history");
        List<String> list = multiSessionHistory.get(conversationID);*/
        List<ChatMessage> history = chatMessageService.getChatMessages(conversationID) ;
        return history;


    }
    @PostMapping("/add_list")
    public  void addList(@RequestBody ChatList chatlist,HttpServletRequest request){
        User user = userservice.getLoginUser(request);
        if(user==null){
            log.info("user is null:用户未登录");
        }
        long userId = user.getId();
        chatlist.setUserId(userId);
        chatlistservice.addChatList(chatlist.getUserId(),chatlist.getConversationId(),chatlist.getDescription());
    }
    /*
    @GetMapping("/{userId}/getlist")
    public List<Long> getlist(@PathVariable Long userId,HttpServletRequest request){}  这里可以不用发userId
     */
    @GetMapping("/get_list")
    public List<ChatList> getlist(HttpServletRequest request){
        User user = userservice.getLoginUser(request);
        if(user==null){
            log.info("user is null:用户未登录");
        }
        long userId = user.getId();
        List<ChatList> chatLists = chatlistservice.getChatList(userId);
        return chatLists;
    }

}
