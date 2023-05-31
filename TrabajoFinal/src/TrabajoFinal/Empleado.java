package TrabajoFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class Empleado extends Persona {
    private int id_empleado;
    private String contrasenya;
    private Cargo cargo;
    private double sueldo;
    private Empresa empresa;
    
    public Empleado() {
        super();
    }

    public Empleado(int id_empleado, double sueldo, Cargo cargo, Empresa empresa) {
        super();
        this.id_empleado = id_empleado;
        this.sueldo = sueldo;
        this.cargo = cargo;
        this.empresa = empresa;
    }

    public Empleado(Persona persona, Empleado empleado) {
        super(persona);
        this.id_empleado = empleado.id_empleado;
        this.sueldo = empleado.sueldo;
        this.cargo = empleado.cargo;
        this.empresa = empleado.empresa; 
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/trabajoProgramacion";
        String username = "MC";
        String password = "Lolalol@12";

        return DriverManager.getConnection(url, username, password);
    }
     
    public void insertarEmpleado(Connection connection) throws SQLException {
        String query = "INSERT INTO empleado (id_empleado, nombre_empleado, contrasnya_empleado, direccion_empleado, "
            + "telefono_empleado, cargo_empleado, sueldo_empleado, rep_id_empresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_empleado);
            statement.setString(2, getNombre());
            statement.setString(3, getDireccion());
            statement.setString(4, getTelefono());
            statement.setString(5, cargo.name());
            statement.setDouble(6, sueldo);
            statement.setInt(7, empresa.getId_empresa());
            statement.executeUpdate();
        }
    }
    
    public void actualizarEmpleado(Connection connection) throws SQLException {
        String query = "UPDATE empleado SET nombre_empleado = ?, contrasenya_empleado = ?, direccion_empleado = ?, telefono_empleado = ?, "
        					+ "cargo_empleado = ?, sueldo_empleado = ?, rep_id_empresa = ? WHERE id_empleado = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, getNombre());
            statement.setString(2, getDireccion());
            statement.setString(3, getTelefono());
            statement.setString(4, cargo.name());
            statement.setDouble(5, sueldo);
            statement.setInt(6, empresa.getId_empresa());
            statement.setInt(7, id_empleado);
            statement.executeUpdate();
        }
    }
    
    public void eliminarEmpleado(Connection connection) throws SQLException {
        String query = "DELETE FROM empleado WHERE id_empleado = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_empleado);
            statement.executeUpdate();
        }
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cargo, contrasenya, empresa, id_empleado, sueldo);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return cargo == other.cargo && Objects.equals(contrasenya, other.contrasenya)
				&& Objects.equals(empresa, other.empresa) && id_empleado == other.id_empleado
				&& Double.doubleToLongBits(sueldo) == Double.doubleToLongBits(other.sueldo);
	}
	
}
