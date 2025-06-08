CREATE TABLE artists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    insta VARCHAR(100),
    face VARCHAR(100),
    image_url VARCHAR(255),
    whatsapp VARCHAR(255),
    cpf varchar(255),
    category_id BIGINT
);

