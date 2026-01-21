--changeset nikzarch:007-create-battle-result-table
--comment: create-battle-result-table
--created: 2026-01-21T20:53:30.246418400Z

CREATE TABLE battle_results (
    id BIGSERIAL PRIMARY KEY,

    witcher_id BIGINT NOT NULL,
    order_id   BIGINT NOT NULL,
    monster_id BIGINT NOT NULL,

    success BOOLEAN NOT NULL,
    chance DOUBLE PRECISION NOT NULL,

    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_battle_results_witcher_id
    ON battle_results (witcher_id);

CREATE INDEX idx_battle_results_order_id
    ON battle_results (order_id);

CREATE INDEX idx_battle_results_created_at
    ON battle_results (created_at);