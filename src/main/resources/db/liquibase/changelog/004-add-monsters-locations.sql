--liquibase formatted sql
--created: 2026-01-14T18:16:39.362632900Z

--changeset sinchi1:1

INSERT INTO location (id, x, y, name, description) VALUES
                                                       (1, 10.0, 20.0, 'Велен', 'Болота, леса и заброшенные деревни'),
                                                       (2, 55.5, 75.2, 'Скеллиге', 'Скалистые острова и прибрежные воды'),
                                                       (3, 90.0, 10.0, 'Новиград', 'Город и окрестности'),
                                                       (4, 15.3, 88.8, 'Каэр Морхен', 'Горы и руины крепости');

--changeset sinchi1:2

INSERT INTO monster (id, name, danger_level) VALUES
                                                 (1, 'Гуль', 2),
                                                 (2, 'Утопец', 1),
                                                 (3, 'Грифон', 4),
                                                 (4, 'Леший', 5),
                                                 (5, 'Катакан', 6);

--changeset sinchi1:3

INSERT INTO monster_feature (id, name) VALUES
                                           (1, 'NECROPHAGE'),
                                           (2, 'FLYING'),
                                           (3, 'FOREST_DWELLER'),
                                           (4, 'VAMPIRE'),
                                           (5, 'POISON_RESISTANT'),
                                           (6, 'FIRE_WEAKNESS'),
                                           (7, 'LIGHT_SENSITIVE');

--changeset sinchi1:4

INSERT INTO monster_to_feature (monster_id, monster_feature_id) VALUES
                                                                    (1, 1), -- Гуль → некрофаг
                                                                    (1, 5),
                                                                    (2, 1), -- Утопец → некрофаг
                                                                    (3, 2), -- Грифон → летающий
                                                                    (3, 6),
                                                                    (4, 3), -- Леший → лесной
                                                                    (4, 7),
                                                                    (5, 4), -- Катакан → вампир
                                                                    (5, 7);

--changeset sinchi1:5

INSERT INTO location_monster (monster_id, location_id) VALUES
                                                           (1, 1), -- Гуль → Велен
                                                           (2, 1),
                                                           (2, 2), -- Утопец → Скеллиге
                                                           (3, 3), -- Грифон → Новиград
                                                           (4, 1), -- Леший → Велен
                                                           (4, 4), -- Леший → Каэр Морхен
                                                           (5, 3);

