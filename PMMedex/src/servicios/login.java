/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Mauricio
 */
public class login {

    Conexion cc = new Conexion();
    Connection cn = cc.conexion();
    private String user;
    private String password;
    private String nombre;
    private ArrayList<String> permisos;
    ArrayList<String> tmp = new ArrayList<>();

    public login() {
   
    }

    public login(ArrayList<String> permisos) {
        this.permisos = permisos;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getPermisos() {
        return permisos;
    }

    
    public void limpiarPermisos(){
      this.permisos.clear();
    }
    
    public void setPermisos(ArrayList<String> permisos) {
        this.permisos = permisos;
    }

    public boolean verificarUser(String user, String password) throws SQLException {
        String sqlPuesto = "SELECT * FROM usuarios WHERE user = '" + user + "' AND password = '" + password + "'";
        PreparedStatement stm = cn.prepareStatement(sqlPuesto);
        ResultSet rs = stm.executeQuery();
        if (rs != null && rs.next()) {
            setUser(rs.getString("user"));
            setPassword(rs.getString("password"));
            setNombre(rs.getString("nombre"));
            int id_user = rs.getInt("id_user");
            String sqlPermisos = "SELECT * FROM usuarios_permisos WHERE id_user = " + id_user + "";
            PreparedStatement stmp = cn.prepareStatement(sqlPermisos);
            ResultSet rsp = stmp.executeQuery();
            while (rsp.next()) {
                String permiso = Integer.toString(rsp.getInt("id_permiso"));
                tmp.add(permiso);
            }
            setPermisos(tmp);   
            

            return true;
        }
        return false;
    }

}
