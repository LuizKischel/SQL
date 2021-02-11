package Controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import Model.Beneficiario;
import Model.Categoria;

public class main {

	
	public static void main(String[] args) {
		
		ArrayList<Beneficiario> beneficiarios = new ArrayList<Beneficiario>();
		Beneficiario beneficiarioAtual;
		do {
			try {
				beneficiarioAtual = cadastro();
				beneficiarios.add(beneficiarioAtual);
				System.out.println("\nBENEFICI�RIO CADASTRADO:\n");
				System.out.println(beneficiarioAtual.toString());
				System.out.println(beneficiarioAtual.beneficio());
				System.out.println(beneficiarioAtual.regrasAplicadas());
			} catch (Exception e) {
				e.printStackTrace();
			}
			mostrarDadosBeneficiarios(beneficiarios);
		} while(cadastrarNovoBeneficiario());
		
		System.out.println("Cadastro de benefici�rios finalizado.");
	}
	
	public static void mostrarDadosBeneficiarios(ArrayList<Beneficiario> beneficiarios) {
		System.out.println("\nRESUMO DOS BENEFICI�RIOS:\n");
		System.out.println("Usu�rios lidos: " + String.valueOf(beneficiarios.size()));
		ArrayList<Beneficiario> saoBeneficiarios = new ArrayList<Beneficiario>();
		double valorTotalConcedido = 0;
		for (Beneficiario beneficiario : beneficiarios) {
			if(beneficiario.isBeneficiario()) {
				valorTotalConcedido += beneficiario.getValorBeneficio();
				saoBeneficiarios.add(beneficiario);
			}
		}
		int beneficiariosMaior = 2;
		System.out.println("Total de benefic�rios: " + String.valueOf(saoBeneficiarios.size()));
		System.out.println("Valor total concedido: R$ " + String.valueOf(Math.round(valorTotalConcedido * 100.0) / 100.0));
		saoBeneficiarios.sort((x, y) -> x.compareValor(y));
		System.out.println("\nBenefici�rios que recebem maior valor:");
		for (int i = 0; i < saoBeneficiarios.size(); i++) {
			if(i >= beneficiariosMaior) {
				break;
			}
			Beneficiario beneficiario = saoBeneficiarios.get(i);
			System.out.println("> " + beneficiario.getNomeCompleto() + " - R$ " + String.valueOf(beneficiario.getValorBeneficio()));
		}
		saoBeneficiarios.sort((x, y) -> x.compareTempo(y));
		System.out.println("\nBenefici�rios que recebem por mais tempo:");
		for (int i = 0; i < saoBeneficiarios.size(); i++) {
			if(i >= beneficiariosMaior) {
				break;
			}
			Beneficiario beneficiario = saoBeneficiarios.get(i);
			System.out.println("> " + beneficiario.getNomeCompleto() + " - " + String.valueOf(beneficiario.getMesesBeneficio()) + " meses");
		}
	}
	
	public static Beneficiario cadastro() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Iniciando cadastro de novo benefici�rio.");
		System.out.println("Informe o nome completo do benefici�rio");
		String nomeCompleto = scan.nextLine();
		System.out.println("Informe a data de nascimento do benefici�rio");
		Date dataNascimento;
		while(true) {
			try {
				dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(scan.nextLine());
				break;
			} catch (ParseException e) {
				System.out.println("Formato de data inv�lido, insira novamente. \nExemplo de formato v�lido: 30/08/2002");
			} 
		}
		System.out.println("Informe o UF do estado do benefici�rio:");
		String estado = scan.nextLine();
		System.out.println("Em que categoria se encontra o benefici�rio? (Empregado, Empregador ou Desempregado)");
		String categoriaDescricao;
		while(true) {
			categoriaDescricao = scan.nextLine();
			if(Categoria.isValid(categoriaDescricao)) {
				break;
			}
			System.out.println("Categoria inv�lida, digite uma das tr�s categorias: Empregado, Empregador ou Desempregado.");
		}
		Categoria categoria = Categoria.getCategoria(categoriaDescricao);
		@SuppressWarnings("unchecked")
		Beneficiario beneficiario = (Beneficiario) categoria.getClasse().getConstructor(String.class, Date.class, String.class, Categoria.class).newInstance(nomeCompleto, dataNascimento, estado, categoria);
		return beneficiario;
	}
	
	public static boolean cadastrarNovoBeneficiario() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nDeseja cadastrar novo benefici�rio? (S/N)");
		String resposta = scan.next().toLowerCase();
		if(resposta.equals("s") || resposta.equals("si") || resposta.equals("sim") || resposta.equals("y") || resposta.equals("yes") ) {
			return true;			
		}
		return false;
	}

}
