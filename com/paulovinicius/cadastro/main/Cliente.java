/**
 * @author Paulo Vinícius Santos Costa
 * Desafio de programação da Capgemini
 */
package com.paulovinicius.cadastro.main;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Classe que gerencia o cliente e faz cálculos pertinentes ao mesmo
 * 
 * @author Paulo Vinícius Santos Costa
 */
public class Cliente {
	private int id;
	private String nomeAnuncio, nomeCliente;
	private Date dataInicio, dataTermino;
	private float investimentoDiario;

	public Cliente() {
	};

	public void setCliente(int id, String nomeAnuncio, String nomeCliente, Date dataInicio, Date dataTermino,
			float investimentoDiario) {
		this.id = id;
		this.nomeAnuncio = nomeAnuncio;
		this.nomeCliente = nomeCliente;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.investimentoDiario = investimentoDiario;
	}

	/**
	 * Retorna quantidade de cliques, veja o método calculo
	 * 
	 * @return quantidade de cliques em inteiro
	 */
	public int quantidadeCliques() {
		return this.calculo(this.valorTotalInvestido(), 1);
	}

	/**
	 * Retorna quantidade de compartilhamentos, veja o método calculo
	 * 
	 * @return quantidade de compartilhamentos em inteiro
	 */
	public int quantidadeCompartilhamentos() {
		return this.calculo(this.valorTotalInvestido(), 2);
	}

	/**
	 * Retorna quantidade de visualizações, veja o método calculo
	 * 
	 * @return quantidade de visualizações em inteiro
	 */
	public int quantidadeVisualizacoes() {
		return this.calculo(this.valorTotalInvestido(), 3);
	}

	/**
	 * Calcula o valor total investido baseado no tempo da campanha
	 * 
	 * @return valor total investido
	 */
	public float valorTotalInvestido() {
		long diferencaTempo = this.dataTermino.getTime() - this.dataInicio.getTime();
		long diferencaDias = ((diferencaTempo / (1000 * 60 * 60 * 24)) % 365);

		return diferencaDias * this.investimentoDiario;
	}

	/**
	 * Realiza o cálculo de acordo com os critérios da atividade, que são, em ordem
	 * de execução: <br>
	 * <br>
	 * 1 - 30 pessoas visualizam o anúncio original (não compartilhado) a cada
	 * R$1,00 investido; <br>
	 * 2 - a cada 100 pessoas que visualizam o anúncio 12 clicam nele; <br>
	 * 3 - a cada 20 pessoas que clicam no anúncio 3 compartilham nas redes sociais;
	 * <br>
	 * 4 - o mesmo anúncio é compartilhado no máximo 4 vezes em sequência; <br>
	 * 5 - cada compartilhamento nas redes sociais gera 40 novas visualizações. <br>
	 * <br>
	 * São usados doubles para lidar com números fracionários, que são arredondados
	 * em certo ponto para não ocorrerem erros (já que não existem 0,5 pessoas).
	 * 
	 * @param valorMonetario valor em dinheiro usado para o cálculo; valor menor que
	 *                       zero retorna -1
	 * @param opt            1 calcula cliques, 2 calcula compartilhamentos, 3
	 *                       calcula visualizacoes, outros valores retornam -1
	 * @return veja parâmetros opt e valorMonetario
	 */
	private int calculo(float valorMonetario, int opt) {
		if (valorMonetario < 0) {
			return -1;
		} else {
			double numeroCliques, numeroCompartilhamentos, numeroVisualizacoes;

			numeroVisualizacoes = valorMonetario * 30.0;

			switch (opt) {
			case 1:
				numeroCliques = numeroVisualizacoes * (12.0 / 100.0); // Número de cliques
				return Double.valueOf(numeroCliques).intValue();

			case 2:
				numeroCliques = numeroVisualizacoes * (12.0 / 100.0); // Número de cliques
				numeroCompartilhamentos = numeroCliques * (3.0 / 20.0); // Número de compartilhamentos
				return Double.valueOf(numeroCompartilhamentos).intValue();

			case 3:
				numeroCliques = numeroVisualizacoes * (12.0 / 100.0); // Número de cliques

				numeroCompartilhamentos = numeroCliques * (3.0 / 20.0); // Número de compartilhamentos
				numeroCompartilhamentos = Math.floor(numeroCompartilhamentos); // Arredondando (não existem 0,5 pessoas)
				numeroCompartilhamentos -= Math.floor(numeroCompartilhamentos / 5.0); // Ajustando (entre 5 pessoas, 1
																						// não compartilha)

				numeroVisualizacoes += (numeroCompartilhamentos * 40.0);
				return Double.valueOf(numeroVisualizacoes).intValue();

			default:
				return -1;
			}
		}
	}

	/**
	 * Converte objeto LocalDate para java.util.Date
	 * 
	 * @param dataLocal a data em questão
	 * @return objeto java.util.Date
	 */
	public Date localDateParaDate(LocalDate dataLocal) {
		java.util.Date dataUtil = Date.from(dataLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date data = new Date(dataUtil.getTime());
		return data;
	}

	/**
	 * Verifica se a data recebida é igual à data inicial
	 * 
	 * @param dataLocal data recebida a ser comparada com DataInicio
	 * @return verdadeiro ou falso
	 */
	public boolean VerificaDataValidaInicialIgual(LocalDate dataLocal) {
		if (dataLocal != null) {
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy/mm/dd");
			if (formataData.format(this.getDataInicio())
					.equals(formataData.format(this.localDateParaDate(dataLocal)))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Verifica se a data recebida é igual à data final
	 * 
	 * @param dataLocal data recebida a ser comparada com DataTermino
	 * @return verdadeiro ou falso
	 */
	public boolean VerificaDataValidaFinalIgual(LocalDate dataLocal) {
		if (dataLocal != null) {
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy/mm/dd");
			if (formataData.format(this.getDataTermino())
					.equals(formataData.format(this.localDateParaDate(dataLocal)))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Verifica se uma data está entre a DataInicio e DataFim
	 * 
	 * @param dataLocal data a ser comparada
	 * @return verdadeiro ou falso
	 * @throws ParseException
	 */
	public boolean entreDatas(LocalDate localDateInicial, LocalDate localDateFinal) {
		SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");

		try {
			String dataInicialString = localDateInicial.toString();
			String dataInicialClienteString = this.getDataInicio().toString();
			String dataFinalString = localDateFinal.toString();
			String dataFinalClienteString = this.getDataTermino().toString();

			java.util.Date dataInicial = formataData.parse(dataInicialString);
			java.util.Date dataInicialCliente = formataData.parse(dataInicialClienteString);
			java.util.Date dataFinal = formataData.parse(dataFinalString);
			java.util.Date dataFinalCliente = formataData.parse(dataFinalClienteString);

			return ((dataInicial.getTime() >= dataInicialCliente.getTime())
					&& (dataFinal.getTime() <= dataFinalCliente.getTime()))
					|| (dataInicial.getTime() <= dataInicialCliente.getTime()
							&& (dataFinal.getTime()) >= dataFinalCliente.getTime());
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	/**
	 * Verifica se a data não é null.
	 * 
	 * @param data data a ser verificada.
	 * @return true ou false caso não nulo ou nulo
	 */
	public boolean verificaDataValida(LocalDate data) {
		if (data != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the nomeAnuncio
	 */
	public String getNomeAnuncio() {
		return nomeAnuncio;
	}

	/**
	 * @param nomeAnuncio the nomeAnuncio to set
	 */
	public void setNomeAnuncio(String nomeAnuncio) {
		this.nomeAnuncio = nomeAnuncio;
	}

	/**
	 * @return the nomeCliente
	 */
	public String getNomeCliente() {
		return nomeCliente;
	}

	/**
	 * @param nomeCliente the nomeCliente to set
	 */
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataTermino
	 */
	public Date getDataTermino() {
		return dataTermino;
	}

	/**
	 * @param dataTermino the dataTermino to set
	 */
	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	/**
	 * @return the investimentoDiario
	 */
	public float getInvestimentoDiario() {
		return investimentoDiario;
	}

	/**
	 * @param investimentoDiario the investimentoDiario to set
	 */
	public void setInvestimentoDiario(float investimentoDiario) {
		this.investimentoDiario = investimentoDiario;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
