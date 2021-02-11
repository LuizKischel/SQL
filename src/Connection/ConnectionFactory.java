package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {								
		
		public Connection getConnection(String usuario, String senha, int port, String schema) {
	        Connection conn = null;
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            String url = "jdbc:mysql://localhost:"+ String.valueOf(port) +"/" + schema;
	            String username = usuario; 
	            String password = senha;

	            conn = DriverManager.getConnection(url, username, password);
	            return conn;

	        } catch (ClassNotFoundException e) {
	            System.out.println("O driver expecificado nao foi encontrado.");
	            return null;

	        } catch (SQLException e) {
	            System.out.println(e);
	            System.out.println("Nao foi possivel conectar ao banco de dados.");
	            return null;
	        }
	    }
}
