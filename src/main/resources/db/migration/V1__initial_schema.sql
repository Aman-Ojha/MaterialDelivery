

CREATE TABLE IF NOT EXISTS material_delivery (
    id BIGSERIAL PRIMARY KEY,
    material_id VARCHAR(50) NOT NULL,
    tons INTEGER NOT NULL check (tons>50),
    delivery_time TIMESTAMPTZ NOT NULL,
    plant_name VARCHAR(100) NOT NULL
);


CREATE INDEX idx_material_delivery_material_time ON material_delivery(plant_name, delivery_time);