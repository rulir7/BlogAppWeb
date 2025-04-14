1. Tipo de Aplicação:
É um blog desenvolvido com Spring Boot
Usa arquitetura MVC (Model-View-Controller)
Possui sistema de posts e comentários

2. Funcionalidades Principais:
Criação de posts
Edição de posts
Exclusão de posts
Visualização de posts
Sistema de comentários (adicionar, editar, excluir)

3. Tecnologias Utilizadas:
Spring Boot como framework principal
JPA/Hibernate para persistência
MySQL como banco de dados
Thymeleaf para templates HTML
Validação de dados com Jakarta Validation

4. Estrutura do Código:
 4.1 Controller (BlogAppController.java):
  Gerencia todas as requisições HTTP  
  Implementa CRUD para posts e comentários
  Usa ModelAndView para passar dados para as views

  4.2 Model:
  PostModel: Representa um post do blog
  PostComentarioModel: Representa comentários nos posts

  4.3 Service:
  BlogAppService: Contém a lógica de negócios
  Gerencia operações de banco de dados
  
5. Endpoints Principais:
/posts: Lista todos os posts
/posts/{id}: Mostra um post específico
/newpost: Formulário para novo post
/editpost/{id}: Edição de post
/deletepost/{id}: Remoção de post
Endpoints similares para comentários

6. Características Técnicas:
Usa UUID para identificadores
Implementa validação de formulários
Usa mensagens flash para feedback ao usuário
Gerenciamento de datas com LocalDate

7. Segurança e Validação:
Validação de dados com @Valid
Tratamento de erros com BindingResult
Redirecionamentos seguros
