# Pb - Desafio 3

**Gerenciamento de eventos e ingressos**, uma aplicação desenvolvida com arquitetura de microsserviços. Este projeto está disponível online, rodando na AWS em instâncias EC2:
- [Documentação Swagger: ms-event-management](http://3.20.203.38:8080/swagger-ui/index.html#/)
- [Documentação Swagger: ms-ticket-management](http://3.19.219.171:8080/swagger-ui/index.html#/)

## Tecnologias Utilizadas

![Java](https://img.shields.io/badge/Java-%23F7B93E.svg?&style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-%23A6B5E4.svg?&style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-%2342A5F5.svg?&style=for-the-badge&logo=springcloud&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-%232496ED.svg?&style=for-the-badge&logo=docker&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-232F3E.svg?&style=for-the-badge&logo=amazonwebservices&logoColor=white)
![Insomnia](https://img.shields.io/badge/Insomnia-%23F0F0F0.svg?&style=for-the-badge&logo=insomnia&logoColor=black)

## Estrutura do Projeto

O repositório está organizado nas seguintes pastas e arquivos principais:

- **ms-event-management**: Microsserviço para gerenciamento de eventos.
- **ms-ticket-management**: Microsserviço para gerenciamento de ingressos.
- **docker-compose.yml**: Arquivo para orquestração dos serviços utilizando Docker.
- **Insomnia_Management_API.json**: Arquivo com os endpoints da API para importação no Insomnia.

## Pré-requisitos

Antes de começar, certifique-se de ter instalado:

[![Java 17+](https://img.shields.io/badge/Java%2017%2B-%23F7B93E.svg?&style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
[![Maven](https://img.shields.io/badge/Maven-%23C71F37.svg?&style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/install.html)
[![Docker](https://img.shields.io/badge/Docker-%232496ED.svg?&style=for-the-badge&logo=docker&logoColor=white)](https://docs.docker.com/get-docker/)
[![Insomnia](https://img.shields.io/badge/Insomnia-%23F0F0F0.svg?&style=for-the-badge&logo=insomnia&logoColor=black)](https://insomnia.rest/download)

## Como Executar

1. Clone o repositório:

   ```bash
   git clone https://github.com/jowgaze/PbNovDes3_JonathaCarvalho.git
   cd PbNovDes3_JonathaCarvalho
   ```

2. Utilize o **Docker Compose** para subir os serviços:

   ```bash
   docker-compose up
   ```

3. Teste os endpoints utilizando localmente a documentação swagger completa:
    - ms-event: `http://localhost:8080/swagger-ui/index.html#/`
    - ms-ticket: `http://localhost:8081/swagger-ui/index.html#/`

4. Teste os endpoints utilizando o Insomnia:
    - Importe o arquivo `Insomnia_Management_API.json` no Insomnia.

## Endpoints
### Eventos
#### 1. Criar Evento
**Endpoint:** `POST /br/com/compass/eventmanagement/v1/create-event`

**Requisição:**
```json
{
  "name": "Linkin Park",
  "dateTime": "2025-11-13T20:00:00Z",
  "zipCode": "70070701"
}
```

**Respostas:**
- `201 Created`: Evento criado com sucesso
```json
{
  "id": "eventID",
  "name": "Linkin Park",
  "dateTime": "2025-11-13T20:00:00",
  "address": {
    "zipCode": "70070-701",
    "street": "Setor SRPN",
    "neighborhood": "Asa Norte",
    "locality": "Brasília",
    "state": "DF"
  }
}
```
- `404 Not Found`: Endereço não encontrado
- `422 Unprocessable Entity`: Erro de validação de campo

---

#### 2. Atualizar Evento
**Endpoint:** `PUT /br/com/compass/eventmanagement/v1/update-event/{id}`

**Requisição:**
```json
{
  "name": "Linkin Park Cover",
  "dateTime": "2025-11-13T22:00:00Z",
  "zipCode": "70070701"
}
```

**Respostas:**
- `204 No Content`: Evento atualizado com sucesso
- `404 Not Found`: Evento ou novo endereço não encontrado
- `409 Conflict`: O evento possui ingressos vinculados
- `422 Unprocessable Entity`: Erro de validação de campo
- `503 Service Unavailable`: Serviço de ingressos indisponível

---

#### 3. Recuperar Evento por ID
**Endpoint:** `GET /br/com/compass/eventmanagement/v1/get-event/{id}`

**Respostas:**
- `200 OK`: Evento recuperado com sucesso
- `404 Not Found`: Evento não encontrado

---

#### 4. Listar Todos os Eventos
**Endpoint:** `GET /br/com/compass/eventmanagement/v1/get-all-events`

**Respostas:**
- `200 OK`: Lista de eventos recuperada com sucesso

---

#### 5. Listar Eventos Ordenados
**Endpoint:** `GET /br/com/compass/eventmanagement/v1/get-all-events/sorted?direction=asc|desc`

**Parâmetros:**
- `direction` (opcional, padrão `asc`): Ordenação crescente (`asc`) ou decrescente (`desc`).

**Respostas:**
- `200 OK`: Eventos recuperados e ordenados com sucesso

---

#### 6. Excluir Evento
**Endpoint:** `DELETE /br/com/compass/eventmanagement/v1/delete-event/{id}`

**Respostas:**
- `204 No Content`: Evento excluído com sucesso
- `404 Not Found`: Evento não encontrado
- `409 Conflict`: O evento possui ingressos vinculados
- `503 Service Unavailable`: Serviço de ingressos indisponível

---

### Ingressos
#### 1. Criar um Ingresso
**Endpoint:** `POST /br/com/compass/ticketmanagement/v1/create-ticket`

**Requisição:**
```json
{
  "cpf": "89022747484",
  "customerName": "Tatiana das Neves",
  "customerMail": "neves.tatiana@mail.com",
  "eventId": "eventID",
  "brlAmount": "50",
  "usdAmount": "10"
}
```

**Respostas:**
- **201 Created** - Ingresso criado com sucesso.
```json
{
  "id": "1",
  "cpf": "89022747484",
  "customerName": "Tatiana das Neves",
  "customerMail": "neves.tatiana@mail.com",
  "event": {
    "id": "eventID",
    "name": "Linkin Park",
    "dateTime": "2025-11-13T20:00:00",
    "address": {
      "zipCode": "70070-701",
      "street": "Setor SRPN",
      "neighborhood": "Asa Norte",
      "locality": "Brasília",
      "state": "DF"
    }
  },
  "brlAmount": "50",
  "usdAmount": "10"
}
```
- **404 Not Found** - Evento não encontrado.
- **422 Unprocessable Entity** - Erro de validação de campo.
- **503 Service Unavailable** - Serviço de eventos ou RabbitMQ indisponível.

---

#### 2. Atualizar um Ingresso
**Endpoint:** `PUT /br/com/compass/ticketmanagement/v1/update-ticket/{id}`

**Requisição:**
```json
{
  "cpf": "36312197255",
  "customerName": "João Neves",
  "customerMail": "neves.joao@mail.com",
  "brlAmount": "50",
  "usdAmount": "10"
}
```

**Respostas:**
- **204 No Content** - Ingresso atualizado com sucesso.
- **404 Not Found** - Ingresso não encontrado.
- **422 Unprocessable Entity** - Erro de validação de campo.

---

#### 3. Buscar um Ingresso por ID
**Endpoint:** `GET /br/com/compass/ticketmanagement/v1/get-ticket/{id}`

**Resposta:**
- **200 OK** - Ingresso encontrado.
- **404 Not Found** - Ingresso ou evento não encontrado.
- **503 Service Unavailable** - Serviço de eventos indisponível.

---

#### 4. Buscar Ingressos por CPF
**Endpoint:** `GET /br/com/compass/ticketmanagement/v1/get-ticket-by-cpf/{cpf}`

**Resposta:**
- **200 OK** - Lista de ingressos encontrados.
- **404 Not Found** - Nenhum ingresso encontrado.
- **422 Unprocessable Entity** - Erro de validação de CPF.
- **503 Service Unavailable** - Serviço de eventos indisponível.

---

#### 5. Verificar se Evento Possui Ingressos
**Endpoint:** `GET /br/com/compass/ticketmanagement/v1/check-tickets-by-event/{eventId}`

**Resposta:**
```json
{
  "eventId": "eventId",
  "hasTickets": true,
   "error": "The event cannot be deleted because tickets have been sold."
}
```

**Códigos de resposta:**
- **200 OK** - Verificação realizada com sucesso.

---

#### 6. Cancelar um Ingresso (Soft Delete)
**Endpoint:** `DELETE /br/com/compass/ticketmanagement/v1/cancel-ticket/{id}`

**Códigos de resposta:**
- **204 No Content** - Ingresso cancelado com sucesso.
- **404 Not Found** - Ingresso não encontrado.

## Deploy
Para o deploy, cada microsserviço tem um docker-compose próprio para rodar em instâncias diferentes, bastando apenas configurar as variáveis de ambiente para o Open Feign:
- TICKET_URL: host:port do microsserviço de ingressos.
- EVENT_URL: host:port do microsserviço de eventos.

**Desenvolvido com 💻 e ☕ por [jowgaze](https://github.com/jowgaze)**
