# 🎬 Cinetech – Sistema Web de Cinema

**Cinetech** é um sistema web para gestão de um cinema moderno, permitindo o controle completo de filmes, sessões, assentos, ingressos, avaliações, fidelidade e reservas para eventos. Este projeto é construído com **Spring Boot 3.2.x, JPA, PostgreSQL** no backend.

---

## 📋 Histórias Implementadas

### Primeira Entrega

1. **Gerenciamento de Filmes**
   - [F1] Cadastro de filmes com informações básicas (título, gênero, duração, idioma, classificação etária)
   - [F2] Remoção automática após fim de exibição
   - [F3] Remoção automática de filmes com avaliação baixa (< 2.5)

2. **Gerenciamento de Sessões**
   - [F4] Criação de sessões com controle de lotação
   - [F5] Emissão de crédito em caso de cancelamento

3. **Gerenciamento de Ingressos**
   - [F6] Seleção e reserva de assentos
   - [F7] Emissão de ingressos

### Segunda Entrega

1. **Sistema de Clientes**
   - [F8] Cadastro de clientes com validação de CPF e email
   - [F9] Controle de perfil de cliente (comum, VIP)

2. **Sistema de Fidelidade**
   - [F10] Acúmulo de pontos por compras
   - [F11] Aplicação de descontos por fidelidade

3. **Sistema de Promoções**
   - [F12] Cadastro de promoções por horário
   - [F13] Cadastro de promoções por perfil (meia-entrada)

---

## 🛠️ Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3.2.x
- Spring Data JPA
- Spring Validation
- PostgreSQL 15+
- Flyway (migrações)
- Lombok
- MapStruct (mapeamento)
- JUnit 5
- Maven

### Frontend
- React 18
- TypeScript
- Material-UI
- Axios
- React Query

---

## 🏗️ Arquitetura do Projeto

O projeto segue os princípios de Clean Architecture e Domain-Driven Design (DDD):

```
src/main/java/com/cinetech/api/
├── aplicacao/           # Casos de uso e serviços
│   ├── casosdeuso/     # Implementações dos casos de uso
│   └── servicos/       # Serviços de aplicação
├── dominio/            # Entidades e regras de negócio
│   ├── modelos/        # Entidades de domínio
│   ├── portas/         # Interfaces (ports)
│   ├── repositorios/   # Interfaces de repositório
│   └── servicos/       # Serviços de domínio
└── infraestrutura/     # Implementações técnicas
    ├── persistencia/   # Implementações de repositório
    │   ├── entidade/   # Entidades JPA
    │   ├── jpa/        # Repositórios JPA
    │   └── mapper/     # Mapeadores
    ├── web/           # Controllers e DTOs
    └── config/        # Configurações
```

### Padrões de Projeto Implementados

1. **Value Object Pattern**
   - **Objetivo**: Representar identificadores imutáveis
   - **Arquivos**:
     - `src/main/java/com/cinetech/api/dominio/modelos/filme/FilmeId.java`
     - `src/main/java/com/cinetech/api/dominio/modelos/sessao/SessaoId.java`
     - `src/main/java/com/cinetech/api/dominio/modelos/cliente/ClienteId.java`

2. **Repository Pattern**
   - **Objetivo**: Abstrair a persistência de dados
   - **Arquivos**:
     - `src/main/java/com/cinetech/api/dominio/repositorios/FilmeRepositorio.java`
     - `src/main/java/com/cinetech/api/dominio/repositorios/SessaoRepositorio.java`
     - `src/main/java/com/cinetech/api/dominio/repositorios/ClienteRepositorio.java`

3. **Factory Pattern**
   - **Objetivo**: Encapsular a criação de objetos complexos
   - **Arquivos**:
     - `src/main/java/com/cinetech/api/dominio/modelos/filme/FilmeId.java` (método `novo()`)
     - `src/main/java/com/cinetech/api/dominio/modelos/sessao/SessaoId.java` (método `novo()`)
     - `src/main/java/com/cinetech/api/dominio/modelos/cliente/ClienteId.java` (método `novo()`)

4. **DTO Pattern**
   - **Objetivo**: Transferir dados entre camadas
   - **Arquivos**:
     - `src/main/java/com/cinetech/api/infraestrutura/web/dto/ClienteDTO.java`

5. **Mapper Pattern**
   - **Objetivo**: Converter entre objetos de domínio e entidades JPA
   - **Arquivos**:
     - `src/main/java/com/cinetech/api/infraestrutura/persistencia/mapper/FilmeMapper.java`
     - `src/main/java/com/cinetech/api/infraestrutura/persistencia/mapper/SessaoMapper.java`
     - `src/main/java/com/cinetech/api/infraestrutura/persistencia/mapper/ClienteMapper.java`

---

## 🚀 Como Executar

### Pré-requisitos

1. Java 17 ou superior
2. Maven 3.8+
3. PostgreSQL 15+
4. Node.js 18+ (para o frontend)
5. npm 9+ ou yarn 1.22+

### Configuração do Banco de Dados

1. Instale o PostgreSQL
2. Crie o banco de dados:
   ```sql
   CREATE DATABASE cinetech;
   ```
3. Configure o `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/cinetech
   spring.datasource.username=postgres
   spring.datasource.password=SUA_SENHA_AQUI
   spring.jpa.hibernate.ddl-auto=validate
   spring.flyway.enabled=true
   ```

### Executando o Backend

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/cinetech.git
   cd cinetech
   ```

2. Execute o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```

3. A API estará disponível em:
   - API REST: `http://localhost:8080/api`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - OpenAPI: `http://localhost:8080/v3/api-docs`

### Executando o Frontend

1. Navegue até a pasta do frontend:
   ```bash
   cd frontend
   ```

2. Instale as dependências:
   ```bash
   npm install
   ```

3. Inicie o servidor:
   ```bash
   npm start
   ```

4. O frontend estará disponível em:
   - Interface Web: `http://localhost:3000`
   - Página Inicial: `http://localhost:3000/home`

### Verificando se está tudo funcionando

1. Backend:
   - Acesse `http://localhost:8080/api/filmes` para ver a lista de filmes
   - Acesse `http://localhost:8080/swagger-ui.html` para ver a documentação da API

2. Frontend:
   - Acesse `http://localhost:3000` para ver a página inicial
   - Faça login com as credenciais padrão:
     - Usuário: `admin@cinetech.com`
     - Senha: `admin123`

---

## 🧪 Testes

O projeto utiliza JUnit 5 para testes unitários e de integração:

```bash
./mvnw test
```

---

## 📚 Documentação

### ✅ Descrição do Domínio (Linguagem Onipresente)
> Descrição detalhada do domínio com seus termos e contextos de negócio, seguindo os princípios de Domain-Driven Design.

📄 [Descrição do Domínio - Linguagem Onipresente](https://docs.google.com/document/d/1ljjS3MdCNJ1ICzl2iHkGtTbbmUeqh5sGUR-j-Z_dQPY/edit?usp=sharing)

---

### ✅ Context Map Diagram
> Modelo(s) do(s) subdomínio(s) desenvolvido com o Context Mapper (arquivo CML)

📄 [Diagramas UML gerados pela ferramenta](https://drive.google.com/drive/folders/1UUFX-MXj5uJZiZsWHHebn5MnIQWp-EC4?usp=sharing)

---

### ✅ Mapa de Histórias do Usuário
> Estrutura visual e textual das jornadas dos usuários do sistema com foco em suas experiências e funcionalidades.

📄 [Mapa de Histórias do Usuário (PDF/PNG)](https://drive.google.com/drive/folders/1jvSpfGGg1DuddcfuCANlJetDbwfmFGyl?usp=sharing)

---

### ✅ Protótipos de Baixa Fidelidade
> Protótipos esboçados das principais telas do sistema, apresentando navegação e experiência do usuário.

📄 [Protótipos Baixa Fidelidade (Figma)](https://www.figma.com/design/ZJobBqNk6vtbl5q4hxJP8t/Untitled?node-id=1-3&t=yAPDriBurVbDJS1F-1)

---

### ✅ Cenários BDD (Behavior-Driven Development)
> Modelos Given-When-Then para especificação e automação de testes com foco nas regras de negócio.

📄 [Cenários BDD - Funcionalidades Implementadas (PDF ou Markdown)](https://docs.google.com/document/d/1NpiIm_egSG-9yo--d5ruzQnb9pZ8-D8GTQ4E4FA0H8g/edit?usp=drivesdk)

---

## 👥 Equipe

Integrantes: Thiago von Sohsten, Enzo Nunes, Sérgio Gouveia, Thiago Belo, Gustavo Carneiro, Guilherme Alencar, José Jorge, Kauan Novello e Henrique Lobo
Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
