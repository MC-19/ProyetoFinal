package TrabajoFinal;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Empresa {
    private int id_empresa;
    private String nombre;
    private String direccion;
    private String telefono;

    private Set<Empleado> empleados = new LinkedHashSet<Empleado>();
    private Set<Cliente> clientes = new LinkedHashSet<>();
    private Set<Producto> productos = new LinkedHashSet<>();
    private Set<Factura> facturas = new LinkedHashSet<>();

    public Empresa() {

    }

    public Empresa(int id_empresa, String nombre, String direccion, String telefono, Set <Empleado> empleado) {
        this.id_empresa = id_empresa;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.empleados.addAll(empleado);
    }
    
    public Empresa(Empresa empresa) {
        this.id_empresa = empresa.id_empresa;
        this.nombre = empresa.nombre;
        this.direccion = empresa.direccion;
        this.telefono = empresa.telefono;
        this.empleados.addAll(empleados);
    }

    

    public int getId_empresa() {
		return id_empresa;
	}

	public void setId_empresa(int id_empresa) {
		this.id_empresa = id_empresa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public Set<Empleado> getEmpleados() {
        return empleados;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Set<Factura> getFacturas() {
        return facturas;
    }
    
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/trabajoProgramacion";
        String username = "MC";
        String password = "Lolalol@12";

        return DriverManager.getConnection(url, username, password);
    }

    public void insertarEmpresa(Connection connection) throws SQLException {
        String query = "INSERT INTO empresa (id_empresa, nombre_empresa, direccion_empresa, telefono_empresa) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_empresa);
            statement.setString(2, nombre);
            statement.setString(3, direccion);
            statement.setString(4, telefono);
            statement.executeUpdate();
        }
    }

    public void actualizarEmpresa(Connection connection) throws SQLException {
        String query = "UPDATE empresa SET nombre_empresa = ?, direccion_empresa = ?, telefono_empresa = ? WHERE id_empresa = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, direccion);
            statement.setString(3, telefono);
            statement.setInt(4, id_empresa);
            statement.executeUpdate();
        }
    }

    public void eliminarEmpresa(Connection connection) throws SQLException {
        String query = "DELETE FROM empresa WHERE id_empresa = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_empresa);
            statement.executeUpdate();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(direccion, id_empresa, nombre, telefono);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Empresa other = (Empresa) obj;
        return id_empresa == other.id_empresa && Objects.equals(nombre, other.nombre)
                && Objects.equals(direccion, other.direccion) && Objects.equals(telefono, other.telefono);
    }

	@Override
	public String toString() {
		return "Nombre: " + nombre + "\nDireccion: " + direccion + "\nTelefono: " + telefono + "";
	}    
    
  
}

