package servicios;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {

    Connection conectar = null;

    public Connection conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection("jdbc:mysql://192.99.203.15:3306/pm_medex", "root", "Medex-2015");
//            conectar = DriverManager.getConnection("jdbc:mysql://www.db4free.net:3306/pm_medex", "mauricio", "openEHR2008");
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
