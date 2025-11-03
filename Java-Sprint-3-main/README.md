
# Integrantes

- Alexsandro Macedo: RM557068
- Leonardo Faria Salazar: RM557484
- Guilherme Felipe da Silva Souza: RM558282

# Eficientiza - Sistema de Gerenciamento de Motos

## Descrição

O **Eficientiza** é um sistema de gerenciamento de motos que permite o cadastro, controle e monitoramento de motos e vagas. A aplicação permite que administradores (ADMIN) e operadores (OPERADOR) gerenciem e acompanhem as movimentações das motos no estacionamento, incluindo a visualização do histórico, a gestão das vagas e a administração dos usuários.

# Acesso ao sistema:

Para acessar o sistema pode ser usado as seguintes credenciais:

**Acesso como ADMIN**

- E-mail: admin@gmail.com

- Senha: admin

**Acesso como OPERADOR**

- E-mail: operador@gmail.com

- Senha: operador

### Imagens do sistema
<img width="1364" height="632" alt="image" src="https://github.com/user-attachments/assets/287e072a-362a-40e2-b1de-d8b5f1f6a724" />
<img width="1365" height="632" alt="image" src="https://github.com/user-attachments/assets/82cdf906-cfcf-413c-a54e-7bf720849648" />
<img width="1353" height="634" alt="image" src="https://github.com/user-attachments/assets/bd405154-677a-47b3-85b5-b7ad38d506bc" />
<img width="1352" height="638" alt="image" src="https://github.com/user-attachments/assets/a8f81465-89cc-4eea-8dbe-c4c7fbaadafc" />
<img width="1346" height="633" alt="image" src="https://github.com/user-attachments/assets/7fe0eba0-1eb8-408a-80d9-b8e5cd56b16c" />
<img width="1347" height="633" alt="image" src="https://github.com/user-attachments/assets/a93c2f7a-4e2e-4f6b-8e1d-24c53c64cb02" />







### Funcionalidades:

- **Cadastro de Motos**: Adicionar, editar e excluir motos.
- **Cadastro e Gestão de Vagas**: Monitoramento de vagas, status e a moto associada.
- **Histórico de Movimentações**: Acompanhamento do histórico de entrada e saída das motos.
- **Gestão de Usuários**: Administradores podem gerenciar usuários, atribuindo roles como ADMIN ou OPERADOR.
- **Login e Autenticação**: Sistema de autenticação baseado em **Spring Security**, com validação de senha e roles.

## Tecnologias Utilizadas

- **Spring Boot 3.x**
- **Spring Security** (para autenticação e autorização)
- **Thymeleaf** (para renderização de templates HTML)
- **Spring Data JPA** (para interação com o banco de dados)
- **H2 Database** (ou outro banco de dados configurável, como MySQL ou PostgreSQL)
- **Tailwind CSS** (para a estilização da interface)

## Estrutura do Projeto

A arquitetura do projeto segue o padrão **MVC** (Model-View-Controller), onde a lógica de negócios é separada da apresentação, e a interação com o banco de dados é feita por meio de **Repositories**.

### **Models**

As **models** representam as entidades do sistema e são responsáveis por mapear as tabelas do banco de dados.

- **`Usuario`**: Representa os usuários do sistema com os campos `id`, `nome`, `email`, `senha` e `tipo` (admin ou operador).
- **`Moto`**: Representa as motos, com informações como `placa`, `modelo`, `status`, etc.
- **`Vaga`**: Representa as vagas de estacionamento, com `id`, `status` de ocupação e a `motoId` associada.
- **`HistoricoMoto`**: Representa o histórico de movimentações das motos, incluindo a data de entrada e saída da moto.

### **Services**

Os **services** contêm a lógica de negócios e são responsáveis pela manipulação dos dados da aplicação.

- **`UsuarioService`**: Responsável por autenticar e gerenciar os usuários.
- **`MotoService`**: Manipula as operações de moto (adicionar, editar, excluir).
- **`VagaService`**: Realiza operações sobre as vagas de estacionamento.
- **`HistoricoMotoService`**: Registra e manipula as movimentações das motos.

### **Controllers**

Os **controllers** são responsáveis por mapear as requisições HTTP e interagir com os services para fornecer os dados corretos para as views.

- **`UsuarioController`**: Controla a página de login e a gestão de usuários.
- **`MotoController`**: Gerencia a listagem e os formulários para o cadastro de motos.
- **`VagaController`**: Gerencia a listagem e o controle de vagas.
- **`HistoricoMotoController`**: Controla o histórico de movimentações das motos.

### **Segurança**

A segurança é gerenciada pelo **Spring Security**. Ele utiliza autenticação baseada em **usuário e senha** com dois tipos de roles:
- **ADMIN**: Pode acessar todas as funcionalidades do sistema.
- **OPERADOR**: Tem acesso restrito, podendo visualizar e editar motos e vagas, mas não pode acessar páginas restritas como o gerenciamento de usuários.

### **Estrutura de Banco de Dados**

A aplicação utiliza um banco de dados relacional, e a estrutura de tabelas é composta por:

- **tb_mtt_usuario_c3_java**: Tabela de usuários, contendo informações como nome, e-mail, senha e tipo de usuário.
- **tb_mtt_moto_c3_java**: Tabela que armazena informações sobre as motos, como placa e modelo.
- **tb_mtt_vaga_c3_java**: Tabela de vagas de estacionamento, com o status de ocupação e a referência à moto associada.
- **tb_mtt_historico_moto_c3_java**: Tabela que registra o histórico de movimentações das motos no estacionamento.

## Instalação e Execução

### Pré-requisitos

Antes de executar o projeto, você precisará ter as seguintes ferramentas instaladas:

- **Java 17** ou superior
- **Maven** ou **Gradle** para gerenciar dependências
- **Banco de Dados** (H2, MySQL ou PostgreSQL)

### Passo 1: Clone o repositório

```bash
git clone https://github.com/seu-repositorio/eficientiza.git
```

### Passo 2: Navegue até o diretório do projeto

```bash
cd eficientiza
```

### Passo 3: Instalar as dependências e compilar o projeto

Se estiver usando **Maven**:

```bash
mvn clean install
```

Se estiver usando **Gradle**:

```bash
gradle build
```

### Passo 4: Configuração do banco de dados

O **Eficientiza** já vem configurado para usar o **H2 Database** para desenvolvimento, mas você pode alterar para outro banco de dados no arquivo **`application.properties`**.

Exemplo para usar **MySQL**:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/eficientiza
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

### Passo 5: Executar a aplicação

Para rodar a aplicação, execute o comando:

Se estiver usando **Maven**:

```bash
mvn spring-boot:run
```

Se estiver usando **Gradle**:

```bash
gradle bootRun
```

### Passo 6: Acessando a aplicação

A aplicação estará disponível em **http://localhost:8080**. Você pode acessar as seguintes URLs:

- **`/login`**: Página de login
- **`/home`**: Página inicial após login
- **`/motos`**: Lista de motos
- **`/vagas`**: Lista de vagas
- **`/usuarios`**: Gestão de usuários (somente para ADMIN)
- **`/historico-motos`**: Gestão de historico (somente para ADMIN)

### Estrutura de Arquivos

```plaintext
src/
├── main/
│   ├── java/
│   │   └── br/com/fiap/eficientiza_challenge_03/
│   │       ├── controller/  # Controllers
│   │       ├── model/       # Entidades (Models)
│   │       ├── repository/  # Repositórios JPA
│   │       ├── service/     # Services
│   │       └── config/      # Configurações do Spring Security
│   └── resources/
│       ├── db.migration/    # Versionamento do banco de dados com Flyway
│       ├── templates/       # Páginas Thymeleaf
│       ├── application.properties  # Configurações do banco de dados
│       └── static/          # Arquivos estáticos (CSS, JS, Imagens)
└── test/                   # Testes unitários e integração
```

