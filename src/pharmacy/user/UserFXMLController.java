package pharmacy.user;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pharmacy.notifications.PharmacyNotification;

public class UserFXMLController implements Initializable {

    @FXML
    private JFXTextField user_id;
    @FXML
    private JFXTextField user_name;
    @FXML
    private JFXTextField user_password;
    @FXML
    private JFXTextField user_fullname;
    @FXML
    private JFXTextField user_phone;
    @FXML
    private JFXTextField user_salary;
    @FXML
    private JFXTextField user_address;
    @FXML
    private JFXComboBox<String> user_type;
    
    // Table
    @FXML
    private TableView<UserData> tableData;
    @FXML
    private TableColumn<UserData, String> user_id_col;
    @FXML
    private TableColumn<UserData, String> user_name_col;
    @FXML
    private TableColumn<UserData, String> user_password_col;
    @FXML
    private TableColumn<UserData, String> user_fullname_col;
    @FXML
    private TableColumn<UserData, Integer> user_phone_col;
    @FXML
    private TableColumn<UserData, String> user_address_col;
    @FXML
    private TableColumn<UserData, Integer> user_salary_col;
    @FXML
    private TableColumn<UserData, String> user_type_col;
    
    private String type;
    
    UserDao userDao = new UserDao();
    
    ObservableList<String> comboList = FXCollections.observableArrayList("Admin", "User");
    ObservableList<UserData> userData = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user_type.setItems(comboList);
        loadUserData();
        
        tableData.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                user_id.setText(tableData.getSelectionModel().getSelectedItem().getUSER_ID());
                user_fullname.setText(tableData.getSelectionModel().getSelectedItem().getUSER_FULLNAME());
                user_address.setText(tableData.getSelectionModel().getSelectedItem().getUSER_ADDRESS());
                user_phone.setText(tableData.getSelectionModel().getSelectedItem().getUSER_PHONE() + "");
                user_salary.setText(tableData.getSelectionModel().getSelectedItem().getUSER_SALARY() + "");
                user_type.setValue(tableData.getSelectionModel().getSelectedItem().getUSER_TYPE());
                user_name.setText(tableData.getSelectionModel().getSelectedItem().getUSER_NAME());
                user_password.setText(tableData.getSelectionModel().getSelectedItem().getUSER_PASSWORD());
            }
        });
        
    }    
    
    public void AddUser(ActionEvent event){
        try {
            userDao.AddUser(user_id.getText(), user_fullname.getText(), user_name.getText(),
                user_password.getText(), user_address.getText(), Integer.parseInt(user_phone.getText()),
                user_type.getValue(),Integer.parseInt(user_salary.getText()));
            
            new PharmacyNotification().addNotification("User");
            
            loadUserData();
            
        } catch (Exception e) {
            System.out.println("Not Added");
        }
    }
    
    public void EditUser(ActionEvent event){
        try {
            userDao.EditUser(user_id.getText(), user_fullname.getText(), user_name.getText(),
                user_password.getText(), user_address.getText(), Integer.parseInt(user_phone.getText()),
                user_type.getValue(), Integer.parseInt(user_salary.getText()));
            
            new PharmacyNotification().editNotification("User");
            loadUserData();
        } catch (Exception e) {
            System.out.println("Not Updated");
        }
    }
    
    public void DeleteUser(ActionEvent event){
        try {
            userDao.DeleteUser(user_id.getText());
            
            new PharmacyNotification().deleteNotification("User");
            loadUserData();
        } catch (Exception e) {
            System.out.println("Not Deleted");
        }
    }
    
    public void Clear(){
        user_id.setText("");
        user_name.setText("");
        user_password.setText("");
        user_fullname.setText("");
        user_address.setText("");
        user_salary.setText("");
        user_phone.setText("");
    }
    
    public void loadUserData(){
        userData = userDao.loadUserData();
        user_id_col.setCellValueFactory(new PropertyValueFactory("USER_ID"));
        user_name_col.setCellValueFactory(new PropertyValueFactory("USER_NAME"));
        user_password_col.setCellValueFactory(new PropertyValueFactory("USER_PASSWORD"));
        user_fullname_col.setCellValueFactory(new PropertyValueFactory("USER_FULLNAME"));
        user_phone_col.setCellValueFactory(new PropertyValueFactory("USER_PHONE"));
        user_address_col.setCellValueFactory(new PropertyValueFactory("USER_ADDRESS"));
        user_type_col.setCellValueFactory(new PropertyValueFactory("USER_TYPE"));
        user_salary_col.setCellValueFactory(new PropertyValueFactory("USER_SALARY"));
        
        tableData.setItems(null);
        tableData.setItems(userData);
    }
    
}
