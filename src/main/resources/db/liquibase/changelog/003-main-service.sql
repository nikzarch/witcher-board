--created: 2026-01-14T18:16:39.362632900Z

--changeset sinchi1:1
CREATE TABLE location (
                          id BIGSERIAL PRIMARY KEY,
                          x DOUBLE PRECISION,
                          y DOUBLE PRECISION,
                          name VARCHAR(255),
                          description VARCHAR(500)
);

--changeset sinchi1:2

CREATE TABLE monster (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255),
                         danger_level SMALLINT
);

--changeset sinchi1:3

CREATE TABLE location_monster (
                                  monster_id BIGINT NOT NULL,
                                  location_id BIGINT NOT NULL,

                                  CONSTRAINT fk_location_monster_monster
                                      FOREIGN KEY (monster_id) REFERENCES monster(id),

                                  CONSTRAINT fk_location_monster_location
                                      FOREIGN KEY (location_id) REFERENCES location(id)
);

--changeset sinchi1:4

CREATE TABLE monster_feature (
                                 id BIGSERIAL PRIMARY KEY,
                                 name VARCHAR(255)
);

--changeset sinchi1:5

CREATE TABLE monster_to_feature (
                                    monster_id BIGINT NOT NULL,
                                    monster_feature_id BIGINT NOT NULL,

                                    CONSTRAINT fk_monster_feature_monster
                                        FOREIGN KEY (monster_id) REFERENCES monster(id),

                                    CONSTRAINT fk_monster_feature_feature
                                        FOREIGN KEY (monster_feature_id) REFERENCES monster_feature(id)
);

--changeset sinchi1:6

CREATE TABLE "order" (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255),
                         description VARCHAR(500),

                         monster_id BIGINT,
                         location_id BIGINT NOT NULL,

                         reward INTEGER,
                         created_at TIMESTAMP WITH TIME ZONE,
                         order_status VARCHAR(32),
                         user_id BIGINT,

                         CONSTRAINT fk_order_monster
                             FOREIGN KEY (monster_id) REFERENCES monster(id),

                         CONSTRAINT fk_order_location
                             FOREIGN KEY (location_id) REFERENCES location(id)
);


--changeset sinchi1:7

CREATE TABLE witcher_offer (
                               id BIGSERIAL PRIMARY KEY,

                               order_id BIGINT,
                               users_id BIGINT,

                               suggested_price BIGINT,
                               witcher_offer_status VARCHAR(32),

                               CONSTRAINT fk_witcher_offer_order
                                   FOREIGN KEY (order_id) REFERENCES "order"(id)
);


--changeset sinchi1:8

CREATE TABLE battles (
                         id BIGSERIAL PRIMARY KEY,

                         witcher_id BIGINT NOT NULL,
                         monster_id BIGINT NOT NULL,
                         battle_success BOOLEAN NOT NULL,

                         CONSTRAINT fk_battles_monster
                             FOREIGN KEY (monster_id) REFERENCES monster(id)
);
