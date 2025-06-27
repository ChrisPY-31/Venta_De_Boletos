package com.chris.venta_de_boletos.Model;

import com.chris.venta_de_boletos.ConexionBD.ConexionPostgreSQL;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Usuario {

    private int id;

    private String correo;

    private String contrasena;

    public Usuario(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Integer RegistroUsuario(Usuario user) {
        String SQL = "INSERT INTO usuario (correo, contrasena) VALUES (?, ?)";

        try (Connection conn = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.correo);
            pstmt.setString(2, user.contrasena);

            int filas = pstmt.executeUpdate();

            Integer idUser;

            if (filas > 0) {
                try (ResultSet resultSet = pstmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        this.id = resultSet.getInt(1);  // Solo esto
                    }

                }
                return this.id;
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar usuario", e);
        }
    }

    public Integer LoginUsuario(Usuario user) {

        String SQL = "SELECT * FROM usuario WHERE correo=? AND contrasena=?";

        try (Connection conn = new ConexionPostgreSQL().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL ,  Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, user.correo);
            pstmt.setString(2, user.contrasena);

            int filas = pstmt.executeUpdate();

            if (filas > 0) {

                try (ResultSet resultSet = pstmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        this.id = resultSet.getInt(1);
                    }
                    this.id = resultSet.getInt("id");

                }
                return this.id;
            }

            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
