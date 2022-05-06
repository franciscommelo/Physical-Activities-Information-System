# Relatório técnico

## Introdução

No âmbito da cadeira Laboratório de Software foi proposto a implementação de uma aplicação JDBC e da respectiva base de dados com o objetivo de guardar a informação de _users_,_sports_,_routes_ e _activities_ associadas a estes.
Numa primeira fase do trabalho proposto foi desenvolvida um programa no qual um utilizador, usando comandos na consola, poderá interagir com a base de dados.
Numa segunda fase do trabalho desenvolveu-se suporte para a apresentação de resultados em diferentes _views_ e adição de _paging_ e de dois novos comandos.
Na terceira fase o objectivo é a criação de um _server HTTP_ que suporta todos os pedidos _GET_ e apresente os resultados.


## Modelação da base de dados

### Modelação conceptual ###

O seguinte diagrama apresenta a modelo entidade-associação para a informação gerida pelo sistema.

![Modelo Entidade-Associação](/docs/templates/ER_Model.png "Modelo Entidade-Associação")

Destacam-se os seguintes aspectos deste modelo:

* A entidade fraca _Activity_ dependendo das entidades fortes _User_, _Sport_ e opcionalmente de _Route_.
* A entidade _Activity_ pode conter n _users_ e n _sports_, mas contêm apenas (opcionalmente) uma _route_.

O modelo conceptual apresenta ainda as seguintes restrições:

* É necessário existir uma verificação que na entidade _Route_ a "startLocation" é diferente da "endLocation" e que o atributo "distance" é maior que zero.
* Na entidade _User_ o email necessita de verificação que é inserido um email válido.
* Na entidade _Activity_ o atributo "duration" necessita de ser maior que zero.

### Modelação física ###

O modelo físico da base de dados está presente em [here](./CreateTable.sql).

Destacam-se os seguintes aspectos deste modelo:

* Todas os _ids_ (Pks) das tabelas são geradas automaticamente.
* O atributo "email" é único na tabela _User_.

## Organização do software

### Processamento de comandos

##### Command Handlers

A interface  `RouteHandler`  contém um único método  `execute`  resposável por executar o pedido e responder através de um  `RouteResponse` e todos os _handler_ vão implementar esta interface.

Cada  _handler_  devolve um  `CommandResult`  que, nesta fase do trabalho, contém um construtor que recebe um objecto (podendo ser uma `LinkedList` com todos os objectos do resultado) e apresenta a informação obtida no _request_ ao utilizador.

Numa segunda fase foi adicionado um `printResult` ao `CommandResult`, de forma a acomodar as novas _views_ html e json.

Os parâmetros dos comandos são passados através da classe  `CommandRequest`  que para além da informação sobre o request, como o  `Path`, contém métodos para obter parâmetros de  _path_  (e.g  `/user/{uid}`, onde  `uid`  é o parâmetro) e parâmetros do  _request_  em sí (e.g  `name=Francisco&email=francisco@email.pt`).

### Encaminhamento dos comandos

Para o  `Router`  poder encaminhar os comandos estes devem ser registados no ínicio da aplicação através do método  `addCommand`. Este método recebe como parâmetros: o Método do  _Request_, o  _Template_  da  `Route`  e o  _Handler_  que contém a lógica necessária aquando da chamada desta  _route_.

Caso o  `Router`  encontre a  _route_  então o  _handler_  desta é retornado, caso contrário é apresentada a mensagem de erro avisando o user de que inserir um comando inválido.

##### Método do Request

Nesta fase do trabalho apenas foram necessários 3 métodos:

-   `EXIT`  que termina a aplicação
-   `GET`  que tem como objectivo ir procurar dados à fonte de dados
-   `POST`  que tem como objectivo inserir novos dados na fonte de dados da aplicação

Numa segunda fase do trabalho foram adicionados mais 2 métodos:

-  `OPTIONS` que apresenta uma descrição de todas as opções possíveis de inserir na linha de comandos
-  `DELETE` que tem como objectivo apagar dados na fonte de dados da aplicação

Com a terceira fase do trabalho foi adicionado mais 1 método:

- `LISTEN` que irá ser o comando que começa o server HTTP

##### Template da Route

Para representar um  _template_, isto é, uma representação das regras que o  _path_  deve seguir, existe uma classe  `PathTemplate`.

Este PathTemplate tem como função dividir o _path_ nos seu vários segmentos. Estes segmentos podem ser de dois tipos:

 - Constantes - secções do `Path`delimitadas por `/`- e.g : `/users`
 - Variáveis - o nome do segmento que varia consoante o pedido, e deve estar no formato`{uid}`, onde `uid` é a chave.

O método  `match`  realiza duas tarefas:
-   Verifica se o  `Path`  especifiado observa o  _template_
-   Obtem os parâmetros variáveis com valores em  `Path`  e chaves presentes no  `PathTemplate`.

Para verificar um possível `match` são percorridos todos os segmentos do _Path_ ao mesmo tempo que são percorridos os segmentos do _Pathtemplate_, avaliando um-a-um. Caso haja segmentos a menos , ou segmentos a mais, no `Path` então o `match` retorna vazio. Caso contrário e um `match` ocorra, um mapa com os parâmetros de `Path` é retornado.

Numa segunda fase o _Path_ contêm opcionalmente um _header_ -e.g:`accept:text/html`. Este _header_ vai definir o tipo de apresentação dos resultados retornados dos _Handlers_ podendo ter estas 3 visualizações na consola:
- `text/plain` apresenta os resultados como na fase anterior.
- `text/html` apresenta os resultados em formato _HTML_.
- `application/json`apresenta os resultados em formato _JSON_.

Também é necessário este _header_ suportar a existência de um _filename_ -e.g: `accept:text/plain|file-name:users.txt`, sendo que se não existir o _print_ será realizado na consola, caso exista o resultado do _handler_ será escrito no ficheiro com o nome do _filename_ passado. Todas as opções de visualização suportam este componente.

Numa última parte da segunda fase foi adicionado suporte a _paging_. Esta encontra-se nos _parameters_ do `Path`, e.g: `skip=6&top=3`. É um parâmetro opcional que irá segmentar a informação apresentada, suportada por todas as _views_ disponíveis. Esta verificação vai ocorrer nos DAOs (_Data Access Object_) adcionados para auxiliar no acesso a informação.

### Views 

Nesta segunda fase do trabalho foi adicionado o suporte a apresentação de resultados de 2 novas maneiras, ambas em consola ou em ficheiro.

- _HTML_: criada através de inicialização de várias instâncias de `Element` passando o tipo pretendido -e.g: `"Body"`, cada uma com os seus _childs_ de forma a percorrer recursivamente os elementos obtendo assim a estrutura necessária.
- _JSON_: criada através de uma conversão do `CommandResult` para o formato pretendido, esta conversão é realizada através de `switch` dependedo do _handler_ passado e com o auxílio de DAOs (_Data Access Object_). 

Na terceira fase do trabalho o objectivo seria apresentar os comandos _GET_ apresentados em diferentes _views_ no server. Seguindo esta estrutra entre os diferentes comandos e ligações entre os mesmos:

![Esquema de Ligações](/docs/templates/Sports.png "Esquema de Ligações")

Foram criadas _views_ para apresentação de cada comando e utilizando uma `NavigationBarView` existem links de ligação a outras _views_ e à `HomeView` (_root_).
Existe também suporte para _paging_ nos comandos onde é indicado no esquema, sempre representado com um tamanho de página igual a 5.


### Gestão de ligações e Acesso a dados

Para a gestão das ligações existe a classe `TransactionManager` que recebe a `datasource`. Todo o processamento transacional é tratado através do método doInTransaction desta classe que recebe como parâmetro o bloco transacional a ser processado na forma da interface `TransactionCallback`.

A implementação desta class tem em vista simplificar e evitar repetição de código nas classes de acesso a dados e evitando a repetição dos seguintes processos:

	• Abrir a conexão
    • Inicio e commit da transação
    • Controlo transacional
    • Fechar todos os recursos
    • Tratamento dos erros que possam ocorrer

Sendo que para cada comando SQL seria necessário repetir todos estes processos, então esta class serve como um template e é utilizada em todos os acessos a dados nos handlers melhorando assim a legibilidade do código evitando a constante repetição dos processos de controlo de transação.

Numa terceira fase foi necessário criar uma ligação a um _HTTP server_. Esta ligação e a criação do server realiza-se com ajuda de bibliotecas como o `Servlet`. O server irá estar como _default_ presente no port 8080, sendo possível aceder através do _http://localhost:8080/_.

### Processamento de erros

As excessões que são lançadas durante o acesso a dados são tratadas no `TransactionManager` onde é apresentado ao utilizador o SQL error code e a mensagem associada ao erro, eg:

    SQL STATE: 23505
    ERROR CODE: 0
    MESSAGE: ERROR: duplicate key value violates unique constraint "users_email_key"
      Detail: Key (email)=(francisco1@gmail.com) already exists.

Caso a exceção tenha como fonte causas internas ou expectáveis, como por exemplo, um má introdução do comando ou a falta da configuração da variável de ambiente com a `JDBC_DATABASE_URL` então nesse caso será apresentado ao utilizador a mensagem com a descrição do erro.

## Avaliação crítica

Neste fase do trabalho concluímos todos os objetivos que foram especificados no enunciado.

Pretendem-se ainda melhorar os seguintes aspectos:

 - Realizar uma maior variedade de testes 
 - Implementar as routes em árvore enária, melhorando a forma de procura no Router

Nesta segunda fase do trabalho concluímos todos os objectivos que foram especificados no enunciado.

Pretendem-se ainda melhorar os seguintes aspectos:

 - Implementar uma _DLS_ para a _view HTML_

Nesta terceira fase do trabalho todos os objectivos especificados no encunciado foram concluídos.

