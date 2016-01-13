package servicios;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Mauricio
 */
public class Patietns {

    Conexion cc = new Conexion();
    Connection cn = cc.conexion();
    private String TypeIdent;
    private String IdPatient;
    private String Nombre;
    private String Apellido;
    private String id;
    private String opc;
    private final String logo = "/Report/logo.png";
    DefaultTableModel modelo = null;
    String sql = "";

    public Patietns(String TypeIdent, String IdPatient, String Nombre, String Apellido, String id, String opc) {
        this.TypeIdent = TypeIdent;
        this.IdPatient = IdPatient;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.id = id;
        this.opc = opc;
    }

    public Patietns() {

    }

    public Patietns(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpc() {
        return opc;
    }

    public void setOpc(String opc) {
        this.opc = opc;
    }

    public String getTypeIdent() {
        return TypeIdent;
    }

    public void setTypeIdent(String TypeIdent) {
        this.TypeIdent = TypeIdent;
    }

    public String getIdPatient() {
        return IdPatient;
    }

    public void setIdPatient(String IdPatient) {
        this.IdPatient = IdPatient;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public boolean addPatients() {
        try {
            sql = "SELECT * FROM pacientes WHERE identificacion = '" + getIdPatient() + "'";
            PreparedStatement stm = cn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return false;
            }
            if (getOpc().equals("create")) {
                sql = "INSERT INTO pacientes (tipo_identificacion, identificacion, nombre, apellidos) "
                        + " VALUES (?, ?, ?, ?)";
            } else {
                sql = "UPDATE  pacientes SET  tipo_identificacion = ?, identificacion = ? , nombre = ?,  apellidos = ?"
                        + " WHERE id_paciente = ?";
            }
            PreparedStatement guardarStmt = cn.prepareStatement(sql);
            guardarStmt.setString(1, getTypeIdent());
            guardarStmt.setString(2, getIdPatient());
            guardarStmt.setString(3, getNombre());
            guardarStmt.setString(4, getApellido());
            if (!getOpc().equals("create")) {
                guardarStmt.setInt(5, Integer.parseInt(getId()));
            }
            guardarStmt.execute();
            return true;
        } catch (SQLException | NumberFormatException e) {
            System.out.println("error: " + e);
            return false;
        }
    }

    public DefaultTableModel cargarPatients(String dato) {
        try {
            if (dato.equals("")) {
                sql = "SELECT * FROM pacientes";
            } else {
                sql = "SELECT * FROM pacientes WHERE identificacion LIKE '%" + dato + "%' OR nombre LIKE '%" + dato + "%' OR apellidos LIKE '%" + dato + "%' ";
            }
            String Titulos[] = {"Tipo Documento", "Documento", "Nombres", "Apellidos"};
            modelo = new DefaultTableModel(null, Titulos) {
                @Override
                public boolean isCellEditable(int row, int column) {//para evitar que las celdas sean editables
                    return false;
                }
            };
            JTable jt = new JTable(modelo);
            String fila[] = new String[4];
            PreparedStatement stm = cn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                fila[0] = rs.getString("tipo_identificacion");
                fila[1] = rs.getString("identificacion");
                fila[2] = rs.getString("nombre");
                fila[3] = rs.getString("apellidos");
                modelo.addRow(fila);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al extraer los datos de la tabla " + e, "Error", JOptionPane.ERROR_MESSAGE);
            return modelo;
        }
    }

    public DefaultTableModel cargarControlPatients(String dato, String model) {
        if (dato.equals("")) {
            sql = "SELECT * FROM control_paciente cp"
                    + " INNER JOIN pacientes p ON p.id_paciente = cp.id_paciente WHERE modelo = " + Integer.parseInt(model) + " ";
        } else {
            sql = "SELECT * FROM control_paciente cp"
                    + " INNER JOIN pacientes p ON p.id_paciente = cp.id_paciente WHERE ( p.identificacion LIKE '%" + dato + "%' OR p.nombre LIKE '%" + dato + "%' OR  p.apellidos LIKE '%" + dato + "%' ) AND  modelo = " + Integer.parseInt(model) + " ";

        }
        try {
            if (model.equals("1")) {
                String Titulos[] = {"Documento", "Nombres", "Apellidos", "Fecha", "Hora Admisión", "Hora Dispensación"};
                modelo = new DefaultTableModel(null, Titulos) {
                    @Override
                    public boolean isCellEditable(int row, int column) {//para evitar que las celdas sean editables
                        return false;
                    }
                };
            } else {
                String Titulos[] = {"Documento", "Nombres", "Apellidos", "Fecha", "Hora Admisión", "Hora Medico", "Hora Infusión"};
                modelo = new DefaultTableModel(null, Titulos) {
                    @Override
                    public boolean isCellEditable(int row, int column) {//para evitar que las celdas sean editables
                        return false;
                    }
                };
            }

            JTable jt = new JTable(modelo);
            String fila[] = new String[8];
            PreparedStatement stm = cn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                fila[0] = rs.getString("p.identificacion");
                fila[1] = rs.getString("p.nombre");
                fila[2] = rs.getString("p.apellidos");
                fila[3] = rs.getString("cp.fecha_llegada");
                if (model.equals("1")) {
                    fila[4] = rs.getString("cp.hora_admision");
                    fila[5] = rs.getString("cp.hora_dispensacion");
                } else {
                    fila[4] = rs.getString("cp.hora_admision");
                    fila[5] = rs.getString("cp.hora_medico");
                    fila[6] = rs.getString("cp.hora_infusion");
                }

                modelo.addRow(fila);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al extraer los datos de la tabla " + e, "Error", JOptionPane.ERROR_MESSAGE);
            return modelo;
        }
    }

    public boolean deletePatient() {
        try {
            sql = "DELETE FROM pacientes WHERE id_paciente = ?";
            PreparedStatement guardarStmt = cn.prepareStatement(sql);
            guardarStmt.setInt(1, Integer.parseInt(getId()));
            guardarStmt.execute();
            return true;
        } catch (SQLException | NumberFormatException e) {
            return false;
        }
    }

    public boolean guardarfoto(String id, String foto) throws IOException {
        FileInputStream fis;
        try {
            File file = new File(foto);
            fis = new FileInputStream(file);
            sql = "UPDATE  pacientes SET  foto = ? WHERE identificacion = ?";
            try (PreparedStatement guardarStmt = cn.prepareStatement(sql)) {
                guardarStmt.setBinaryStream(1, fis, (int) file.length());
                guardarStmt.setString(2, id);
                guardarStmt.execute();
            }
            fis.close();
            try {
                if (file.delete()) {
                    System.out.println("borrado");
                }
            } catch (Exception e) {
                System.out.println("error " + e);
            }
            return true;
        } catch (FileNotFoundException | SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public void verReporte(String id) {
        try {
            JasperDesign jd = JRXmlLoader.load("src/Report/report1.jrxml");
            //parametros de entrada
            Map parametros = new HashMap();
            //  parametros.clear();
            parametros.put("logo", this.getClass().getResourceAsStream(logo));
            parametros.put("doc", id);
            //fin parametros de entrada
            JasperReport jasperRep = JasperCompileManager.compileReport(jd);
            JasperPrint JasPrint = JasperFillManager.fillReport(jasperRep, parametros, cn);
            JasperViewer jv = new JasperViewer(JasPrint, false);
            jv.setVisible(true);
            jv.setTitle("Reporte Paciente");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //    public void printFinger(String id_document) throws IOException {
//        Image data;        
//        try {
//            sql = "SELECT * FROM pacientes WHERE identificacion = '" + id_document + "'";
//            PreparedStatement stm = cn.prepareStatement(sql);
//            try (ResultSet rs = stm.executeQuery()) {
//                if (rs.next()) {
//                    //se lee la cadena de bytes de la base de datos
//                    byte[] b = rs.getBytes("foto");
//                    String document = rs.getString("identificacion");
//                    // esta cadena de bytes sera convertida en una imagen
//                    data = ConvertirImagen(b);
//                    Icon icon = new ImageIcon(data);
//                    pf.lblfoto.setIcon(icon);
//                    pf.lblDocumentAddFinger.setText(document);
//                    pf.setLocationRelativeTo(null);
//                    pf.setVisible(true);
//                }
//                rs.close();
//            }
//        } catch (SQLException | NumberFormatException e) {
//            System.out.println("error:" + e);
//        }
//    }
    //metodo que dada una cadena de bytes la convierte en una imagen con extension jpeg
//    private Image ConvertirImagen(byte[] bytes) throws IOException {
//        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
//        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
//        ImageReader reader = (ImageReader) readers.next();
//        Object source = bis; // File or InputStream
//        ImageInputStream iis = ImageIO.createImageInputStream(source);
//        reader.setInput(iis, true);
//        ImageReadParam param = reader.getDefaultReadParam();
//        return reader.read(0, param);
//    }
}
