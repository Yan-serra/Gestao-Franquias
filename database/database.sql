-- Banco de dados - Sistema de Gestão de Franquias
-- Script para criação das tabelas principais do projeto

CREATE TABLE IF NOT EXISTS franqueadora (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    cnpj VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS perfil (
    id BIGSERIAL PRIMARY KEY,
    cargo VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS categoria (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS fornecedor (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    cnpj VARCHAR(255),
    telefone VARCHAR(255),
    email VARCHAR(255),
    ativo BOOLEAN
);

CREATE TABLE IF NOT EXISTS unidade_franqueada (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    cnpj VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(255),
    endereco VARCHAR(255),
    telefone VARCHAR(255),
    data_inicio DATE,
    ativa BOOLEAN,
    franqueadora_id BIGINT,

    CONSTRAINT fk_unidade_franqueadora
        FOREIGN KEY (franqueadora_id)
        REFERENCES franqueadora(id)
);

CREATE TABLE IF NOT EXISTS usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    senha VARCHAR(255),
    ativo BOOLEAN,
    perfil_id BIGINT,

    CONSTRAINT fk_usuario_perfil
        FOREIGN KEY (perfil_id)
        REFERENCES perfil(id)
);

CREATE TABLE IF NOT EXISTS responsavel (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    cpf VARCHAR(255),
    email VARCHAR(255),
    telefone VARCHAR(255),
    unidade_id BIGINT,

    CONSTRAINT fk_responsavel_unidade
        FOREIGN KEY (unidade_id)
        REFERENCES unidade_franqueada(id)
);

CREATE TABLE IF NOT EXISTS produto (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    descricao VARCHAR(255),
    preco_base DOUBLE PRECISION,
    ativo BOOLEAN,
    categoria_id BIGINT,
    fornecedor_id BIGINT,

    CONSTRAINT fk_produto_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria(id),

    CONSTRAINT fk_produto_fornecedor
        FOREIGN KEY (fornecedor_id)
        REFERENCES fornecedor(id)
);

CREATE TABLE IF NOT EXISTS estoque (
    id BIGSERIAL PRIMARY KEY,
    quantidade INTEGER,
    estoque_minimo INTEGER,
    produto_id BIGINT,
    unidade_id BIGINT,

    CONSTRAINT fk_estoque_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto(id),

    CONSTRAINT fk_estoque_unidade
        FOREIGN KEY (unidade_id)
        REFERENCES unidade_franqueada(id)
);

CREATE TABLE IF NOT EXISTS movimentacao_estoque (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(255),
    quantidade INTEGER,
    data_movimentacao TIMESTAMP,
    estoque_id BIGINT,

    CONSTRAINT fk_movimentacao_estoque
        FOREIGN KEY (estoque_id)
        REFERENCES estoque(id)
);

CREATE TABLE IF NOT EXISTS item_venda (
    id BIGSERIAL PRIMARY KEY,
    quantidade INTEGER,
    preco_unitario DOUBLE PRECISION,
    subtotal DOUBLE PRECISION,
    produto_id BIGINT,

    CONSTRAINT fk_item_venda_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto(id)
);

CREATE TABLE IF NOT EXISTS venda (
    id BIGSERIAL PRIMARY KEY,
    data_venda TIMESTAMP,
    valor_total DOUBLE PRECISION,
    unidade_id BIGINT,

    CONSTRAINT fk_venda_unidade
        FOREIGN KEY (unidade_id)
        REFERENCES unidade_franqueada(id)
);

CREATE TABLE IF NOT EXISTS venda_itens (
    venda_id BIGINT NOT NULL,
    itens_id BIGINT NOT NULL,

    CONSTRAINT fk_venda_itens_venda
        FOREIGN KEY (venda_id)
        REFERENCES venda(id),

    CONSTRAINT fk_venda_itens_item
        FOREIGN KEY (itens_id)
        REFERENCES item_venda(id)
);

CREATE TABLE IF NOT EXISTS royalty (
    id BIGSERIAL PRIMARY KEY,
    percentual DOUBLE PRECISION,
    faturamento DOUBLE PRECISION,
    valor_devido DOUBLE PRECISION,
    valor_pago DOUBLE PRECISION,
    status VARCHAR(255),
    data_inicio DATE,
    data_fim DATE,
    unidade_id BIGINT,

    CONSTRAINT fk_royalty_unidade
        FOREIGN KEY (unidade_id)
        REFERENCES unidade_franqueada(id)
);

CREATE TABLE IF NOT EXISTS chamado_suporte (
    id BIGSERIAL PRIMARY KEY,
    categoria VARCHAR(255),
    prioridade VARCHAR(255),
    descricao VARCHAR(255),
    status VARCHAR(255),
    data_abertura TIMESTAMP,
    data_fechamento TIMESTAMP,
    unidade_id BIGINT,

    CONSTRAINT fk_chamado_unidade
        FOREIGN KEY (unidade_id)
        REFERENCES unidade_franqueada(id)
);