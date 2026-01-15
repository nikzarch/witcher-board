--liquibase formatted sql

--changeset sinchi1:1
ALTER TABLE "order" RENAME TO orders;
