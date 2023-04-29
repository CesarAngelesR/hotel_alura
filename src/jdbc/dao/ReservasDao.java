package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jdbc.modelo.Reserva;

public class ReservasDao {
	
	private Connection connection;
	
	public ReservasDao(Connection connection) {
		this.connection = connection;
	}
	
	public void guardar(Reserva reserva) {
		try {
			String sql = "INSERT INTO RESERVA (fechaentrada, fechasalida, valor, formadepago) VALUES (?, ?, ?, ?)";

			try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				statement.setDate(1, reserva.getFechaE());
				statement.setDate(2, reserva.getFechaS());
				statement.setString(3, reserva.getValor());
				statement.setString(4, reserva.getFormaPago());

				statement.executeUpdate();

				try (ResultSet rst = statement.getGeneratedKeys()) {
					while (rst.next()) {
						reserva.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<Reserva> buscar() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, fechaentrada, fechasalida, valor, formadepago FROM RESERVA";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.execute();

				transformarResultSetEnReserva(reservas, statement);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> buscarId(String id) {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {

			String sql = "SELECT id, fechaentrada, fechasalida, valor, formadepago FROM RESERVA WHERE id = ?";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, id);
				statement.execute();

				transformarResultSetEnReserva(reservas, statement);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Eliminar(Integer id) {
		try (PreparedStatement statement = connection.prepareStatement("DELETE FROM RESERVA WHERE id = ?")) {
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Actualizar(Date fechaE, Date fechaS, String valor, String formaPago, Integer id) {
		try (PreparedStatement statement = connection
				.prepareStatement("UPDATE RESERVA SET fechaentrada = ?, fechasalida = ?, valor = ?, formaDEPago = ? WHERE id = ?")) {
			statement.setDate(1, fechaE);
			statement.setDate(2, fechaS);
			statement.setString(3, valor);
			statement.setString(4, formaPago);
			statement.setInt(5, id);
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
						
	private void transformarResultSetEnReserva(List<Reserva> reservas, PreparedStatement statement) throws SQLException {
		try (ResultSet rst = statement.getResultSet()) {
			while (rst.next()) {
				Reserva produto = new Reserva(rst.getInt(1), rst.getDate(2), rst.getDate(3), rst.getString(4), rst.getString(5));

				reservas.add(produto);
			}
		}
	}
}
