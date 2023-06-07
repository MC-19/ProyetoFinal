package TrabajoFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main3 {
    static Empresa empresa = new Empresa();
    static Empleado empleado = new Empleado();
    static Producto producto = new Producto();
    static Cliente cliente = new Cliente();
    static Factura factura = new Factura();
    
    public static void main(String[] args) {


        try (Connection conectar = Empresa.getConnection()) {
            Scanner scanner = new Scanner(System.in);
            int opcion;

            System.out.print("Ingrese el nombre de la empresa (por ejemplo, FEMPA): ");
            String nombreEmpresa = scanner.nextLine();

            boolean empresaValida = verificarEmpresa(nombreEmpresa, conectar);

            if (!empresaValida) {
                System.out.println("Empresa no válida. Saliendo del programa.");
                return;
            }

            System.out.print("Ingrese la contraseña del empleado: ");
            String contraseñaEmpleado = scanner.nextLine();

            boolean empleadoValido = verificarEmpleado(contraseñaEmpleado, conectar);

            if (!empleadoValido) {
                System.out.println("Empleado no válido. Saliendo del programa.");
                return;
            }

            String cargoEmpleado = obtenerCargoEmpleado(contraseñaEmpleado, conectar);

            do {
                opcion = mostrarMenu(scanner, cargoEmpleado);
                ejecutarOpcion(opcion, scanner, conectar, empresa, empleado, producto, cliente, factura);
            } while (opcion != 0);

            scanner.close();
        } catch (SQLException e) {
            System.out.println("Error en la operación: " + e.getMessage());
        }
    }

        private static boolean verificarEmpresa(String nombreEmpresa, Connection connection) throws SQLException {
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

        private static boolean verificarEmpleado(String contraseñaEmpleado, Connection connection) throws SQLException {
            String sql = "SELECT * FROM empleado WHERE contrasenya_empleado = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, contraseñaEmpleado);
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            }
        }

        private static String obtenerCargoEmpleado(String contraseñaEmpleado, Connection connection) throws SQLException {
            String sql = "SELECT cargo_empleado FROM empleado WHERE contrasenya_empleado = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, contraseñaEmpleado);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("cargo_empleado");
                }
            }
            return null;
        }


        private static int mostrarMenu(Scanner scanner, String cargoEmpleado) {
            if (cargoEmpleado.equalsIgnoreCase("Empleado")) {
                System.out.println("\n--- MENÚ DE OPCIONES ---");
                System.out.println("1. Menú Producto");
                System.out.println("2. Menú Cliente");
                System.out.println("3. Menú Factura");
                System.out.println("0. Salir");
                System.out.print("Ingresa una opción: ");
                return scanner.nextInt();
            } else if (cargoEmpleado.equalsIgnoreCase("Admin")) {
                System.out.println("\n--- MENÚ DE OPCIONES ---");
                System.out.println("1. Menú Empresa");
                System.out.println("2. Menú Empleado");
                System.out.println("3. Menú Producto");
                System.out.println("4. Menú Cliente");
                System.out.println("5. Menú Factura");
                System.out.println("0. Salir");
                System.out.print("Ingresa una opción: ");
                return scanner.nextInt();
            } else {
                System.out.println("Cargo de empleado no válido");
                return 0;
            }
        }




    private static void ejecutarOpcion(int opcion, Scanner scanner, Connection connection, Empresa empresa,
            Empleado empleado, Producto producto, Cliente cliente, Factura factura) throws SQLException {
        scanner.nextLine();

        switch (opcion) {
            case 1:
                ejecutarMenuEmpresa(scanner, connection, empresa);
                break;
            case 2:
                ejecutarMenuEmpleado(scanner, connection, empleado);
                break;
            case 3:
                ejecutarMenuProducto(scanner, connection, producto);
                break;
            case 4:
                ejecutarMenuCliente(scanner, connection, cliente);
                break;
            case 5:
                ejecutarMenuFactura(scanner, connection, factura);
                break;
            case 0:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
        }
    }
    
    
    
  //----------------------------------------------------------PARTE DE LA EMPRESA------------------------------------------------------------------\\

    

    private static void ejecutarMenuEmpresa(Scanner scanner, Connection connection, Empresa empresa)
            throws SQLException {
        int opcion;

        do {
            opcion = mostrarMenuEmpresa(scanner);
            ejecutarOpcionEmpresa(opcion, scanner, connection, empresa);
        } while (opcion != 0);
    }

    private static int mostrarMenuEmpresa(Scanner scanner) {
        System.out.println("\n------ Menú Empresa ------");
        System.out.println("1. Actualizar empresa");
        System.out.println("2. Mostrar información de la empresa");
        System.out.println("\n0. Volver al menú principal");
        System.out.print("Ingresa una opción: ");
        return scanner.nextInt();
    }

    private static void ejecutarOpcionEmpresa(int opcion, Scanner scanner, Connection connection, Empresa empresa)
            throws SQLException {
        scanner.nextLine(); 

        switch (opcion) {
            case 1:
                actualizarEmpresa(scanner, connection, empresa);
                break;
            case 2:
                mostrarInformacionEmpresa(scanner, connection, empresa);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
        }
    }

    private static void actualizarEmpresa(Scanner scanner, Connection connection, Empresa empresa)
            throws SQLException {
        System.out.print("Ingresa el nombre de la empresa a actualizar: ");
        String nombreEmpresaActualizar = scanner.nextLine();

        Empresa empresaActualizar = obtenerEmpresaPorNombre(connection, nombreEmpresaActualizar);
        if (empresaActualizar != null) {
            System.out.print("\nIngresa el nuevo nombre de la empresa: ");
            empresaActualizar.setNombre(scanner.nextLine());
            System.out.print("Ingresa la nueva dirección de la empresa: ");
            empresaActualizar.setDireccion(scanner.nextLine());
            System.out.print("Ingresa el nuevo teléfono de la empresa: ");
            empresaActualizar.setTelefono(scanner.nextLine());

            try {
                connection.setAutoCommit(false); 
                empresaActualizar.actualizarEmpresa(connection);
                connection.commit(); 
                System.out.println("La empresa se ha actualizado correctamente.");
            } catch (SQLException e) {
                connection.rollback(); 
                System.out.println("Error al actualizar la empresa: " + e.getMessage());
            } finally {
                connection.setAutoCommit(true); 
            }
        } else {
            System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
        }
    }

    private static void mostrarInformacionEmpresa(Scanner scanner, Connection connection, Empresa empresa)
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
                	System.out.println(empleado.toString());
                    System.out.println("----------------------");
                }
            }

            Set<Producto> productos = obtenerProductosPorEmpresa(connection, mostrarEmpresa.getId_empresa());

            if (productos.isEmpty()) {
                System.out.println("No hay productos registrados en esta empresa.");
            } else {
                System.out.println("\n--- Información del producto ---");
                for (Producto producto : productos) {
                	System.out.println(producto.toString());
                    System.out.println("----------------------");
                }
            }

            Set<Cliente> clientes = obtenerClientesPorEmpresa(connection, mostrarEmpresa.getId_empresa());

            if (clientes.isEmpty()) {
                System.out.println("No hay clientes registrados en esta empresa.");
            } else {
                System.out.println("\n--- Información del cliente ---");
                for (Cliente cliente : clientes) {
                	System.out.println(cliente.toString());
                    System.out.println("----------------------");
                }
            }
            
            Set<Factura> facturas = obtenerFacturasPorEmpresa(connection, mostrarEmpresa.getId_empresa());

            if (facturas.isEmpty()) {
                System.out.println("No hay facturas registradas en esta empresa.");
            } else {
                System.out.println("\n--- Información de las facturas ---");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                
                for (Factura factura : facturas) {
                    System.out.println("Forma de pago: " + factura.getPago());
                    System.out.println("Fecha del pago: " + factura.getFecha().format(formatter));
                    System.out.println("Cantidad del producto: " + factura.getCantidad());
                    System.out.println("Nombre del producto: " + factura.getProducto().getNombre());
                    System.out.println("Importe total: " + factura.getTotal());
                    System.out.println("Cliente que compró el producto: " + factura.getCliente().getNombre());
                    System.out.println("Empleado que realizó la factura: " + factura.getEmpleado().getNombre());
                    System.out.println("----------------------");
                }
            }
            
        } else {
            System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
        }
    }

    

    
    private static Empresa obtenerEmpresaPorNombre(Connection connection, String nombre) throws SQLException {
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
    
    private static Set<Producto> obtenerProductosPorEmpresa(Connection connection, int idEmpresa) throws SQLException {
        String query = "SELECT * FROM producto WHERE rep_id_empresa = ?";

        Set<Producto> productos = new LinkedHashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEmpresa);

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
    
    private static Set<Factura> obtenerFacturasPorEmpresa(Connection connection, int idEmpresa) throws SQLException {
        String query = "SELECT * FROM Factura WHERE rep_id_empresa = ?";

        Set<Factura> facturas = new LinkedHashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEmpresa);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Factura factura = new Factura();
                    factura.setId_factura(resultSet.getInt("id_factura"));
                    factura.setPago(formaPago.valueOf(resultSet.getString("forma_pago")));
                    factura.setFecha(resultSet.getDate("fecha_pago").toLocalDate());
                    factura.setCantidad(resultSet.getInt("cantidad_producto"));
                    factura.setTotal(resultSet.getDouble("total"));
                    
                    int repIdProducto = resultSet.getInt("rep_id_producto");
                    Producto producto = obtenerProductoPorId(connection, repIdProducto);
                    if (producto != null) {
                        factura.setProducto(producto);
                    }

                    int repIdCliente = resultSet.getInt("rep_id_cliente");
                    Cliente cliente = obtenerClientePorId(connection, repIdCliente);
                    if (cliente != null) {
                        factura.setCliente(cliente);
                    }

                    int repIdEmpleado = resultSet.getInt("rep_id_empleado");
                    Empleado empleado = obtenerEmpleadoPorId(connection, repIdEmpleado);
                    if (empleado != null) {
                        factura.setEmpleado(empleado);
                    }

                    facturas.add(factura);
                }
            }
        }

        return facturas;
    }


    
    private static Empresa obtenerEmpresaPorId(Connection connection, int id) throws SQLException {
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
    
//----------------------------------------------------------PARTE DE LOS EMPLEADOS------------------------------------------------------------------\\

    
    
    private static void ejecutarMenuEmpleado(Scanner scanner, Connection connection, Empleado empleado)
            throws SQLException {
        int opcion;

        do {
            opcion = mostrarMenuEmpleado(scanner);
            ejecutarOpcionEmpleado(opcion, scanner, connection, empleado);
        } while (opcion != 0);
    }

    private static int mostrarMenuEmpleado(Scanner scanner) {
        System.out.println("\n------ Menú Empleado ------");
        System.out.println("1. Insertar empleado");
        System.out.println("2. Actualizar empleado");
        System.out.println("3. Eliminar empleado");
        System.out.println("4. Mostrar empleados ordenados por salario"); // Nueva opción para mostrar empleados ordenados por salario
        System.out.println("0. Volver al menú principal");
        System.out.print("Ingresa una opción: ");
        return scanner.nextInt();
    }
    
    private static void ejecutarOpcionEmpleado(int opcion, Scanner scanner, Connection connection, Empleado empleado)
            throws SQLException {
        scanner.nextLine();

        switch (opcion) {
            case 1:
                insertarEmpleado(scanner, connection, empleado);
                break;
            case 2:
                actualizarEmpleado(scanner, connection, empleado);
                break;
            case 3:
                eliminarEmpleado(scanner, connection, empleado);
                break;
            case 4:
                mostrarEmpleadosOrdenadosPorSalario(connection);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
        }
    }

	private static void insertarEmpleado(Scanner scanner, Connection connection, Empleado empleado) throws SQLException {
        System.out.print("\nIngresa el nombre del empleado: ");
        String nombreEmpleado = scanner.nextLine();

        if (nombreEmpleado.isEmpty()) {
            System.out.println("Error: el nombre del empleado no puede estar vacío.");
        } else {
            empleado.setNombre(nombreEmpleado);

            System.out.print("Ingresa la contraseña del empleado: ");
            String contrasenyaEmpleado = scanner.nextLine();

            if (contrasenyaEmpleado.isEmpty()) {
                System.out.println("Error: la contraseña del empleado no puede estar vacía.");
            } else {
                empleado.setContrasenya(contrasenyaEmpleado);

                System.out.print("Ingresa la dirección del empleado: ");
                String direccionEmpleado = scanner.nextLine();

                if (direccionEmpleado.isEmpty()) {
                    System.out.println("Error: la dirección del empleado no puede estar vacía.");
                } else {
                    empleado.setDireccion(direccionEmpleado);

                    System.out.print("Ingresa el teléfono del empleado: ");
                    String telefonoEmpleado = scanner.nextLine();

                    if (telefonoEmpleado.isEmpty()) {
                        System.out.println("Error: el teléfono del empleado no puede estar vacío.");
                    } else {
                        empleado.setTelefono(telefonoEmpleado);

                        System.out.print("Ingresa el cargo del empleado: ");
                        String cargoEmpleado = scanner.nextLine();

                        if (cargoEmpleado.isEmpty()) {
                            System.out.println("Error: el cargo del empleado no puede estar vacío.");
                        } else {
                            empleado.setCargo(Cargo.valueOf(cargoEmpleado));

                            System.out.print("Ingresa el sueldo del empleado: ");
                            String sueldoEmpleado = scanner.nextLine();

                            if (sueldoEmpleado.isEmpty()) {
                                System.out.println("Error: el sueldo del empleado no puede estar vacío.");
                            } else {
                                empleado.setSueldo(Double.parseDouble(sueldoEmpleado));

                                System.out.print("Ingresa el nombre de la empresa: ");
                                String nombreEmpresa = scanner.nextLine();

                                if (nombreEmpresa.isEmpty()) {
                                    System.out.println("Error: el nombre de la empresa no puede estar vacío.");
                                } else {
                                    Empresa empresa = obtenerEmpresaPorNombre(connection, nombreEmpresa);

                                    if (empresa != null) {
                                        empleado.setEmpresa(empresa);
                                        empleado.insertarEmpleado(connection);
                                        System.out.println("El empleado se ha insertado correctamente.");
                                    } else {
                                        System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private static void actualizarEmpleado(Scanner scanner, Connection connection, Empleado empleado)
            throws SQLException {
        System.out.print("Ingresa el nombre del empleado a actualizar: ");
        String nombreActualizar = scanner.nextLine();

        Empleado empleadoActualizar = obtenerEmpleadoPorNombre(connection, nombreActualizar);
        if (empleadoActualizar != null) {
            System.out.print("\nIngresa el nuevo nombre del empleado: ");
            empleadoActualizar.setNombre(scanner.nextLine());
            System.out.print("\nIngresa la nueva contraseña del empleado: ");
            empleadoActualizar.setContrasenya(scanner.nextLine());
            System.out.print("Ingresa la nueva dirección del empleado: ");
            empleadoActualizar.setDireccion(scanner.nextLine());
            System.out.print("Ingresa el nuevo teléfono del empleado: ");
            empleadoActualizar.setTelefono(scanner.nextLine());
            System.out.print("Ingresa el nuevo cargo del empleado: ");
            empleadoActualizar.setCargo(Cargo.valueOf(scanner.nextLine()));
            System.out.print("Ingresa el nuevo sueldo del empleado: ");
            empleadoActualizar.setSueldo(Double.parseDouble(scanner.nextLine()));

            empleadoActualizar.actualizarEmpleado(connection);
            System.out.println("El empleado se ha actualizado correctamente.");
        } else {
            System.out.println("No se encontró ningún empleado con el nombre proporcionado.");
        }
    }

    private static void eliminarEmpleado(Scanner scanner, Connection connection, Empleado empleado)
            throws SQLException {
        System.out.print("Ingresa el nombre del empleado a eliminar: ");
        String nombreEliminar = scanner.nextLine();

        Empleado empleadoEliminar = obtenerEmpleadoPorNombre(connection, nombreEliminar);
        if (empleadoEliminar != null) {
            empleadoEliminar.eliminarEmpleado(connection);
            System.out.println("El empleado se ha eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún empleado con el nombre proporcionado.");
        }
    }

    private static Empleado obtenerEmpleadoPorNombre(Connection connection, String nombre) throws SQLException {
        String query = "SELECT * FROM empleado WHERE nombre_empleado = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Empleado empleado = new Empleado();
                    empleado.setId_empleado(resultSet.getInt("id_empleado"));
                    empleado.setNombre(resultSet.getString("nombre_empleado"));
                    empleado.setContrasenya(resultSet.getString("contrasenya_empleado"));
                    empleado.setDireccion(resultSet.getString("direccion_empleado"));
                    empleado.setTelefono(resultSet.getString("telefono_empleado"));
                    empleado.setCargo(Cargo.valueOf(resultSet.getString("cargo_empleado")));
                    empleado.setSueldo(resultSet.getDouble("sueldo_empleado"));

                    int idEmpresa = resultSet.getInt("rep_id_empresa");
                    Empresa empresa = obtenerEmpresaPorId(connection, idEmpresa);
                    if (empresa != null) {
                        empleado.setEmpresa(empresa);
                    }

                    return empleado;
                } else {
                    return null;
                }
            }
        }
    }
    
    private static void mostrarEmpleadosOrdenadosPorSalario(Connection connection) throws SQLException {
        String query = "SELECT nombre_empleado, cargo_empleado, sueldo_empleado, rep_id_empresa FROM empleado ORDER BY sueldo_empleado DESC ";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            System.out.println("\n------ Empleados ordenados por salario (ascendente) ------");
            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                empleado.setNombre(resultSet.getString("nombre_empleado"));
                empleado.setCargo(Cargo.valueOf(resultSet.getString("cargo_empleado")));
                empleado.setSueldo(resultSet.getDouble("sueldo_empleado"));

                int idEmpresa = resultSet.getInt("rep_id_empresa");
                Empresa empresa = obtenerEmpresaPorId(connection, idEmpresa);
                if (empresa != null) {
                    empleado.setEmpresa(empresa);
                }

                System.out.println(empleado);
            }
        }
    }


    
    private static Empleado obtenerEmpleadoPorId(Connection connection, int idEmpleado) throws SQLException {
        String query = "SELECT nombre_empleado FROM Empleado WHERE id_empleado = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEmpleado);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Empleado empleado = new Empleado();
                    empleado.setNombre(resultSet.getString("nombre_empleado"));

                    return empleado;
                } else {
                    return null;
                }
            }
        }
    }

    
//----------------------------------------------------------PARTE DE LOS PRODUCTOS------------------------------------------------------------------\\

    
    
    private static void ejecutarMenuProducto(Scanner scanner, Connection connection, Producto producto)
            throws SQLException {
        int opcion;

        do {
            opcion = mostrarMenuproducto(scanner);
            ejecutaropcionProducto(opcion, scanner, connection, producto);
        } while (opcion != 0);
    }

    private static int mostrarMenuproducto(Scanner scanner) {
        System.out.println("\n------ Menú Empleado ------");
        System.out.println("1. Insertar producto");
        System.out.println("2. Actualizar producto");
        System.out.println("3. Eliminar producto");
        System.out.println("0. Volver al menú principal");
        System.out.print("Ingresa una opción: ");
        return scanner.nextInt();
    }
    
    private static void ejecutaropcionProducto(int opcion, Scanner scanner, Connection connection, Producto producto)
            throws SQLException {
        scanner.nextLine();

        switch (opcion) {
            case 1:
                insertarProducto(scanner, connection, producto);
                break;
            case 2:
                actualizarProducto(scanner, connection, producto);
                break;
            case 3:
                eliminarProducto(scanner, connection, producto);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
        }
    }

    private static void insertarProducto(Scanner scanner, Connection connection, Producto producto) throws SQLException {
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
                        Empresa empresa = obtenerEmpresaPorNombre(connection, nombreEmpresa);

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

    
	private static void actualizarProducto(Scanner scanner, Connection connection, Producto producto) throws SQLException {
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
	
	
	private static void eliminarProducto(Scanner scanner, Connection connection, Producto producto) throws SQLException {
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
        String query = "SELECT * FROM Producto WHERE nombre_producto = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    producto.setId_producto(resultSet.getInt("id_producto"));
                    producto.setNombre(resultSet.getString("nombre_producto"));
                    producto.setStock(resultSet.getInt("stock"));
                    producto.setPrecio(resultSet.getFloat("precio"));

                    int idEmpresa = resultSet.getInt("rep_id_empresa");
                    Empresa empresa = obtenerEmpresaPorId(connection, idEmpresa);
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
    
    private static Producto obtenerProductoPorId(Connection connection, int idProducto) throws SQLException {
        String query = "SELECT nombre_producto FROM Producto WHERE id_producto = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idProducto);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Producto producto = new Producto();
                    producto.setNombre(resultSet.getString("nombre_producto"));

                    return producto;
                } else {
                    return null;
                }
            }
        }
    }
    
    
  //----------------------------------------------------------PARTE DE LOS CLIENTES------------------------------------------------------------------\\

    private static void ejecutarMenuCliente(Scanner scanner, Connection connection, Cliente cliente)
            throws SQLException {
        int opcion;

        do {
            opcion = mostrarMenuCliente(scanner);
            ejecutarOpcionCliente(opcion, scanner, connection, cliente);
        } while (opcion != 0);
    }

    private static int mostrarMenuCliente(Scanner scanner) {
        System.out.println("\n------ Menú Empleado ------");
        System.out.println("1. Insertar cliente");
        System.out.println("2. Actualizar cliente");
        System.out.println("3. Eliminar cliente");
        System.out.println("0. Volver al menú principal");
        System.out.print("Ingresa una opción: ");
        return scanner.nextInt();
    }

    private static void ejecutarOpcionCliente(int opcion, Scanner scanner, Connection connection, Cliente cliente)
            throws SQLException {
        scanner.nextLine();

        switch (opcion) {
            case 1:
                insertarCliente(scanner, connection, cliente);
                break;
            case 2:
                actualizarCliente(scanner, connection, cliente);
                break;
            case 3:
                eliminarCliente(scanner, connection, cliente);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
        }
    }

    private static void insertarCliente(Scanner scanner, Connection connection, Cliente cliente) throws SQLException {
        System.out.print("\nIngresa el nombre del cliente: ");
        String nombreCliente = scanner.nextLine();

        if (nombreCliente.isEmpty()) {
			System.out.println("Error: El nombre del empleado no puede estar vacio. ");
		} else {
            cliente.setNombre(nombreCliente);
            
            System.out.println("Introduce la direccion del empleado: ");
            String direccionCliente = scanner.nextLine();
            
            if (direccionCliente.isEmpty()) {
				System.out.println("Error: La dirreccion del cliente no puede estar vacia. ");
			} else {
				cliente.setDireccion(direccionCliente);
				
				System.out.println("Introduce el telefono del cliente: ");
				String telefonoCliente = scanner.nextLine();
				
				if (telefonoCliente.isEmpty()) {
					System.out.println("Error: El telefono del cliente no puede estar vacio. ");
				} else {
					cliente.setTelefono(telefonoCliente);
					
                    System.out.print("Ingresa el nombre de la empresa: ");
                    String nombreEmpresa = scanner.nextLine();

                    if (nombreEmpresa.isEmpty()) {
                        System.out.println("Error: el nombre de la empresa no puede estar vacío.");
                    } else {
                        Empresa empresa = obtenerEmpresaPorNombre(connection, nombreEmpresa);

                        if (empresa != null) {
                            cliente.setEmpresa(empresa);
                            cliente.insertarCliente(connection);
                            System.out.println("El producto se ha insertado correctamente.");
                        } else {
                            System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
                        }
                    }
				}
			}
		}
    }


    private static void actualizarCliente(Scanner scanner, Connection connection, Cliente cliente)
            throws SQLException {
        System.out.print("Ingresa el nombre del cliente a actualizar: ");
        String nombreActualizar = scanner.nextLine();

        Cliente clienteActualizar = obtenerClientePorNombre(connection, nombreActualizar);
        if (clienteActualizar != null) {
            System.out.print("\nIngresa el nuevo nombre del cliente: ");
            clienteActualizar.setNombre(scanner.nextLine());
            System.out.print("Ingresa la nueva dirección del cliente: ");
            clienteActualizar.setDireccion(scanner.nextLine());
            System.out.print("Ingresa el nuevo teléfono del cliente: ");
            clienteActualizar.setTelefono(scanner.nextLine());

            clienteActualizar.actualizarCliente(connection);
            System.out.println("El cliente se ha actualizado correctamente.");
        } else {
            System.out.println("No se encontró ningún cliente con el nombre proporcionado.");
        }
    }

    private static void eliminarCliente(Scanner scanner, Connection connection, Cliente cliente)
            throws SQLException {
        System.out.print("Ingresa el nombre del cliente a eliminar: ");
        String nombreEliminar = scanner.nextLine();

        Cliente clienteEliminar = obtenerClientePorNombre(connection, nombreEliminar);
        if (clienteEliminar != null) {
        	clienteEliminar.eliminarCliente(connection);
            System.out.println("El empleado se ha eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún empleado con el nombre proporcionado.");
        }
    }

    private static Cliente obtenerClientePorNombre(Connection connection, String nombre) throws SQLException {
        String query = "SELECT * FROM Cliente WHERE nombre_cliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cliente.setId_cliente(resultSet.getInt("id_cliente"));
                    cliente.setNombre(resultSet.getString("nombre_cliente"));
                    cliente.setDireccion(resultSet.getString("direccion_cliente"));
                    cliente.setTelefono(resultSet.getString("telefono_cliente"));

                    int idEmpresa = resultSet.getInt("rep_id_empresa");
                    Empresa empresa = obtenerEmpresaPorId(connection, idEmpresa);
                    if (empresa != null) {
                        cliente.setEmpresa(empresa);
                    }

                    return cliente;
                } else {
                    return null;
                }
            }
        }
    }
    
    private static Cliente obtenerClientePorId(Connection connection, int idCliente) throws SQLException {
        String query = "SELECT nombre_cliente FROM Cliente WHERE id_cliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCliente);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cliente.setNombre(resultSet.getString("nombre_cliente"));


                    return cliente;
                } else {
                    return null;
                }
            }
        }
    }


    
    
//----------------------------------------------------------PARTE DE LOS FACTURAS------------------------------------------------------------------\\
    
    private static void ejecutarMenuFactura(Scanner scanner, Connection connection, Factura factura)
            throws SQLException {
        int opcion;

        do {
            opcion = mostrarMenuFactura(scanner);
            ejecutarOpcionFactura(opcion, scanner, connection, factura);
        } while (opcion != 0);
    }

    private static int mostrarMenuFactura(Scanner scanner) {
        System.out.println("\n------ Menú Factura ------");
        System.out.println("1. Crear factura");
        System.out.println("2. Actualizar factura");
        System.out.println("3. Eliminar factura");
        System.out.println("0. Volver al menú principal");
        System.out.print("Ingresa una opción: ");
        return scanner.nextInt();
    }

    private static void ejecutarOpcionFactura(int opcion, Scanner scanner, Connection connection,  Factura factura)
            throws SQLException {
        scanner.nextLine();

        switch (opcion) {
            case 1:
                insertarFactura(scanner, connection, factura);
                break;
            case 2:
//                actualizarFactura(scanner, connection, factura);
                break;
            case 3:
//                eliminarFactura(scanner, connection, factura);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
        }
    }

    private static void insertarFactura(Scanner scanner, Connection connection, Factura factura) throws SQLException {
        System.out.print("Ingresa el nombre de la empresa: ");
        String nombreEmpresa = scanner.nextLine();
        Empresa empresa = obtenerEmpresaPorNombre(connection, nombreEmpresa);

        if (empresa != null) {
            factura.setEmpresa(empresa);

            System.out.println("Formas de Pago:");
            for (formaPago forma : formaPago.values()) {
                System.out.println(forma.name());
            }
            System.out.print("\nIngresa la forma de pago: ");
            String pago = scanner.nextLine();

            if (pago.isEmpty()) {
                System.out.println("Error: la forma de pago no puede ser vacía. ");
            } else {
                factura.setPago(formaPago.valueOf(pago));
            }

            LocalDate fecha = LocalDate.now();
            factura.setFecha(fecha);

            System.out.print("Ingresa la cantidad de productos: ");
            int cantidad = Integer.parseInt(scanner.nextLine());
            factura.setCantidad(cantidad);

            System.out.print("Ingresa el nombre del producto: ");
            String nombreProducto = scanner.nextLine();
            Producto producto = obtenerProductoPorNombre(connection, nombreProducto);

            if (producto != null) {
                factura.setProducto(producto);
                double total = factura.getProducto().getPrecio() * factura.getCantidad();
                factura.setTotal(total);

                System.out.print("Ingresa el nombre del cliente: ");
                String nombreCliente = scanner.nextLine();
                Cliente cliente = obtenerClientePorNombre(connection, nombreCliente);

                if (cliente != null) {
                    factura.setCliente(cliente);

                    System.out.print("Ingresa el nombre del empleado: ");
                    String nombreEmpleado = scanner.nextLine();
                    Empleado empleado = obtenerEmpleadoPorNombre(connection, nombreEmpleado);

                    if (empleado != null) {
                        factura.setEmpleado(empleado);

                        factura.insertarFactura(connection);
                        System.out.println("La factura se ha insertado correctamente.");
                    } else {
                        System.out.println("No se encontró ningún empleado con el nombre proporcionado.");
                    }
                } else {
                    System.out.println("No se encontró ningún cliente con el nombre proporcionado.");
                }
            } else {
                System.out.println("No se encontró ningún producto con el nombre proporcionado.");
            }
        } else {
            System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
        }
    }

}
