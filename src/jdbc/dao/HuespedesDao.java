package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.modelo.Huespedes;
import jdbc.modelo.Reserva;


public class HuespedesDao {
private Connection connection;
	
	public HuespedesDao(Connection connection) {
		this.connection = connection;
	}
	
	public void guardar(Huespedes huespedes) {
		try {
			String sql = "INSERT INTO huesped (nombre, apellido, fechanacimiento, nacionalidad, telefono, idreserva) VALUES (?, ?, ?, ?,?,?)";

			try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				statement.setString(1, huespedes.getNombre());
				statement.setString(2, huespedes.getApellido());
				statement.setDate(3, huespedes.getFechaN());
				statement.setString(4, huespedes.getNacionalidad());
				statement.setString(5, huespedes.getTelefono());
				statement.setInt(6, huespedes.getIdReserva());

				statement.execute();

				try (ResultSet rst = statement.getGeneratedKeys()) {
					while (rst.next()) {
						huespedes.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Huespedes> listarHuespedes() {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {
			String sql = "SELECT id, nombre, apellido, fechanacimiento, nacionalidad, telefono, idreserva FROM huesped";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.execute();

				transformarResultSetEnHuesped(huespedes, statement);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Huespedes> buscarId(String id) {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {

			String sql = "SELECT id, nombre, apellido, fechanacimiento, nacionalidad, telefono, idreserva FROM huesped WHERE idreserva = ?";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, id);
				statement.execute();

				transformarResultSetEnHuesped(huespedes, statement);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int Actualizar(String nombre, String apellido, Date fechaN, String nacionalidad, String telefono, String idReserva, Integer id) {
		try{
			final PreparedStatement statement = connection.prepareStatement("UPDATE huesped SET nombre = ?, apellido = ?, fechanacimiento = ?, nacionalidad = ?, telefono = ?, idreserva = ? WHERE id = ?"); 
				try (statement) {
					statement.setString(1, nombre);
					statement.setString(2, apellido);
					statement.setDate(3, fechaN);
					statement.setString(4, nacionalidad);
					statement.setString(5, telefono);
					statement.setString(6, idReserva);
					statement.setInt(7, id);
					statement.execute();
					
		            int updateCount = statement.getUpdateCount();

		            return updateCount; 
				} 
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void Eliminar(Integer id) {
		try (PreparedStatement statement = connection.prepareStatement("DELETE FROM huesped WHERE id = ?")) {
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void transformarResultSetEnHuesped(List<Huespedes> reservas, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Huespedes huespedes = new Huespedes(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDate(4), rst.getString(5), rst.getString(6), rst.getInt(7));
				reservas.add(huespedes);
			}
		}				
	}
	
	
		
}

