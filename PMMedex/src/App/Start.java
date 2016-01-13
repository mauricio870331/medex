package App;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
//import servicios.Encriptar;



public class Start {

    public static void main(String[] args) throws IOException, SQLException {
        try {
//            String passEnc = Encriptar.generateDigest("123456");
//            System.out.println(passEnc);
            principal p = new principal();
            p.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error: " + e);
//            System.exit(-1);
        }

    }

}
