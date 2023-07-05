# Table 생성용 DDL

CREATE TABLE `volunteer`
(
    `id`                                  bigint       not null primary key auto_increment,
    `o_auth_type`                         varchar(20)  not null,
    `o_auth_identifier`                   varchar(50)  not null,
    `o_auth_access_token`                 varchar(100) null,
    `o_auth_refresh_token`                 varchar(100) null,
    `is_deleted`                          boolean      not null,
    `nickname`                            varchar(20)  not null,
    `phone`                           varchar(13)  not null,
    `email`                               varchar(100) not null,
    `user_role`                           varchar(20)  not null,
    `created_at`                          datetime    not null,
    `modified_at`                         datetime,
    unique index UDX_EMAIL (`email`),
    unique index UDX_NICKNAME(`nickname`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `shelter_user`
(
    `id`                                  bigint       not null primary key auto_increment,
    `email`                               varchar(100) not null,
    `password`                            varchar(255) not null,
    `shelter_id`                          bigint       not null,
    `created_at`                          datetime    not null,
    `modified_at`                         datetime,
    unique index UDX_EMAIL (`email`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `shelter`
(
    `id`                                  bigint       not null primary key auto_increment,
    `name`                                varchar(50)  not null,
    `notice`                              varchar(255),
    `description`                         text         not null,
    `phone_num`                           varchar(13)  not null,
    `is_parking_enabled`                  boolean,
    `parking_notice`                      varchar(200),
    `profile_image_url`                   varchar(100),
    `bank_name`                            varchar(50),
    `bank_account_num`                    varchar(50),
    `address`                             varchar(100) not null,
    `address_detail`                      varchar(50),
    `postal_code`                         varchar(5)   not null,
    `latitude`                            double       not null,
    `longitude`                           double       not null,
    `created_at`                          datetime    not null,
    `modified_at`                         datetime,
    index IDX_NAME (`name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `shelter_out_link`
(
    `id`                                  bigint          not null primary key auto_increment,
    `out_link_type`                       varchar(20)     not null,
    `url`                                 varchar(200)    not null,
    `shelter_id`                          bigint          not null,
    index IDX_SHELTER_ID (`shelter_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `observation_animal`
(
    `id`                                  bigint          not null primary key auto_increment,
    `name`                                varchar(20)     not null,
    `profile_image_url`                   varchar(100),
    `special_note`                        varchar(255)    not null,
    `age`                                 varchar(10),
    `gender`                              varchar(20),
    `breed`                               varchar(30),
    `shelter_id`                          bigint          not null,
    `created_at`                          datetime        not null,
    `modified_at`                         datetime,
    index IDX_SHELTER_ID (`shelter_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `volunteer_event`
(
    `id`                                  bigint           not null primary key auto_increment,
    `title`                               varchar(50)      not null,
    `recruit_num`                         int              not null,
    `materials`                           varchar(100),
    `age_limit`                           varchar(30)      not null,
    `status`                              varchar(30)      not null,
    `category`                            varchar(30)      not null,
    `shelter_id`                          bigint           not null,
    `start_at`                            datetime         not null,
    `end_at`                              datetime         not null,
    `created_at`                          datetime         not null,
    `modified_at`                         datetime,
    index IDX_SHELTER_ID (`shelter_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `volunteer_event_join_queue`
(
    `id`                                      bigint           not null primary key auto_increment,
    `volunteer_event_id`                      bigint           not null,
    `volunteer_id`                            bigint           not null,
    index IDX_VOLUNTEER_EVENT_ID(`volunteer_event_id`),
    index IDX_VOLUNTEER_ID(`volunteer_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `volunteer_event_waiting_queue`
(
    `id`                                       bigint            not null primary key auto_increment,
    `volunteer_event_id`                       bigint            not null,
    `volunteer_id`                             bigint            not null,

    index IDX_VOLUNTEER_EVENT_ID(`volunteer_event_id`),
    index IDX_VOLUNTEER_EVENT_ID_AND_VOLUNTEER_ID(`volunteer_event_id`,`volunteer_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `shelter_bookmark`
(
    `id`                                  bigint            not null primary key auto_increment,
    `shelter_id`                          bigint            not null,
    `volunteer_id`                        bigint            not null,
     index IDX_SHELTER_BOOKMARK(`volunteer_id`,`shelter_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

