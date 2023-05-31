package TrabajoFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

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
     
    public void insertarProducto(Connection connection) throws SQLException {
        String query = "INSERT INTO producto (id_producto, nombre_producto, stock, precio, "
           + "rep_id_empresa) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_producto);
            statement.setString(2, nombre);
            statement.setInt(3, stock);
            statement.setDouble(4, precio);
            statement.setInt(5, empresa.getId_empresa());
            statement.executeUpdate();
        }
    }
    
    public void actualizarProducto(Connection connection) throws SQLException {
        String query = "UPDATE producto SET nombre_produto = ?, stock = ?, precio = ?, "
        					+ "rep_id_empresa = ? WHERE id_empleado = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setInt(2, stock);
            statement.setDouble(3, precio);
            statement.setInt(4, empresa.getId_empresa());
            statement.setInt(5, id_producto);
            statement.executeUpdate();
        }
    }
    
    public void eliminarProducto(Connection connection) throws SQLException {
        String query = "DELETE FROM producto WHERE id_producto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
    
    public static void insertarProducto(Scanner scanner, Connection connection, Producto producto) throws SQLException {
        System.out.print("\nIngresa el nombre del producto: ");
        String nombreProducto = scanner.nextLine();

        if (nombreProducto.isEmpty()) {
            System.out.println("Error: el nombre del producto no puede estar vacío.");
        } else {
            producto.setNombre(nombreProducto);

            System.out.print("Ingresa el stock del producto: ");
            int stockProducto = scanner.nextInt();
            scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

            if (stockProducto <= 0) {
                System.out.println("Error: el stock del producto no puede estar vacío.");
            } else {
                producto.setStock(stockProducto);

                System.out.print("Ingresa el precio del producto: ");
                double precioProducto = scanner.nextDouble();
                scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

                if (precioProducto <= 0) {
                    System.out.println("Error: el precio del producto no puede estar vacío.");
                } else {
                    producto.setPrecio(precioProducto);

                    System.out.print("Ingresa el nombre de la empresa: ");
                    String nombreEmpresa = scanner.nextLine();

                    if (nombreEmpresa.isEmpty()) {
                        System.out.println("Error: el nombre de la empresa no puede estar vacío.");
                    } else {
                        Empresa empresa = Empresa.obtenerEmpresaPorNombre(connection, nombreEmpresa);

                        if (empresa != null) {
                            producto.setEmpresa(empresa);
                            producto.insertarProducto(connection);
                            System.out.println("El producto se ha insertado correctamente.");
                        } else {
                            System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
                        }
                    }
                }
            }
        }
    }

    
	public static void actualizarProducto(Scanner scanner, Connection connection, Producto producto) throws SQLException {
        System.out.print("Ingresa el nombre del producto a actualizar: ");
        String nombreActualizar = scanner.nextLine();
        
        Producto productoActualizar = obtenerProductoPorNombre(connection, nombreActualizar);
        
        if (productoActualizar != null) {
            System.out.print("\nIngresa el nuevo nombre del producto: ");
            productoActualizar.setNombre(scanner.nextLine());
            System.out.print("\nIngresa el nuevo stock del producto: ");
            productoActualizar.setNombre(scanner.nextLine());
            System.out.print("\nIngresa el nuevo precio del producto: ");
            productoActualizar.setNombre(scanner.nextLine());
            
            productoActualizar.actualizarProducto(connection);
            System.out.println("El producto se ha actualizado correctamente.");
		}else {
            System.out.println("Error : El producto no se ha actualizado. ");
		}
	}
	
	
	public static void eliminarProducto(Scanner scanner, Connection connection, Producto producto) throws SQLException {
        System.out.print("Ingresa el nombre del empleado a eliminar: ");
        String nombreEliminar = scanner.nextLine();

        Producto productoEliminar = obtenerProductoPorNombre(connection, nombreEliminar);
        if (productoEliminar != null) {
        	productoEliminar.eliminarProducto(connection);
            System.out.println("El empleado se ha eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún empleado con el nombre proporcionado.");
        }
		
	}
	
    private static Producto obtenerProductoPorNombre(Connection connection, String nombre) throws SQLException {
        String query = "SELECT * FROM producto WHERE nombre_producto = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Producto producto = new Producto();
                    producto.setId_producto(resultSet.getInt("id_producto"));
                    producto.setNombre(resultSet.getString("nombre_producto"));
                    producto.setStock(resultSet.getInt("stock"));
                    producto.setPrecio(resultSet.getDouble("precio"));


                    int idEmpresa = resultSet.getInt("id_empresa");
                    Empresa empresa = Empresa.obtenerEmpresaPorId(connection, idEmpresa);
                    if (empresa != null) {
                        producto.setEmpresa(empresa);
                    }

                    return producto;
                } else {
                    return null;
                }
            }
        }
    }
}
