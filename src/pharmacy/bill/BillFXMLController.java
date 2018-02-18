package pharmacy.bill;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pharmacy.database.SQLConnection;

public class BillFXMLController implements Initializable {

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
    
    @FXML
    private Pane pane;
    
    @FXML
    private JFXTextField bill_id;
    @FXML
    private DatePicker bill_date;
    
    @FXML
    private Label sub_price;
    @FXML
    private Label bill_total_price;
    
    @FXML
    private JFXComboBox drug_name;
    @FXML
    private JFXComboBox drug_tabs;
    @FXML
    private JFXTextField drug_amount;
    @FXML
    private JFXTextField drug_retail_price;
    @FXML
    private JFXTextField drug_total_price;
    
    // Table
    @FXML
    private TableView<BillData> billTableData;
    @FXML
    private TableColumn<BillData, String> bill_id_col;
    @FXML
    private TableColumn<BillData, String> bill_date_col;
    @FXML
    private TableColumn<BillData, String> drug_name_col;
    @FXML
    private TableColumn<BillData, Integer> drug_tabs_col;
    @FXML
    private TableColumn<BillData, Integer> drug_amount_col;
    @FXML
    private TableColumn<BillData, Integer> drug_retail_price_col;
    @FXML
    private TableColumn<BillData, Integer> drug_total_price_col;
    
    // Globals
    private Connection connection;
    private ResultSet resultSet;
    PreparedStatement preparedStatement;
    
    String user_name = "";
    
    //private ObservableList<String> drug_names_list = FXCollections.observableArrayList();
    private final ObservableList<String> drug_tabs_list = FXCollections.observableArrayList("1", "2", "3", "4", "5");
    private ObservableList<BillData> table_list;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = SQLConnection.SQLConnector();
        
        drug_tabs.setItems(drug_tabs_list);
        
        //drug_names_list = getDrugNames();
        drug_name.setItems(getDrugNames());
        
        bill_id.setText(getAutoNumber("bills", "bill_id"));
        
        sub_price.setText("0");
        bill_total_price.setText("0");
        
        pane.setOpacity(0);
        
        System.out.println(user_name);
        
    }
    
    public void SetUser(String user){
        username.setText(user);
        
        user_name = username.getText();
        
        // Get User Info
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
            Logger.getLogger(BillFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addDrug(ActionEvent event){
        int totalPrice = Integer.parseInt(drug_total_price.getText()) * Integer.parseInt(drug_amount.getText());
        addBillDrug(Integer.parseInt(getAutoNumber("bills", "bill_no")), Integer.parseInt(bill_id.getText()), bill_date.getValue().toString(), drug_name.getValue().toString(),
                Integer.parseInt(drug_amount.getText()), Integer.parseInt(drug_tabs.getValue().toString()),
                Integer.parseInt(drug_retail_price.getText()), totalPrice, user_name);
        loadTableData();
        
        sub_price.setText(String.valueOf(calculatePrice(Integer.parseInt(bill_id.getText()))));
        //sub_price.setText(Integer.parseInt(sub_price.getText()) + Integer.parseInt(drug_total_price.getText()) + "");
        
    }
    
    public void deleteDrug(ActionEvent event){
        deleteBillDrug(billTableData.getSelectionModel().getSelectedItem().getBILL_NO());
        
        loadTableData();
        
        sub_price.setText(String.valueOf(calculatePrice(Integer.parseInt(bill_id.getText()))));
    }
    
    public void saveBill(ActionEvent event){
        bill_total_price.setText(sub_price.getText());
    }
    
    public void closeBill(ActionEvent event){
        FadeTransition fade = new FadeTransition(Duration.seconds(1), pane);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
    }
    
    private ObservableList<String> getDrugNames(){
        ObservableList<String> list = FXCollections.observableArrayList();
        
        String sql = "select drug_name from drugs";
        
        try {
            resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()){
                list.add(resultSet.getString("drug_name"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BillFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public void OnComboClicked(){
        String sql = "select * from drugs where drug_name='"+drug_name.getValue().toString()+"'";
        
        try {
            resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()){
                //drug_tabs.setValue(resultSet.getInt("drug_tabs"));
                drug_retail_price.setText(String.valueOf(resultSet.getInt("drug_retail_price")));
                drug_total_price.setText(String.valueOf(resultSet.getInt("drug_total_price")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BillFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void newBill(ActionEvent event){
        FadeTransition fade = new FadeTransition(Duration.seconds(1), pane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
    
    public void logOut(ActionEvent event){
        try {
            username.getScene().getWindow().hide();
            
            Parent root = FXMLLoader.load(getClass().getResource("/pharmacy/login/LoginFXML.fxml"));
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(BillFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // SQL Handler Methods
    public void addBillDrug(int bill_no, int bill_id, String bill_date, String drug_name, int drug_amount, int drug_tabs, int drug_retail_price, int drug_total_price, String user_name){
        String sql = "insert into bills (bill_no, bill_id, bill_date, drug_name, drug_amount, drug_tabs, drug_retail_price, drug_total_price, user_name) values (?,?,?,?,?,?,?,?,?)";
        
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bill_no);
            preparedStatement.setInt(2, bill_id);
            preparedStatement.setString(3, bill_date);
            preparedStatement.setString(4, drug_name);
            preparedStatement.setInt(5, drug_amount);
            preparedStatement.setInt(6, drug_tabs);
            preparedStatement.setInt(7, drug_retail_price);
            preparedStatement.setInt(8, drug_total_price);
            preparedStatement.setString(9, user_name);
            
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(BillFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void deleteBillDrug(int bill_no){
        String sql = "delete from bills where bill_no = ?";
        
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bill_no);
            
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(BillFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTableData(){
        table_list  = FXCollections.observableArrayList();
        String sql = "select * from bills where bill_id='"+bill_id.getText()+"'";
        
        try {
            resultSet = connection.createStatement().executeQuery(sql);
            while(resultSet.next()){
                table_list.add(new BillData(resultSet.getInt("bill_no"), resultSet.getInt("bill_id"), resultSet.getString("bill_date"), resultSet.getString("drug_name"),
                                        resultSet.getInt("drug_amount"), resultSet.getInt("drug_tabs"),resultSet.getInt("drug_retail_price"),
                                        resultSet.getInt("drug_total_price")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        bill_id_col.setCellValueFactory(new PropertyValueFactory("BILL_ID"));
        bill_date_col.setCellValueFactory(new PropertyValueFactory("BILL_DATE"));
        drug_name_col.setCellValueFactory(new PropertyValueFactory("DRUG_NAME"));
        drug_amount_col.setCellValueFactory(new PropertyValueFactory("DRUG_AMOUNT"));
        drug_tabs_col.setCellValueFactory(new PropertyValueFactory("DRUG_TABS"));
        drug_retail_price_col.setCellValueFactory(new PropertyValueFactory("DRUG_RETAIL_PRICE"));
        drug_total_price_col.setCellValueFactory(new PropertyValueFactory("DRUG_TOTAL_PRICE"));
        
        billTableData.setItems(null);
        billTableData.setItems(table_list);
    }
    
    public String getAutoNumber(String table_name, String column_name){
        String num = "";
        String sql = "select max(" + column_name + ")+1 as auto_num from " + table_name;
        
        try {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(sql);
            
            while(stmt.getResultSet().next()){
                num = stmt.getResultSet().getString("auto_num");
            }
            
            if (num == null || num.equals("")){
                return "1";
            }
            else{
                return num;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BillFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            return "0";
        }
    }
    
    public int calculatePrice(int bill_id){
        ObservableList<Integer> price = FXCollections.observableArrayList();
        int totalPrice = 0;
        String sql = "select drug_total_price from bills where bill_id = ?";
        
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bill_id);
            
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                //price.add(resultSet.getInt("drug_total_price"));
                totalPrice += resultSet.getInt("drug_total_price");
            }
            
            return totalPrice;
            
        } catch (SQLException ex) {
            Logger.getLogger(BillFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
}
