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
                gestorEmpresa.actualizarEmpresa(scanner, connection, empresa);
                break;
            case 2:
                gestorEmpresa.mostrarInformacionEmpresa(scanner, connection, empresa);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
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
                gestorEmpresa.insertarEmpleado(scanner, connection, empleado);
                break;
            case 2:
                gestorEmpresa.actualizarEmpleado(scanner, connection, empleado);
                break;
            case 3:
                gestorEmpresa.eliminarEmpleado(scanner, connection, empleado);
                break;
            case 4:
                gestorEmpresa.mostrarEmpleadosOrdenadosPorSalario(connection);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
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
                gestorEmpresa.insertarProducto(scanner, connection, producto);
                break;
            case 2:
                gestorEmpresa.actualizarProducto(scanner, connection, producto);
                break;
            case 3:
                gestorEmpresa.eliminarProducto(scanner, connection, producto);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
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
                gestorEmpresa.insertarCliente(scanner, connection, cliente);
                break;
            case 2:
                gestorEmpresa.actualizarCliente(scanner, connection, cliente);
                break;
            case 3:
                gestorEmpresa.eliminarCliente(scanner, connection, cliente);
                break;
            case 4:
                gestorEmpresa.buscarFacturasPorCliente(scanner, connection, cliente);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
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

    private static void ejecutarOpcionFactura(int opcion, Scanner scanner, Connection connection, Factura factura)
            throws SQLException {
        scanner.nextLine();

        switch (opcion) {
            case 1:
                gestorEmpresa.insertarFactura(scanner, connection, factura);
                break;
            case 2:
                gestorEmpresa.actualizarFactura(scanner, connection, factura);
                break;
            case 3:
                gestorEmpresa.eliminarFactura(scanner, connection, factura);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
        }
    }




}
