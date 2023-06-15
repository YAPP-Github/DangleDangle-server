# Table 생성용 DDL

CREATE TABLE `user`
(
    `id`                                  bigint       not null primary key auto_increment,
    `o_auth_type`                         varchar(20)  not null,
    `o_auth_access_token`                 varchar(100) not null,
    `o_auth_refresh_token`                 varchar(100) not null,
    `is_deleted`                          boolean      not null,
    `status`                              varchar(20)  not null,
    `nickname`                            varchar(20)  not null,
    `email`                               varchar(100) not null,
    `user_role`                           varchar(20)  not null,
    `created_at`                          timestamp    not null,
    `modified_at`                         timestamp,
    unique index UDX_EMAIL (`email`),
    unique index UDX_NICKNAME(`nickname`)
);

CREATE TABLE `shelter_user`
(
    `id`                                  bigint       not null primary key auto_increment,
    `email`                               varchar(100) not null,
    `password`                            varchar(255) not null,
    `shelter_id`                          bigint       not null,
    `created_at`                          timestamp    not null,
    `modified_at`                         timestamp,
    unique index UDX_EMAIL (`email`)
);


# shelter의 경우, image가 여러개로 늘어날 수 있다. (이 때 별도의 테이블을 파야 할 듯)
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
    `created_at`                          timestamp    not null,
    `modified_at`                         timestamp,
    unique index UDX_NAME (`name`)
);

CREATE TABLE `shelter_out_link`
(
    `id`                                  bigint          not null primary key auto_increment,
    `out_link_type`                       varchar(20)     not null,
    `url`                                 varchar(200)    not null,
    `shelter_id`                          bigint          not null,
    index IDX_SHELTER_ID (`shelter_id`)
);

# 관찰동물의 경우 사진은 무조건 1장
CREATE TABLE `observation_animal`
(
    `id`                                  bigint          not null primary key auto_increment,
    `name`                                varchar(20)     not null,
    `profile_image_url`                   varchar(100)    not null,
    `special_note`                        varchar(255)    not null,
    `shelter_id`                          bigint          not null,
    `created_at`                          timestamp       not null,
    `modified_at`                         timestamp,
    index IDX_SHELTER_ID (`shelter_id`)
);

CREATE TABLE `observation_animal_tag`
(
    `id`                                  bigint          not null primary key auto_increment,
    `name`                                varchar(20)     not null,
    unique index UDX_NAME (`name`)
);

CREATE TABLE `observation_animal_tag_mapping`
(
    `id`                                  bigint          not null primary key auto_increment,
    `observation_animal_id`               bigint          not null,
    `observation_animal_tag_id`           bigint          not null,
    index IDX_OBSERVATION_ANIMAL (`observation_animal_id`)
);

CREATE TABLE `volunteer_event`
(
    `id`                                  bigint           not null primary key auto_increment,
    `title`                               varchar(50)      not null,
    `recruit_num`                         int              not null,
    `participant_num`                     int              not null,
    `materials`                           varchar(100),
    `age_limit`                           varchar(20)      not null,
    `status`                              varchar(20)      not null,
    `shelter_id`                          bigint           not null,
    `event_at`                            timestamp        not null,
    `created_at`                          timestamp        not null,
    `modified_at`                         timestamp,
    index IDX_SHELTER_ID (`shelter_id`)
);

CREATE TABLE `volunteer_event_user_mapping`
(
    `id`                                  bigint           not null primary key auto_increment,
    `volunteer_event_id`                  bigint           not null,
    `user_id`                             bigint           not null,
    index IDX_VOLUNTEER_EVENT_ID(`volunteer_event_id`),
    index IDX_USER_ID(`user_id`)
);


CREATE TABLE `volunteer_event_activity_type`
(
    `id`                                  bigint            not null primary key auto_increment,
    `name`                                varchar(20)       not null,
    unique index UDX_NAME (`name`)
);

CREATE TABLE `volunteer_event_activity_type_mapping`
(
    `id`                                  bigint            not null primary key auto_increment,
    `volunteer_event_id`                  bigint            not null,
    `volunteer_event_activity_type_id`    bigint            not null,
    index IDX_VOLUNTEER_EVENT_ID(`volunteer_event_id`)
);

CREATE TABLE `volunteer_event_waiting_queue`
(
    `id`                                  bigint            not null primary key auto_increment,
    `volunteer_event_id`                  bigint            not null,
    `user_id`                             bigint            not null,
    index IDX_VOLUNTEER_EVENT_ID(`volunteer_event_id`)
);


CREATE TABLE `volunteer_event_join_queue`
(
    `id`                                  bigint            not null primary key auto_increment,
    `volunteer_event_id`                  bigint            not null,
    `user_id`                             bigint            not null,
    index IDX_VOLUNTEER_EVENT_ID(`volunteer_event_id`)
);


