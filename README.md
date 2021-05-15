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

4. Mova a pasta (com seu conteúdo) com/fxml/ para bin/fxml/. 

5. Execute o programa com o seguinte comando, no mesmo diretório onde a  pasta bin/ está:

	    java --module-path caminho-para-biblioteca-do-javafx --add-modules javafx.controls,javafx.fxml -classpath ./bin/:caminho-para-ojdbc6.jar com.paulovinicius.cadastro.main.Main

	Exemplo:
	
		java --module-path /opt/javafx-sdk-11.0.2/lib/ --add-modules javafx.controls,javafx.fxml -classpath ./bin/:/home/paulo/eclipse-workspace/cadastrodeanuncios/jar/ojdbc6.jar com.paulovinicius.cadastro.main.Main

Se o procedimento fora feito corretamente, o programa deve abrir.

# Utilizando o programa

Eis a tela que você verá ao abrir o programa:

