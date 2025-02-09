# Projeto Formulario - Backend em Spring Boot

## Pré-requisitos
- Java JDK 11+
- PostgreSQL

## Configuração
1. Clone o repositório: `https://github.com/davicruz23/Projeto-UGTSIC-Back.git`
2. Configure o banco de dados PostgreSQL:
   - Crie um banco de dados chamado `formulariobd` pode alterar se preferir
   - Configure as propiedades de conexão em `application.properties`

## Funcionalidades
- Enviar Formulario com os campos nome,email,telefone,Cargo desejado,Observação e um arquivo nos formatos .doc,.docx,pdf
- Listar os formularios recebidos apenas para o admin

## Banco de dados
- Foi ultilizado o banco de dados PostgreSQL para persistência de dados. e preciso configurar para utiliza-lo
- Deixarei o `spring.jpa.hibernate.ddl-auto=create-drop` para sempre que iniciar criar as tabelas e ao fim da execução
  excluir os dados do banco. se preferir pode deixar em update.

## Segurança
- Foi adicionado autenticação do spring security auth utilizando token jwt

##Teste Unitário
- Foi implementado um teste no back na classe formulario, ele se localiza em `main/java/test/formularioTest`
  
## Email
1. Deixei teste@teste.com como padrão como solicitado em `application.properties` pode alterar o email que consta colocando o de sua preferência
2. No FormularioController na linha 110 é preciso colocar o email que colocou no `application.properties`
3. será enviado um email para o administrador que no caso é teste@teste.com (como o email é fake e não possue senha o envio do formulario vai falhar!)
4. estou deixando comentado no código um email e senha criado no google apps que usei e vai funcionar e retornar status 200. lembrando que se por um email válido no formulário
   você receberá a confirmação do envio e recebimento do formulário
   A senha é obrigatória para a maioria dos servidores SMTP. "Se realmente desejar usar o serviço de email, é preciso configurar uma senha no google app"

## Estrutura do Projeto
- `src/main/java/DaviugtsicApplication`: para executar a aplicação
- `src/main/resources`: Arquivos de configuração

## Endpoints da API
- `POST /formulario/enviar`: Cria um novo formulario
- `POST/auth/login`: Uusuario admin colocado localmente nas configurações
- `GET//formularios/list`: apenas o admin pode ter acesso após autenticação (usuario:admin/senha:admin)

## Contribuições
Contribuições são bem-vindas!

## Contato
Para perguntas ou feedback, entre em contato em davifieledeus@gmail.com
