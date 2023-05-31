package TrabajoFinal;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Cliente extends Persona {
    private int id_cliente;
    private Empresa empresa;

    private Set<Factura> facturas = new LinkedHashSet<>();

    public Cliente() {
        super();
    }

    public Cliente(int id_cliente) {
        super();
        this.id_cliente = id_cliente;
    }

    public Cliente(Persona persona, Cliente cliente) {
        super(persona);
        this.id_cliente = cliente.id_cliente;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(id_cliente);
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
        Cliente other = (Cliente) obj;
        return id_cliente == other.id_cliente;
    }

    @Override
    public String toString() {
        return "Cliente [" + super.toString() + ", id_cliente=" + id_cliente + "]";
    }
}
