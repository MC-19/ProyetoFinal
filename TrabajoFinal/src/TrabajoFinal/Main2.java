package TrabajoFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        // Crear una instancia de Empresa
        Empresa empresa = new Empresa();
        
        // Obtener una conexión a la base de datos
        try (Connection connection = Empresa.getConnection()) {
            Scanner scanner = new Scanner(System.in);
            int opcion;
            
            do {
                System.out.println("\n--- MENÚ DE OPCIONES ---");
                System.out.println("1. Insertar empresa");
                System.out.println("2. Actualizar empresa");
                System.out.println("3. Obtener empresa por nombre");
                System.out.println("4. Eliminar empresa por nombre");
                System.out.println("0. Salir");
                System.out.print("Ingresa una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                
                switch (opcion) {
                    case 1:
                        System.out.print("\nIngresa el nombre de la empresa: ");
                        empresa.setNombre(scanner.nextLine());
                        System.out.print("Ingresa la dirección de la empresa: ");
                        empresa.setDireccion(scanner.nextLine());
                        System.out.print("Ingresa el teléfono de la empresa: ");
                        empresa.setTelefono(scanner.nextLine());
                        
                        empresa.insertarEmpresa(connection);
                        System.out.println("La empresa se ha insertado correctamente");
                        break;
                    case 2:
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
                        break;
                    case 3:
                        System.out.print("\nIngresa el nombre de la empresa a buscar: ");
                        String nombreBuscar = scanner.nextLine();
                        
                        Empresa empresaBuscar = obtenerEmpresaPorNombre(connection, nombreBuscar);
                        if (empresaBuscar != null) {
                            System.out.println("Empresa encontrada: " + empresaBuscar.toString());
                        } else {
                            System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
                        }
                        break;
                    case 4:
                        System.out.print("Ingresa el nombre de la empresa a eliminar: ");
                        String nombreEliminar = scanner.nextLine();
                        
                        Empresa empresaEliminar = obtenerEmpresaPorNombre(connection, nombreEliminar);
                        if (empresaEliminar != null) {
                            empresaEliminar.eliminarEmpresa(connection);
                            System.out.println("La empresa se ha eliminado correctamente.");
                        } else {
                            System.out.println("No se encontró ninguna empresa con el nombre proporcionado.");
                        }
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intenta nuevamente.");
                        break;
                }
            } while (opcion != 0);
            
            scanner.close();
        } catch (SQLException e) {
            System.out.println("Error en la operación: " + e.getMessage());
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
                    return null; // No se encontró ninguna empresa con el nombre proporcionado
                }
            }
        }
    }

}
