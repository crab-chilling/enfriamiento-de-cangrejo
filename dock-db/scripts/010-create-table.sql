CREATE TABLE card (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    family VARCHAR(255),
    affinity VARCHAR(255),
    image_url VARCHAR(255),
    miniature_url VARCHAR(255),
    energy INT NOT NULL,
    health INT NOT NULL,
    attack INT NOT NULL,
    defence INT NOT NULL
);

CREATE TABLE kuser (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    email_address VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    wallet FLOAT NOT NULL,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE user_card (
    id SERIAL PRIMARY KEY,
    user_id BIGINT,
    card_id BIGINT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES kuser(id),
    CONSTRAINT fk_card FOREIGN KEY (card_id) REFERENCES card(id)
);

CREATE TABLE store_item (
    id SERIAL PRIMARY KEY,
    card_id BIGINT,
    user_id BIGINT,
    price FLOAT NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES kuser(id),
    CONSTRAINT fk_card_id FOREIGN KEY (card_id) REFERENCES card(id)
);
