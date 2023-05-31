package TrabajoFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Empleado extends Persona {
    private int id_empleado;
    private String contrasenya;
    private Cargo cargo;
    private double sueldo;
    private Empresa empresa;
    
    public Empleado() {
        super();
    }

    public Empleado(int id_empleado, double sueldo, Cargo cargo, Empresa empresa) {
        super();
        this.id_empleado = id_empleado;
        this.sueldo = sueldo;
        this.cargo = cargo;
        this.empresa = empresa;
    }

    public Empleado(Persona persona, Empleado empleado) {
        super(persona);
        this.id_empleado = empleado.id_empleado;
        this.sueldo = empleado.sueldo;
        this.cargo = empleado.cargo;
        this.empresa = empleado.empresa; 
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
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
     
    public void insertarEmpleado(Connection connection) throws SQLException {
        String query = "INSERT INTO empleado (id_empleado, nombre_empleado, contrasnya_empleado, direccion_empleado, "
            + "telefono_empleado, cargo_empleado, sueldo_empleado, rep_id_empresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_empleado);
            statement.setString(2, getNombre());
            statement.setString(3, getDireccion());
            statement.setString(4, getTelefono());
            statement.setString(5, cargo.name());
            statement.setDouble(6, sueldo);
            statement.setInt(7, empresa.getId_empresa());
            statement.executeUpdate();
        }
    }
    
    public void actualizarEmpleado(Connection connection) throws SQLException {
        String query = "UPDATE empleado SET nombre_empleado = ?, contrasenya_empleado = ?, direccion_empleado = ?, telefono_empleado = ?, "
        					+ "cargo_empleado = ?, sueldo_empleado = ?, rep_id_empresa = ? WHERE id_empleado = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, getNombre());
            statement.setString(2, getDireccion());
            statement.setString(3, getTelefono());
            statement.setString(4, cargo.name());
            statement.setDouble(5, sueldo);
            statement.setInt(6, empresa.getId_empresa());
            statement.setInt(7, id_empleado);
            statement.executeUpdate();
        }
    }
    
    public void eliminarEmpleado(Connection connection) throws SQLException {
        String query = "DELETE FROM empleado WHERE id_empleado = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_empleado);
            statement.executeUpdate();
        }
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cargo, contrasenya, empresa, id_empleado, sueldo);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return cargo == other.cargo && Objects.equals(contrasenya, other.contrasenya)
				&& Objects.equals(empresa, other.empresa) && id_empleado == other.id_empleado
				&& Double.doubleToLongBits(sueldo) == Double.doubleToLongBits(other.sueldo);
	}
	
	public static void insertarEmpleado(Scanner scanner, Connection connection, Empleado empleado) throws SQLException {
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
                                    Empresa empresa = Empresa.obtenerEmpresaPorNombre(connection, nombreEmpresa);

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


	public static void actualizarEmpleado(Scanner scanner, Connection connection, Empleado empleado)
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

	public static void eliminarEmpleado(Scanner scanner, Connection connection, Empleado empleado)
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

                    int idEmpresa = resultSet.getInt("id_empresa");
                    Empresa empresa = Empresa.obtenerEmpresaPorId(connection, idEmpresa);
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
    
    public static boolean verificarEmpleado(String contraseñaEmpleado, Connection connection) throws SQLException {
        String sql = "SELECT * FROM empleado WHERE contrasenya_empleado = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contraseñaEmpleado);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
    
    public static String obtenerCargoEmpleado(String contraseñaEmpleado, Connection connection) throws SQLException {
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
	
}
