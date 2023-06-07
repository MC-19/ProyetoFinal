package TrabajoFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Cliente extends Persona {
    private int id_cliente;
    private Empresa empresa;

    private Set<Factura> facturas = new LinkedHashSet<>();

    public Cliente() {
        super();
    }

    public Cliente(int id_cliente, Set <Factura> factura) {
        super();
        this.id_cliente = id_cliente;
        this.facturas.addAll(factura);
    }

    public Cliente(Persona persona, Cliente cliente) {
        super(persona);
        this.id_cliente = cliente.id_cliente;
        this.facturas.addAll(cliente.facturas);
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
    public Set<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(Set<Factura> facturas) {
		this.facturas = facturas;
	}

	public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/trabajoProgramacion";
        String username = "MC";
        String password = "Lolalol@12";

        return DriverManager.getConnection(url, username, password);
    }
     
    public void insertarCliente(Connection conectar) throws SQLException {
        String query = "INSERT INTO cliente (id_cliente, nombre_cliente, direccion_cliente, "
            + "telefono_cliente, rep_id_empresa) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conectar.prepareStatement(query)) {
            statement.setInt(1, id_cliente);
            statement.setString(2, getNombre());
            statement.setString(3, getDireccion());
            statement.setString(4, getTelefono());
            statement.setInt(5, empresa.getId_empresa());
            statement.executeUpdate();
        }
    }
    
    public void actualizarCliente(Connection conectar) throws SQLException {
        String query = "UPDATE cliente SET nombre_cliente = ?, direccion_cliente = ?, telefono_cliente = ?, "
        					+ "rep_id_empresa = ? WHERE id_cliente = ?";
        try (PreparedStatement statement = conectar.prepareStatement(query)) {
            statement.setString(1, getNombre());
            statement.setString(2, getDireccion());
            statement.setString(3, getTelefono());
            statement.setInt(4, empresa.getId_empresa());
            statement.setInt(5, id_cliente);
            statement.executeUpdate();
        }
    }
    
    public void eliminarCliente(Connection conectar) throws SQLException {
        String query = "DELETE FROM cliente WHERE id_cliente = ?";
        try (PreparedStatement statement = conectar.prepareStatement(query)) {
            statement.setInt(1, id_cliente);
            statement.executeUpdate();
        }
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(empresa, facturas, id_cliente);
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
		Cliente other = (Cliente) obj;
		return Objects.equals(empresa, other.empresa) && Objects.equals(facturas, other.facturas)
				&& id_cliente == other.id_cliente;
	}
	
	@Override
	public String toString() {
	    return "Cliente {" +
	            "\n   Nombre: " + nombre +
	            "\n   Dirección: " + direccion +
	            "\n   Teléfono: " + telefono +
	            "\n}";
	}
}
