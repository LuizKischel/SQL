package Controler;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Connection.ConnectionFactory;
import Model.Beneficiario;
import Model.Categoria;
import Model.Desempregado;
import Model.Empregado;
import Model.Empregador;

public class Teste {

	public static void main(String[] args) throws ParseException, SQLException {
		
		Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse("02/02/2001");
		Beneficiario ben = new Empregado("Teste", dataNascimento, "SC", Categoria.EMPREGADO, true);
		Beneficiario bene = new Empregador("Teste", dataNascimento, "SC", Categoria.EMPREGADOR, 10);
		Beneficiario benee = new Desempregado("Teste", dataNascimento, "SC", Categoria.DESEMPREGADO, 100);
		benee.inserirDb();
	
	}

}
