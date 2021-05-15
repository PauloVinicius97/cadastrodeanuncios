package com.paulovinicius.cadastro.fx;

import com.paulovinicius.cadastro.dao.ClienteDAO;
import com.paulovinicius.cadastro.main.Cliente;
import com.paulovinicius.cadastro.main.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Classe que controla tela de adicionar clientes, utilizando JavaFX 2.
 * 
 * @author Paulo Vinícius Santos Costa
 */
public class TelaAdicionarController {

	@FXML
	private TextField nomeDoAnuncioTexto;

	@FXML
	private TextField nomeClienteTexto;

	@FXML
	private TextField investimentoDiaTexto;

	@FXML
	private DatePicker dataInicioTexto;

	@FXML
	private DatePicker dataFimTexto;

	@FXML
	private Button VoltarBotao;

	@FXML
	private Button adicionarClienteBotao;

	@FXML
	private Text statusTexto;

	@FXML
	/**
	 * Ao clicar no botão "Voltar", usuário é retornado para a tela inciial.
	 * 
	 * @param event Clique no botão "Voltar".
	 */
	void VoltarBotao(ActionEvent event) {
		Main.trocaTela("/fxml/TelaInicial.fxml");
	}

	@FXML
	/**
	 * Adiciona cliente ao banco de dados utilizado, a partir do uso de objeto DAO.
	 * Primeiro são verificados se os dados estão corretos. Caso esteja tudo ok, os
	 * dados são adicionados como um novo cliente no banco. Caso não, usuário será
	 * avisado do que está errado a partir da interface.
	 * 
	 * @param event Clique no botão "Adicionar cliente".
	 */
	void adicionarClienteBotao(ActionEvent event) {
		Cliente cliente = new Cliente();
		ClienteDAO clienteDAO = new ClienteDAO();

		if (nomeDoAnuncioTexto.getText().trim().isEmpty() || nomeClienteTexto.getText().trim().isEmpty()
				|| dataInicioTexto.getValue() == null || dataFimTexto.getValue() == null
				|| investimentoDiaTexto.getText().trim().isEmpty()) {
			statusTexto.setText("Digite os dados!");
		} else if (dataInicioTexto.getValue() == null || dataFimTexto.getValue() == null) {
			statusTexto.setText("Data inválida!");
		} else {
			try {
				float investimentoDiario = Float.parseFloat(investimentoDiaTexto.getText());

				cliente.setNomeAnuncio(nomeDoAnuncioTexto.getText());
				cliente.setNomeCliente(nomeClienteTexto.getText());
				cliente.setDataInicio(cliente.localDateParaDate(dataInicioTexto.getValue()));
				cliente.setDataTermino(cliente.localDateParaDate(dataFimTexto.getValue()));
				cliente.setInvestimentoDiario(investimentoDiario);
				
				if(cliente.valorTotalInvestido() < 0) {
					statusTexto.setText("Data de término está antes da data de início.");
				} else {
					if (clienteDAO.insert(cliente)) {
						statusTexto.setText("Adicionado com sucesso!");
					} else {
						statusTexto.setText("Erro ao inserir no banco.");
					}
				}
			} catch (NumberFormatException e) {
				statusTexto.setText("Investimento diário inválido!");
			}
		}
	}
}
