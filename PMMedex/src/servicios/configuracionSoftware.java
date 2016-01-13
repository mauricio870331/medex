package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 *
 * @author Mauricio
 */
public class configuracionSoftware {
    Conexion cc = new Conexion();
    Connection cn = cc.conexion();
    private String id_programa;
    private String punto;   
    private int opcion;

    public configuracionSoftware(String id_programa, String punto, int opcion) {
        this.id_programa = id_programa;
        this.punto = punto;  
        this.opcion = opcion;
    }

    public String getId_programa() {
        return id_programa;
    }

    public void setId_programa(String id_programa) {
        this.id_programa = id_programa;
    }

    public String getPunto() {
        return punto;
    }

    public void setPunto(String punto) {
        this.punto = punto;
    }
  

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }
    
    public boolean addConfig() {
        try {
            String sql = "INSERT INTO config_software (id_software, tipo_atencion, modelo) "
                + " VALUES (?, ?, ?)";
        PreparedStatement guardarStmt = cn.prepareStatement(sql);
        guardarStmt.setString(1, getId_programa());
        guardarStmt.setString(2, getPunto());
        guardarStmt.setInt(3, getOpcion());
        guardarStmt.execute();
        return true;
        } catch (SQLException | NumberFormatException e) {
            System.out.println("error: "+e);
            return false;
        }
    }
}
