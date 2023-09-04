--liquibase formatted sql

--changeset ksadej:100

CREATE TABLE `orders` (
	`id` bigint NOT NULL AUTO_INCREMENT,
    `data_added` datetime NOT NULL,
    `order_status` varchar(255) NOT NULL,
    `basket_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE orders add constraint fk_user_entity_id_order_id foreign key (user_entity_id) references user (user_id);

