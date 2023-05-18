package TrabajoFinal;

import java.util.Objects;

public class Empleado extends Persona {
    private int id_empleado;
    private double sueldo;
    private String cargo;

    public Empleado() {
        super();
    }

    public Empleado(int id_empleado, double sueldo, String cargo) {
        super();
        this.id_empleado = id_empleado;
        this.sueldo = sueldo;
        this.cargo = cargo;
    }

    public Empleado(Persona persona, Empleado empleado) {
        super(persona);
        this.id_empleado = empleado.id_empleado;
        this.sueldo = empleado.sueldo;
        this.cargo = empleado.cargo;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(cargo, id_empleado, sueldo);
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
        return Objects.equals(cargo, other.cargo) && id_empleado == other.id_empleado
                && Double.doubleToLongBits(sueldo) == Double.doubleToLongBits(other.sueldo);
    }

    @Override
    public String toString() {
        return "Empleado [" + super.toString() + "id_empleado=" + id_empleado + ", sueldo=" + sueldo + ", cargo=" + cargo + "]";
    }
}
