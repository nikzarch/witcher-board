--liquibase formatted sql

--changeset sinchi1:1
CREATE TABLE notifications (
                               id BIGSERIAL PRIMARY KEY,

                               message TEXT NOT NULL,

                               user_id BIGINT NOT NULL,

                               zoned_date_time TIMESTAMP WITH TIME ZONE NOT NULL,

                               CONSTRAINT fk_notifications_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users (id)
                                       ON DELETE CASCADE
);