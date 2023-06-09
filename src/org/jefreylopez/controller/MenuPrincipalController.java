package org.jefreylopez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.jefreylopez.system.Principal;

public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
     public void ventanaProgramador() {
        escenarioPrincipal.ventanaProgramador();
    }
     
    public void ventanaPacientes(){
        escenarioPrincipal.ventanaPacientes();
    } 
}
