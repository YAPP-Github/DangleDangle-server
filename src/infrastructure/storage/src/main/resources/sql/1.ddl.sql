# Table 생성용 DDL

CREATE TABLE `account`
(
    `id`                          int          not null primary key auto_increment,
    `user_identifier`             char(36)     not null,
    `o_auth_type`                 varchar(20)  not null,
    `is_spam_notification_agreed` boolean      not null,
    `status`                      varchar(20)  not null,
    `email`                       varchar(100) not null,
    `created_at`                  timestamp    not null,
    unique index UDX_USER_IDENTIFIER (`user_identifier`)
);

