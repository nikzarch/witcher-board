--changeset nikzarch:002-add-balance
--comment: add-balance
--created: 2026-01-14T18:16:39.362632900Z

ALTER TABLE users ADD COLUMN balance BIGINT NOT NULL DEFAULT 0;