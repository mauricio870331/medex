package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Mauricio
 */
public class configuracionSoftware {
    Conexion cc = new Conexion();
    Connection cn = cc.conexion();
    private String id_programa;
    private String punto;
    private String direccion;
    private String programa;

    public configuracionSoftware(String id_programa, String punto, String direccion, String programa) {
        this.id_programa = id_programa;
        this.punto = punto;
        this.direccion = direccion;
        this.programa = programa;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }
    
    public boolean addConfig() throws SQLException {
        String sql = "INSERT INTO config_program (id_software, punto, direccion, programa) "
                + " VALUES (?, ?, ?, ?)";
        PreparedStatement guardarStmt = cn.prepareStatement(sql);
        guardarStmt.setString(1, getId_programa());
        guardarStmt.setString(2, getPunto());
        guardarStmt.setString(3, getDireccion());
        guardarStmt.setString(4, getPrograma());
        return guardarStmt.execute();
    }
}
