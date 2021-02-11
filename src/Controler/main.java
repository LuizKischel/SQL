package Controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import Connection.ConnectionFactory;
import Model.Beneficiario;
import Model.Categoria;

public class main {

	public static void main(String[] args) throws SQLException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {	
		
		do {
			int acao = menuCadastro();
			if(acao == 0) {
				break;
			}
			else if (acao == 1) {
				loopCadastro();
			}
			else if (acao == 2) {
				loopUpdate();
			}
			else if (acao == 3) {
				loopDelete();
			}
			else if (acao == 4) {
				listarBeneficiario();
			}
			else if(acao == 5) {
				mostrarDadosBeneficiarios();
			}
		} while (true);
		System.out.println("Programa Finalizado!!!!!!");
	}
	
	public static Connection connection = new ConnectionFactory().getConnection("root", "", 3306, "beneficiario");
	
	public static void mostrarDadosBeneficiarios() throws SQLException {
		String queryTotal = "SELECT COUNT(*) AS total FROM beneficiario";	
		Statement statement = connection.createStatement();
		ResultSet resultTotal = statement.executeQuery(queryTotal);
		resultTotal.next();
		System.out.println("\nRESUMO DOS BENEFICIÁRIOS:\n");
		System.out.println("Usuários lidos: " + String.valueOf(resultTotal.getInt("total")));
		String queryTotalisBeneficiario = "SELECT COUNT(*) AS total FROM beneficiario WHERE valorBeneficio != 0";
		ResultSet resultTotalisBeneficiario = statement.executeQuery(queryTotalisBeneficiario);
		resultTotalisBeneficiario.next();
		
		
		System.out.println("Total de beneficários: " + String.valueOf(resultTotalisBeneficiario.getInt("total")));
		String queryTotalValorBeneficio = "SELECT SUM(valorBeneficio) AS total FROM beneficiario";
		ResultSet resultTotalValorBeneficio = statement.executeQuery(queryTotalValorBeneficio);
		resultTotalValorBeneficio.next();
		System.out.println("Valor total concedido: R$ " + String.valueOf(Math.round(resultTotalValorBeneficio.getDouble("total") * 100.0) / 100.0));
		System.out.println("\nBeneficiários que recebem maior valor:");
		String queryBeneficiarioMaiorValor = "SELECT valorBeneficio, nomeCompleto FROM beneficiario ORDER BY valorBeneficio DESC LIMIT 2";
		ResultSet resultBeneficiarioMaiorValor = statement.executeQuery(queryBeneficiarioMaiorValor);
		while (resultBeneficiarioMaiorValor.next()) {
			System.out.println("> " + resultBeneficiarioMaiorValor.getString("nomeCompleto") + " - R$ " + String.valueOf(resultBeneficiarioMaiorValor.getDouble("valorBeneficio")));
		}
		
		String queryBeneficiarioMaiorMes = "SELECT mesesBeneficio, nomeCompleto FROM beneficiario ORDER BY mesesBeneficio DESC LIMIT 2";
		ResultSet resultBeneficiarioMaiorMes = statement.executeQuery(queryBeneficiarioMaiorMes);
		System.out.println("\nBeneficiários que recebem maior numero de meses: ");
		while (resultBeneficiarioMaiorMes.next()) {
			System.out.println("> " + resultBeneficiarioMaiorMes.getString("nomeCompleto") + " - " + String.valueOf(resultBeneficiarioMaiorMes.getInt("mesesBeneficio")) + " meses.");
		}
	}
	
	public static Beneficiario cadastro() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Iniciando cadastro de novo beneficiário.");
		System.out.println("Informe o nome completo do beneficiário");
		String nomeCompleto = scan.nextLine();
		System.out.println("Informe a data de nascimento do beneficiário");
		Date dataNascimento;
		while(true) {
			try {
				dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(scan.nextLine());
				break;
			} catch (ParseException e) {
				System.out.println("Formato de data inválido, insira novamente. \nExemplo de formato válido: 30/08/2002");
			} 
		}
		System.out.println("Informe o UF do estado do beneficiário:");
		String estado = scan.nextLine();
		System.out.println("Em que categoria se encontra o beneficiário? (Empregado, Empregador ou Desempregado)");
		String categoriaDescricao;
		while(true) {
			categoriaDescricao = scan.nextLine();
			if(Categoria.isValid(categoriaDescricao)) {
				break;
			}
			System.out.println("Categoria inválida, digite uma das três categorias: Empregado, Empregador ou Desempregado.");
		}
		Categoria categoria = Categoria.getCategoria(categoriaDescricao);
		@SuppressWarnings("unchecked")
		Beneficiario beneficiario = (Beneficiario) categoria.getClasse().getConstructor(String.class, Date.class, String.class, Categoria.class).newInstance(nomeCompleto, dataNascimento, estado, categoria);
		return beneficiario;
	}
	
	public static boolean cadastrarNovoBeneficiario() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nDeseja cadastrar novo beneficiário? (S/N)");
		String resposta = scan.next().toLowerCase();
		if(resposta.equals("s") || resposta.equals("si") || resposta.equals("sim") || resposta.equals("y") || resposta.equals("yes") ) {
			return true;			
		}
		return false;
	}

	public static int menuCadastro() throws IOException {
		System.out.println("MENU");
		System.out.println("0 - Sair\n1 - Novo Beneficiario\n2 - Atualizar Beneficiario\n3 - Deletar Beneficiario\n4 - Listar Beneficiarios\n5 - Informações");
		Scanner scan = new Scanner(System.in);
		int acao;
		do {
			acao = scan.nextInt();
		} while (acao < 0 || acao > 5);
		limparConsole();
		return acao;
	}
	
	public static void limparConsole() {
		for (int i = 0; i < 3; i++) {
			System.out.println("");
		}
	}
	
	public static void listarBeneficiario() throws SQLException {
		Statement statement = connection.createStatement();
		String query = "SELECT beneficiario.nomeCompleto, beneficiario.id, categoria.descricao FROM beneficiario INNER JOIN categoria ON categoria.id = beneficiario.categoriaid";
		ResultSet result = statement.executeQuery(query);
		System.out.println("Lista de Beneficiarios: ");
		while (result.next()) {
			System.out.println(result.getInt("id") + ") " + result.getString("nomeCompleto") + " - " + result.getString("descricao"));
		}
	}
	
	public static void loopCadastro() throws SQLException {
		Beneficiario beneficiarioAtual;	
		do {
			try {
				beneficiarioAtual = cadastro();
				beneficiarioAtual.inserirDb();
				System.out.println("\nBENEFICIÁRIO CADASTRADO:\n");
				System.out.println(beneficiarioAtual.toString());
				System.out.println(beneficiarioAtual.beneficio());
				System.out.println(beneficiarioAtual.regrasAplicadas());
			} catch (Exception e) {
				e.printStackTrace();
			}
			mostrarDadosBeneficiarios();
		} while(cadastrarNovoBeneficiario());
		System.out.println("Cadastro de beneficiários finalizado.");
	}

	public static void loopUpdate() throws SQLException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Statement statement = connection.createStatement();
		Scanner scan = new Scanner(System.in);
		do {
			listarBeneficiario();
			System.out.println("Digite 0 para cancelar!!");
			System.out.println("Digite o ID do beneficiario que deseja atualizar: ");
			int id = scan.nextInt();
			if(id == 0) {
				break;
			}
			else {
				Beneficiario beneficiario = cadastro();
				beneficiario.atualizarDb(id);
			}
		} while (true);
		
	}
	
	public static void loopDelete() throws SQLException {
		Statement statement = connection.createStatement();
		Scanner scan = new Scanner(System.in);
		do {
			listarBeneficiario();
			System.out.println("Digite 0 para cancelar!!");
			System.out.println("Digite o ID do beneficiario que deseja deletar: ");
			int id = scan.nextInt();
			if(id == 0) {
				break;
			}
			else {
				String comandoDelete = "DELETE FROM beneficiario WHERE id = " + id; 
				statement.execute(comandoDelete);
			}
		} while (true);
	}

}






