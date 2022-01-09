## Considerações iniciais:
Abaixo uma lista das afirmações que foram consideradas verdadeiras durante o desenvolvimento:
	
	* no banco de dados, a chave primária será um ID gerado pelo próprio banco
	* o CPF será único então na rota post precisamos buscar se aquele CPF existe e retornar um BAD_REQUEST e uma mensagem informando que o cliente já existe no banco de dados
	* as url's do Swagger podem ser acessadas sem nenhum tipo de autenticação
	* as rotas da API serão acessadas via postman durante o desenvolvimento, tornando desnecessária a tela de login do Spring Boot
	* o usuário com acesso à aplicação será o email: admin@neoapp.com e a senha: neoapp1234Admin 

## Preparando o ambiente
	
	* criação do docker-compose para gerar o nosso postgresql
	* preparação do application.properties para o spring poder conversar com o Postgresql
	* inclusão das dependências necessárias no pom.xml


## Ordem cronológica da codificação
	
	* Definir como irá funcionar os modelos dos Clientes e dos Usuários
	* Desenvolver os Controllers, Repository, Formularios e Dto's para Clientes	
	* Repetir o passo anterior para Usuários	
	* Desenvolver o pacote security que vai cuidar da autenticação e gerar os tokens	
	* Desenvolver o pacote validação que irá cuidar dos erros de formulários
	* Desenvolver os testes necessários para os controllers,repository e gerador de token

### Definindo Clientes
	
	*  Que campos possui a classe clientes?
	*  Qual será a chave primária?
	*  Quais atributos devem ser únicos?
	*  Que atributos e métodos devemos ter na classe? 
	*  Que informações da classe devem retornar nas requisições?
	*  Repetir a mesma abordagem para Usuários


### Desenvolvendo o ClienteController
	
	Primeiro é necessário criar o repository do cliente para que possam ser adicionadas as funções de busca conforme a necessidade, feito isso, partimos para as rotas

	* listarClientes() rotas: (GET /clientes) (GET /clientes?parametro="valor")
	por enquanto vai retornar clienteRepository.findall() porque os parâmetros recebidos são opcionais e é necessário uma notação @query na função dentro do repository para trabalhar com os valores que possívelmente serão nulos
		
	*  detalharCliente() rota: (GET /cliente/{id}) 
	retorna as informaçõs daquele cliente através da função findByCpf criada no clienteRepository
		
	* cadastrar() rota: (POST /clientes)
	Os dados recebidos no body em formato JSON são enviados para um formulário e esse formulário deve converter esses dados em uma instância de cliente. Feito isso o controller vai salvar no banco de dados e retornar um 201(created)

	* atualizar() rota: (PUT /clientes/{id})
	 quase igual a rota post. instancia um formulário com os dados recebidos, atualiza e salva no banco de dados, o ClienteForm recebeu uma nova função chamada atualizar() que cuida da lógica de atualização
	
	* deletar() rota: (DELETE /clientes/{id}) a JPA já tem uma função delete, é só atribuir qual campo deve ser ultilizado como filtro, será passado o CPF que será único no BD.

	* durante os testes as rotas put e delete foram atualizadas com a anotação @Transactional para poderem funcionar;

	* foi criada a função FindByOptionalParams dentro do clienteRepository com uma anotação @Query que avalia se os parâmetros passados não são nulos e atribui uma clausula Where com aquele parâmetro caso ele não seja nulo. Dessa forma, podem ser passados 1,2 ou 3 parâmetros para buscar os clientes (nome,cpf e email);

	* as rotas estavam rertornando erro 500 quando não encontravam nenhum cliente, as funções foram atualizadas com um bloco try/catch que valida Optional<Cliente> com um if(cliente.isPresent()) para resolver esse problema. os Repository's agora retornam um Optional ao invés de um cliente
	
### Autenticação

	* Com o CRUD do cliente pronto, podemos partir para a parte de segurança da Api. Vamos precisar de um pacote security para tomar conta da autenticação, verificar as rotas e criar o token JWT

	* Desenvolvemos o LoginForm que vai receber os dados do login do body e o TokenDto que vai ser ultilizado para retornar as informações do Token

	* Criamos o controller AutenticacaoController com a rota para Login

	* Caso o usuário não consiga efetuar login por algum motivo, vamos retornar 400(badRequest) então colocaremos um try/catch na rota de login para cuidar disso

	* Vamos começar pelo mais fácil, implementar o AuthService que vai procurar o usuário por email e lançar uma exception caso não encontre

	* criamos agora a classe SecurityConfig e fazemos os 'Overrides' necessários
	* Na função configure que recebe um AuthenticationManagerBuilder precisamos implementar o BCryptPasswordEncoder para ultilizarmos criptografia nas senhas
	* Já na função configure que recebe um HttpSecurity definimos como irá funcionar as nossas rotas: todas as requisições post em /auth serão permitidas, fora isso todas as requisições só serão aceitas se tiverem autenticadas. Definimos o login como Stateless e configuramos um filtro antes de cada requisição que será a classe AuthViaTokenFilter

	* na classe AuthViaTokenFilter vamos extender a OncePerRequestFilter porque vamos filtrar todas as requisições que baterem na nossa api e que não estejam com .permitAll
	precisamos de funções para recuperar o token do cabeçalho da requisição,validar esse token recuperado e validar o usuário a partir desse token
	
	* Por ultimo,  após autenticar nosso usuário, nossa classe TokenService vai cuidar de gerar esse token para podermos retornar ao cliente caso o login esteja correto.
	*no nosso aplication.properties vamos deixar salvo o secret da aplicação e o tempo de expiração para centralizar as variáveis de configuração da nossa aplicação.
	* vamos gerar nosso token com o nome do usuário logado, a data de criação do token e o tempo de expiração, nessa classe também vamos definir uma função que valida o token recebido para podermos ultilizar na nossa classe AuthViaToken
	
	* Foi adicionado o usuário e senha direto no banco. Para conseguir a senha criptografada foi só criar uma classe main e gerar um sysout com o Bcrypt, pegar a string no console e inserir no campo senha do banco.
	
	
		public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("neoapp1234Admin"));
	}
	
	insert into usuario (email,nome,senha) values("admin@neoapp.com","admin","$2a$10$8Ffus5n4ap1EUp4k1EsKPOXcPH.6zCQ/rQfgUPHpCdhZ.CmxXb1Fa");

## Adicionando o Swagger
	* adicionado o swagger no pom.xml
	* foi necessário criar uma variavel static final com as url's do swagger para poder acessa-los sem precisar de login já que não desenvolvemos uma tela de login
	
## Testes

	* Com a aplicação pronta, por ser um projeto pequeno, uma nova revisão foi feita na estrutura do código onde foram implementados ou atualizados os testes e cada teste foi acompanhado de uma automatização com o JUnit


## Deploy no Heroku

	* o aplication.properties foi atualizado com as variáveis do heroku para a aplicação poder rodar na nuvem. o próprio heroku cuida dos testes e deploy da aplicação, sendo necessário somente adicionar um resource do postgresql e inserir o usuario e senha do administrador para permitir acesso

