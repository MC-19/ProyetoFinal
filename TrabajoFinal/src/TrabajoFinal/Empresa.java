package TrabajoFinal;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Empresa {
    private int id_empresa;
    private String nombre;
    private String direccion;
    private String telefono;

    private Set<Empleado> empleados = new LinkedHashSet<>();
    private Set<Cliente> clientes = new LinkedHashSet<>();
    private Set<Producto> productos = new LinkedHashSet<>();
    private Set<Factura> facturas = new LinkedHashSet<>();

    public Empresa() {

    }

    public Empresa(int id_empresa, String nombre, String direccion, String telefono, 
                   Set<Empleado> empleados, Set<Cliente> clientes, Set<Producto> productos, Set<Factura> facturas) {
        this.id_empresa = id_empresa;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.empleados.addAll(empleados);
        this.clientes.addAll(clientes);
        this.productos.addAll(productos);
        this.facturas.addAll(facturas);
    }
    
    public Empresa(Empresa empresa) {
        this.id_empresa = empresa.id_empresa;
        this.nombre = empresa.nombre;
        this.direccion = empresa.direccion;
        this.telefono = empresa.telefono;
        this.empleados = new LinkedHashSet<>(empresa.empleados);
        this.clientes = new LinkedHashSet<>(empresa.clientes);
        this.productos = new LinkedHashSet<>(empresa.productos);
        this.facturas = new LinkedHashSet<>(empresa.facturas);
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

    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public Set<Factura> getFacturas() {
        return facturas;
    }

    public void agregarFactura(Factura factura) {
        facturas.add(factura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientes, direccion, empleados, facturas, id_empresa, nombre, productos, telefono);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Empresa other = (Empresa) obj;
        return id_empresa == other.id_empresa && Objects.equals(nombre, other.nombre)
                && Objects.equals(direccion, other.direccion) && Objects.equals(telefono, other.telefono)
                && Objects.equals(empleados, other.empleados) && Objects.equals(clientes, other.clientes)
                && Objects.equals(productos, other.productos) && Objects.equals(facturas, other.facturas);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Empresa [id_empresa=").append(id_empresa)
          .append(", nombre=").append(nombre)
          .append(", direccion=").append(direccion)
          .append(", telefono=").append(telefono);

        if (!empleados.isEmpty()) {
            sb.append(", empleados=").append(empleados);
        }

        if (!clientes.isEmpty()) {
            sb.append(", clientes=").append(clientes);
        }

        if (!productos.isEmpty()) {
            sb.append(", productos=").append(productos);
        }

        if (!facturas.isEmpty()) {
            sb.append(", facturas=").append(facturas);
        }

        sb.append("]");

        return sb.toString();
    }

}
