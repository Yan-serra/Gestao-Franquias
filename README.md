````text
# GESTÃO DE FRANQUIAS

## DESCRIÇÃO

Este projeto acadêmico foi desenvolvido em Java com Spring Boot para a criação de uma API REST voltada ao gerenciamento de franquias. A aplicação permite cadastrar, consultar, 
atualizar e excluir informações relacionadas a franqueadoras, unidades franqueadas, usuários, perfis, categorias e produtos.
O sistema utiliza PostgreSQL como banco de dados e Spring Security com JWT para realizar a autenticação dos usuários e proteger os endpoints da aplicação.

## OBJETIVO

Desenvolver uma aplicação back-end para o gerenciamento de franquias, aplicando conceitos de API REST, banco de dados, operações CRUD, validações, autenticação e segurança.

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
Gestao-Franquias/

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
├── pom.xml
└── README.md
```

## FUNCIONALIDADES

==> Cadastro, consulta, atualização e exclusão de registros.
==> Gerenciamento de franqueadoras.
==> Gerenciamento de unidades franqueadas.
==> Gerenciamento de usuários e perfis.
==> Gerenciamento de categorias e produtos.
==> Validação de CNPJ duplicado.
==> Validação de e-mail duplicado.
==> Ativação e inativação de usuários.
==> Autenticação com JWT.
==> Proteção de endpoints.
==> Controle de acesso por perfil.

## BANCO DE DADOS

O projeto utiliza PostgreSQL como banco de dados.

O banco utilizado pela aplicação é:

```text
franquias-DB
```

A conexão com o banco é configurada no arquivo:

```text
src/main/resources/application.properties
```

Exemplo de configuração:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/franquias-DB
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA
```

## AUTENTICAÇÃO

A aplicação utiliza Spring Security com JWT para realizar a autenticação dos usuários, trazendo mais segurança e confiança,
 tanto para o cliente quanto o usuario que for utilizar esse sistema.

O login é realizado pelo endpoint:

```http
POST /auth/login
```

Após o login, o sistema gera um token JWT que deve ser enviado nas requisições protegidas:

```text
Authorization: Bearer TOKEN_JWT
```

## PERFIS DE ACESSO

Entre os perfis utilizados no sistema estão:

```text
ADMIN
GERENTE
```

O acesso a determinados endpoints depende do perfil do usuário autenticado.

## SWAGGER

Com a aplicação em execução, a documentação da API pode ser acessada pelo endereço:

```text
http://localhost:8080/swagger-ui/index.html
```

## TESTES

Os endpoints da aplicação foram testados utilizando o Postman.

Foram realizados testes de:

==> POST
==> GET
==> GET por ID
==> PUT
==> DELETE
==> Validações
==> Autenticação
==> Geração e utilização do token JWT
==> Endpoints protegidos

````text

## INSTALAÇÃO

Para executar o projeto em outra máquina, primeiro clone o repositório:

```bash
git clone https://github.com/Yan-serra/Gestao-Franquias
````

Depois, importe o projeto no Eclipse utilizando a opção:

```text
File
↓
Import
↓
Existing Maven Projects
```

Selecione a pasta do projeto e finalize a importação.

Antes de executar a aplicação, é necessário possuir o PostgreSQL instalado e criar o banco de dados:

```text
franquias-DB
```

Depois, configure o usuário e a senha do PostgreSQL no arquivo:

```text
src/main/resources/application.properties
```

```
```


## EXECUÇÃO DO PROJETO

No Eclipse, localize a classe principal da aplicação.

Depois:

```text
Botão direito na classe principal
        ↓
Run As
        ↓
Spring Boot App
```

Com a aplicação iniciada corretamente, ela ficará disponível normalmente em:

```text
http://localhost:8080
```

## VERSIONAMENTO

O projeto utiliza Git e GitHub para controle de versão.

Durante o desenvolvimento, foram realizados commits para registrar as principais etapas e alterações do projeto.

## AUTOR

Yan Kevin dos Santos Serra

## LICENÇA

Projeto desenvolvido exclusivamente para fins acadêmicos.
````
