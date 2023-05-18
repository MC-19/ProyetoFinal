package TrabajoFinal;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Factura {
    private int id_factura;
    private LocalDate fecha;
    private Empresa empresa;
    private Empleado empleado;
    private Cliente cliente;
    private Item[] items;
    private double total;

    public Factura() {
    	
    }

    public Factura(int id_factura, LocalDate fecha, Empresa empresa, Empleado empleado, Cliente cliente, Item[] items, double total) {
        this.id_factura = id_factura;
        this.fecha = fecha;
        this.empresa = empresa;
        this.empleado = empleado;
        this.cliente = cliente;
        this.items = items;
        this.total = total;
    }

    public Factura(Factura factura) {
        this.id_factura = factura.id_factura;
        this.fecha = factura.fecha;
        this.empresa = factura.empresa;
        this.empleado = factura.empleado;
        this.cliente = factura.cliente;
        this.items = factura.items;
        this.total = factura.total;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void calcularTotal() {
        double totalFactura = 0.0;
        for (Item item : items) {
            totalFactura += item.getPrecio() * item.getCantidad();
        }
        this.total = totalFactura;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(items);
        result = prime * result + Objects.hash(cliente, empleado, empresa, fecha, id_factura, total);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Factura other = (Factura) obj;
        return Objects.equals(cliente, other.cliente) && Objects.equals(empleado, other.empleado)
                && Objects.equals(empresa, other.empresa) && Objects.equals(fecha, other.fecha)
                && id_factura == other.id_factura && Arrays.equals(items, other.items)
                && Double.doubleToLongBits(total) == Double.doubleToLongBits(other.total);
    }

    @Override
    public String toString() {
        return "Factura [id_factura=" + id_factura + ", fecha=" + fecha + ", empresa=" + empresa + ", empleado="
                + empleado + ", cliente=" + cliente + ", items=" + Arrays.toString(items) + ", total=" + total + "]";
    }
}
