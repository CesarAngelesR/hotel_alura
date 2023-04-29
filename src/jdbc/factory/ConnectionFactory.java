package jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	private DataSource datasourse;
	
	public ConnectionFactory() {
		ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotel_alura?userTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("NCae7884");
		pooledDataSource.setMaxPoolSize(10);
		
		
		this.datasourse = pooledDataSource;
	}
	
	public Connection recuperaConexion() {
		try{
			return this.datasourse.getConnection();
		
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
