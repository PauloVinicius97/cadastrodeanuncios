package com.paulovinicius.cadastro.teste;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.paulovinicius.cadastro.main.Cliente;

/**
 * Realiza alguns testes na classe Cliente.
 * 
 * @author Paulo Vinícius Santos Costa
 *
 */
public class ClienteTest {
	Date data1;
	Date data2;
	Cliente cliente;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cliente = new Cliente();
		cliente.setCliente(1, "Anuncio", "Cliente", data1, data2, 1f);
		this.data1 = Date.valueOf("2020-10-23");
		this.data2 = Date.valueOf("2020-10-27");
	}
	
	@Test
	/**
	 * Verifica se a quantidade de cliques está vindo correta baseada nos dados apresentados.
	 * @throws Exception qualquer excessão que seja descoberta pelo teste.
	 */
	public void testquantidadeCliques() throws Exception {
		this.setUp();
		assertEquals(14, cliente.quantidadeCliques());
	}
	
	@Test
	/**
	 * Verifica se a quantidade de compartilhamentos está correta baseada nos dados apresentados.
	 * @throws Exception qualquer excessão que seja descoberta pelo teste.
	 */
	public void testQuantidadeCompartilhamentos() throws Exception {
		this.setUp();
		assertEquals(2, cliente.quantidadeCompartilhamentos());
	}
	
	@Test
	/**
	 * Verifica se a quantidade de visualizações está correta baseada nos dados apresentados.
	 * @throws Exception qualquer excessão que seja descoberta pelo teste.
	 */
	public void testQuantidadeVisualizacoes() throws Exception {
		this.setUp();
		assertEquals(200, cliente.quantidadeVisualizacoes());
	}
}