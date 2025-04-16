CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                        `userAccount` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
                        `userPassword` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',

                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1866146876963602434 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';
-- 聊天消息表
CREATE TABLE `chat_message` (
                                `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
                                `message` TEXT NOT NULL COMMENT '用户消息',
                                `reply` TEXT COMMENT '回复消息',
                                `conversation_id` BIGINT NOT NULL COMMENT '会话ID',
                                `time` DATETIME NOT NULL COMMENT '消息时间',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
    COMMENT='聊天消息';
-- 用户聊天列表
CREATE TABLE `chat_list` (
                             `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             `user_id` BIGINT NOT NULL COMMENT '用户ID',
                             `conversation_id` BIGINT NOT NULL COMMENT '会话ID',
                             `description` VARCHAR(512) COMMENT '聊天描述',
                             PRIMARY KEY (`id`),
                             KEY `idx_user_id` (`user_id`),
                             KEY `idx_conversation_id` (`conversation_id`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
    COMMENT='聊天列表';


