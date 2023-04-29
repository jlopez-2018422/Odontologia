/*
Nombre: Jefrey Eduardo López Ampérez
Código Técnico: IN5AM
Carné:2018422
Fecha de creación: 5-4-2022
Fecha de modificaciones: -0-2022
 */
package org.jefreylopez.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jefreylopez.controller.MenuPrincipalController;
import org.jefreylopez.controller.PacienteController;
import org.jefreylopez.controller.ProgramadorController;


public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/jefreylopez/view/";  
    private Stage escenarioPrincipal;
    private Scene escena;
     
       
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Smile Expert Odontología");
        escenarioPrincipal.getIcons().add(new Image("/org/jefreylopez/image/Logo.jpg"));
//        Parent root = FXMLLoader.load(getClass().getResource("/org/jefreylopez/view/MenuPrincipalView.fxml"));
//        Scene escena = new Scene(root);
//        escenarioPrincipal.setScene(escena);
        menuPrincipal();
        escenarioPrincipal.show();
        
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController ventanaMenu = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",500,400);
            ventanaMenu.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController vistaProgramador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml",400,400);
            vistaProgramador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPacientes(){
        try{
            PacienteController vistaPaciente = (PacienteController)cambiarEscena("PacientesView.fxml",1100,400);
            vistaPaciente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
     public static void main(String[] args) {
        launch(args);
    }
    
     public Initializable cambiarEscena(String fxml, int ancho, int alto)throws Exception{
         
         Initializable resultado = null;
         FXMLLoader cargadorFXML = new FXMLLoader();
         InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
         cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
         cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
         escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
         escenarioPrincipal.setScene(escena);
         escenarioPrincipal.sizeToScene();
         resultado =(Initializable)cargadorFXML.getController();
         
         return resultado; 
     }

   
}
