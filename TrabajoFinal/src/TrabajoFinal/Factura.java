package TrabajoFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Factura {
    private int id_factura;
    private formaPago pago;
    private LocalDate fecha;
    private Item[] items;
    private double total;
    private Cliente cliente;
    private Empleado empleado;
    private Empresa empresa;

    public Factura() {

    }

    public Factura(int id_factura, formaPago pago, LocalDate fecha, Item[] items, double total, Cliente cliente, Empleado empleado, Empresa empresa) {
    	this.id_factura = id_factura;
    	this.pago = pago;
    	this.fecha = fecha;
    	this.items = items;
    	this.total = total;
    	this.cliente = cliente;
    	this.empleado = empleado;
    	this.empresa = empresa;
    }
    
    public Factura(Factura factura) {
    	this.id_factura = id_factura;
    	this.pago = pago;
    	this.fecha = fecha;
    	this.items = items;
    	this.total = total;
    	this.cliente = cliente;
    	this.empleado = empleado;
    	this.empresa = empresa;
    }
    
    public int getId_factura() {
		return id_factura;
	}

	public void setId_factura(int id_factura) {
		this.id_factura = id_factura;
	}

	public formaPago getPago() {
		return pago;
	}

	public void setPago(formaPago pago) {
		this.pago = pago;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void calcularTotal() {
        double totalFactura = 0.0;
        for (Item item : items) {
            totalFactura += item.getPrecio() * item.getCantidad();
        }
        this.total = totalFactura;
    }
	
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/trabajoProgramacion";
        String username = "MC";
        String password = "Lolalol@12";

        return DriverManager.getConnection(url, username, password);
    }
     
    public void insertarEmpleado(Connection conectar) throws SQLException {
        String query = "INSERT INTO factura (id_factura, forma_pago, fecha_pago, cantida_producto, "
            + "total, rep_id_item, rep_id_cliente, rep_id_empleado, rep_id_empresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conectar.prepareStatement(query)) {
            statement.setInt(1, id_factura);
            statement.setString(2, pago.name());
            statement.setInt(8, empresa.getId_empresa());
            statement.executeUpdate();
        }
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(items);
		result = prime * result + Objects.hash(cliente, empleado, empresa, fecha, id_factura, pago, total);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Factura other = (Factura) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(empleado, other.empleado)
				&& Objects.equals(empresa, other.empresa) && Objects.equals(fecha, other.fecha)
				&& id_factura == other.id_factura && Arrays.equals(items, other.items) && pago == other.pago
				&& Double.doubleToLongBits(total) == Double.doubleToLongBits(other.total);
	}

	@Override
	public String toString() {
		return "Factura [id_factura=" + id_factura + ", pago=" + pago + ", fecha=" + fecha + ", items="
				+ Arrays.toString(items) + ", total=" + total + ", cliente=" + cliente + ", empleado=" + empleado
				+ ", empresa=" + empresa + "]";
	}

}
