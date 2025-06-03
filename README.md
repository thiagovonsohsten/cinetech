# 🎬 Cinetech – Sistema Web de Cinema

**Cinetech** é um sistema web para gestão de um cinema moderno, permitindo o controle completo de filmes, sessões, assentos, ingressos, avaliações, fidelidade e reservas para eventos. Este projeto é construído com **Spring Boot 3.3.0, JPA, PostgreSQL** no backend.

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.0
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Lombok
- Cucumber (para testes BDD)
- Maven

---

## 📌 Funcionalidades Implementadas

1. **Gerenciamento de Filmes**
   - Cadastro e atualização de filmes
   - Remoção automática após fim de exibição
   - Classificação etária para cada filme
   - Controle de período de exibição
   - Sistema de avaliação com notas e comentários
   - Filtro automático de conteúdo ofensivo em comentários
   - Remoção automática de filmes com avaliação média abaixo de 2.5

2. **Gerenciamento de Sessões**
   - Criação e controle de sessões
   - Bloqueio automático de sessão lotada
   - Emissão automática de crédito em caso de cancelamento

3. **Gerenciamento de Ingressos**
   - Seleção de assentos
   - Emissão de ingressos
   - Controle de disponibilidade

4. **Gerenciamento de Usuários**
   - Cadastro e autenticação
   - Sistema de fidelidade

5. **Gerenciamento de Salas**
   - Configuração de salas
   - Controle de capacidade

6. **Gerenciamento de Clientes**
   - Cadastro de clientes
   - Histórico de compras
   - Sistema de avaliação de filmes
   - Restrição de avaliação apenas para filmes assistidos

7. **Gerenciamento de Créditos**
   - Emissão automática de créditos em caso de cancelamento
   - Controle de validade dos créditos
   - Aplicação de créditos em novas compras

8. **Gerenciamento de Promoções**
   - Cadastro de promoções
   - Aplicação automática de descontos
   - Validação de regras de negócio

9. **Gerenciamento de Avaliações**
   - Sistema de avaliação com notas (1-5) e comentários
   - Filtro automático de conteúdo ofensivo
   - Moderação de comentários
   - Cálculo de média de avaliações por filme
   - Remoção automática de filmes com avaliação baixa

---

## 🏗️ Arquitetura do Projeto

O projeto segue os princípios de Domain-Driven Design (DDD) com a seguinte estrutura:

```
src/main/java/com/cinetech/api/
├── aplicacao/      # Casos de uso e serviços
├── dominio/        # Entidades e regras de negócio
├── infraestrutura/ # Implementações técnicas
└── ApiApplication.java
```

---

## 🚀 Como Executar

### Pré-requisitos

1. Java 17 ou superior
2. Maven
3. PostgreSQL 12 ou superior
4. Node.js 16 ou superior (para o frontend)
5. npm ou yarn (para o frontend)

### Configuração do Banco de Dados

1. Instale o PostgreSQL se ainda não tiver instalado
2. Crie um banco de dados chamado `cinetech`:
   ```sql
   CREATE DATABASE cinetech;
   ```
3. Configure o arquivo `src/main/resources/application.properties` com as credenciais do seu PostgreSQL:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5433/cinetech
   spring.datasource.username=postgres
   spring.datasource.password=SUA_SENHA_AQUI
   ```

### Executando o Backend

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/cinetech.git
   cd cinetech
   ```

2. Execute o projeto usando Maven:
   ```bash
   ./mvnw spring-boot:run
   # ou
   mvn spring-boot:run
   ```

3. O Flyway executará automaticamente as migrações do banco de dados
4. A API estará disponível em `http://localhost:8080`

### Executando o Frontend

1. Navegue até a pasta do frontend:
   ```bash
   cd frontend
   ```

2. Instale as dependências:
   ```bash
   npm install
   # ou
   yarn install
   ```

3. Inicie o servidor de desenvolvimento:
   ```bash
   npm start
   # ou
   yarn start
   ```

4. O frontend estará disponível em `http://localhost:3000`

### Verificando se está tudo funcionando

1. Verifique se o backend está rodando acessando:
   ```
   http://localhost:8080/api/filmes
   ```
   Deve retornar uma lista (vazia inicialmente) de filmes.

2. Verifique se o frontend está rodando acessando:
   ```
   http://localhost:3000
   ```
   Deve mostrar a página inicial do sistema.

### Solução de Problemas

Se encontrar algum erro:

1. Verifique se o PostgreSQL está rodando
2. Verifique se as credenciais do banco estão corretas
3. Verifique se o banco `cinetech` foi criado
4. Verifique se as migrações foram executadas corretamente
5. Verifique os logs da aplicação para mais detalhes

---

## 🧪 Testes

O projeto utiliza Cucumber para testes de comportamento (BDD). Para executar os testes:

```bash
./mvnw test
# ou
mvn test
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

## 🎯 Padrões de Projeto Implementados

O projeto utiliza diversos padrões de projeto para garantir uma arquitetura limpa, manutenível e extensível:

### Strategy Pattern
- **Objetivo**: Encapsular diferentes algoritmos de desconto
- **Arquivos**:
  - `src/main/java/com/cinetech/api/dominio/modelos/pagamento/EstrategiaDesconto.java` (Interface)
  - `src/main/java/com/cinetech/api/dominio/modelos/pagamento/DescontoFidelidade.java` (Implementação)
  - `src/main/java/com/cinetech/api/dominio/modelos/pagamento/DescontoPromocao.java` (Implementação)

### Observer Pattern
- **Objetivo**: Notificar componentes sobre mudanças no estado das sessões
- **Arquivos**:
  - `src/main/java/com/cinetech/api/dominio/modelos/sessao/ObservadorSessao.java` (Interface)
  - `src/main/java/com/cinetech/api/dominio/modelos/pagamento/ProcessadorPagamento.java` (Implementação)

### Iterator Pattern
- **Objetivo**: Iterar sobre os assentos de uma sala de forma padronizada
- **Arquivos**:
  - `src/main/java/com/cinetech/api/dominio/modelos/sala/Assento.java`
  - `src/main/java/com/cinetech/api/dominio/modelos/sala/AssentoIterator.java`
  - `src/main/java/com/cinetech/api/dominio/modelos/sala/Sala.java` (Implementa Iterable)

### Value Object Pattern
- **Objetivo**: Representar identificadores imutáveis
- **Arquivos**:
  - `src/main/java/com/cinetech/api/dominio/modelos/filme/FilmeId.java`
  - `src/main/java/com/cinetech/api/dominio/modelos/sala/SalaId.java`
  - `src/main/java/com/cinetech/api/dominio/modelos/sessao/SessaoId.java`
  - `src/main/java/com/cinetech/api/dominio/modelos/cliente/ClienteId.java`

### Repository Pattern
- **Objetivo**: Abstrair a persistência de dados
- **Arquivos**:
  - `src/main/java/com/cinetech/api/dominio/repositorios/FilmeRepository.java`
  - `src/main/java/com/cinetech/api/dominio/repositorios/SalaRepository.java`
  - `src/main/java/com/cinetech/api/dominio/repositorios/SessaoRepository.java`

### Factory Pattern
- **Objetivo**: Encapsular a criação de objetos complexos
- **Arquivos**:
  - `src/main/java/com/cinetech/api/dominio/modelos/filme/FilmeFactory.java`
  - `src/main/java/com/cinetech/api/dominio/modelos/sessao/SessaoFactory.java`

### Mapper Pattern
- **Objetivo**: Converter entre objetos de domínio e entidades JPA
- **Arquivos**:
  - `src/main/java/com/cinetech/api/infraestrutura/persistencia/mapper/FilmeMapper.java`
  - `src/main/java/com/cinetech/api/infraestrutura/persistencia/mapper/SalaMapper.java`
  - `src/main/java/com/cinetech/api/infraestrutura/persistencia/mapper/SessaoMapper.java`

---

## 👥 Equipe

Integrantes: Thiago von Sohsten, Enzo Nunes, Sérgio Gouveia, Thiago Belo, Gustavo Carneiro, Guilherme Alencar, José Jorge, Kauan Novello e Henrique Lobo


