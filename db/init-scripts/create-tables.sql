CREATE TABLE IF NOT EXISTS dishes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password_hash VARCHAR(255),
    theme VARCHAR(25),
    lang VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS menus (
    id SERIAL PRIMARY KEY,
    price DECIMAL,
    menu_title VARCHAR(255),
    rating DECIMAL,
    calories INT,
    chef_id INT,
    CONSTRAINT fk_chef
        FOREIGN KEY(chef_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS menu_dishes (
    menu_id BIGINT REFERENCES menus(id) ON DELETE CASCADE,
    dish_id BIGINT REFERENCES dishes(id) ON DELETE CASCADE,
    PRIMARY KEY(menu_id, dish_id)
);

CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY(user_id, role_id)
);

INSERT INTO dishes (name) VALUES('pizza');
INSERT INTO dishes (name) VALUES ('pityoka');

INSERT INTO roles (name) VALUES('ADMIN');
INSERT INTO roles (name) VALUES('CHEF');
INSERT INTO roles (name) VALUES('CLIENT');
