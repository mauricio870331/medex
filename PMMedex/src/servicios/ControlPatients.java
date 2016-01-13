package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mauricio
 */
public class ControlPatients {

    Conexion cc = new Conexion();
    Connection cn = cc.conexion();
    Leer le = new Leer();
    private int id_paciente;
    private String date;
    private String time;
    private String tipo_atencion;
    private int model;
    PreparedStatement guardarStmt;

    public ControlPatients(int id_paciente, String date, String time, String tipo_atencion, int model) {
        this.id_paciente = id_paciente;
        this.date = date;
        this.time = time;
        this.tipo_atencion = tipo_atencion;
        this.model = model;
    }

    public ControlPatients() {

    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTipo_atencion() {
        return tipo_atencion;
    }

    public void setTipo_atencion(String tipo_atencion) {
        this.tipo_atencion = tipo_atencion;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public String createControl() {
        String sqlCP = "";
        String sqlCPU = "";
        String result = "0";
        try {
            sqlCP = "SELECT * FROM control_paciente WHERE  id_paciente = " + getId_paciente()
                    + " AND fecha_llegada = '" + getDate() + "' AND modelo = " + getModel() + " ORDER BY id_control DESC";
            PreparedStatement stmp = cn.prepareStatement(sqlCP);
            ResultSet rsp = stmp.executeQuery();
            if (rsp.next()) {               
                switch (getModel()) {
                    case 1:
                        if (rsp.getString("dispensacion") == null && !getTipo_atencion().equals("Admisión")) {
                            sqlCPU = "UPDATE control_paciente SET dispensacion = ?, hora_dispensacion = ? WHERE id_control = ?";
                            guardarStmt = cn.prepareStatement(sqlCPU);
                            guardarStmt.setString(1, getTipo_atencion());
                            guardarStmt.setString(2, getTime());
                            guardarStmt.setInt(3, rsp.getInt("id_control"));
                            guardarStmt.execute();
                            result = "2"; 
                        } else {
                            result = "1";
                        }
                        break;
                    case 2:
                        if (rsp.getString("infusion") == null && !getTipo_atencion().equals("Admisión")) {
                            switch (getTipo_atencion()) {
                                case "Medico":
                                    sqlCPU = "UPDATE control_paciente SET medico = ?, hora_medico = ? WHERE id_control = ?";
                                    guardarStmt = cn.prepareStatement(sqlCPU);
                                    guardarStmt.setString(1, getTipo_atencion());
                                    guardarStmt.setString(2, getTime());
                                    guardarStmt.setInt(3, rsp.getInt("id_control"));
                                    guardarStmt.execute();
                                    break;
                                case "Infusión":
                                    sqlCPU = "UPDATE control_paciente SET infusion = ?, hora_infusion = ? WHERE id_control = ?";
                                    guardarStmt = cn.prepareStatement(sqlCPU);
                                    guardarStmt.setString(1, getTipo_atencion());
                                    guardarStmt.setString(2, getTime());
                                    guardarStmt.setInt(3, rsp.getInt("id_control"));
                                    guardarStmt.execute();
                                    break;
                            }
                           result = "2"; 
                        } else {
                           result = "1";
                        }
                        break;
                }
            } else {                
                String sql = "INSERT INTO control_paciente (id_paciente, fecha_llegada, hora_admision, admision, modelo) "
                        + " VALUES (?, ?, ?, ?, ?)";
                guardarStmt = cn.prepareStatement(sql);
                guardarStmt.setInt(1, getId_paciente());
                guardarStmt.setString(2, getDate());
                guardarStmt.setString(3, getTime());
                guardarStmt.setString(4, getTipo_atencion());
                guardarStmt.setInt(5, getModel());
                guardarStmt.execute();
                result = "2";
            }
            return result;
        } catch (Exception e) {
            System.out.println("error: " + e);
            return result;
        }
    }

    public boolean getModelAndAtention() {
        try {
            String sqlCS = "SELECT * FROM config_software WHERE id_software = '" + le.leer() + "'";
            PreparedStatement stm = cn.prepareStatement(sqlCS);
            ResultSet rs = stm.executeQuery();
            if (rs.next() && rs.getRow() != 0) {                
                setTipo_atencion(rs.getString("tipo_atencion"));
                return true;
            }else{            
            return false;
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(ControlPatients.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
