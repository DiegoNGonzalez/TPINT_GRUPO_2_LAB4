package entidades;

import java.util.ArrayList;
import java.util.Date;

public class Cliente {
	private Usuario usuario;
	private int idCliente;
	private String dni;
	private String cuil;
	private String nombre;
	private String apellido;
	private String email;
	private String telefono;
	private String direccion;
	private Date fechaNacimiento;
	private char sexo;
	private boolean estado;
	private Nacionalidad nacionalidad;
	private Localidad localidad;
	private Provincia provincia;
	private ArrayList<Cuenta> cuentas;
	
	
	public  Cliente () {
		
	}
	
	public Cliente(Usuario usuario, int idCliente, String dni, String cuil, String nombre, String apellido, String email,
			String telefono, String direccion, Date fechaNacimiento, char sexo,boolean estado, Nacionalidad nacionalidad, Localidad localidad, Provincia provincia, ArrayList<Cuenta> cuentas) {
		super();
		this.usuario = usuario;
		this.idCliente = idCliente;
		this.dni = dni;
		this.cuil=cuil;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.telefono = telefono;
		this.direccion = direccion;
		this.fechaNacimiento = fechaNacimiento;
		this.sexo = sexo;
		this.estado=estado;
		this.nacionalidad= nacionalidad;
		this.localidad=localidad;
		this.provincia=provincia;
		this.cuentas=cuentas;
	}

	//Getters y setters
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	

	public Nacionalidad getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(Nacionalidad nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	
	

	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(ArrayList<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", email=" + email + ", telefono=" + telefono + ", direccion=" + direccion + ", fechaNacimiento="
				+ fechaNacimiento + ", sexo=" + sexo + "]";
	}
	

}
