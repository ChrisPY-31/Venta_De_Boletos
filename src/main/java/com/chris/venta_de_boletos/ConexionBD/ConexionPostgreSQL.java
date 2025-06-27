package com.chris.venta_de_boletos.ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionPostgreSQL {
    private Connection connection;
    private String usuario = "postgres"; // Usuario de PostgreSQL
    private String password = "Chripeya01."; // Cambia a la contraseña de tu usuario de PostgreSQL
    private String servidor = "localhost";
    private String puerto = "5433"; // Puerto predeterminado de PostgreSQL
    private String nombreBD = "VentaBoletos";

    // Concatenar la URL de conexión para PostgreSQL
    private String url = "jdbc:postgresql://" + servidor + ":" + puerto + "/" + nombreBD;
    private String driver = "org.postgresql.Driver"; // Driver de PostgreSQL

    public ConexionPostgreSQL() {
        try {
            // Cargar el driver de PostgreSQL
            Class.forName(driver);

            // Crear la conexión y asignarla a la variable connection
            connection = DriverManager.getConnection(url, usuario, password);

            // Verificar si la conexión es exitosa
            if (connection != null) {
                System.out.println("Conexión exitosa a PostgreSQL");
            }

        } catch (Exception e) {
            System.out.println("Error al conectar con la base de datos PostgreSQL");
            System.out.println("Error: " + e.getMessage());
            System.out.println("Detalle del error: ");
            e.printStackTrace();
        }

    }
        public Connection getConnection() {
            return connection;
        }

}
