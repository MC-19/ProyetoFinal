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

        try (Connection connection = Empresa.getConnection()) {
            Scanner scanner = new Scanner(System.in);
            int opcion;

            do {
                opcion = mostrarMenu(scanner);
                ejecutarOpcion(opcion, scanner, connection, empresa, empleado);
            } while (opcion != 0);

            scanner.close();
        } catch (SQLException e) {
            System.out.println("Error en la operación: " + e.getMessage());
        }
    }

    private static int mostrarMenu(Scanner scanner) {
        System.out.println("\n--- MENÚ DE OPCIONES ---");
        System.out.println("1. Menú Empresa");
        System.out.println("2. Menú Empleado");
        System.out.println("0. Salir");
        System.out.print("Ingresa una opción: ");
        return scanner.nextInt();
    }

    private static void ejecutarOpcion(int opcion, Scanner scanner, Connection connection, Empresa empresa,
            Empleado empleado) throws SQLException {
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
                insertarEmpresa(scanner, connection, empresa);
                break;
            case 2:
                actualizarEmpresa(scanner, connection, empresa);
                break;
            case 3:
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

    private static void insertarEmpresa(Scanner scanner, Connection connection, Empresa empresa) throws SQLException {
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

    private static void actualizarEmpresa(Scanner scanner, Connection connection, Empresa empresa)
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
//                    producto.setSueldo(resultSet.getDouble("sueldo_empleado"));

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
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
                break;
        }
    }

    private static void insertarEmpleado(Scanner scanner, Connection connection, Empleado empleado)
            throws SQLException {
        System.out.print("\nIngresa el nombre del empleado: ");
        String nombreEmpleado = scanner.nextLine();

        if (nombreEmpleado.isEmpty()) {
            System.out.println("Error: el nombre del empleado no puede estar vacío.");
        } else {
            empleado.setNombre(nombreEmpleado);

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
                        empleado.setCargo(Cargo.valueOf(cargoEmpleado)); // Establecer el cargo del empleado

                        System.out.print("Ingresa el sueldo del empleado: ");
                        String sueldoEmpleado = scanner.nextLine();

                        if (sueldoEmpleado.isEmpty()) {
                            System.out.println("Error: el sueldo del empleado no puede estar vacío.");
                        } else {
                            empleado.setSueldo(Double.parseDouble(sueldoEmpleado)); // Convertir a tipo double

                            System.out.print("Ingresa el nombre de la empresa: ");
                            String nombreEmpresa = scanner.nextLine();

                            if (nombreEmpresa.isEmpty()) {
                                System.out.println("Error: el nombre de la empresa no puede estar vacío.");
                            } else {
                                Empresa empresa = obtenerEmpresaPorNombre(connection, nombreEmpresa);

                                if (empresa != null) {
                                    empleado.setEmpresa(empresa); // Establecer la empresa del empleado
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

    private static void actualizarEmpleado(Scanner scanner, Connection connection, Empleado empleado)
            throws SQLException {
        System.out.print("Ingresa el nombre del empleado a actualizar: ");
        String nombreActualizar = scanner.nextLine();

        Empleado empleadoActualizar = obtenerEmpleadoPorNombre(connection, nombreActualizar);
        if (empleadoActualizar != null) {
            System.out.print("\nIngresa el nuevo nombre del empleado: ");
            empleadoActualizar.setNombre(scanner.nextLine());
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
                    empleado.setDireccion(resultSet.getString("direccion_empleado"));
                    empleado.setTelefono(resultSet.getString("telefono_empleado"));
                    empleado.setCargo(Cargo.valueOf(resultSet.getString("cargo_empleado")));
                    empleado.setSueldo(resultSet.getDouble("sueldo_empleado"));

                    int idEmpresa = resultSet.getInt("id_empresa");
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
    
//----------------------------------------------------------PARTE DE LOS PRODUCTOS------------------------------------------------------------------\\

}
