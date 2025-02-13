# To-do-List
API de gerenciamento de tarefas

Sistema de Gerenciamento de Tarefas (To-Do List) Você deve desenvolver um sistema RESTful para gerenciar tarefas (To-Do List) utilizando Spring Boot e seguindo boas práticas de arquitetura e desenvolvimento. O sistema permitirá aos usuários:

Criar, visualizar, atualizar e excluir tarefas. 
Definir status e prazos para as tarefas.
Gerenciar usuários e associar tarefas a eles. 
Registrar logs, métricas e rastreamento distribuído.
Utilizar mensageria para notificações assíncronas.

📌 Requisitos Técnicos 1️⃣ Funcionalidades Principais

✅ CRUD de tarefas (/tasks):
Criar uma nova tarefa. 
Listar todas as tarefas de um usuário. 
Buscar uma tarefa por ID. 
Atualizar uma tarefa (nome, descrição, status, prazo).
Excluir uma tarefa.

✅ CRUD de usuários (/users):
Criar um usuário. 
Listar todos os usuários. 
Buscar um usuário por ID. 
Associar tarefas a um usuário.

✅ Filtros avançados: 
Filtrar tarefas por status (Pendente, Em andamento, Concluída).
Filtrar tarefas com prazo vencido.
Ordenar tarefas por prioridade.

✅ Notificações Assíncronas: 
Quando uma tarefa for concluída, enviar um evento para um sistema de notificação via RabbitMQ.

✅ Observabilidade: 
Configurar logs estruturados com SLF4J + Logback.
Expor métricas da API com Micrometer e Prometheus. 
Implementar Tracing Distribuído com Jaeger ou Zipkin.

✅ Deploy com Docker:
Criar um Dockerfile para a aplicação.
Criar um docker-compose.yml para rodar banco de dados, RabbitMQ e a aplicação.

📌 Tecnologias Utilizadas 
Backend (Spring Boot) 
✅ Spring Boot – Framework principal da aplicação
✅ Spring Web – Para criar a API REST 
✅ Spring Data JPA – Para acesso ao banco de dados 
✅ Spring Actuator – Para métricas e monitoramento 
✅ Spring AMQP – Para mensageria com RabbitMQ

Banco de Dados 
✅ PostgreSQL (ou MySQL) – Banco de dados relacional 

Infraestrutura e Observabilidade
✅ Docker e Docker Compose – Para containerização 
✅ RabbitMQ – Para processamento assíncrono 
✅ Prometheus + Grafana – Para monitoramento 
✅ Jaeger ou Zipkin – Para tracing distribuído
