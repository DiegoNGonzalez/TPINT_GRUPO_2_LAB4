package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import dao.PrestamoDao;
import entidades.Cliente;
import entidades.Cuenta;
import entidades.Prestamo;

public class PrestamoDaoImpl implements PrestamoDao {

	@Override
	public ArrayList<Prestamo> listarPrestamos() {
		String query = "SELECT idPrestamo, idCliente, idCuenta, fechaAltaPrestamo, importePrestamo, mesesPlazo, importeCuota, cantidadCuotas, EstadoPrestamo FROM prestamos WHERE EstadoPrestamo = 1";
		ArrayList<Prestamo> listaPrestamos = new ArrayList<>();

		try (Connection conexion = Conexion.getConnection();
				PreparedStatement statement = conexion.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Prestamo prestamo = new Prestamo();
				Cliente cliente = new ClienteDaoImpl().obtenerClientePorId(resultSet.getInt("idCliente"));
				Cuenta cuenta = new CuentaDaoImpl().obtenerCuentaPorId(resultSet.getInt("idCuenta"));

				prestamo.setIdPrestamo(resultSet.getInt("idPrestamo"));
				prestamo.setCliente(cliente);
				prestamo.setCuenta(cuenta);
				prestamo.setFechaAltaPrestamo(resultSet.getDate("fechaAltaPrestamo"));
				prestamo.setImporteTotal(resultSet.getFloat("importePrestamo"));
				prestamo.setPlazo(resultSet.getInt("mesesPlazo"));
				prestamo.setImporteCuota(resultSet.getFloat("importeCuota"));
				prestamo.setCantCuotas(resultSet.getInt("cantidadCuotas"));
				prestamo.setEstado(resultSet.getString("EstadoPrestamo"));

				listaPrestamos.add(prestamo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaPrestamos;
	}

	@Override
	public ArrayList<Prestamo> listarPrestamosXEstado(String estado) {
		String query = "SELECT idPrestamo, idCliente, idCuenta, fechaAltaPrestamo, importePrestamoSolicitado , importePrestamo, mesesPlazo, importeCuota, cantidadCuotas, EstadoPrestamo FROM prestamos WHERE EstadoPrestamo = ?";
		ArrayList<Prestamo> listaPrestamos = new ArrayList<>();

		try (Connection conexion = Conexion.getConnection();
				PreparedStatement statement = conexion.prepareStatement(query)) {

			statement.setString(1, estado);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Prestamo prestamo = new Prestamo();

					Cliente cliente = new ClienteDaoImpl().obtenerClientePorId(resultSet.getInt("idCliente"));
					Cuenta cuenta = new CuentaDaoImpl().obtenerCuentaPorId(resultSet.getInt("idCuenta"));

					prestamo.setIdPrestamo(resultSet.getInt("idPrestamo"));
					prestamo.setCliente(cliente);
					prestamo.setCuenta(cuenta);
					prestamo.setFechaAltaPrestamo(resultSet.getDate("fechaAltaPrestamo")); // Usar getTimestamp si
																							// necesitamos la hora
					prestamo.setImporteSolicitado(resultSet.getFloat("importePrestamoSolicitado"));
					prestamo.setImporteTotal(resultSet.getFloat("importePrestamo"));
					prestamo.setPlazo(resultSet.getInt("mesesPlazo"));
					prestamo.setImporteCuota(resultSet.getFloat("importeCuota"));
					prestamo.setCantCuotas(resultSet.getInt("cantidadCuotas"));
					prestamo.setEstado(resultSet.getString("EstadoPrestamo"));

					listaPrestamos.add(prestamo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaPrestamos;
	}

	public ArrayList<Prestamo> listarPrestamosPendientes() {
		return listarPrestamosXEstado("Pendiente");
	}

	public ArrayList<Prestamo> listarPrestamosAprobados() {
		return listarPrestamosXEstado("Activo");
	}

	public ArrayList<Prestamo> listarPrestamosRechazados() {
		return listarPrestamosXEstado("Rechazado");
	}

	@Override
	public boolean agregarPrestamo(Prestamo prestamo) {
		String query = "INSERT INTO prestamos(idCliente, idCuenta, fechaAltaPrestamo, importePrestamoSolicitado ,importePrestamo, mesesPlazo, importeCuota, cantidadCuotas, EstadoPrestamo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conexion = Conexion.getConnection();
				PreparedStatement statement = conexion.prepareStatement(query)) {

			statement.setInt(1, prestamo.getCliente().getIdCliente());
			statement.setInt(2, prestamo.getCuenta().getIdCuenta());
			statement.setDate(3, new java.sql.Date(prestamo.getFechaAltaPrestamo().getTime()));
			statement.setFloat(4, prestamo.getImporteSolicitado());
			statement.setFloat(5, prestamo.getImporteTotal());
			statement.setInt(6, prestamo.getPlazo());
			statement.setFloat(7, prestamo.getImporteCuota());
			statement.setInt(8, prestamo.getCantCuotas());
			statement.setString(9, prestamo.getEstado());

			int filas = statement.executeUpdate();
			return filas > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean bajarPrestamo(int idPrestamo) {
		String query = "UPDATE prestamos SET EstadoPrestamo = 'Rechazado' WHERE idPrestamo = ?";

		try (Connection conexion = Conexion.getConnection();
				PreparedStatement statement = conexion.prepareStatement(query)) {

			statement.setInt(1, idPrestamo);

			return statement.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Prestamo prestamoXId(int idPrestamo) {
		String query = "SELECT idPrestamo, idCliente, idCuenta, fechaAltaPrestamo, importePrestamoSolicitado, importePrestamo, mesesPlazo, importeCuota, cantidadCuotas, EstadoPrestamo "
				+ "FROM prestamos WHERE idPrestamo = ?";

		Prestamo prestamo = null;

		try (Connection conexion = Conexion.getConnection();
				PreparedStatement statement = conexion.prepareStatement(query)) {

			statement.setInt(1, idPrestamo);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					prestamo = new Prestamo();

					Cliente cliente = new ClienteDaoImpl().obtenerClientePorId(resultSet.getInt("idCliente"));
					Cuenta cuenta = new CuentaDaoImpl().obtenerCuentaPorId(resultSet.getInt("idCuenta"));

					prestamo.setIdPrestamo(resultSet.getInt("idPrestamo"));
					prestamo.setCliente(cliente);
					prestamo.setCuenta(cuenta);
					prestamo.setFechaAltaPrestamo(resultSet.getDate("fechaAltaPrestamo"));
					prestamo.setImporteSolicitado(resultSet.getFloat("importePrestamoSolicitado"));
					prestamo.setImporteTotal(resultSet.getFloat("importePrestamo"));
					prestamo.setPlazo(resultSet.getInt("mesesPlazo"));
					prestamo.setImporteCuota(resultSet.getFloat("importeCuota"));
					prestamo.setCantCuotas(resultSet.getInt("cantidadCuotas"));
					prestamo.setEstado(resultSet.getString("EstadoPrestamo"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return prestamo;
	}

	@Override
	public ArrayList<Prestamo> listarPrestamosXCliente(int idCliente) {
		String query = "SELECT idPrestamo, idCliente, idCuenta, fechaAltaPrestamo, importePrestamoSolicitado, importePrestamo, mesesPlazo, importeCuota, cantidadCuotas, EstadoPrestamo "
				+ "FROM prestamos WHERE idCliente = ?";
		ArrayList<Prestamo> listaPrestamos = new ArrayList<Prestamo>();

		try (Connection conexion = Conexion.getConnection();
				PreparedStatement statement = conexion.prepareStatement(query)) {

			statement.setInt(1, idCliente);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Prestamo prestamo = new Prestamo();
					Cliente cliente = new ClienteDaoImpl().obtenerClientePorId(resultSet.getInt("idCliente"));
					Cuenta cuenta = new CuentaDaoImpl().obtenerCuentaPorId(resultSet.getInt("idCuenta"));

					prestamo.setIdPrestamo(resultSet.getInt("idPrestamo"));
					prestamo.setCliente(cliente);
					prestamo.setCuenta(cuenta);
					prestamo.setFechaAltaPrestamo(resultSet.getDate("fechaAltaPrestamo"));
					prestamo.setImporteSolicitado(resultSet.getFloat("importePrestamoSolicitado"));
					prestamo.setImporteTotal(resultSet.getFloat("importePrestamo"));
					prestamo.setPlazo(resultSet.getInt("mesesPlazo"));
					prestamo.setImporteCuota(resultSet.getFloat("importeCuota"));
					prestamo.setCantCuotas(resultSet.getInt("cantidadCuotas"));
					prestamo.setEstado(resultSet.getString("EstadoPrestamo"));

					listaPrestamos.add(prestamo);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error de SQL: " + e.getMessage());
			e.printStackTrace(); // Proporciona m�s detalles sobre el error
		} catch (Exception e) {
			System.err.println("Error inesperado: " + e.getMessage());
			e.printStackTrace(); // Maneja otros tipos de errores
		}

		return listaPrestamos;
	}

	@Override
	public boolean darDeAltaPrestamo(Prestamo prestamo) {
		String query = "UPDATE prestamos SET EstadoPrestamo = ? WHERE idPrestamo = ?";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement statement = conexion.prepareStatement(query)) {
			statement.setString(1, prestamo.getEstado());
			statement.setInt(2, prestamo.getIdPrestamo());
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void SP_AprobarPrestamo(Prestamo prestamo) throws SQLException {
		try {
			Connection conexion = Conexion.getConnection();
			CallableStatement cst = conexion.prepareCall("CALL spAprobarPrestamo" + "(?,?,?,?,?)");

			cst.setInt(1, prestamo.getCuenta().getIdCuenta());
			cst.setInt(2, prestamo.getIdPrestamo());
			cst.setFloat(3, prestamo.getImporteSolicitado());
			cst.setFloat(4, prestamo.getImporteCuota());
			cst.setInt(5, prestamo.getCantCuotas());

			cst.execute();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
	}

	@Override
	public int ultimoID() {
		String query = "SELECT MAX(id_prestamo) AS ultimo_id FROM prestamos";
		try (Connection conexion = Conexion.getConnection();
				PreparedStatement statement = conexion.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt("ultimo_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Retornar -1 si no se encuentra un ID valido.
	}

	@Override
	public ArrayList<Prestamo> filtrarPrestamos(Date fechaInicio, Date fechaFin, Float montoMinimo, Float montoMaximo,
			int idCliente) {
		ArrayList<Prestamo> prestamosFiltrados = new ArrayList<>();
		String query = "SELECT DISTINCT p.idPrestamo, p.idCliente, p.idCuenta, p.fechaAltaPrestamo, "
				+ "p.importePrestamo, p.mesesPlazo, p.importeCuota, p.cantidadCuotas, p.EstadoPrestamo "
				+ "FROM prestamos p WHERE 1=1 ";

		ArrayList<Object> parametros = new ArrayList<>();

		// Filtro por fecha
		if (fechaInicio != null && fechaFin != null) {
			query += " AND p.fechaAltaPrestamo BETWEEN ? AND ? ";
			parametros.add(fechaInicio);
			parametros.add(fechaFin);
		}

		// Filtro por importe
		if (montoMinimo != null && montoMaximo != null) {
			query += " AND p.importePrestamo BETWEEN ? AND ? ";
			parametros.add(montoMinimo);
			parametros.add(montoMaximo);
		}

		if (idCliente > 0) {
			query += " AND p.idCliente = ? ";
			parametros.add(idCliente);
		}

		try (Connection conexion = Conexion.getConnection();
				PreparedStatement statement = conexion.prepareStatement(query)) {

			// Establecer parámetros
			for (int i = 0; i < parametros.size(); i++) {
				statement.setObject(i + 1, parametros.get(i));
			}

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Prestamo prestamo = new Prestamo();
					prestamo.setIdPrestamo(resultSet.getInt("idPrestamo"));
					Cliente cliente = new Cliente();
					cliente.setIdCliente(idCliente);
					prestamo.setCliente(cliente);
					Cuenta cuenta = new CuentaDaoImpl().obtenerCuentaPorId(resultSet.getInt("idCuenta"));
					prestamo.setCuenta(cuenta);
					prestamo.setFechaAltaPrestamo(resultSet.getDate("fechaAltaPrestamo"));
					prestamo.setImporteTotal(resultSet.getFloat("importePrestamo"));
					prestamo.setPlazo(resultSet.getInt("mesesPlazo"));
					prestamo.setImporteCuota(resultSet.getFloat("importeCuota"));
					prestamo.setCantCuotas(resultSet.getInt("cantidadCuotas"));
					prestamo.setEstado(resultSet.getString("EstadoPrestamo"));

					prestamosFiltrados.add(prestamo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return prestamosFiltrados;
	}

	@Override
	public ArrayList<String> generarReportePrestamos(Date fechaInicio, Date fechaFin) {
		ArrayList<String> reporte = new ArrayList<>();
		ResultSet rs = null;

		String query = "SELECT " + "    p.idPrestamo AS `ID Prestamo`, "
				+ "    CONCAT(c.nombre, ' ', c.apellido) AS Cliente, " + "    p.importePrestamo AS `Monto Solicitado`, "
				+ "    (p.importePrestamo - COALESCE(SUM(CASE WHEN q.estadoPago = 1 THEN q.montoPagado ELSE 0 END), 0)) AS `Monto Pendiente`, "
				+ "    p.fechaAltaPrestamo AS `Fecha de Solicitud` " + "FROM Prestamos p "
				+ "INNER JOIN Clientes c ON p.idCliente = c.idCliente "
				+ "LEFT JOIN Cuotas q ON p.idPrestamo = q.idPrestamo " + "WHERE p.fechaAltaPrestamo BETWEEN ? AND ? "
				+ "GROUP BY p.idPrestamo, c.nombre, c.apellido, p.importePrestamo, p.fechaAltaPrestamo "
				+ "ORDER BY p.fechaAltaPrestamo DESC;";

		try (Connection conexion = Conexion.getConnection();
				PreparedStatement statement = conexion.prepareStatement(query)) {

			statement.setDate(1, new java.sql.Date(fechaInicio.getTime()));
			statement.setDate(2, new java.sql.Date(fechaFin.getTime()));

			rs = statement.executeQuery();

			while (rs.next()) {
				int idPrestamo = rs.getInt("ID Prestamo");
				String cliente = rs.getString("Cliente");
				double montoSolicitado = rs.getDouble("Monto Solicitado");
				double montoPendiente = rs.getDouble("Monto Pendiente");
				Date fechaSolicitud = rs.getDate("Fecha de Solicitud");

				String montoSolicitadoStr = String.format("%.2f", montoSolicitado).replace(',', '.');
				String montoPendienteStr = String.format("%.2f", montoPendiente).replace(',', '.');

				String fechaSolicitudStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaSolicitud);

				String lineaReporte = String.format("%d, %s, $%s,  $%s,  %s", idPrestamo, cliente, montoSolicitadoStr,
						montoPendienteStr, fechaSolicitudStr);

				reporte.add(lineaReporte);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reporte;
	}

	@Override
	public boolean finalizarPrestamo(int idPrestamo) {
		String query = "UPDATE prestamos SET EstadoPrestamo = 'Finalizado' WHERE idPrestamo = ?";

		try (Connection conexion = Conexion.getConnection();
				PreparedStatement statement = conexion.prepareStatement(query)) {

			statement.setInt(1, idPrestamo);

			return statement.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
