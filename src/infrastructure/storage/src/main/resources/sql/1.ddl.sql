# Table 생성용 DDL

CREATE TABLE `user`
(
    `id`                                  int          not null primary key auto_increment,
    `o_auth_type`                         varchar(20)  not null,
    `o_auth_access_token`                 varchar(100) not null,
    `is_deleted`                          boolean      not null,
    `status`                              varchar(20)  not null,
    `email`                               varchar(100) not null,
    `user_role`                               varchar(20)  not null,
    `created_at`                          timestamp    not null,
    unique index UDX_EMAIL (`email`)
);

CREATE TABLE `shelter_user`
(
    `id`                                  int          not null primary key auto_increment,
    `email`                               varchar(100) not null,
    `password`                            varchar(255) not null,
    `shelter_id`                          int          not null,
    `created_at`                          timestamp    not null,
    unique index UDX_EMAIL (`email`)
);


# shelter의 경우, image가 여러개로 늘어날 수 있다. (이 때 별도의 테이블을 파야 할 듯)
CREATE TABLE `shelter`
(
    `id`                                  int          not null primary key auto_increment,
    `name`                                varchar(50)  not null,
    `email`                               varchar(100) not null,
    `notice`                              varchar(255) not null,
    `description`                         text         not null,
    `phone_num`                           varchar(13)  not null,
    `is_parking_enabled`                  boolean      not null,
    `profile_image`                       varchar(100) not null,
    `created_at`                          timestamp    not null,
    unique index UDX_NAME (`name`)
);

CREATE TABLE `shelter_out_link`
(
    `id`                                  int          not null primary key auto_increment,
    `link_type`                           varchar(20)  not null,
    `url`                                 varchar(200) not null,
    `shelter_id`                          int          not null,
    index IDX_SHELTER_ID (`shelter_id`)
);

# 관찰동물의 경우 사진은 무조건 1장
CREATE TABLE `observation_animal`
(
    `id`                                  int          not null primary key auto_increment,
    `name`                                char(36)     not null,
    `profile_image`                       varchar(100) not null,
    `special_note`                        text         not null,
    `shelter_id`                          int          not null,
    `created_at`                          timestamp    not null,
    index IDX_SHELTER_ID (`shelter_id`)
);

CREATE TABLE `observation_animal_tag`
(
    `id`                                  int          not null primary key auto_increment,
    `name`                                varchar(20)  not null,
    unique index UDX_NAME (`name`)
);

CREATE TABLE `observation_animal_tag_mapping`
(
    `id`                                  int        not null primary key auto_increment,
    `observation_animal_id`               int        not null,
    `observation_animal_tag_id`           int        not null,
    index IDX_OBSERVATION_ANIMAL (`observation_animal_id`)
);

CREATE TABLE `volunteer_event`
(
    `id`                                  int          not null primary key auto_increment,
    `title`                               varchar(50)  not null,
    `recruit_num`                         int          not null,
    `participant_num`                     int          not null,
    `materials`                           varchar(100),
    `age_limit`                           varchar(20)  not null,
    `status`                              varchar(20)  not null,
    `shelter_id`                          int          not null,
    `shelter_user_id`                     int          not null,
    `event_at`                            timestamp    not null,
    `created_at`                          timestamp    not null,
    unique index IDX_SHELTER_ID (`shelter_id`)
);

CREATE TABLE `volunteer_event_user_mapping`
(
    `id`                                  int  not null primary key auto_increment,
    `volunteer_event_id`                  int  not null,
    `user_id`                             int  not null,
    index IDX_VOLUNTEER_EVENT_ID(`volunteer_event_id`),
    index IDX_USER_ID(`user_id`)
);


CREATE TABLE `volunteer_event_activity_type`
(
    `id`                                  int          not null primary key auto_increment,
    `name`                                varchar(20)  not null,
    unique index UDX_NAME (`name`)
);

CREATE TABLE `volunteer_event_activity_type_mapping`
(
    `id`                                  int  not null primary key auto_increment,
    `volunteer_event_id`                  int  not null,
    `volunteer_event_activity_type_id`    int  not null,
    index IDX_VOLUNTEER_EVENT_ID(`volunteer_event_id`)
);

CREATE TABLE `volunteer_event_waiting_queue`
(
    `id`                                  int  not null primary key auto_increment,
    `volunteer_event_id`                  int  not null,
    `user_id`                             int  not null,
    index IDX_VOLUNTEER_EVENT_ID(`volunteer_event_id`)
);


CREATE TABLE `volunteer_event_join_queue`
(
    `id`                                  int  not null primary key auto_increment,
    `volunteer_event_id`                  int  not null,
    `user_id`                             int  not null,
    index IDX_VOLUNTEER_EVENT_ID(`volunteer_event_id`)
);


