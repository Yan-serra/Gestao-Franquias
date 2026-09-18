# GESTÃO DE FRANQUIAS

## DESCRIÇÃO

Este projeto acadêmico foi desenvolvido em Java com Spring Boot para a criação de uma API REST voltada ao gerenciamento de franquias. A aplicação permite administrar franqueadoras, 
unidades franqueadas, usuários, perfis, responsáveis, categorias, produtos, fornecedores, estoque, vendas, royalties e chamados de suporte.
O sistema utiliza PostgreSQL como banco de dados e Spring Security com JWT para autenticação e proteção dos endpoints.

## OBJETIVO

Desenvolver uma aplicação back-end para gerenciamento de franquias, aplicando conceitos de API REST, banco de dados relacional, operações CRUD, validações, 
regras de negócio, autenticação, autorização e segurança.

## TECNOLOGIAS UTILIZADAS

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Maven
- Swagger / OpenAPI
- Postman
- Eclipse IDE
- Git
- GitHub

## ESTRUTURA DO PROJETO

```text
gestao_franquias/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/franquias/gestao/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── database/
│   ├── database.sql
│   └── dados-exemplo.sql
├── postman/
├── pom.xml
└── README.md
```

## FUNCIONALIDADES

==> Gerenciamento de franqueadoras.
==> Gerenciamento de unidades franqueadas.
==> Gerenciamento de usuários e perfis.
==> Cadastro de responsáveis pelas unidades.
==> Gerenciamento de categorias e produtos.
==> Gerenciamento de fornecedores.
==> Controle de estoque.
==> Entrada e saída de estoque.
==> Registro de vendas e itens.
==> Cálculo do valor das vendas.
==> Controle e cálculo de royalties.
==> Gerenciamento de chamados de suporte.
==> Consultas e indicadores.
==> Validação de CNPJ duplicado.
==> Validação de e-mail duplicado.
==> Ativação e inativação.
==> Impedimento de estoque negativo.
==> Bloqueio de venda em unidade inativa.
==> Autenticação utilizando JWT.
==> Proteção de endpoints.
==> Controle de acesso de acordo com o perfil do usuário.

## BANCO DE DADOS

O projeto utiliza PostgreSQL.

O banco utilizado pela aplicação é:

```text
Franquias-DB
```

A configuração está localizada em:

```text
src/main/resources/application.properties
```

Configuração utilizada:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Franquias-DB
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
```

A senha do PostgreSQL não fica armazenada diretamente no projeto.

Ela deve ser configurada através da variável de ambiente:

```text
DB_PASSWORD
```

## AUTENTICAÇÃO

A aplicação utiliza Spring Security com JWT.

O login é realizado através de:

```http
POST /auth/login
```

Exemplo:

```json
{
    "email": "usuario@email.com",
    "senha": "senha"
}
```

Após o login, o token deve ser utilizado nas requisições protegidas:

```text
Authorization: Bearer TOKEN_JWT
```

## PERFIS DE ACESSO

Os principais perfis utilizados são:

```text
ADMIN
GERENTE
```

O perfil ADMIN possui acesso às operações administrativas de usuários e perfis. ADMIN e GERENTE podem acessar os principais módulos operacionais da aplicação.

## SWAGGER

Com a aplicação em execução, a documentação da API pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

## TESTES

Os endpoints foram testados utilizando Postman.

Foram realizados testes de:

==> POST
==> GET
==> GET por ID
==> PUT
==> DELETE
==> autenticação
==> geração de JWT
==> acesso autorizado
==> acesso negado
==> validação de CNPJ
==> validação de e-mail
==> unidade ativa e inativa
==> estoque
==> vendas
==> royalties
==> chamados
==> consultas e relatórios

A Collection utilizada nos testes está disponível na pasta:

```text
postman/
```

## INSTALAÇÃO

Clone o repositório:

```bash
git clone https://github.com/Yan-serra/Gestao-Franquias
```

No Eclipse:

```text
File
↓
Import
↓
Existing Maven Projects
```

Selecione a pasta do projeto e conclua a importação.

É necessário possuir PostgreSQL instalado.

Crie o banco:

```text
Franquias-DB
```

Os scripts SQL utilizados para criação e dados de exemplo estão disponíveis na pasta:

```text
database/
```

Configure no Eclipse a variável de ambiente:

```text
DB_PASSWORD
```

com a senha do PostgreSQL utilizado na máquina.

## EXECUÇÃO DO PROJETO

No Eclipse, localize:

```text
GestaoFranquiasApplication.java
```

Depois:

```text
Botão direito
↓
Run As
↓
Spring Boot App
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

## VERSIONAMENTO

O projeto utiliza Git e GitHub para controle de versão.

Foram realizados commits durante o desenvolvimento para registrar as principais etapas e alterações realizadas.

## AUTOR

Yan Kevin dos Santos Serra

## LICENÇA

Projeto desenvolvido exclusivamente para fins acadêmicos.