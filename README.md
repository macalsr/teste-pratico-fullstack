# Teste prático Full Stack

## Back-end
### Tecnologias Utilizadas

- Java 19
- Spring Boot 3.1.0
- Maven 3.9.2
- Docker (última versão)
- Docker-compose (última versão)
- PostgreSQL (última versão)

### Como Executar

Antes de executar a aplicação, certifique-se de ter o Docker e o Docker-compose instalados na sua máquina.

1. Clone o repositório para sua máquina local:

    ```
    git clone https://github.com/macalsr/teste-pratico-fullstack.git
    ```

2. Navegue ate a pasta do Docker para iniciar o serviço do banco de dados:

    ```
    cd docker
    ```
3. Execute o Docker-compose para criar e iniciar o serviço do banco de dados:

    ```
    docker-compose up -d
    ```

4. Navegue até o diretório do projeto:

    ```
    cd api
    ```

5. Execute a aplicação Spring Boot com o Maven:

    ```
    mvn spring-boot:run
    ```

6. Acesse a aplicação em [http://localhost:8080](http://localhost:8080).

### Caminhos dos Controllers

- **ContatosController:** `/contatos`
- **ProfissionalController:** `/profissionais`

### Endpoints

### Contacts

- **GET /contatos**
    - Response: Lista de contatos com base nos critérios definidos em Params
    - Params:
        - q - String: Filtro para buscar contatos que contenham o texto em qualquer um de seus atributos
        - fields - List<String>: Opcional. Quando presente apenas os campos listados em fields deverão ser retornados
- **GET /contatos/:id**
    - Response: Todos os dados do contato que possui o ID passado na URL
- **POST /contatos**
    - Body: Content-type: Json
    - Response: Sucesso contato com id {ID} cadastrado
- **PUT /contatos/:id**
    - Body: Content-type: Json
    - Response: Sucesso cadastrado alterado
- **DELETE /contatos/:id**
    - Response: Sucesso contato excluído

### Profissionais

- **GET /profissionais**
    - Response: Lista de profissionais com base nos critérios definidos em Params
    - Params:
        - q - String: Filtro para buscar profissionais que contenham o texto em qualquer um de seus atributos
        - fields - List<String>: Opcional. Quando presente apenas os campos listados em fields deverão ser retornados
- **GET /profissionais/:id**
    - Response: Todos os dados do profissional que possui o ID passado na URL
- **POST /profissionais**
    - Body: Content-type: Json
    - Response: Sucesso profissional com id {ID} cadastrado
- **PUT /profissionais/:id**
    - Body: Content-type: Json
    - Response: Sucesso cadastrado alterado
- **DELETE /profissionais/:id**
    - Response: Sucesso contato excluído

## Front-End

### Instruções de Uso

1. Clone o repositório do projeto.
2. Navegue ate a pasta front-end.
3. Instale as dependências utilizando o comando `npm install`.
4. Inicie o servidor de desenvolvimento com o comando `ng serve`.
5. Acesse a aplicação em seu navegador através do endereço `http://localhost:4200`.
6. Para acessar o projeto, utilize as seguintes credenciais:

- **Login:** admin@admin.com
- **Senha:** 123

### Tecnologias Utilizadas

- Angular: Framework utilizado para o desenvolvimento do Front-end.
- Angular Material: Biblioteca de componentes utilizada para a interface do usuário.
- TypeScript: Linguagem de programação utilizada para o desenvolvimento do Angular.
- HTML e CSS: Utilizados para a estruturação e estilização da aplicação.

### Considerações Finais

Este projeto foi desenvolvido de acordo com os requisitos fornecidos, seguindo as melhores práticas do Angular e utilizando as funcionalidades do Angular Material.

### Autores

Maria Carolina Santana Ribeiro

