package TrabajoFinal;

public enum Cargo {
    Empleado("Empleado"),
    Admin("Admin");

    private String value;

    Cargo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Cargo fromString(String value) {
        for (Cargo cargo : Cargo.values()) {
            if (cargo.value.equalsIgnoreCase(value)) {
                return cargo;
            }
        }
        return null;
    }
}

