package TrabajoFinal;

import java.util.Objects;

public abstract class Persona {
	protected String nombre;
	protected String direccion;
	protected String telefono;
	
	public Persona() {
		
	}
	
	public Persona(String nombre, String direccion, String telefono) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	
	public Persona(Persona persona) {
		this.nombre = persona.nombre;
		this.direccion = persona.direccion;
		this.telefono = persona.telefono;
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

	@Override
	public int hashCode() {
		return Objects.hash(direccion, nombre, telefono);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(direccion, other.direccion) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(telefono, other.telefono);
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + "]";
	}
	
    public int compare(Empleado empleado1, Empleado empleado2) {
        // Compara los sueldos de los empleados
        if (empleado1.getSueldo() < empleado2.getSueldo()) {
            return -1;
        } else if (empleado1.getSueldo() > empleado2.getSueldo()) {
            return 1;
        } else {
            return 0;
        }
    }
}
