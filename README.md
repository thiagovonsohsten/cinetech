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

1. Clone o repositório
2. Certifique-se de ter o Java 17 instalado
3. Instale o PostgreSQL e crie um banco chamado `cinetech`
4. Configure o arquivo `src/main/resources/application.properties` com as credenciais do seu PostgreSQL:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5433/cinetech
   spring.datasource.username=postgres
   spring.datasource.password=SUA_SENHA_AQUI
   ```
5. Execute o projeto usando Maven:
   ```bash
   ./mvnw spring-boot:run
   # ou
   mvn spring-boot:run
   ```
6. A aplicação estará disponível em `http://localhost:8080`

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

## 👥 Equipe

Integrantes: Thiago von Sohsten, Enzo Nunes, Sérgio Gouveia, Thiago Belo, Gustavo Carneiro, Guilherme Alencar, José Jorge, Kauan Novello e Henrique Lobo


