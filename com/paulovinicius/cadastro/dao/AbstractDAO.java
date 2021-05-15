package com.paulovinicius.cadastro.dao;

import java.util.ArrayList;

/**
 * Classe abstrata utilizando padrão DAO. Implementada em ClienteDAO.
 * 
 * @author Paulo Vinícius Santos Costa
 *
 * @param <T> Classe que vai ter seus dados persistidos.
 */
public abstract class AbstractDAO<T> {
	/**
	 * Faz inserção do objeto no banco de dados.
	 * 
	 * @param objeto Qualquer objeto para a implementação.
	 * @return true ou false, se inserido com sucesso ou não.
	 */
	public abstract boolean insert(T objeto);

	/**
	 * Faz atualização do objeto no banco de dados.
	 * 
	 * @param objeto Qualquer objeto para a implementação.
	 * @return true ou false, se atualizado com sucesso ou não
	 */
	public abstract boolean update(T objeto);

	/**
	 * Deleta certo objeto do banco de dados.
	 * 
	 * @param objeto Qualquer objeto para a implementação.
	 * @return true ou false, se deletado com sucesso ou não
	 */
	public abstract boolean delete(T objeto);

	/**
	 * Pega todas as entradas do banco e as transforma em objetos.
	 * 
	 * @return ArrayList com todos os objetos de tal tabela no banco.
	 */
	public abstract ArrayList<T> getAll();
}
