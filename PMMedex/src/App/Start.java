package App;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;



public class Start {

    public static void main(String[] args) throws IOException, SQLException {
        try {
//            Conexion cc = new Conexion();
//            Connection cn = cc.conexion();
            principal p = new principal();
            p.setVisible(true);
//             JOptionPane.showMessageDialog(null, "Conexion exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error: " + e);
//            System.exit(-1);
        }

    }

}
