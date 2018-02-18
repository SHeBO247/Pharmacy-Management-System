package pharmacy.admin;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pharmacy.database.SQLConnection;

/**
 * FXML Controller class
 *
 * @author SHeBO
 */
public class AdminFXMLController implements Initializable {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label username;
    
    @FXML
    private JFXTextField user_id;
    @FXML
    private JFXTextField user_fullname;
    @FXML
    private JFXTextField user_address;
    @FXML
    private JFXTextField user_phone;
    @FXML
    private JFXTextField user_salary;
    
    // GLOBALS
    Connection connection;
    ResultSet resultSet;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = SQLConnection.SQLConnector();
    }
    
    public void SetUser(String user){
        username.setText(user);
        
        String sql = "select * from users where user_name='"+user+"'";
        
        try {
            resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()){
                user_id.setText(resultSet.getString("user_id"));
                user_fullname.setText(resultSet.getString("user_fullname"));
                user_address.setText(resultSet.getString("user_address"));
                user_phone.setText(String.valueOf(resultSet.getString("user_phone")));
                user_salary.setText(String.valueOf(resultSet.getInt("user_salary")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void logOut(){
        SetUser("");
        
        try {
            rootPane.getScene().getWindow().hide();
            
            Parent root = FXMLLoader.load(getClass().getResource("/pharmacy/login/LoginFXML.fxml"));
            
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openUsers(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pharmacy/user/UserFXML.fxml"));
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            
            stage.setTitle("Users Panel");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openDrugs(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pharmacy/drug/DrugFXML.fxml"));
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            
            stage.setTitle("Drugs Panel");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openSuppliers(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pharmacy/supplier/SupplierFXML.fxml"));
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            
            stage.setTitle("Suppliers Panel");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openInfos(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pharmacy/info/InfoFXML.fxml"));
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            
            stage.setTitle("Information Panel");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
