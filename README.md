# Projeto Formulario - Backend em Spring Boot

## Pré-requisitos
- Java JDK 11+
- PostgreSQL

## Configuração
1. Clone o repositório: `https://github.com/davicruz23/Projeto-UGTSIC-Back.git`
2. Configure o banco de dados PostgreSQL:
   - Crie um banco de dados chamado `formulariobd` pode alterar se preferir
   - Configure as propiedades de conexão em `application.properties`
  
## Email
1. Deixei um email configurado apenas para testes em `application.properties` altere o email que consta colocando o de sua preferência
2. No FormularioController na linha 110 é preciso colocar o email que colocou no `application.properties`
3. Em `src/main/java/DaviugtsicApplication` também e preciso alterar o email que foi colocado nos anteriores

## Estrutura do Projeto
- `src/main/java/DaviugtsicApplication`: para executar a aplicação
- `src/main/resources`: Arquivos de configuração

## Endpoints da API
- `POST /formulario`: Cria um novo formulario

## Contribuições
Contribuições são bem-vindas! Abra um problema ou envie uma solicitação pull

## Contato
Para perguntas ou feedback, entre em contato em davifieledeus@gmail.com
