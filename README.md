# 🚀 Ventrya — Fluxo e Regras de Negócio

## 📌 1. Visão Geral

O **Ventrya** é uma plataforma voltada à empregabilidade jovem, permitindo que estudantes adquiram experiências práticas por meio de desafios, recebam avaliações com Inteligência Artificial, construam um portfólio profissional e pratiquem entrevistas de emprego.

**Tecnologias:** Java 21, Spring Boot, Spring Security, JWT, Spring Data JPA, Oracle Database, Flyway, Spring AI e RabbitMQ.

---

## 🔄 2. Fluxo Geral da Aplicação

1. **Cadastro e login:** estudante cria sua conta e realiza autenticação via JWT.
2. **Exploração de desafios:** consulta organizações fictícias e filtra desafios por área profissional.
3. **Candidatura:** estudante se candidata a um desafio aberto e dentro do prazo. A aprovação é automática.
4. **Envio da solução:** estudante envia a descrição e o link do projeto. A entrega é definitiva.
5. **Feedback automático:** Spring AI avalia a solução e gera comentários, nota, pontos fortes e melhorias.
6. **Portfólio automático:** após o feedback, a IA gera uma experiência profissional baseada na solução.
7. **Entrevista simulada:** estudante escolhe uma área e responde a cinco perguntas comuns de RH.
8. **Avaliação da entrevista:** IA avalia as respostas e gera feedback individual e geral.

> **Observação:** os fluxos de geração automática com IA e RabbitMQ ainda serão implementados.

---

## ⚙️ 3. Services e Regras de Negócio

### 🔐 AuthService — Autenticação

**Responsabilidade:** cadastro e login.

- Impede cadastro de e-mails duplicados.
- Armazena senhas criptografadas com BCrypt.
- Atribui automaticamente a role `STUDENT` no cadastro público.
- Valida as credenciais no login.
- Gera token JWT para autenticação.

**Métodos:** `register()` e `login()`.

### 👤 UserService — Perfil

**Responsabilidade:** consulta dos dados do usuário autenticado.

- Identifica o usuário pelo e-mail autenticado.
- Retorna os dados do perfil sem expor a senha.
- Impede acesso ao perfil privado de outros usuários.
- Retorna erro quando o usuário não é encontrado.

**Método:** `getMyProfile()`.

### 🏢 OrganizationService — Organizações

**Responsabilidade:** consulta das organizações vinculadas aos desafios.

- Organizações fictícias cadastradas previamente no banco.
- Permite listar organizações com paginação.
- Permite consultar organizações pelo ID.
- Não disponibiliza cadastro, edição ou exclusão pelo aplicativo.

**Métodos:** `findAll()` e `findById()`.

### 🎯 ChallengeService — Desafios

**Responsabilidade:** catálogo e consulta de desafios.

- Desafios cadastrados previamente no banco.
- Cada desafio pertence a uma organização.
- Permite listar desafios com paginação.
- Permite filtrar por área profissional.
- Permite consultar desafios por organização e ID.
- A disponibilidade para candidatura é validada no `ApplicationService`.

**Métodos:** `findAll()`, `findById()`, `findByProfessionalArea()` e `findByOrganization()`.

### 📝 ApplicationService — Candidaturas

**Responsabilidade:** gerenciar candidaturas aos desafios.

- Apenas estudantes podem se candidatar.
- O desafio deve existir e possuir status `OPEN`.
- Não permite candidaturas após o prazo do desafio.
- Cada estudante pode se candidatar apenas uma vez ao mesmo desafio.
- A candidatura é vinculada ao estudante autenticado.
- Aprovação automática com status `APPROVED`.
- Estudantes só podem consultar suas próprias candidaturas.

**Métodos:** `apply()`, `findById()` e `findMyApplications()`.

### 📤 SubmissionService — Entregas

**Responsabilidade:** registrar soluções enviadas pelos estudantes.

- Apenas estudantes autenticados podem enviar soluções.
- A candidatura deve pertencer ao estudante e estar `APPROVED`.
- Não permite entregas após o prazo do desafio.
- Cada candidatura permite apenas uma entrega.
- A primeira entrega é definitiva, sem edição ou exclusão.
- Registra descrição, URL do projeto e data de envio.
- Nova entrega recebe status `SUBMITTED`.
- Estudantes só podem consultar suas próprias entregas.

**Métodos:** `submit()`, `findById()` e `findByApplication()`.

### 🤖 FeedbackService — Avaliações

**Responsabilidade:** disponibilizar avaliações das soluções.

- Cada feedback pertence a uma entrega (`Submission`).
- Apenas o estudante responsável pode consultar a avaliação.
- A avaliação contém comentário, nota, pontos fortes e sugestões de melhoria.
- Estudantes não podem criar ou modificar feedbacks.
- A geração automática será realizada pelo Spring AI.
- O processamento assíncrono será integrado ao RabbitMQ.

**Métodos atuais:** `findById()` e `findBySubmission()`.

### 💼 PortfolioService — Portfólio

**Responsabilidade:** disponibilizar experiências profissionais geradas.

- Cada portfólio pertence a um estudante.
- Cada registro está associado a uma entrega.
- O estudante só pode consultar seus próprios portfólios.
- A geração será automática após a conclusão do feedback.
- A IA utilizará o desafio, a solução e a avaliação para gerar o conteúdo.
- Cada entrega deverá gerar no máximo um portfólio.

**Métodos atuais:** `findById()`, `findMyPortfolios()` e `findBySubmission()`.

### 🎤 InterviewService — Entrevistas

**Responsabilidade:** gerenciar entrevistas simuladas com cinco perguntas.

- Apenas estudantes autenticados podem iniciar entrevistas.
- O estudante escolhe uma área profissional.
- Cada entrevista possui exatamente cinco perguntas de RH.
- As perguntas devem ser respondidas em sequência.
- Cada pergunta permite apenas uma resposta definitiva.
- Respostas não podem estar vazias e possuem limite de 4.000 caracteres.
- O estudante pode consultar seu histórico de entrevistas.
- Após a quinta resposta, a entrevista entra em processamento.
- A IA deverá gerar avaliações individuais e feedback geral.

**Métodos:** `startInterview()`, `findById()`, `findMyInterviews()` e `submitAnswer()`.

**Status da entrevista:**
- `IN_PROGRESS`: estudante respondendo.
- `PROCESSING`: aguardando avaliação da IA.
- `COMPLETED`: avaliação concluída.
- `FAILED`: erro no processamento.

---

## 🔒 4. Roles e Controle de Acesso

O projeto utiliza **Spring Security + JWT** para autenticação e autorização.

| Role | Permissões |
|---|---|
| `STUDENT` | Candidaturas, entregas, feedbacks, portfólios e entrevistas |
| `ADMIN` | Funcionalidades administrativas e consulta de usuários |
| `ORGANIZATION` | Reservada para evolução futura |

### Regras de segurança

- Cadastro público gera exclusivamente usuários `STUDENT`.
- Roles administrativas não podem ser escolhidas durante o cadastro.
- Endpoints protegidos exigem JWT válido.
- Estudantes não podem acessar informações privadas de outros estudantes.
- Endpoints administrativos devem exigir `ROLE_ADMIN`.
- Operações exclusivas de estudantes devem exigir `ROLE_STUDENT`.
- Tentativas de acesso sem permissão devem retornar HTTP `403 Forbidden`.

**Pendente:** implementar e testar permissões específicas nos Controllers e disponibilizar ao menos um endpoint administrativo funcional.

---

## 🗺️ 5. Próximos Passos de Implementação

### Etapa 1 — Controllers REST e Segurança

- [ ] Criar Controllers para os Services.
- [ ] Definir os endpoints REST e seus métodos HTTP.
- [ ] Configurar autorização por roles no `SecurityConfig`.
- [ ] Implementar ao menos uma funcionalidade exclusiva de `ADMIN`.
- [ ] Garantir que operações de estudantes exijam `ROLE_STUDENT`.
- [ ] Documentar os endpoints com Swagger/OpenAPI.

### Etapa 2 — Testes da API

- [ ] Testar cadastro e login com JWT.
- [ ] Testar consultas de organizações e desafios.
- [ ] Testar candidaturas e prevenção de duplicidade.
- [ ] Testar bloqueio de candidaturas e entregas fora do prazo.
- [ ] Testar envio definitivo das soluções.
- [ ] Testar entrevistas com cinco perguntas.
- [ ] Testar permissões de `STUDENT` e `ADMIN`.
- [ ] Validar respostas HTTP `400`, `401`, `403`, `404` e `409`.

### Etapa 3 — Integração com Spring AI

- [ ] Configurar Spring AI e o provedor de modelo.
- [ ] Implementar geração automática de feedback das soluções.
- [ ] Implementar geração automática de portfólio após o feedback.
- [ ] Implementar avaliação individual das cinco respostas da entrevista.
- [ ] Implementar avaliação geral da entrevista.
- [ ] Persistir os resultados gerados no Oracle.

### Etapa 4 — Mensageria com RabbitMQ

- [ ] Configurar conexão com RabbitMQ.
- [ ] Criar exchange, queues e bindings.
- [ ] Implementar publicação do evento `SubmissionCreated`.
- [ ] Criar consumidor para geração assíncrona de feedback.
- [ ] Implementar evento `FeedbackGenerated` para iniciar a geração do portfólio.
- [ ] Implementar processamento assíncrono da avaliação das entrevistas.
- [ ] Tratar falhas, reprocessamentos e mensagens duplicadas.

### Etapa 5 — Integração com Frontend

- [ ] Compartilhar documentação Swagger com o time.
- [ ] Integrar cadastro, login e autenticação JWT.
- [ ] Integrar catálogo de desafios e candidaturas.
- [ ] Integrar envio de soluções.
- [ ] Exibir feedbacks e portfólios gerados.
- [ ] Implementar fluxo das cinco perguntas da entrevista.
- [ ] Tratar estados de carregamento, processamento e erros.

---

## 📍 6. Estado Atual do Desenvolvimento

| Componente | Situação |
|---|---|
| Entidades e relacionamentos JPA | Estruturados |
| Repositories | Estruturados |
| DTOs e Mappers | Estruturados |
| Services e regras de negócio | Mapeados; ajustes e testes pendentes |
| Spring Security e JWT | Base implementada; autorização por endpoint pendente |
| Controllers | Pendente |
| Swagger | Configuração/documentação dos endpoints pendente |
| Testes com Postman | Pendente |
| Spring AI | Pendente |
| RabbitMQ | Pendente |
| Integração frontend | Pendente |

**Próxima implementação:** Controllers REST com autorização por roles, documentação Swagger e testes no Postman. Depois, integração com Spring AI e RabbitMQ para automatizar feedbacks, portfólios e avaliações das entrevistas.
