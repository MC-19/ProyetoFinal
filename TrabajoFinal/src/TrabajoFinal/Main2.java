package TrabajoFinal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = obtenerConexion();

        Empresa empresa = new Empresa();
        Empleado empleado = new Empleado();

        ejecutarMenu(scanner, connection, empresa, empleado);

        scanner.close();
        connection.close();
    }

    private static void ejecutarMenu(Scanner scanner, Connection connection, Empresa empresa, Empleado empleado)
            throws SQLException {
        int opcion;

        while (true) {
            opcion = mostrarMenu(scanner);
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    ejecutarMenuEmpresa(scanner, connection, empresa);
                    break;
                case 2:
                    ejecutarMenuEmpleado(scanner, connection, empleado);
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción inválida. Intenta nuevamente.");
                    break;
            }
        }
    }

    private static int mostrarMenu(Scanner scanner) {
        System.out.println("=== Menú ===");
        System.out.println("1. Menú Empresa");
        System.out.println("2. Menú Empleado");
        System.out.println("0. Salir");
        System.out.print("Ingresa una opción: ");
        return scanner.nextInt();
    }

    private static void ejecutarMenuEmpresa(Scanner scanner, Connection connection, Empresa empresa)
            throws SQLException {
        int opcion;

        do {
            opcion = mostrarMenuEmpresa(scanner);
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    // Código para la opción 1 del menú de la empresa
                    break;
                case 2:
                    // Código para la opción 2 del menú de la empresa
                    break;
                case 0:
                    System.out.println("Regresando al menú principal...");
                    return;
                default:
                    System.out.println("Opción inválida. Intenta nuevamente.");
                    break;
            }
        } while (opcion != 0);
    }

    private static int mostrarMenuEmpresa(Scanner scanner) {
        System.out.println("=== Menú Empresa ===");
        System.out.println("1. Opción 1");
        System.out.println("2. Opción 2");
        System.out.println("0. Volver al menú principal");
        System.out.print("Ingresa una opción: ");
        return scanner.nextInt();
    }

    private static void ejecutarMenuEmpleado(Scanner scanner, Connection connection, Empleado empleado)
            throws SQLException {
        int opcion;

        do {
            opcion = mostrarMenuEmpleado(scanner);
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    // Código para la opción 1 del menú del empleado
                    break;
                case 2:
                    // Código para la opción 2 del menú del empleado
                    break;
                case 0:
                    System.out.println("Regresando al menú principal...");
                    return;
                default:
                    System.out.println("Opción inválida. Intenta nuevamente.");
                    break;
            }
        } while (opcion != 0);
    }

    private static int mostrarMenuEmpleado(Scanner scanner) {
        System.out.println("=== Menú Empleado ===");
        System.out.println("1. Opción 1");
        System.out.println("2. Opción 2");
        System.out.println("0. Volver al menú principal");
        System.out.print("Ingresa una opción: ");
        return scanner.nextInt();
    }

    private static Connection obtenerConexion() throws SQLException {
        // Lógica para obtener la conexión a la base de datos
        return null;
    }
}
