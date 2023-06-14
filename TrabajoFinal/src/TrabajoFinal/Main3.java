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
    	    // Establecezco la conexión a la base de datos utilizando la clase Empresa y el método getConnection()
    	    
    	    Scanner scanner = new Scanner(System.in); 
    	    // Crear un objeto Scanner para leer la entrada del usuario
    	    
    	    int opcion; // Variable para almacenar la opción seleccionada del menú
    	    
    	    // Solicitar al usuario que ingrese el nombre de la empresa
    	    System.out.print("Ingrese el nombre de la empresa (por ejemplo, FEMPA): ");
    	    String nombreEmpresa = scanner.nextLine();
    	    
    	    // Verifica si el nombre de la empresa es válido llamando al método verificarEmpresa()
    	    boolean empresaValida = verificarEmpresa(nombreEmpresa, conectar);
    	    
    	    if (!empresaValida) { 
    	        // Si la empresa no es válida, mostrara un mensaje y saldra del programa
    	        System.out.println("Empresa no válida. Saliendo del programa.");
    	        return;
    	    }
    	    
    	    // Solicitar al usuario que ingrese la contraseña del empleado
    	    System.out.print("Ingrese la contraseña del empleado: ");
    	    String contraseñaEmpleado = scanner.nextLine();
    	    
    	    // Obtener el objeto Empleado correspondiente a la contraseña ingresada llamando al método obtenerEmpleado()
    	    Empleado empleado = obtenerEmpleado(contraseñaEmpleado, conectar);
    	    
    	    if (empleado == null) { 
    	        // Si el empleado no es válido, mostrara un mensaje y salir del programa
    	        System.out.println("Empleado no válido. Saliendo del programa.");
    	        return;
    	    }
    	    
    	    // Obtener el cargo del empleado
    	    Cargo cargoEmpleado = empleado.getCargo();
    	    
    	    do {
    	        // Mostrar el menú según el cargo del empleado y obtener la opción seleccionada
    	        opcion = mostrarMenu(scanner, cargoEmpleado);
    	        
    	        // Ejecutar la opción seleccionada llamando al método ejecutarOpcion()
    	        ejecutarOpcion(opcion, scanner, conectar, empresa, empleado, producto, cliente, factura);
    	        
    	    } while (opcion != 0);
    	    
    	    scanner.close(); 
    	    // Cerrar el objeto Scanner
    	    
    	} catch (SQLException e) { 
    	    // Capturar cualquier excepción de SQL que ocurra y mostrar un mensaje de error
    	    System.out.println("Error en la operación: " + e.getMessage());
    	}

    }

    private static Empleado obtenerEmpleado(String contraseñaEmpleado, Connection connection) throws SQLException {
        String sql = "SELECT cargo_empleado FROM empleado WHERE contrasenya_empleado = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contraseñaEmpleado);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cargo = resultSet.getString("cargo_empleado");
                Empleado empleado = new Empleado();
                empleado.setCargo(Cargo.valueOf(cargo));
                return empleado;
            }
        }
        return null;
    }
    
    // Este método recibe una contraseña de empleado y una conexión a la base de datos.
    // Realiza una consulta SQL para obtener el cargo del empleado asociado a la contraseña proporcionada.
    // Crea un nuevo objeto Empleado, establece el cargo obtenido y lo devuelve.

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
    // Este método recibe el nombre de una empresa y una conexión a la base de datos.
    // Realiza una consulta SQL para verificar si existe una empresa con el nombre proporcionado.
    // Si el resultado contiene al menos una fila, devuelve true; de lo contrario, devuelve false.

    private static boolean verificarEmpleado(String contraseñaEmpleado, Connection connection) throws SQLException {
        String sql = "SELECT * FROM empleado WHERE contrasenya_empleado = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contraseñaEmpleado);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
    // Este método recibe una contraseña de empleado y una conexión a la base de datos.
    // Realiza una consulta SQL para verificar si existe un empleado con la contraseña proporcionada.
    // Si el resultado contiene al menos una fila, devuelve true; de lo contrario, devuelve false.

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
    // Este método recibe una contraseña de empleado y una conexión a la base de datos.
    // Realiza una consulta SQL para obtener el cargo del empleado asociado a la contraseña proporcionada.
    // Devuelve el cargo como una cadena de texto.

    private static int mostrarMenu(Scanner scanner, Cargo cargoEmpleado) {
        if (cargoEmpleado != null) {
            if (cargoEmpleado == Cargo.Empleado) {
                System.out.println("\n--- MENÚ DE OPCIONES ---");
                System.out.println("3. Menú Producto");
                System.out.println("4. Menú Cliente");
                System.out.println("5. Menú Factura");
                System.out.println("0. Salir");
                System.out.print("Ingresa una opción: ");
                return scanner.nextInt();
            } else if (cargoEmpleado == Cargo.Admin) {
                System.out.println("\n--- MENÚ DE OPCIONES ---");
                System.out.println("1. Menú Empresa");
                System.out.println("2. Menú Empleado");
                System.out.println("3. Menú Producto");
                System.out.println("4. Menú Cliente");
                System.out.println("5. Menú Factura");
                System.out.println("0. Salir");
                System.out.print("Ingresa una opción: ");
                return scanner.nextInt();
            }
        }
        System.out.println("Cargo de empleado no válido");
        return 0;
    }
    // Este método recibe un objeto Scanner y el cargo de un empleado.
    // Basado en el cargo, muestra un menú de opciones en la consola y solicita al usuario que ingrese una opción.
    // Devuelve la opción ingresada por el usuario como un entero.

    private static void ejecutarOpcion(int opcion, Scanner scanner, Connection connection, Empresa empresa,
            Empleado empleado, Producto producto, Cliente cliente, Factura factura) throws SQLException {
        scanner.nextLine();

        if (empleado != null && empleado.getCargo() != null) {
            if (empleado.getCargo().equals(Cargo.Empleado)) {
                switch (opcion) {
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
            } else if (empleado.getCargo().equals(Cargo.Admin)) {
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
            } else {
                System.out.println("Cargo de empleado no válido");
            }
        } else {
            System.out.println("Empleado no válido. Verifica que el empleado esté inicializado correctamente.");
        }
    }
    // Este método recibe una opción, un objeto Scanner, una conexión a la base de datos, y varios objetos de distintas clases.
    // Basado en la opción y el cargo del empleado, ejecuta diferentes métodos correspondientes a cada opción.
    // Dependiendo del caso, se ejecuta una funcionalidad específica relacionada con la opción seleccionada por el usuario.
    // Si la opción es inválida o el empleado no es válido, se muestra un mensaje de error correspondiente.
    
    
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
                connection.setAutoCommit(false); // Desactivar la confirmación automática de la transacción
                empresaActualizar.actualizarEmpresa(connection); // Actualizar la empresa en la base de datos
                connection.commit(); // Confirmar la transacción
                System.out.println("La empresa se ha actualizado correctamente.");
            } catch (SQLException e) {
                connection.rollback(); // En caso de error, deshacer los cambios realizados
                System.out.println("Error al actualizar la empresa: " + e.getMessage());
            } finally {
                connection.setAutoCommit(true); // Reactivar la confirmación automática de la transacción
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

                    System.out.println(factura.toString());
                }
            }
            
        } else {
            System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
        }
    }

    // Función para obtener una empresa por su nombre
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

    // Función para obtener empleados por empresa
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

    // Función para obtener productos por empresa
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
                    producto.setPrecio(resultSet.getDouble("precio"));
                    producto.setStock(resultSet.getInt("stock"));

                    productos.add(producto);
                }
            }
        }

        return productos;
    }

    // Función para obtener clientes por empresa
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

    // Función para obtener facturas por empresa
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

    // Función para obtener una empresa por su ID
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
        // Solicitar y obtener el nombre del empleado
        System.out.print("\nIngresa el nombre del empleado: ");
        String nombreEmpleado = scanner.nextLine();

        // Verificar si el nombre está vacío
        if (nombreEmpleado.isEmpty()) {
            System.out.println("Error: el nombre del empleado no puede estar vacío.");
        } else {
            empleado.setNombre(nombreEmpleado);

            // Solicitar y obtener la contraseña del empleado
            System.out.print("Ingresa la contraseña del empleado: ");
            String contrasenyaEmpleado = scanner.nextLine();

            // Verificar si la contraseña está vacía
            if (contrasenyaEmpleado.isEmpty()) {
                System.out.println("Error: la contraseña del empleado no puede estar vacía.");
            } else {
                empleado.setContrasenya(contrasenyaEmpleado);

                // Solicitar y obtener la dirección del empleado
                System.out.print("Ingresa la dirección del empleado: ");
                String direccionEmpleado = scanner.nextLine();

                // Verificar si la dirección está vacía
                if (direccionEmpleado.isEmpty()) {
                    System.out.println("Error: la dirección del empleado no puede estar vacía.");
                } else {
                    empleado.setDireccion(direccionEmpleado);

                    // Solicitar y obtener el teléfono del empleado
                    System.out.print("Ingresa el teléfono del empleado: ");
                    String telefonoEmpleado = scanner.nextLine();

                    // Verificar si el teléfono está vacío
                    if (telefonoEmpleado.isEmpty()) {
                        System.out.println("Error: el teléfono del empleado no puede estar vacío.");
                    } else {
                        empleado.setTelefono(telefonoEmpleado);

                        // Solicitar y obtener el cargo del empleado
                        System.out.print("Ingresa el cargo del empleado: ");
                        String cargoEmpleado = scanner.nextLine();

                        // Verificar si el cargo está vacío
                        if (cargoEmpleado.isEmpty()) {
                            System.out.println("Error: el cargo del empleado no puede estar vacío.");
                        } else {

                            // Solicitar y obtener el sueldo del empleado
                            System.out.print("Ingresa el sueldo del empleado: ");
                            String sueldoEmpleado = scanner.nextLine();

                            // Verificar si el sueldo está vacío
                            if (sueldoEmpleado.isEmpty()) {
                                System.out.println("Error: el sueldo del empleado no puede estar vacío.");
                            } else {
                                empleado.setSueldo(Double.parseDouble(sueldoEmpleado));

                                // Solicitar y obtener el nombre de la empresa
                                System.out.print("Ingresa el nombre de la empresa: ");
                                String nombreEmpresa = scanner.nextLine();

                                // Verificar si el nombre de la empresa está vacío
                                if (nombreEmpresa.isEmpty()) {
                                    System.out.println("Error: el nombre de la empresa no puede estar vacío.");
                                } else {
                                    Empresa empresa = obtenerEmpresaPorNombre(connection, nombreEmpresa);

                                    // Verificar si se encontró una empresa con el nombre proporcionado
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
        // Solicitar y obtener el nombre del empleado a actualizar
        System.out.print("Ingresa el nombre del empleado a actualizar: ");
        String nombreActualizar = scanner.nextLine();

        // Obtener el empleado a actualizar
        Empleado empleadoActualizar = obtenerEmpleadoPorNombre(connection, nombreActualizar);

        // Verificar si se encontró un empleado con el nombre proporcionado
        if (empleadoActualizar != null) {
            // Solicitar y obtener los nuevos datos del empleado
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

            // Actualizar el empleado en la base de datos
            empleadoActualizar.actualizarEmpleado(connection);
            System.out.println("El empleado se ha actualizado correctamente.");
        } else {
            System.out.println("No se encontró ningún empleado con el nombre proporcionado.");
        }
    }

    private static void eliminarEmpleado(Scanner scanner, Connection connection, Empleado empleado)
            throws SQLException {
        // Solicitar y obtener el nombre del empleado a eliminar
        System.out.print("Ingresa el nombre del empleado a eliminar: ");
        String nombreEliminar = scanner.nextLine();

        // Obtener el empleado a eliminar
        Empleado empleadoEliminar = obtenerEmpleadoPorNombre(connection, nombreEliminar);

        // Verificar si se encontró un empleado con el nombre proporcionado
        if (empleadoEliminar != null) {
            // Eliminar el empleado de la base de datos
            empleadoEliminar.eliminarEmpleado(connection);
            System.out.println("El empleado se ha eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún empleado con el nombre proporcionado.");
        }
    }


    private static Empleado obtenerEmpleadoPorNombre(Connection connection, String nombre) throws SQLException {
        // Consulta SQL para obtener el empleado por nombre
        String query = "SELECT * FROM empleado WHERE nombre_empleado = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crear un objeto Empleado y establecer sus atributos según los valores del resultado de la consulta
                    Empleado empleado = new Empleado();
                    empleado.setId_empleado(resultSet.getInt("id_empleado"));
                    empleado.setNombre(resultSet.getString("nombre_empleado"));
                    empleado.setContrasenya(resultSet.getString("contrasenya_empleado"));
                    empleado.setDireccion(resultSet.getString("direccion_empleado"));
                    empleado.setTelefono(resultSet.getString("telefono_empleado"));
                    empleado.setCargo(Cargo.valueOf(resultSet.getString("cargo_empleado")));
                    empleado.setSueldo(resultSet.getDouble("sueldo_empleado"));

                    // Obtener el id de la empresa relacionada al empleado y obtener la instancia de la empresa
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
        // Consulta SQL para obtener los empleados ordenados por salario de forma descendente
        String query = "SELECT * FROM empleado ORDER BY sueldo_empleado DESC ";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            System.out.println("\n------ Empleados ordenados por salario (descendente) ------");
            while (resultSet.next()) {
                // Crear un objeto Empleado y establecer sus atributos según los valores del resultado de la consulta
                Empleado empleado = new Empleado();
                empleado.setNombre(resultSet.getString("nombre_empleado"));
                empleado.setDireccion(resultSet.getString("direccion_empleado"));
                empleado.setTelefono(resultSet.getString("telefono_empleado"));
                empleado.setCargo(Cargo.valueOf(resultSet.getString("cargo_empleado")));
                empleado.setSueldo(resultSet.getDouble("sueldo_empleado"));

                // Obtener el id de la empresa relacionada al empleado y obtener la instancia de la empresa
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
        // Consulta SQL para obtener el empleado por su id
        String query = "SELECT nombre_empleado FROM Empleado WHERE id_empleado = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEmpleado);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crear un objeto Empleado y establecer su nombre según el resultado de la consulta
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
        System.out.println("\n------ Menú Productos ------");
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
        // Solicitar el nombre del producto al usuario
        System.out.print("\nIngresa el nombre del producto: ");
        String nombreProducto = scanner.nextLine();

        if (nombreProducto.isEmpty()) {
            System.out.println("Error: el nombre del producto no puede estar vacío.");
        } else {
            // Establecer el nombre del producto en el objeto Producto
            producto.setNombre(nombreProducto);

            // Solicitar el precio del producto al usuario
            System.out.print("Ingresa el precio del producto: ");
            double precioProducto = scanner.nextDouble();
            scanner.nextLine(); // Consumir el carácter de nueva línea pendiente

            if (precioProducto <= 0) {
                System.out.println("Error: el precio del producto debe ser mayor que cero.");
            } else {
                // Establecer el precio del producto en el objeto Producto
                producto.setPrecio(precioProducto);

                // Solicitar el nombre de la empresa al usuario
                System.out.print("Ingresa el nombre de la empresa: ");
                String nombreEmpresa = scanner.nextLine();

                if (nombreEmpresa.isEmpty()) {
                    System.out.println("Error: el nombre de la empresa no puede estar vacío.");
                } else {
                    // Obtener la empresa según el nombre proporcionado
                    Empresa empresa = obtenerEmpresaPorNombre(connection, nombreEmpresa);

                    if (empresa != null) {
                        // Establecer la empresa en el objeto Producto
                        producto.setEmpresa(empresa);
                        // Insertar el producto en la base de datos
                        producto.insertarProducto(connection);
                        System.out.println("El producto se ha insertado correctamente.");
                    } else {
                        System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
                    }
                }
            }
        }
    }


    private static void actualizarProducto(Scanner scanner, Connection connection, Producto producto) throws SQLException {
        // Solicitar el nombre del producto a actualizar al usuario
        System.out.print("Ingresa el nombre del producto a actualizar: ");
        String nombreActualizar = scanner.nextLine();

        // Obtener el producto a actualizar según el nombre proporcionado
        Producto productoActualizar = obtenerProductoPorNombre(connection, nombreActualizar);

        if (productoActualizar != null) {
            // Solicitar al usuario el nuevo nombre del producto
            System.out.print("\nIngresa el nuevo nombre del producto: ");
            productoActualizar.setNombre(scanner.nextLine());
            // Solicitar al usuario el nuevo precio del producto
            System.out.print("\nIngresa el nuevo precio del producto: ");
            productoActualizar.setPrecio(Double.parseDouble(scanner.nextLine()));

            // Actualizar el producto en la base de datos
            productoActualizar.actualizarProducto(connection);
            System.out.println("El producto se ha actualizado correctamente.");
        } else {
            System.out.println("Error: El producto no se ha encontrado.");
        }
    }

    private static void eliminarProducto(Scanner scanner, Connection connection, Producto producto) throws SQLException {
        // Solicitar el nombre del producto a eliminar al usuario
        System.out.print("Ingresa el nombre del producto a eliminar: ");
        String nombreEliminar = scanner.nextLine();

        // Obtener el producto a eliminar según el nombre proporcionado
        Producto productoEliminar = obtenerProductoPorNombre(connection, nombreEliminar);
        if (productoEliminar != null) {
            // Eliminar el producto de la base de datos
            productoEliminar.eliminarProducto(connection);
            System.out.println("El producto se ha eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún producto con el nombre proporcionado.");
        }
    }


    private static Producto obtenerProductoPorNombre(Connection connection, String nombre) throws SQLException {
        String query = "SELECT * FROM Producto WHERE nombre_producto = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crear una instancia de Producto y establecer los valores obtenidos de la base de datos
                    Producto producto = new Producto();
                    producto.setId_producto(resultSet.getInt("id_producto"));
                    producto.setNombre(resultSet.getString("nombre_producto"));
                    producto.setPrecio(resultSet.getDouble("precio"));

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
                    // Crear una instancia de Producto y establecer el nombre obtenido de la base de datos
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
        System.out.println("\n------ Menú Cliente ------");
        System.out.println("1. Insertar cliente");
        System.out.println("2. Actualizar cliente");
        System.out.println("3. Eliminar cliente");
        System.out.println("4. Buscar facturas por cliente");
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
            case 4:
                buscarFacturasPorCliente(scanner, connection, cliente);
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
        	//Solicita el nombre del cliente
        
        if (nombreCliente.isEmpty()) {
			System.out.println("Error: El nombre del empleado no puede estar vacio. ");
		} else {
            cliente.setNombre(nombreCliente);
            //Se establece el nombre del cliente
            
            System.out.println("Introduce la direccion del empleado: ");
            String direccionCliente = scanner.nextLine();
            	//Solicita la direccion del cliente
            
            if (direccionCliente.isEmpty()) {
				System.out.println("Error: La dirreccion del cliente no puede estar vacia. ");
			} else {
				cliente.setDireccion(direccionCliente);
					//Se establece la direccion del cliente
				
				System.out.println("Introduce el telefono del cliente: ");
				String telefonoCliente = scanner.nextLine();
					//Solicita el telefono ddel cliente
				
				if (telefonoCliente.isEmpty()) {
					System.out.println("Error: El telefono del cliente no puede estar vacio. ");
				} else {
					cliente.setTelefono(telefonoCliente);
						//Se establece el telefono del cliente
					
                    System.out.print("Ingresa el nombre de la empresa: ");
                    String nombreEmpresa = scanner.nextLine();
                    	//Se solicita el nombre de la empresa
                    
                    if (nombreEmpresa.isEmpty()) {
                        System.out.println("Error: el nombre de la empresa no puede estar vacío.");
                    } else {
                        Empresa empresa = obtenerEmpresaPorNombre(connection, nombreEmpresa);
                        	//Se establece el cliente en la empresa
                        
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


    private static void actualizarCliente(Scanner scanner, Connection connection, Cliente cliente) throws SQLException {
        System.out.print("Ingresa el nombre del cliente a actualizar: ");
        String nombreActualizar = scanner.nextLine();

        // Obtener el cliente a actualizar por su nombre
        Cliente clienteActualizar = obtenerClientePorNombre(connection, nombreActualizar);

        if (clienteActualizar != null) {
            System.out.print("\nIngresa el nuevo nombre del cliente: ");
            clienteActualizar.setNombre(scanner.nextLine());
            System.out.print("Ingresa la nueva dirección del cliente: ");
            clienteActualizar.setDireccion(scanner.nextLine());
            System.out.print("Ingresa el nuevo teléfono del cliente: ");
            clienteActualizar.setTelefono(scanner.nextLine());

            // Actualizar el cliente en la base de datos
            clienteActualizar.actualizarCliente(connection);
            System.out.println("El cliente se ha actualizado correctamente.");
        } else {
            System.out.println("No se encontró ningún cliente con el nombre proporcionado.");
        }
    }

    private static void eliminarCliente(Scanner scanner, Connection connection, Cliente cliente) throws SQLException {
        System.out.print("Ingresa el nombre del cliente a eliminar: ");
        String nombreEliminar = scanner.nextLine();

        // Obtener el cliente a eliminar por su nombre
        Cliente clienteEliminar = obtenerClientePorNombre(connection, nombreEliminar);

        if (clienteEliminar != null) {
            // Eliminar el cliente de la base de datos
            clienteEliminar.eliminarCliente(connection);
            System.out.println("El cliente se ha eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún cliente con el nombre proporcionado.");
        }
    }

    private static void buscarFacturasPorCliente(Scanner scanner, Connection connection, Cliente cliente)
            throws SQLException {
        System.out.print("Ingresa el nombre del cliente: ");
        String nombreCliente = scanner.nextLine();

        // Obtener el cliente por su nombre
        Cliente clienteBuscar = obtenerClientePorNombre(connection, nombreCliente);

        if (clienteBuscar != null) {
            // Obtener las facturas del cliente
            Set<Factura> facturas = obtenerFacturasPorCliente(connection, clienteBuscar.getId_cliente());

            if (!facturas.isEmpty()) {
                System.out.println("\nFacturas del cliente " + clienteBuscar.getNombre() + ":");
                for (Factura factura : facturas) {
                    System.out.println("Forma de pago: " + factura.getPago());
                    System.out.println("Fecha de emisión: " + factura.getFecha());
                    System.out.println("Cantidad de producto: " + factura.getCantidad());
                    System.out.println("Nombre del producto: " + factura.getNombre());
                    System.out.println("Total de la factura: " + factura.getTotal());
                    System.out.println("Nombre del empleado: " + factura.getEmpleado().getNombre());
                    System.out.println("-----------------------------");
                }
            } else {
                System.out.println("No se encontraron facturas para el cliente " + clienteBuscar.getNombre());
            }
        } else {
            System.out.println("No se encontró ningún cliente con el nombre proporcionado.");
        }
    }





    private static Cliente obtenerClientePorNombre(Connection connection, String nombre) throws SQLException {
        String query = "SELECT * FROM Cliente WHERE nombre_cliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            	//Se hace una select para obtener el cliente por Nombre
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

                    return cliente; // Devuelve el objeto Cliente encontrado
                } else {
                    return null; // No se encontró ningún cliente con el nombre proporcionado
                }
            }
        }
    }

    private static Cliente obtenerClientePorId(Connection connection, int idCliente) throws SQLException {
        String query = "SELECT nombre_cliente FROM Cliente WHERE id_cliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCliente);
            		//Se hace una select para obtener el id_cliente 
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cliente.setNombre(resultSet.getString("nombre_cliente"));

                    return cliente; // Devuelve el objeto Cliente encontrado
                } else {
                    return null; // No se encontró ningún cliente con el ID proporcionado
                }
            }
        }
    }


    private static Set<Factura> obtenerFacturasPorCliente(Connection connection, int idCliente) throws SQLException {
        String query = "SELECT * FROM Factura WHERE rep_id_cliente = ?";

        Set<Factura> facturas = new LinkedHashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCliente);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Factura factura = new Factura();
                    factura.setId_factura(resultSet.getInt("id_factura"));
                    factura.setPago(formaPago.valueOf(resultSet.getString("forma_pago")));
                    factura.setFecha(resultSet.getDate("fecha_pago").toLocalDate());
                    factura.setCantidad(resultSet.getInt("cantidad_producto"));
                    factura.setNombre(resultSet.getString("producto_nombre"));
                    factura.setTotal(resultSet.getDouble("total"));

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

    private static void ejecutarOpcionFactura(int opcion, Scanner scanner, Connection connection, Factura factura)
            throws SQLException {
        scanner.nextLine();

        switch (opcion) {
            case 1:
                insertarFactura(scanner, connection, factura);
                break;
            case 2:
                actualizarFactura(scanner, connection, factura);
                break;
            case 3:
                eliminarFactura(scanner, connection, factura);
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
        System.out.println("\nIngrese el nombre de la empresa: ");
        String nombreEmpresa = scanner.nextLine();
        Empresa empresa = obtenerEmpresaPorNombre(connection, nombreEmpresa);

        if (empresa != null) {
            factura.setEmpresa(empresa);

            System.out.println("Formas de pago: ");
            for (formaPago forma : formaPago.values()) {
                System.out.println("{" + forma.name() + "}"); // Mostrar las formas de pago disponibles
            }

            System.out.println("Ingrese la forma de pago: ");
            String pago = scanner.nextLine();

            if (pago.isEmpty()) {
                System.out.println("La forma de pago no puede estar vacía. "); // Validar que la forma de pago no esté vacía
            } else {
                factura.setPago(formaPago.valueOf(pago)); // Asignar la forma de pago a la factura

                LocalDate fecha = LocalDate.now();
                factura.setFecha(fecha); // Asignar la fecha actual a la factura

                System.out.println("Ingrese la cantidad del producto: ");
                int cantidad = Integer.parseInt(scanner.nextLine());
                scanner.nextLine();

                if (cantidad <= 0) {
                    System.out.println("La cantidad debe ser superior a 0. "); // Validar que la cantidad sea mayor a 0
                } else {
                    factura.setCantidad(cantidad);

                    System.out.println("Ingrese el nombre del producto: ");
                    String nombreProducto = scanner.nextLine();

                    if (nombreProducto.isEmpty()) {
                        System.out.println("El nombre del producto no puede estar vacío"); // Validar que el nombre del producto no esté vacío
                    } else {
                        Producto producto = obtenerProductoPorNombre2(connection, nombreProducto);

                        if (producto != null) {
                            if (producto.getStock() >= cantidad) { // Verificar si hay suficiente stock del producto
                                factura.setProducto(producto);
                                int nuevoStockProducto = producto.getStock() - cantidad;
                                producto.setStock(nuevoStockProducto);

                                producto.setEmpresa(empresa);
                                producto.actualizarProducto(connection); // Actualizar el stock del producto en la base de datos

                                double total = producto.getPrecio() * cantidad * producto.getIVA();
                                factura.setTotal(total); // Calcular y asignar el total de la factura

                                System.out.println("Ingrese el nombre del cliente: ");
                                String nombreCliente = scanner.nextLine();
                                Cliente cliente = obtenerClientePorNombre(connection, nombreCliente);

                                if (cliente != null) {
                                    factura.setCliente(cliente);

                                    System.out.println("Ingrese el nombre del empleado: ");
                                    String nombreEmpleado = scanner.nextLine();
                                    Empleado empleado = obtenerEmpleadoPorNombre(connection, nombreEmpleado);

                                    if (empleado != null) {
                                        factura.setEmpleado(empleado);

                                        factura.insertarFactura(connection, factura); // Insertar la factura en la base de datos

                                        System.out.println("Factura ingresada correctamente.");
                                    } else {
                                        System.out.println("No se encontró ningún empleado con el nombre proporcionado.");
                                    }
                                } else {
                                    System.out.println("No se encontró ningún cliente con el nombre proporcionado.");
                                }
                            } else {
                                System.out.println("No hay suficiente stock disponible para el producto.");
                            }
                        } else {
                            System.out.println("No se encontró ningún producto con el nombre proporcionado.");
                        }
                    }
                }
            }
        } else {
            System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
        }
    }



    private static void actualizarFactura(Scanner scanner, Connection connection, Factura factura) throws SQLException {
        System.out.print("Ingresa el número de factura que deseas actualizar: ");
        int numeroFactura = Integer.parseInt(scanner.nextLine());
        Factura facturaExistente = obtenerFacturaPorNumero(connection, numeroFactura);

        if (facturaExistente != null) {
            System.out.print("Ingresa la nueva cantidad de productos: ");
            int nuevaCantidad = Integer.parseInt(scanner.nextLine());
            facturaExistente.setCantidad(nuevaCantidad); // Actualizar la cantidad de productos en la factura existente

            facturaExistente.actualizarFactura(connection); // Actualizar la factura en la base de datos
            System.out.println("La factura se ha actualizado correctamente.");
        } else {
            System.out.println("No se encontró ninguna factura con el número proporcionado.");
        }
    }

    private static void eliminarFactura(Scanner scanner, Connection connection, Factura factura) throws SQLException {
        System.out.print("Ingresa el número de factura que deseas eliminar: ");
        int numeroFactura = Integer.parseInt(scanner.nextLine());
        Factura facturaExistente = obtenerFacturaPorNumero(connection, numeroFactura);

        if (facturaExistente != null) {
            facturaExistente.eliminarFactura(connection); // Eliminar la factura de la base de datos
            System.out.println("La factura se ha eliminado correctamente.");
        } else {
            System.out.println("No se encontró ninguna factura con el número proporcionado.");
        }
    }


    private static Factura obtenerFacturaPorNumero(Connection connection, int idFactura) throws SQLException {
        String query = "SELECT * FROM factura WHERE id_factura = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idFactura);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Factura factura = new Factura();
            factura.setId_factura(resultSet.getInt("id_factura")); // Obtener el ID de la factura del resultado de la consulta
            return factura;
        }

        return null;
    }

    private static Producto obtenerProductoPorNombre2(Connection connection, String nombreProducto) throws SQLException {
        String query = "SELECT * FROM Producto WHERE nombre_producto = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, nombreProducto);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Producto producto = new Producto();
            producto.setId_producto(resultSet.getInt("id_producto")); // Obtener el ID del producto del resultado de la consulta
            producto.setNombre(resultSet.getString("nombre_producto")); // Obtener el nombre del producto del resultado de la consulta
            producto.setPrecio(resultSet.getDouble("precio")); // Obtener el precio del producto del resultado de la consulta
            producto.setStock(resultSet.getInt("stock")); // Obtener el stock del producto del resultado de la consulta

            return producto;
        }

        return null;
    }


}
