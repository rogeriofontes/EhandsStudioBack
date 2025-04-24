CREATE TABLE produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    tamanho VARCHAR(255),
    imagem_url VARCHAR(255),
    preco DECIMAL(10, 2),
    categoria_id BIGINT
);