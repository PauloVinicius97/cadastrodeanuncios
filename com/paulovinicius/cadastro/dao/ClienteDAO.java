package com.paulovinicius.cadastro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.paulovinicius.cadastro.main.Cliente;

/**
 * Classe padrão DAO, implementada de AbstractDAO, que cuida de persistir os
 * dados da classe Cliente.
 * 
 * @author Paulo Vinícius Santos Costa
 */
public class ClienteDAO extends AbstractDAO<Cliente> {

	@Override
	public boolean insert(Cliente cli) {
		Conexao c = new Conexao();
		Connection con = c.getConexao();

		// nomeanuncio, nomecliente, datainicio, datatermino, investimentodiario
		String sql = "INSERT INTO cadastrodeanuncios (nomeanuncio, nomecliente, datainicio, datatermino, investimentodiario) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);

			// Cliente tem nomeAnuncio, nomeCliente, dataInicio, dataTermino,
			// investimentoDiario
			ps.setString(1, cli.getNomeAnuncio());
			ps.setString(2, cli.getNomeCliente());
			ps.setDate(3, cli.getDataInicio());
			ps.setDate(4, cli.getDataTermino());
			ps.setFloat(5, cli.getInvestimentoDiario());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Não foi possível inserir no banco.");
			e.printStackTrace();
			return false;
		} finally {
			c.desconecta();
		}
		return true;
	}

	@Override
	public boolean update(Cliente cli) {
		// Não implementado pois não há edição de Clientes no programa.
		return false;
	}

	@Override
	public boolean delete(Cliente cli) {
		Conexao c = new Conexao();
		Connection con = c.getConexao();

		String sql = "DELETE FROM cadastrodeanuncios WHERE id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, cli.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Não foi possível deletar");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ArrayList<Cliente> getAll() {
		ArrayList<Cliente> clientes = new ArrayList<>();

		Conexao c = new Conexao();
		Connection con = c.getConexao();

		String sql = "SELECT * FROM cadastrodeanuncios ORDER BY id";

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			// No banco: id, nomeanuncio, nomecliente, datainicio, datatermino,
			// investimentodiario
			// No Java: id, nomeAnuncio, nomeCliente, dataInicio, dataTermino,
			// investimentoDiario
			while (rs.next()) {
				Cliente cli = new Cliente();

				cli.setId(rs.getInt("id"));
				cli.setNomeAnuncio(rs.getString("nomeanuncio"));
				cli.setNomeCliente(rs.getString("nomecliente"));
				cli.setDataInicio(rs.getDate("datainicio"));
				cli.setDataTermino(rs.getDate("datatermino"));
				cli.setInvestimentoDiario(rs.getFloat("investimentodiario"));

				clientes.add(cli);
			}
		} catch (SQLException e) {
			System.out.println("Não foi possível carregar");
			e.printStackTrace();
			return null;
		}
		return clientes;
	}
}