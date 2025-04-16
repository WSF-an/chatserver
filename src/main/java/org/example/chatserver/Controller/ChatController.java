package org.example.chatserver.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.chatserver.dto.ChatList;
import org.example.chatserver.dto.User;
import org.example.chatserver.service.ChatListService;
import org.example.chatserver.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("chat")
@Slf4j
public class ChatController {

    @Autowired
    private Userservice userservice;

    @Autowired
    private ChatListService chatlistservice;

    @PostMapping("/{sessionId}/message")
    public String charAI(@PathVariable String sessionId,@RequestParam String message, HttpServletRequest request){
        HttpSession session = request.getSession();
        Map<String, List<String>> multiSessionHistory =
                (Map<String, List<String>>) session.getAttribute("multi_history");

        if (multiSessionHistory == null) {
            multiSessionHistory = new HashMap<>();
        }

        List<String> history = multiSessionHistory.computeIfAbsent(
                sessionId, k -> new ArrayList<>());

        history.add("用户：" + message);
        // 模拟 AI 回复
        String reply = "AI：" + message + "（我已经收到了）";
        history.add(reply);

        session.setAttribute("multi_history", multiSessionHistory);
        return reply;

    }
    @GetMapping("/{sessionId}/history")
    public List<String> history(@PathVariable String sessionId,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null){
            log.info("session is null:无会话记录");
        }
        Map<String, List<String>> multiSessionHistory = (Map<String, List<String>>) session.getAttribute("multi_history");
        List<String> list = multiSessionHistory.get(sessionId);
        return list;


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
