--liquibase formatted sql

--changeset ksadej:100

create table order (
    id bigint not null auto_increment PRIMARY KEY,
    data_added datetime not null,
    order_status varchar(32) not null,
    basket_id bigint not null,
    user_id bigint not null
    constraint fk_order_user_id foreign key (user_id) references 'user'(id)
);