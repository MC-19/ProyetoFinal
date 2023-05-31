package TrabajoFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class Producto {
    private int id_producto;
    private String nombre;
    private int stock;
    private double precio;
    private double IVA = 1.21;
    private Empresa empresa;

    public Producto() {

    }

    public Producto(int id_producto, String nombre, int stock, double precio) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
    }

    public Producto(Producto producto) {
        this.id_producto = producto.id_producto;
        this.nombre = producto.nombre;
        this.stock = producto.stock;
        this.precio = producto.precio;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getIVA() {
        return IVA;
    }

    public void setIVA(double iVA) {
        IVA = iVA;
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
     
    public void insertarProducto(Connection conectar) throws SQLException {
        String query = "INSERT INTO producto (id_producto, nombre_producto, stock, precio, "
           + "rep_id_empresa) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conectar.prepareStatement(query)) {
            statement.setInt(1, id_producto);
            statement.setString(2, nombre);
            statement.setInt(3, stock);
            statement.setDouble(4, precio);
            statement.setInt(5, empresa.getId_empresa());
            statement.executeUpdate();
        }
    }
    
    public void actualizarProducto(Connection conectar) throws SQLException {
        String query = "UPDATE producto SET nombre_produto = ?, stock = ?, precio = ?, "
        					+ "rep_id_empresa = ? WHERE id_empleado = ?";
        try (PreparedStatement statement = conectar.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setInt(2, stock);
            statement.setDouble(3, precio);
            statement.setInt(4, empresa.getId_empresa());
            statement.setInt(5, id_producto);
            statement.executeUpdate();
        }
    }
    
    public void eliminarProducto(Connection conectar) throws SQLException {
        String query = "DELETE FROM producto WHERE id_producto = ?";
        try (PreparedStatement statement = conectar.prepareStatement(query)) {
            statement.setInt(1, id_producto);
            statement.executeUpdate();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(IVA, id_producto, nombre, precio, stock);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Producto other = (Producto) obj;
        return Double.doubleToLongBits(IVA) == Double.doubleToLongBits(other.IVA) && id_producto == other.id_producto
                && Objects.equals(nombre, other.nombre)
                && Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio) && stock == other.stock;
    }

    @Override
    public String toString() {
        return "Producto [id_producto=" + id_producto + ", nombre=" + nombre + ", stock=" + stock + ", precio=" + precio
                + ", IVA=" + IVA + "]";
    }
    
    
}
