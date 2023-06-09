package TrabajoFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class Factura {
    private int id_factura;
    private formaPago pago;
    private LocalDate fecha;
    private int cantidad;
    private String nombre;
    private double total;
    private Cliente cliente;
    private Empleado empleado;
    private Empresa empresa;
    private Producto producto;

    public Factura() {
    }

    public Factura(int id_factura, formaPago pago, LocalDate fecha, int cantidad, double total, Cliente cliente, Empleado empleado, Empresa empresa, Producto producto) {
        this.id_factura = id_factura;
        this.pago = pago;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.total = total;
        this.cliente = cliente;
        this.empleado = empleado;
        this.empresa = empresa;
        this.producto = producto;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/trabajoProgramacion";
        String username = "MC";
        String password = "Lolalol@12";

        return DriverManager.getConnection(url, username, password);
    }

    public void insertarFactura(Connection connection, Factura factura) throws SQLException {
        String query = "INSERT INTO Factura (id_factura, forma_pago, fecha_pago, cantidad_producto, producto_nombre, total, rep_id_producto, rep_id_cliente, rep_id_empleado, rep_id_empresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, factura.getId_factura());
            statement.setString(2, factura.getPago().name());
            statement.setObject(3, factura.getFecha());
            statement.setInt(4, factura.getCantidad());
            	if (factura.getProducto() != null) {
            		statement.setString(5, factura.getProducto().getNombre());
            	} else {
            		statement.setString(5, "Nombre de Producto No Disponible");
            	}
            statement.setDouble(6, factura.getTotal());
            statement.setInt(7, factura.getProducto().getId_producto());
            statement.setInt(8, factura.getCliente().getId_cliente());
            statement.setInt(9, factura.getEmpleado().getId_empleado());
            statement.setInt(10, factura.getEmpresa().getId_empresa());
            statement.executeUpdate();
            System.out.println("Factura insertada exitosamente.");
        }
    }

    
    public void actualizarFactura(Connection conectar) throws SQLException {
        String query = "UPDATE factura SET forma_pago = ?, fecha_pago = ?, cantidad_producto = ?, producto_nombre = ?, total = ?, " +
                "rep_id_producto = ?, rep_id_cliente = ?, rep_id_empleado = ?, rep_id_empresa = ? WHERE id_factura = ?";
        try (PreparedStatement statement = conectar.prepareStatement(query)) {
            statement.setString(1, pago.name());
            statement.setObject(2, fecha);
            statement.setInt(3, cantidad);
            statement.setString(4, producto.getNombre());
            statement.setDouble(5, total);
            statement.setInt(6, producto.getId_producto());
            statement.setInt(7, cliente.getId_cliente());
            statement.setInt(8, empleado.getId_empleado());
            statement.setInt(9, empresa.getId_empresa());
            statement.setInt(10, id_factura);
            statement.executeUpdate();
        }
    }

    
    public void eliminarFactura(Connection conectar) throws SQLException {
        String query = "DELETE FROM factura WHERE id_factura = ?";
        try (PreparedStatement statement = conectar.prepareStatement(query)) {
            statement.setInt(1, id_factura);
            statement.executeUpdate();
        }
    }


	@Override
	public int hashCode() {
		return Objects.hash(cantidad, cliente, empleado, empresa, fecha, id_factura, pago, producto, total);
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
		return cantidad == other.cantidad && Objects.equals(cliente, other.cliente)
				&& Objects.equals(empleado, other.empleado) && Objects.equals(empresa, other.empresa)
				&& Objects.equals(fecha, other.fecha) && id_factura == other.id_factura && pago == other.pago
				&& Objects.equals(producto, other.producto)
				&& Double.doubleToLongBits(total) == Double.doubleToLongBits(other.total);
	}

	@Override
	public String toString() {
	    return "Factura {" +
	            "\n   Id Factura: " + id_factura +
	            "\n   Forma de pago: " + pago +
	            "\n   Fecha del pago: " + fecha +
	            "\n   Cantidad del producto: " + cantidad +
	            "\n   Nombre del producto: " + producto.getNombre() +
	            "\n   Cliente: " + cliente.getNombre() +
	            "\n   Empleado: " + empleado.getNombre() +
	            "\n}";
	}
	
}
