package TrabajoFinal;

import java.util.Objects;

public class Item extends Producto {
    private int id_item;
    private int cantidad;

    public Item() {
        super();
    }

    public Item(int id_item, int cantidad) {
        super();
        this.id_item = id_item;
        this.cantidad = cantidad;
    }

    public Item(Producto producto, Item item) {
        super(producto);
        this.id_item = item.id_item;
        this.cantidad = item.cantidad;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(cantidad, id_item);
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
        Item other = (Item) obj;
        return cantidad == other.cantidad && id_item == other.id_item;
    }
    
}
