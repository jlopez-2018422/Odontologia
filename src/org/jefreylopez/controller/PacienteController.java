package org.jefreylopez.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.jefreylopez.bean.Paciente;
import org.jefreylopez.db.Conexion;
import org.jefreylopez.system.Principal;

public class PacienteController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUARLIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;      
    private ObservableList<Paciente> listaPaciente;
    private DatePicker fNacimiento;
    private DatePicker fPV;
    @FXML private TextField txtCodigoPaciente;
    @FXML private TextField txtNombresPaciente;
    @FXML private TextField txtApellidosPaciente;
    @FXML private TextField txtSexo;
    @FXML private TextField txtDireccionPaciente;
    @FXML private TextField txtTelefonoPersonal;
    @FXML private GridPane grpFechas ;
    @FXML private TableView tblPacientes;
    @FXML private TableColumn colCodigoPaciente;
    @FXML private TableColumn colNombresPaciente;
    @FXML private TableColumn colApellidosPaciente;
    @FXML private TableColumn colSexo;
    @FXML private TableColumn colFechaNacimiento;
    @FXML private TableColumn colDireccionPaciente;
    @FXML private TableColumn colTelefonoPersonal;
    @FXML private TableColumn colFechaPrimeraVisita;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fNacimiento = new DatePicker(Locale.ENGLISH);
        fNacimiento.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fNacimiento.getCalendarView().todayButtonTextProperty().set("Today");
        fNacimiento.getCalendarView().setShowWeeks(false);
        grpFechas.add(fNacimiento,3,1);
        fPV = new DatePicker (Locale.ENGLISH);
        fPV.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fPV.getCalendarView().todayButtonTextProperty().set("Today");
        fPV.getCalendarView().setShowWeeks(false);
        grpFechas.add(fPV, 4, 2);
        fNacimiento.getStylesheets().add("/org/jefreylopez/resource/DatePicker.css");
        fPV.getStylesheets().add("/org/jefreylopez/resource/DatePicker.css");
        
    }
    
    
    public void cargarDatos(){
        tblPacientes.setItems(getPaciente());
        colCodigoPaciente.setCellValueFactory(new PropertyValueFactory<Paciente, Integer>("codigoPaciente"));
        colNombresPaciente.setCellValueFactory(new PropertyValueFactory<Paciente, String>("nombresPaciente"));
        colApellidosPaciente.setCellValueFactory(new PropertyValueFactory<Paciente, String>("apellidosPaciente"));
        colSexo.setCellValueFactory(new PropertyValueFactory<Paciente, String>("sexo"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<Paciente, Date>("fechaNacimiento"));
        colDireccionPaciente.setCellValueFactory(new PropertyValueFactory<Paciente, String>("direccionPaciente")); 
        colTelefonoPersonal.setCellValueFactory(new PropertyValueFactory<Paciente, String>("telefonoPersonal")); 
        colFechaPrimeraVisita.setCellValueFactory(new PropertyValueFactory<Paciente, Date>("fechaPrimeraVisita"));
  
    }
    
    public  ObservableList<Paciente>getPaciente(){
        ArrayList<Paciente> lista = new ArrayList<Paciente>();
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPaciente}");
           ResultSet resultado = procedimiento.executeQuery();
           while(resultado.next()){
            lista.add(new Paciente(resultado.getInt("codigoPaciente"),
                                resultado.getString("nombresPaciente"),
                                resultado.getString("apellidosPaciente"),
                                resultado.getString("sexo"),
                                resultado.getDate("fechaNacimiento"),
                                resultado.getString("direccionPaciente"),
                                resultado.getString("telefonoPersonal"),
                                resultado.getDate("fechaPrimeraVisita")));
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaPaciente = FXCollections.observableArrayList(lista);
    }
    
    @FXML
    public void nuevo(){
    switch(tipoDeOperacion){
        case NINGUNO:
            activarControles();
            btnNuevo.setText("Guardar");
            btnEliminar.setText("Cancelar");
            btnEditar.setDisable(true);
            btnReporte.setDisable(true);
            imgNuevo.setImage(new Image("/org/jefreylopez/image/guardar.png"));
            imgEliminar.setImage(new Image("/org/jefreylopez/image/cancelar.png"));
            tipoDeOperacion = operaciones.GUARDAR;
            break;
        case GUARDAR:
            guardar();
            desactivarControles();
            limpiarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            imgNuevo.setImage(new Image("/org/jefreylopez/image/nuevo.png"));
            imgNuevo.setImage(new Image("/org/jefreylopez/image/eliminar.png"));
            tipoDeOperacion = operaciones.NINGUNO;
            cargarDatos();
        break;
    }
}
    
    public void guardar(){
    Paciente registro = new Paciente();
    registro.setCodigoPaciente(Integer.parseInt(txtCodigoPaciente.getText()));
    registro.setNombresPaciente(txtNombresPaciente.getText());
    registro.setSexo(txtSexo.getText());
    registro.setFechaNacimiento(fNacimiento.getSelectedDate());
    registro.setDireccionPaciente(txtDireccionPaciente.getText());
    registro.setTelefonoPersonal(txtTelefonoPersonal.getText());
    registro.setFechaPrimeraVista(fPV.getSelectedDate());
    
    try{
    PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarPaciente(?,?,?,?,?,?,?,?)}");
            procedimiento.setInt(1,registro.getCodigoPaciente());
            procedimiento.setString(2,registro.getNombresPaciente());
            procedimiento.setString(3,registro.getApellidosPaciente());
            procedimiento.setString(4,registro.getSexo());
            procedimiento.setDate(5, new java.sql.Date(registro.getFechaNacimiento().getTime()));
            procedimiento.setString(6,registro.getDireccionPaciente());
            procedimiento.setString(7,registro.getTelefonoPersonal());
            procedimiento.setDate(8,new java.sql.Date(registro.getFechaPrimeraVista().getTime()));
            procedimiento.execute();
            listaPaciente.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
}
    
    public void desactivarControles(){
        txtCodigoPaciente.setEditable(false);
        txtNombresPaciente.setEditable(false);
        txtApellidosPaciente.setEditable(false);
        txtSexo.setEditable(false);
        txtDireccionPaciente.setEditable(false);
        txtTelefonoPersonal.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoPaciente.setEditable(true);
        txtNombresPaciente.setEditable(true);
        txtApellidosPaciente.setEditable(true);
        txtSexo.setEditable(true);
        txtDireccionPaciente.setEditable(true);
        txtTelefonoPersonal.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoPaciente.clear();
        txtNombresPaciente.clear();
        txtApellidosPaciente.clear();
        txtSexo.clear();
        txtDireccionPaciente.clear();
        txtTelefonoPersonal.clear();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
