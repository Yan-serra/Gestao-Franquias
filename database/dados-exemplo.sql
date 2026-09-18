-- Dados de exemplo - Sistema de Gestão de Franquias
-- Utilizados para facilitar os testes da aplicação

-- =========================================
-- PERFIS
-- =========================================

INSERT INTO perfil (cargo)
SELECT 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1
    FROM perfil
    WHERE cargo = 'ADMIN'
);

INSERT INTO perfil (cargo)
SELECT 'GERENTE'
WHERE NOT EXISTS (
    SELECT 1
    FROM perfil
    WHERE cargo = 'GERENTE'
);


-- =========================================
-- USUÁRIOS
-- =========================================

INSERT INTO usuario (nome, email, senha, ativo, perfil_id)
SELECT
    'Administrador',
    'admin@franquias.com',
    'admin123',
    true,
    (
        SELECT id
        FROM perfil
        WHERE cargo = 'ADMIN'
        ORDER BY id
        LIMIT 1
    )
WHERE NOT EXISTS (
    SELECT 1
    FROM usuario
    WHERE email = 'admin@franquias.com'
);

INSERT INTO usuario (nome, email, senha, ativo, perfil_id)
SELECT
    'Gerente Exemplo',
    'gerente@franquias.com',
    'gerente123',
    true,
    (
        SELECT id
        FROM perfil
        WHERE cargo = 'GERENTE'
        ORDER BY id
        LIMIT 1
    )
WHERE NOT EXISTS (
    SELECT 1
    FROM usuario
    WHERE email = 'gerente@franquias.com'
);


-- =========================================
-- FRANQUEADORA
-- =========================================

INSERT INTO franqueadora (nome, cnpj)
SELECT
    'Rede Exemplo',
    '10.000.000/0001-10'
WHERE NOT EXISTS (
    SELECT 1
    FROM franqueadora
    WHERE cnpj = '10.000.000/0001-10'
);


-- =========================================
-- UNIDADE FRANQUEADA
-- =========================================

INSERT INTO unidade_franqueada
(nome, cnpj, cidade, estado, endereco, telefone, data_inicio, ativa, franqueadora_id)
SELECT
    'Unidade Exemplo',
    '20.000.000/0001-20',
    'Piracicaba',
    'SP',
    'Rua Exemplo, 100',
    '19999999999',
    '2026-01-10',
    true,
    (
        SELECT id
        FROM franqueadora
        WHERE cnpj = '10.000.000/0001-10'
        ORDER BY id
        LIMIT 1
    )
WHERE NOT EXISTS (
    SELECT 1
    FROM unidade_franqueada
    WHERE cnpj = '20.000.000/0001-20'
);


-- =========================================
-- RESPONSÁVEL
-- =========================================

INSERT INTO responsavel
(nome, cpf, email, telefone, unidade_id)
SELECT
    'Responsável Exemplo',
    '111.111.111-11',
    'responsavel@exemplo.com',
    '19988888888',
    (
        SELECT id
        FROM unidade_franqueada
        WHERE cnpj = '20.000.000/0001-20'
        ORDER BY id
        LIMIT 1
    )
WHERE NOT EXISTS (
    SELECT 1
    FROM responsavel
    WHERE cpf = '111.111.111-11'
);


-- =========================================
-- CATEGORIA
-- =========================================

INSERT INTO categoria (nome)
SELECT
    'Alimentos'
WHERE NOT EXISTS (
    SELECT 1
    FROM categoria
    WHERE nome = 'Alimentos'
);


-- =========================================
-- FORNECEDOR
-- =========================================

INSERT INTO fornecedor
(nome, cnpj, telefone, email, ativo)
SELECT
    'Fornecedor Exemplo',
    '30.000.000/0001-30',
    '19977777777',
    'fornecedor@exemplo.com',
    true
WHERE NOT EXISTS (
    SELECT 1
    FROM fornecedor
    WHERE cnpj = '30.000.000/0001-30'
);


-- =========================================
-- PRODUTO
-- =========================================

INSERT INTO produto
(nome, descricao, preco_base, ativo, categoria_id, fornecedor_id)
SELECT
    'Produto Exemplo',
    'Produto utilizado para testes da aplicação',
    25.00,
    true,
    (
        SELECT id
        FROM categoria
        WHERE nome = 'Alimentos'
        ORDER BY id
        LIMIT 1
    ),
    (
        SELECT id
        FROM fornecedor
        WHERE cnpj = '30.000.000/0001-30'
        ORDER BY id
        LIMIT 1
    )
WHERE NOT EXISTS (
    SELECT 1
    FROM produto
    WHERE nome = 'Produto Exemplo'
);


-- =========================================
-- ESTOQUE
-- =========================================

INSERT INTO estoque
(quantidade, estoque_minimo, produto_id, unidade_id)
SELECT
    20,
    5,
    (
        SELECT id
        FROM produto
        WHERE nome = 'Produto Exemplo'
        ORDER BY id
        LIMIT 1
    ),
    (
        SELECT id
        FROM unidade_franqueada
        WHERE cnpj = '20.000.000/0001-20'
        ORDER BY id
        LIMIT 1
    )
WHERE NOT EXISTS (
    SELECT 1
    FROM estoque e
    JOIN produto p ON p.id = e.produto_id
    JOIN unidade_franqueada u ON u.id = e.unidade_id
    WHERE p.nome = 'Produto Exemplo'
      AND u.cnpj = '20.000.000/0001-20'
);


-- =========================================
-- MOVIMENTAÇÃO DE ESTOQUE
-- =========================================

INSERT INTO movimentacao_estoque
(tipo, quantidade, data_movimentacao, estoque_id)
SELECT
    'ENTRADA',
    20,
    '2026-09-01 10:00:00',
    e.id
FROM estoque e
JOIN produto p ON p.id = e.produto_id
JOIN unidade_franqueada u ON u.id = e.unidade_id
WHERE p.nome = 'Produto Exemplo'
  AND u.cnpj = '20.000.000/0001-20'
  AND NOT EXISTS (
      SELECT 1
      FROM movimentacao_estoque m
      WHERE m.estoque_id = e.id
        AND m.tipo = 'ENTRADA'
        AND m.quantidade = 20
  );


-- =========================================
-- ROYALTY
-- =========================================

INSERT INTO royalty
(percentual, faturamento, valor_devido, valor_pago,
 status, data_inicio, data_fim, unidade_id)
SELECT
    5.0,
    1000.00,
    50.00,
    0.00,
    'PENDENTE',
    '2026-09-01',
    '2026-09-30',
    (
        SELECT id
        FROM unidade_franqueada
        WHERE cnpj = '20.000.000/0001-20'
        ORDER BY id
        LIMIT 1
    )
WHERE NOT EXISTS (
    SELECT 1
    FROM royalty r
    JOIN unidade_franqueada u ON u.id = r.unidade_id
    WHERE u.cnpj = '20.000.000/0001-20'
      AND r.data_inicio = '2026-09-01'
      AND r.data_fim = '2026-09-30'
);


-- =========================================
-- CHAMADO DE SUPORTE
-- =========================================

INSERT INTO chamado_suporte
(categoria, prioridade, descricao, status,
 data_abertura, data_fechamento, unidade_id)
SELECT
    'Sistema',
    'MEDIA',
    'Chamado criado para demonstração do sistema',
    'ABERTO',
    '2026-09-01 09:00:00',
    NULL,
    (
        SELECT id
        FROM unidade_franqueada
        WHERE cnpj = '20.000.000/0001-20'
        ORDER BY id
        LIMIT 1
    )
WHERE NOT EXISTS (
    SELECT 1
    FROM chamado_suporte
    WHERE descricao = 'Chamado criado para demonstração do sistema'
);