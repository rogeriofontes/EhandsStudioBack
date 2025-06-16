CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf varchar(20),
    address VARCHAR(255),
    phone VARCHAR(20),
    whatsapp VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    user BIGINT

);
