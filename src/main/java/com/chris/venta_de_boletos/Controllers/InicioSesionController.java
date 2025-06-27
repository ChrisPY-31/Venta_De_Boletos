package com.chris.venta_de_boletos.Controllers;

import com.chris.venta_de_boletos.Model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InicioSesionController {

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private TextField txtCorreo;


    @FXML
    void btnIniciarSesion(ActionEvent event) {
        String correo = txtCorreo.getText();
        String contrasena = txtContraseña.getText();

        Usuario usuario = new Usuario(correo, contrasena);

        Integer response = usuario.LoginUsuario(usuario);
        System.out.println(response);

    }

    public void alertas(String mensaje, boolean rol) {
        if (rol) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de formulario");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }
    }

    @FXML
    void btnRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Registro.fxml"));
            Parent root = loader.load();
            RegistroController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnIniciaSesion());
            stage.setOnCloseRequest(e -> controller.closeWindowsRegistro());

            Stage myStage = (Stage) this.txtContraseña.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(InicioSesionController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void closeWindowsInicioSesion() {
        try {
            Stage myStage = (Stage) this.txtCorreo.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(InicioSesionController.class.getName()).log(Level.SEVERE, null, e);

        }
    }
    /*
    public void navegacionComprarUsuario(Persona persona){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/CompraProductos.fxml"));
            Parent root = loader.load();
            CompraController controller = loader.getController();
            controller.idUsuario = persona.getId();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnInicio());
            stage.setOnCloseRequest(e -> controller.closeWindowsCompra());

            Stage myStage = (Stage) this.txtCorreo.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, e);

        }
    }


    */
    /*

    public void navegacionAdministrador() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Administrador.fxml"));
            Parent root = loader.load();
            AdministradorController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.closeWindowsAdministrador());

            Stage myStage = (Stage) this.txtCorreo.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    * */


    public void alertaSesion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenido");
        alert.setHeaderText(null);
        alert.setContentText("Bienvenido " + mensaje);
        alert.showAndWait();
    }

}
