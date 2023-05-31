package TrabajoFinal;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Scanner;
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
	
    public static void insertarEmpresa(Scanner scanner, Connection connection, Empresa empresa) throws SQLException {
        System.out.print("\nIngresa el nombre de la empresa: ");
        String nombreEmpresa = scanner.nextLine();

        if (nombreEmpresa.isEmpty()) {
            System.out.println("Error: el nombre de la empresa no puede estar vacío.");
        } else {
            empresa.setNombre(nombreEmpresa);

            System.out.print("Ingresa la dirección de la empresa: ");
            String direccionEmpresa = scanner.nextLine();

            if (direccionEmpresa.isEmpty()) {
                System.out.println("Error: la dirección de la empresa no puede estar vacía.");
            } else {
                empresa.setDireccion(direccionEmpresa);

                System.out.print("Ingresa el teléfono de la empresa: ");
                String telefonoEmpresa = scanner.nextLine();

                if (telefonoEmpresa.isEmpty()) {
                    System.out.println("Error: el teléfono de la empresa no puede estar vacío.");
                } else {
                    empresa.setTelefono(telefonoEmpresa);
                    
                    empresa.insertarEmpresa(connection);
                    System.out.println("La empresa se ha insertado correctamente");
                }
            }
        }
    }

    public static void actualizarEmpresa(Scanner scanner, Connection connection, Empresa empresa)
            throws SQLException {
        System.out.print("Ingresa el nombre de la empresa a actualizar: ");
        String nombreActualizar = scanner.nextLine();

        Empresa empresaActualizar = obtenerEmpresaPorNombre(connection, nombreActualizar);
        if (empresaActualizar != null) {
            System.out.print("\nIngresa el nuevo nombre de la empresa: ");
            empresaActualizar.setNombre(scanner.nextLine());
            System.out.print("Ingresa la nueva dirección de la empresa: ");
            empresaActualizar.setDireccion(scanner.nextLine());
            System.out.print("Ingresa el nuevo teléfono de la empresa: ");
            empresaActualizar.setTelefono(scanner.nextLine());

            empresaActualizar.actualizarEmpresa(connection);
            System.out.println("La empresa se ha actualizado correctamente.");
        } else {
            System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
        }
    }

    public static void mostrarInformacionEmpresa(Scanner scanner, Connection connection, Empresa empresa)
            throws SQLException {
        System.out.print("\nIngresa el nombre de la empresa: ");
        String nombreEmpresa = scanner.nextLine();

        Empresa mostrarEmpresa = obtenerEmpresaPorNombre(connection, nombreEmpresa);
        if (mostrarEmpresa != null) {
            System.out.println("\n--- Información de la empresa ---");
            System.out.println(mostrarEmpresa.toString());

            Set<Empleado> empleados = obtenerEmpleadosPorEmpresa(connection, mostrarEmpresa.getId_empresa());

            if (empleados.isEmpty()) {
                System.out.println("No hay empleados registrados en esta empresa.");
            } else {
                System.out.println("\n--- Información del empleado ---");
                for (Empleado empleado : empleados) {
                    System.out.println("Nombre: " + empleado.getNombre());
                    System.out.println("Dirección: " + empleado.getDireccion());
                    System.out.println("Teléfono: " + empleado.getTelefono());
                    System.out.println("Cargo: " + empleado.getCargo());
                    System.out.println("Sueldo: " + empleado.getSueldo());
                    System.out.println("----------------------");
                }
            }

            Set<Producto> productos = obtenerProductosPorEmpresa(connection, mostrarEmpresa.getId_empresa());

            if (productos.isEmpty()) {
                System.out.println("No hay productos registrados en esta empresa.");
            } else {
                System.out.println("\n--- Información del producto ---");
                for (Producto producto : productos) {
                    System.out.println("Nombre: " + producto.getNombre());
                    System.out.println("Teléfono: " + producto.getStock());
                    System.out.println("Cargo: " + producto.getPrecio());
                    System.out.println("----------------------");
                }
            }

            Set<Cliente> clientes = obtenerClientesPorEmpresa(connection, mostrarEmpresa.getId_empresa());

            if (clientes.isEmpty()) {
                System.out.println("No hay clientes registrados en esta empresa.");
            } else {
                System.out.println("\n--- Información del cliente ---");
                for (Cliente cliente : clientes) {
                    System.out.println("Nombre: " + cliente.getNombre());
                    System.out.println("Dirección: " + cliente.getDireccion());
                    System.out.println("Teléfono: " + cliente.getTelefono());
                    System.out.println("----------------------");
                }
            }
        } else {
            System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
        }
    }

    
    public static Empresa obtenerEmpresaPorNombre(Connection connection, String nombre) throws SQLException {
        String query = "SELECT * FROM empresa WHERE nombre_empresa = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setId_empresa(resultSet.getInt("id_empresa"));
                    empresa.setNombre(resultSet.getString("nombre_empresa"));
                    empresa.setDireccion(resultSet.getString("direccion_empresa"));
                    empresa.setTelefono(resultSet.getString("telefono_empresa"));

                    return empresa;
                } else {
                    return null;
                }
            }
        }
        
        
    }
    
    private static Set<Empleado> obtenerEmpleadosPorEmpresa(Connection connection, int idEmpresa) throws SQLException {
        String query = "SELECT * FROM empleado WHERE rep_id_empresa = ?";

        Set<Empleado> empleados = new LinkedHashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEmpresa);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Empleado empleado = new Empleado();
                    empleado.setId_empleado(resultSet.getInt("id_empleado"));
                    empleado.setNombre(resultSet.getString("nombre_empleado"));
                    empleado.setDireccion(resultSet.getString("direccion_empleado"));
                    empleado.setTelefono(resultSet.getString("telefono_empleado"));
                    empleado.setCargo(Cargo.valueOf(resultSet.getString("cargo_empleado")));
                    empleado.setSueldo(resultSet.getDouble("sueldo_empleado"));

                    empleados.add(empleado);
                }
            }
        }

        return empleados;
    }
    
    private static Set<Producto> obtenerProductosPorEmpresa(Connection connection, int idProducto) throws SQLException {
        String query = "SELECT * FROM producto WHERE rep_id_empresa = ?";

        Set<Producto> productos = new LinkedHashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idProducto);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Producto producto = new Producto();
                    producto.setId_producto(resultSet.getInt("id_producto"));
                    producto.setNombre(resultSet.getString("nombre_producto"));
                    producto.setStock(resultSet.getInt("stock"));
                    producto.setPrecio(resultSet.getDouble("precio"));

                    productos.add(producto);
                }
            }
        }

        return productos;
    }
    
    private static Set<Cliente> obtenerClientesPorEmpresa(Connection connection, int idCliente) throws SQLException {
        String query = "SELECT * FROM cliente WHERE rep_id_empresa = ?";

        Set<Cliente> clientes = new LinkedHashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCliente);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId_cliente(resultSet.getInt("id_cliente"));
                    cliente.setNombre(resultSet.getString("nombre_cliente"));
                    cliente.setDireccion(resultSet.getString("direccion_cliente"));
                    cliente.setTelefono(resultSet.getString("telefono_cliente"));

                    clientes.add(cliente);
                }
            }
        }

        return clientes;
    }
    
    public static Empresa obtenerEmpresaPorId(Connection connection, int id) throws SQLException {
        String query = "SELECT * FROM empresa WHERE id_empresa = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setId_empresa(resultSet.getInt("id_empresa"));
                    empresa.setNombre(resultSet.getString("nombre_empresa"));
                    empresa.setDireccion(resultSet.getString("direccion_empresa"));
                    empresa.setTelefono(resultSet.getString("telefono_empresa"));

                    return empresa;
                } else {
                    return null;
                }
            }
        }
    }
    
    public static boolean verificarEmpresa(String nombreEmpresa, Connection connection) throws SQLException {
        String sql = "SELECT * FROM empresa WHERE nombre_empresa = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombreEmpresa);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }
  
}

