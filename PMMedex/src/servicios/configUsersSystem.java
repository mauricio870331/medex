package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mauricio
 */
public class configUsersSystem {

    Conexion cc = new Conexion();
    Connection cn = cc.conexion();
    private String pass;
    private String user;
    private String nombre;
    private String Opc;
    private String documento;
    private ArrayList<String> permisos;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    String sql;
    DefaultTableModel modelo = null;
    PreparedStatement stm;
    ResultSet rs;

    public configUsersSystem() {

    }

    public configUsersSystem(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public configUsersSystem(String pass, String user, String nombre, ArrayList<String> permisos, String Opc, String documento, int id) {
        this.pass = pass;
        this.user = user;
        this.nombre = nombre;
        this.permisos = permisos;
        this.documento = documento;
        this.Opc = Opc;
        this.id = id;
    }

    public String getOpc() {
        return Opc;
    }

    public void setOpc(String Opc) {
        this.Opc = Opc;
    }

    public ArrayList<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(ArrayList<String> permisos) {
        this.permisos = permisos;
    }

    public configUsersSystem(String pass, String user, String nombre) {
        this.pass = pass;
        this.user = user;
        this.nombre = nombre;
        this.permisos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean addConfig() {       
        try {
            if (getOpc().equals("create")) {
                String sqV = "SELECT documento FROM usuarios WHERE documento = '" + getDocumento() + "'";
                stm = cn.prepareStatement(sqV);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return false;
                }
                sql = "INSERT INTO usuarios (nombre, user, password, documento) "
                        + " VALUES (?, ?, ?, ?)";
            } else {
                sql = "UPDATE  usuarios SET  nombre = ?, user = ? , password = ?, documento = ?"
                        + " WHERE id_user = ?";
            }
            PreparedStatement guardarStmt = cn.prepareStatement(sql);
            guardarStmt.setString(1, getNombre());
            guardarStmt.setString(2, getUser());
            guardarStmt.setString(3, getPass());
            guardarStmt.setString(4, getDocumento());
            if (!getOpc().equals("create")) {
                guardarStmt.setInt(5, getId());
            }
            guardarStmt.execute();
            if (!getOpc().equals("create")) {                
                sql = "DELETE FROM usuarios_permisos WHERE id_user = ?";
                stm = cn.prepareStatement(sql);
                stm.setInt(1, getId());
                stm.execute();
            }
            if (!getPermisos().isEmpty()) {
                if (getId() == 0) {
                    String sqB = "SELECT id_user FROM usuarios ORDER BY id_user DESC LIMIT 1";
                    stm = cn.prepareStatement(sqB);
                    rs = stm.executeQuery();
                    if (rs.next()) {
                        setId(rs.getInt("id_user"));
                    }
                }
                HashSet hs = new HashSet();//para eliminar elementos repetidos en arraylist
                hs.addAll(permisos);
                permisos.clear();
                permisos.addAll(hs);
                Iterator<String> itrT = permisos.iterator();
                while (itrT.hasNext()) {
                    String id_permiso = itrT.next();
                    String sqlI = "INSERT INTO usuarios_permisos(id_user, id_permiso) "
                            + " VALUES (?, ?)";
                    PreparedStatement guarda = cn.prepareStatement(sqlI);
                    guarda.setInt(1, getId());
                    guarda.setInt(2, Integer.parseInt(id_permiso));
                    guarda.execute();
                }

            }
            return true;
        } catch (SQLException e) {
            System.out.println("error: " + e);
            return false;
        }
    }

    public DefaultTableModel cargarUsersRoot(String dato) {
        try {
            if (dato.equals("")) {
                sql = "SELECT * FROM usuarios WHERE id_user <> 1";
            } else {
                sql = "SELECT * FROM usuarios WHERE id_user <> 1 AND (documento LIKE '%" + dato + "%' OR nombre LIKE '%" + dato + "%' OR user LIKE '%" + dato + "%')";
            }
            String Titulos[] = {"Documento", "Nombre", "Usuario"};
            modelo = new DefaultTableModel(null, Titulos) {
                @Override
                public boolean isCellEditable(int row, int column) {//para evitar que las celdas sean editables
                    return false;
                }
            };
            JTable jt = new JTable(modelo);
            String fila[] = new String[4];
            stm = cn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                fila[0] = rs.getString("documento");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("user");
                modelo.addRow(fila);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al extraer los datos de la tabla " + e, "Error", JOptionPane.ERROR_MESSAGE);
            return modelo;
        }
    }

    public boolean deleteUsers() {
        try {
            sql = "DELETE FROM usuarios WHERE id_user = ?";
            PreparedStatement guardarStmt = cn.prepareStatement(sql);
            guardarStmt.setInt(1, getId());
            guardarStmt.execute();
            sql = "DELETE FROM usuarios_permisos WHERE id_user = ?";
            stm = cn.prepareStatement(sql);
            stm.setInt(1, getId());
            stm.execute();
            return true;
        } catch (SQLException | NumberFormatException e) {
            return false;
        }
    }
}
