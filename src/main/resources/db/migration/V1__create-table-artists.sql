CREATE TABLE artists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255) NOT NULL UNIQUE,
    insta VARCHAR(100),
    face VARCHAR(100),
    image_url VARCHAR(255),
    whatsapp VARCHAR(20) NOT NULL,
    cpf varchar(20),
    category_name varchar(100),
    user BIGINT

);

