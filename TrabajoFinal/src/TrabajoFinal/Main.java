package TrabajoFinal;

import TrabajoFinal.Empresa;
import TrabajoFinal.Persona;
import TrabajoFinal.Empleado;
import TrabajoFinal.Producto;
import TrabajoFinal.Item;
import TrabajoFinal.Cliente;
import TrabajoFinal.Factura;

import java.sql.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Main {
    
    public static void main(String[] args) {
        String usu = "MC";
        String pas = "Lolalol@12";
        String url = "jdbc:mysql://localhost:3307/trabajoProgramacion";
        Connection con = null;
        
        Set<Empresa> empresas = new LinkedHashSet<>();
        Set<Persona> personas = new LinkedHashSet<>();
        Set<Empleado> empleados = new LinkedHashSet<>();
        Set<Producto> productos = new LinkedHashSet<>();
        Set<Item> items = new LinkedHashSet<>();
        Set<Cliente> clientes = new LinkedHashSet<>();
        Set<Factura> facturas = new LinkedHashSet<>();        
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            con = (Connection) DriverManager.getConnection(url, usu, pas);
            System.out.println("Conexión establecida\n");
            
            String verEmpresa = "SELECT * FROM Empresa";
            ResultSet rs=null;
            
             Statement st=con.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
             rs=st.executeQuery(verEmpresa);
            
            while(rs.next()) {
                int id_empresa = rs.getInt("id_empresa");
                String nombre = rs.getString("nombre_empresa");
                String direccion = rs.getString("direccion_empresa");
                String telefono = rs.getString("telefono_empresa");
                
                Empresa empresa = new Empresa(id_empresa,nombre, direccion, telefono, empleados, clientes, productos, facturas);
                
                empresas.add(empresa);
                
                
            }
            
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        
        System.out.println(empresas);
        
        
        
    }
    
    
    
}
