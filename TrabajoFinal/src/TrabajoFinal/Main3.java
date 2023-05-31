package TrabajoFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Main3 {

    public static void main(String[] args) {
        Empresa empresa = new Empresa();
        Empleado empleado = new Empleado();
        Producto producto = new Producto();

        try (Connection connection = Empresa.getConnection()) {
            Scanner scanner = new Scanner(System.in);
            int opcion;

            System.out.print("Ingrese el nombre de la empresa (por ejemplo, Acme Corp): ");
            String nombreEmpresa = scanner.nextLine();

            boolean empresaValida = empresa.verificarEmpresa(nombreEmpresa, connection);

            if (!empresaValida) {
                System.out.println("Empresa no válida. Saliendo del programa.");
                return;
            }

            System.out.print("Ingrese la contraseña del empleado: ");
            String contraseñaEmpleado = scanner.nextLine();

            boolean empleadoValido = empleado.verificarEmpleado(contraseñaEmpleado, connection);

            if (!empleadoValido) {
                System.out.println("Empleado no válido. Saliendo del programa.");
                return;
            }

            String cargoEmpleado = empleado.obtenerCargoEmpleado(contraseñaEmpleado, connection);

            do {
                opcion = mostrarMenu(scanner, cargoEmpleado);
                ejecutarOpcion(opcion, scanner, connection, empresa, empleado, producto );
            } while (opcion != 0);

            scanner.close();
        } catch (SQLException e) {
            System.out.println("Error en la operación: " + e.getMessage());
        }
    }

        private static int mostrarMenu(Scanner scanner, String cargoEmpleado) {
            if (cargoEmpleado.equalsIgnoreCase("Empleado")) {
                System.out.println("\n--- MENÚ DE OPCIONES ---");
                System.out.println("1. Menú Producto");
                System.out.println("2. Menú Factura");
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
            Empleado empleado, Producto producto) throws SQLException {
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
        System.out.println("1. Insertar empresa");
        System.out.println("2. Actualizar empresa");
        System.out.println("3. Mostrar información de la empresa");
        System.out.println("0. Volver al menú principal");
        System.out.print("Ingresa una opción: ");
        return scanner.nextInt();
    }

    private static void ejecutarOpcionEmpresa(int opcion, Scanner scanner, Connection connection, Empresa empresa)
            throws SQLException {
        scanner.nextLine(); 

        switch (opcion) {
            case 1:
                empresa.insertarEmpresa(scanner, connection, empresa);
                break;
            case 2:
                empresa.actualizarEmpresa(scanner, connection, empresa);
                break;
            case 3:
                empresa.mostrarInformacionEmpresa(scanner, connection, empresa);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
        }
    }
    
//---------------------------------------------------------PARTE DE LOS EMPLEADOS-----------------------------------------------------------------\\

    
    
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
        System.out.println("0. Volver al menú principal");
        System.out.print("Ingresa una opción: ");
        return scanner.nextInt();
    }

    private static void ejecutarOpcionEmpleado(int opcion, Scanner scanner, Connection connection, Empleado empleado)
            throws SQLException {
        scanner.nextLine();

        switch (opcion) {
            case 1:
                empleado.insertarEmpleado(scanner, connection, empleado);
                break;
            case 2:
                empleado.actualizarEmpleado(scanner, connection, empleado);
                break;
            case 3:
                empleado.eliminarEmpleado(scanner, connection, empleado);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
        }
    }

    
//---------------------------------------------------------PARTE DE LOS PRODUCTOS-----------------------------------------------------------------\\

    
    
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
                producto.insertarProducto(scanner, connection, producto);
                break;
            case 2:
                producto.actualizarProducto(scanner, connection, producto);
                break;
            case 3:
                producto.eliminarProducto(scanner, connection, producto);
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
        }
    }

  //---------------------------------------------------------PARTE DE LOS CLIENTES----------------------------------------------------------------\\

}
