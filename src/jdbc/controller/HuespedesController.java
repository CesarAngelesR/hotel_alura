package jdbc.controller;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import jdbc.dao.HuespedesDao;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;


public class HuespedesController {
	 private HuespedesDao huespedesDao;
	 
	 public HuespedesController() {
			Connection connection = new ConnectionFactory().recuperaConexion();
			this.huespedesDao = new HuespedesDao(connection);
		}
	 
		public void guardar(Huespedes huespedes) {
			this.huespedesDao.guardar(huespedes);
		}
		public List<Huespedes> listarHuespedes() {
			return this.huespedesDao.listarHuespedes();
		}
		
		public List<Huespedes> listarHuespedesId(String id) {
			return this.huespedesDao.buscarId(id);
		}
		
		public void actualizar(String nombre, String apellido, Date fechaN, String nacionalidad, String telefono, String idReserva, Integer id) {
			this.huespedesDao.Actualizar(nombre, apellido, fechaN, nacionalidad, telefono, idReserva, id);
		}
		
		public void Eliminar(Integer id) {
			this.huespedesDao.Eliminar(id);
		}
}