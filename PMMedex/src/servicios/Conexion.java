package servicios;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {

    Connection conectar = null;
//    Connection conectar2 = null;

    public Connection conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            conectar = DriverManager.getConnection("jdbc:mysql://localhost/almaiz_db", "root", "mauricio");
            conectar = DriverManager.getConnection("jdbc:mysql://localhost/ip_medex", "root", "mauricio");
//            JOptionPane.showMessageDialog(null, conectar+"exito");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return conectar;
    }

    public void desconectar() {
        conectar = null;
//    System.out.println("Desconexion a base de datos listo...");
    }

}
