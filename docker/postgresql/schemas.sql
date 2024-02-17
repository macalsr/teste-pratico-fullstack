CREATE TABLE profissionais (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    nascimento DATE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contatos (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    contato VARCHAR(100) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    profissional_id INTEGER,
    CONSTRAINT fk_profissional_id FOREIGN KEY (profissional_id) REFERENCES profissionais(id)
);
