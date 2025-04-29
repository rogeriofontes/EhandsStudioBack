CREATE TABLE artistas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    telefone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    insta VARCHAR(100),
    face VARCHAR(100),
    foto VARCHAR(255),
    whatsapp VARCHAR(255),
    cpf varchar(255)
);

