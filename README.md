# Complilando o programa
 OBS.: indiquei o Oracle 10g e Oracle JDBC 6 no tutorial pois foram estes que utilizei. É possível que funcione em versões mais atualizadas. Utilizei Linux para a compilação.
 
 1. Utilize um banco Oracle 10g e crie as seguintes tabela, sequência e trigger:

	    CREATE TABLE cadastrodeanuncios (
	    	id NUMBER NOT NULL,
	    	nomeanuncio VARCHAR(100) NOT NULL,
	    	nomecliente VARCHAR(100) NOT NULL,
	    	datainicio DATE NOT NULL,
	    	datatermino DATE NOT NULL,
	    	investimentodiario NUMBER(6,2) NOT NULL
	    );
    
	    ALTER TABLE cadastrodeanuncios ADD (
	    	CONSTRAINT id_pk PRIMARY KEY (id)
	    );
    
	    CREATE SEQUENCE idsequencia START WITH 1;
    
	    CREATE OR REPLACE TRIGGER cadastrodeanuncios_id
	    BEFORE INSERT ON cadastrodeanuncios
	    FOR EACH ROW
	    BEGIN
	    	SELECT idsequencia.NEXTVAL
	    	INTO :new.id
	    	FROM DUAL;
	    END;

2. Na classe com.paulovinicius.cadastro.dao.Conexao, insira os dados de conexão ao banco em "usuario, senha e servidor". 

3. Compile as classes utilizando Java 11 e incluindo as bibliotecas JavaFX 11 e Oracle JDBC 6. Para facilitar o processo, crie um arquivo .txt em um diretório acima de com/ e copie e cole o seguinte (se quiser usar a classe de teste, recomendo abrir o projeto num IDE):

	    ./com/paulovinicius/cadastro/dao/ClienteDAO.java
	    ./com/paulovinicius/cadastro/dao/Conexao.java
	    ./com/paulovinicius/cadastro/dao/AbstractDAO.java
	    ./com/paulovinicius/cadastro/fx/TelaAdicionarController.java
	    ./com/paulovinicius/cadastro/fx/TelaInicialController.java
	    ./com/paulovinicius/cadastro/main/Main.java
	    ./com/paulovinicius/cadastro/main/Cliente.java

	Feito isso, abra o terminal na pasta onde você criou o .txt (lembre-se, uma pasta acima de com/) e rode o seguinte comando:

	    javac -d "bin" --module-path caminho-para-biblioteca-do-javafx --add-modules javafx.controls,javafx.fxml -cp caminho-para-ojdbc6.jar @nome-do-arquivo-txt.txt

	 Exemplo:
 
	    javac -d "bin" --module-path /opt/javafx-sdk-11.0.2/lib/ --add-modules javafx.controls,javafx.fxml -cp /home/paulo/eclipse-workspace/cadastrodeanuncios/jar/ojdbc6.jar @paths.txt 

	Executado o comando corretamente, irá ser criada uma pasta "bin".

4. Mova a pasta fxml/ (com seu conteúdo) para bin/fxml/. 

5. Execute o programa com o seguinte comando, no mesmo diretório onde a  pasta bin/ está:

	    java --module-path caminho-para-biblioteca-do-javafx --add-modules javafx.controls,javafx.fxml -classpath ./bin/:caminho-para-ojdbc6.jar com.paulovinicius.cadastro.main.Main

	Exemplo:
	
		java --module-path /opt/javafx-sdk-11.0.2/lib/ --add-modules javafx.controls,javafx.fxml -classpath ./bin/:/home/paulo/eclipse-workspace/cadastrodeanuncios/jar/ojdbc6.jar com.paulovinicius.cadastro.main.Main

Se o procedimento fora feito corretamente, o programa deve abrir.

# Utilizando o programa

## Tela inicial
Primeira tela a ser exibida ao executar o programa. Eis uma imagem e descrição das funções apresentadas:

![Primeira tela](https://i.imgur.com/XFi9Meg.png)
1. Exibe a campanha, nome do cliente e data de início e fim da campanha. Pode ser reordenado clicando em seus títulos de exibição.

2. Opções de filtro. É possível filtrar utilizando apenas um ou uma combinação dos três itens a seguir:

	- Nome do cliente;
	- Data inicial da campanha;
	- Data de término da campanha.

	Caso marcado "Dentro deste intervalo", é necessário preencher ambas as datas. Esta opção filtra todas as campanhas que estarão ocorrendo neste período de tempo. Desmarcada a opção, irá aparecer apenas resultados onde a campanha começa e/ou termina com as datas preenchidas.

	É necessário clicar em "Aplicar filtro" para o filtro entrar em efeito. Clique em "Limpar filtro" para exibir todos os dados novamente. Clique no ícone da direita das caixas de texto referente às datas para preenchê-las. O campo de texto para busca de clientes é *case sensitive*. 

3. Vai para a tela onde é possível adicionar novos clientes. Explicação mais abaixo.

4. Deleta um cliente selecionado. 

5. Exibe os dados do clientes selecionado. Os dados são calculados utilizando as regras do primeiro desafio:

-   a cada 100 pessoas que visualizam o anúncio, 12 clicam nele;
-   a cada 20 pessoas que clicam no anúncio, 3 compartilham nas redes sociais;
-   cada compartilhamento nas redes sociais gera 40 novas visualizações;
-   30 pessoas visualizam o anúncio original (não compartilhado) a cada R$ 1,00 investido;
-   o mesmo anúncio é compartilhado no máximo 4 vezes em sequência.

6. Exibe o status das ações realizadas pelo usuário, por exemplo: cliente deletado com sucesso ou não, filtro aplicado, filtro limpo, entre outros.

## Tela "Adicionar cliente"
Tela que é exibida ao clicar no botão "Adicionar cliente", na tela inicial. Eis uma imagem da tela e descrição de suas funções:

![Segunda tela](https://i.imgur.com/glqIslb.png)
1. Nome do anúncio. Pode incluir letras e números;

2. Nome do cliente. Pode incluir letras e números;

3. Data de início. Clique no ícone na direita da caixa de texto para selecionar uma data;

4. Data de fim. Clique no ícone na direita da caixa de texto para selecionar uma data;

5. Investimento por dia. Pode ser número fracionário;

6. Botão para inserir o cliente no banco de dados;

7. Exibe se o cliente fora adicionado com sucesso ou se houve algum erro, tais como: data de fim é antes da data de início, valor de investimento inválido, etc.;

8. Volta para a tela de início.
