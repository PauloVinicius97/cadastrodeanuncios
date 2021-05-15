package com.paulovinicius.cadastro.main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Prepara o estágio para exibição da cena do JavaFX 2.
 * 
 * @author Paulo Vinícius Santos Costa
 */
public class Main extends Application {
	static Stage st;

	/**
	 * Faz troca de cena (tela) a partir de String recebida com o caminho do
	 * arquivo.
	 * 
	 * @param caminho Caminho do arquivo fxml.
	 */
	public static void trocaTela(String caminho) {
		Parent root = null;

		try {
			root = FXMLLoader.load(Main.class.getResource(caminho));
		} catch (IOException ex) {
			System.out.println("Erro ao carregar FXML.");
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		Scene scene = new Scene(root);

		st.setScene(scene);
		st.show();
	}

	@Override
	public void start(Stage stage) throws Exception {
		st = stage;

		Parent root = FXMLLoader.load(getClass().getResource("/fxml/TelaInicial.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
