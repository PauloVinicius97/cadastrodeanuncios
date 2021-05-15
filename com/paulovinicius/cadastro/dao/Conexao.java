package com.paulovinicius.cadastro.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Faz conexão em banco de dados Oracle, utilizando JDBC 6 (ojdbc6).
 * 
 * @author Paulo Vinícius Santos Costa.
 */
public class Conexao {
	private String usuario = "";
	private String senha = "";
	private String servidor = "";
	private int porta = 1521; // Porta padrão do Oracle

	private Connection conexao = null;

	public Conexao() {
	}

	/**
	 * Tenta realizar conexão com o banco de dados.
	 * 
	 * @return true ou false se obtido sucesso ou não.
	 */
	public Connection getConexao() {
		if (conexao == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conexao = DriverManager.getConnection("jdbc:oracle:thin:@" + this.servidor + ":" + this.porta + ":XE",
						this.usuario, this.senha);

			} catch (ClassNotFoundException e) {
				System.out.println("Pacote de DB não encontrado.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conexao;
	}

	/**
	 * Desconecta do banco de dados.
	 */
	public void desconecta() {
		try {
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}