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

/**
 *
 * @author Mauricio
 */
public class login {

    Conexion cc = new Conexion();
    Connection cn = cc.conexion();
    String user, password;

    public login() {
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

    public boolean verificarUser(String user, String password) throws SQLException {
        String sqlPuesto = "SELECT * FROM usuarios WHERE user = '" + user + "' AND password = '" + password + "'";
        PreparedStatement stm = cn.prepareStatement(sqlPuesto);
        ResultSet rs = stm.executeQuery();
        if (rs != null && rs.next()) {
//            System.out.println(rs.getString("user")+" "+rs.getString("password")); 
            setUser(rs.getString("user"));
            setPassword(rs.getString("password"));
            return true;
        }
        return false;
    }

}
