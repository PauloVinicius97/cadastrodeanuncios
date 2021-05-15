package com.paulovinicius.cadastro.fx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.paulovinicius.cadastro.dao.ClienteDAO;
import com.paulovinicius.cadastro.main.Cliente;
import com.paulovinicius.cadastro.main.Main;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Controlador da tela inicial do programa, que contém funções tais como:
 * verificar dados do cliente, deletar cliente e filtragem de clientes. Utiliza
 * JavaFX 2.
 * 
 * @author Paulo Vinícius Santos Costa
 */
public class TelaInicialController implements Initializable {
	@FXML
	private TableView<Cliente> tabela;

	@FXML
	private TableColumn<?, ?> campanhaColuna;

	@FXML
	private TableColumn<?, ?> clienteColuna;

	@FXML
	private TableColumn<?, ?> dataDeInicioColuna;

	@FXML
	private TableColumn<?, ?> dataDeFimColuna;

	@FXML
	private TextField nomeCaixaTexto;

	@FXML
	private Button botaoAdicionarCliente;

	@FXML
	private Button botaoDeletarCliente;

	@FXML
	private Button botaoFiltrar;

	@FXML
	private Text valorTotalInvestidoTexto;

	@FXML
	private Text QuantidadeMaxVisTexto;

	@FXML
	private Text QuantidadeMaxCliquesTexto;

	@FXML
	private Text QuantidadeMaxCompTexto;

	@FXML
	private Text statusTexto;

	@FXML
	private Text investimentoDiarioTexto;

	@FXML
	private DatePicker dataInicioCaixaTexto;

	@FXML
	private DatePicker dataFimCaixaTexto;

	@FXML
	private Button limparFiltroBotao;

	@FXML
	private CheckBox intervaloSelecao;

	private ObservableList<Cliente> listaClientes;
	private ClienteDAO clienteDAO = new ClienteDAO();
	private ArrayList<Cliente> clientes;

	@Override
	/**
	 * Inicializa a tela, ou seja: cria objetos a partir do banco de dados e os
	 * insere na tela.
	 */
	public void initialize(URL url, ResourceBundle rb) {
		listaClientes = tabela.getItems();
		tabela.getItems();

		// No banco: id, nomeanuncio, nomecliente, datainicio, datatermino,
		// investimentodiario
		// No Java: id, nomeAnuncio, nomeCliente, dataInicio, dataTermino,
		// investimentoDiario
		campanhaColuna.setCellValueFactory(new PropertyValueFactory<>("nomeAnuncio"));
		clienteColuna.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
		dataDeInicioColuna.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
		dataDeFimColuna.setCellValueFactory(new PropertyValueFactory<>("dataTermino"));

		clientes = clienteDAO.getAll();

		for (Cliente cli : clientes) {
			listaClientes.add(cli);
		}
		statusTexto.setText("Carregado com sucesso.");
	}

	@FXML
	/**
	 * Vai pra tela "Adicionar cliente".
	 * 
	 * @param event clique no botão "Adicionar cliente."
	 */
	void botaoAdicionarCliente(ActionEvent event) {
		Main.trocaTela("/fxml/TelaAdicionar.fxml");
	}

	@FXML
	/**
	 * Deleta o cliente selecionado do banco de dados e apaga informações na tela.
	 * 
	 * @param event clique no botão "Deletar cliente".
	 */
	void botaoDeletarCliente(ActionEvent event) {
		Cliente clienteSelecionado = tabela.getSelectionModel().getSelectedItem();

		listaClientes.remove(clienteSelecionado);

		if (clienteDAO.delete(clienteSelecionado)) {
			statusTexto.setText("Cliente excluído.");

			investimentoDiarioTexto.setText("");
			valorTotalInvestidoTexto.setText("");
			QuantidadeMaxVisTexto.setText("");
			QuantidadeMaxCliquesTexto.setText("");
			QuantidadeMaxCompTexto.setText("");
		} else {
			statusTexto.setText("Erro ao excluir.");
		}
	}

	@FXML
	/**
	 * Atualiza a tela com os dados do cliente selecionado na lista. Mostra mensagem
	 * de erro se não tiver nenhum.
	 * 
	 * @param event Clique na tabela mostrando a lista.
	 */
	void clienteSelecionado(MouseEvent event) {
		try {
			Cliente clienteSelecionado = tabela.getSelectionModel().getSelectedItem();

			investimentoDiarioTexto.setText("R$" + Float.toString(clienteSelecionado.getInvestimentoDiario()));
			valorTotalInvestidoTexto.setText("R$" + Float.toString(clienteSelecionado.valorTotalInvestido()));
			QuantidadeMaxVisTexto.setText(Integer.toString(clienteSelecionado.quantidadeVisualizacoes()));
			QuantidadeMaxCliquesTexto.setText(Integer.toString(clienteSelecionado.quantidadeCliques()));
			QuantidadeMaxCompTexto.setText(Integer.toString(clienteSelecionado.quantidadeCompartilhamentos()));
		} catch (RuntimeException e) {
			statusTexto.setText("Nenhum cliente para selecionar.");
		}
	}

	@FXML
	/**
	 * Faz filtragem dos objetos criados no banco de dados, e mostra-os de acordo
	 * com os dados apresentados. Pode-se filtrar por: período, nome, data início,
	 * data fim, etc.
	 * 
	 * @param event Clique no botão "Filtrar".
	 */
	void botaoFiltrar(ActionEvent event) {
		investimentoDiarioTexto.setText("");
		valorTotalInvestidoTexto.setText("");
		QuantidadeMaxVisTexto.setText("");
		QuantidadeMaxCliquesTexto.setText("");
		QuantidadeMaxCompTexto.setText("");

		nomeCaixaTexto.setDisable(true);
		dataInicioCaixaTexto.setDisable(true);
		dataFimCaixaTexto.setDisable(true);

		Cliente cliente = new Cliente();

		// Se as caixas estão vazias, continuam false
		boolean caixaTextoPreenchida = !nomeCaixaTexto.getText().trim().isEmpty();
		boolean dataInicioPreenchida = cliente.verificaDataValida(dataInicioCaixaTexto.getValue());
		boolean dataFimPreenchida = cliente.verificaDataValida(dataFimCaixaTexto.getValue());
		boolean intervaloMarcado = intervaloSelecao.isSelected();

		System.out.println("Caixa de nome: " + !nomeCaixaTexto.getText().trim().isEmpty());
		System.out.println("Data fim: " + cliente.verificaDataValida(dataInicioCaixaTexto.getValue()));
		System.out.println("Data início: " + cliente.verificaDataValida(dataFimCaixaTexto.getValue()));

		// Se todas as caixas estão vazias
		if (!caixaTextoPreenchida && !dataInicioPreenchida && !dataFimPreenchida && !intervaloMarcado) {
			statusTexto.setText("Digite algum dado para pesquisa.");
			// Se só a caixa de nome está preenchida
			// Se der erro, adicionar o isEmpty() no IF
		} else if (caixaTextoPreenchida && !dataInicioPreenchida && !dataFimPreenchida && !intervaloMarcado) {
			listaClientes.clear();
			for (Cliente cli : clientes) {
				if (cli.getNomeCliente().contains(nomeCaixaTexto.getText())) {
					listaClientes.add(cli);
				}
			}
			statusTexto.setText("Filtro só de nome aplicado.");
			// Se só tem data inicial
		} else if (!caixaTextoPreenchida && dataInicioPreenchida && !dataFimPreenchida && !intervaloMarcado) {
			listaClientes.clear();
			for (Cliente cli : clientes) {
				if (cli.VerificaDataValidaInicialIgual(dataInicioCaixaTexto.getValue())) {
					listaClientes.add(cli);
				}
			}
			statusTexto.setText("Filtro só de data inicial aplicado.");
			// Se só tem data final
		} else if (!caixaTextoPreenchida && !dataInicioPreenchida && dataFimPreenchida && !intervaloMarcado) {
			listaClientes.clear();
			for (Cliente cli : clientes) {
				if (cli.VerificaDataValidaFinalIgual(dataFimCaixaTexto.getValue())) {
					listaClientes.add(cli);
				}
			}
			statusTexto.setText("Filtro só de data final aplicado.");
			// Se tem nome do cliente e data de início
		} else if (caixaTextoPreenchida && dataInicioPreenchida && !dataFimPreenchida && !intervaloMarcado) {
			listaClientes.clear();
			for (Cliente cli : clientes) {
				if (cli.getNomeCliente().contains(nomeCaixaTexto.getText())
						&& cli.VerificaDataValidaInicialIgual(dataInicioCaixaTexto.getValue())) {
					listaClientes.add(cli);
				}
			}
			statusTexto.setText("Filtro de nome e data inicial aplicado.");
			// Se tem nome do cliente e data de fim
		} else if (caixaTextoPreenchida && !dataInicioPreenchida && dataFimPreenchida && !intervaloMarcado) {
			listaClientes.clear();
			for (Cliente cli : clientes) {
				if (cli.getNomeCliente().contains(nomeCaixaTexto.getText())
						&& cli.VerificaDataValidaFinalIgual(dataFimCaixaTexto.getValue())) {
					listaClientes.add(cli);
				}
			}
			statusTexto.setText("Filtro de nome e data final aplicado.");
			// Se só tem data inicial e data final marcados
		} else if (!caixaTextoPreenchida && dataInicioPreenchida && dataFimPreenchida && !intervaloMarcado) {
			listaClientes.clear();
			for (Cliente cli : clientes) {
				if (cli.VerificaDataValidaInicialIgual(dataInicioCaixaTexto.getValue())
						&& cli.VerificaDataValidaFinalIgual(dataFimCaixaTexto.getValue())) {
					listaClientes.add(cli);
				}
			}
			statusTexto.setText("Filtro com ambas datas preenchidas aplicado.");
			// Se tem todos marcados (menos a seleção de intervalo)
		} else if (caixaTextoPreenchida && dataInicioPreenchida && dataFimPreenchida && !intervaloMarcado) {
			listaClientes.clear();
			for (Cliente cli : clientes) {
				if (cli.getNomeCliente().contains(nomeCaixaTexto.getText())
						&& cli.VerificaDataValidaInicialIgual(dataInicioCaixaTexto.getValue())
						&& cli.VerificaDataValidaFinalIgual(dataFimCaixaTexto.getValue())) {
					listaClientes.add(cli);
				}
			}
			statusTexto.setText("Filtro de cliente e datas aplicado.");
			// Se tem todos marcados + a seleção de intervalo
		} else if (caixaTextoPreenchida && dataInicioPreenchida && dataFimPreenchida && intervaloMarcado) {
			listaClientes.clear();
			for (Cliente cli : clientes) {
				if (cli.getNomeCliente().contains(nomeCaixaTexto.getText())
						&& cli.entreDatas(dataInicioCaixaTexto.getValue(), dataFimCaixaTexto.getValue())) {
					listaClientes.add(cli);
				}
				statusTexto.setText("Filtro de cliente e datas + intervalo aplicado.");
			}
			// Se tem ambas as datas e a caixinha tá marcada (sem nome de Cliente)
		} else if (!caixaTextoPreenchida && dataInicioPreenchida && dataFimPreenchida && intervaloMarcado) {
			listaClientes.clear();
			for (Cliente cli : clientes) {
				if (cli.entreDatas(dataInicioCaixaTexto.getValue(), dataFimCaixaTexto.getValue())) {
					listaClientes.add(cli);
				}
			}
			statusTexto.setText("Filtro de datas + intervalo aplicado.");
		} else if ((!dataInicioPreenchida || !dataFimPreenchida) && intervaloMarcado) {
			statusTexto.setText("Precisa selecionar duas datas para busca com intervalo.");
		}

		if (listaClientes.isEmpty()) {
			statusTexto.setText("Nada encontrado com estes parâmetros.");
		}
	}

	@FXML
	/**
	 * Remove o filtro selecionado e mostra todos os dados.
	 * 
	 * @param event Clique no botão "Remover filtro".
	 */
	void limparFiltroBotao(ActionEvent event) {
		investimentoDiarioTexto.setText("");
		valorTotalInvestidoTexto.setText("");
		QuantidadeMaxVisTexto.setText("");
		QuantidadeMaxCliquesTexto.setText("");
		QuantidadeMaxCompTexto.setText("");

		nomeCaixaTexto.setDisable(false);
		dataInicioCaixaTexto.setDisable(false);
		dataFimCaixaTexto.setDisable(false);

		listaClientes.clear();
		for (Cliente cli : clientes) {
			listaClientes.add(cli);
		}
		nomeCaixaTexto.setText("");
		dataInicioCaixaTexto.setValue(null);
		dataFimCaixaTexto.setValue(null);

		statusTexto.setText("Filtro limpo.");
	}
}
