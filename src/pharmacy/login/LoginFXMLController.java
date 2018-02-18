package pharmacy.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pharmacy.admin.AdminFXMLController;
import pharmacy.bill.BillFXMLController;
import pharmacy.database.SQLConnection;

/**
 *
 * @author SHeBO
 */
public class LoginFXMLController implements Initializable {
    
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    
    // FIELDS
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXRadioButton adminRadio;
    @FXML
    private JFXRadioButton userRadio;
    
    // GLOBALS
    String rule;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = SQLConnection.SQLConnector();
        if (connection == null)
            System.exit(0);
        
        if (SQLConnection.isDbConnected()){
            System.out.println("Connected");
        }
        else{
            System.out.println("Not Connected");
        }
        
    }
    
    public void login(){
        // Check the rule of the user
        if (adminRadio.isSelected())
            rule = "Admin";
        if (userRadio.isSelected())
            rule = "User";
        
        if (isLogin(username.getText(), password.getText(), rule)){
            if (rule.equals("Admin")){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/pharmacy/admin/AdminFXML.fxml"));
                    loader.load();
                    
                    AdminFXMLController controller = loader.getController();
                    controller.SetUser(username.getText());
                    
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("Admin Panel");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (rule.equals("User")){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/pharmacy/bill/BillFXML.fxml"));
                    loader.load();
                    
                    BillFXMLController controller = loader.getController();
                    controller.SetUser(username.getText());
                    
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("User Panel");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            username.getScene().getWindow().hide();
            
        }
        else{
            System.out.println("ERROR USERNAME Or PASSWORD");
        }
    }
    
    public void close(){
        System.exit(1);
    }
    
    public boolean isLogin(String username, String password, String type){
        String sqlSelect = "SELECT * from users where user_name=? and user_password=? and user_type=?";
        
        try {
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, type);
            
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
            
        } catch (SQLException ex) {
            return false;
        }
        
    }
    
}
