package pharmacy.drug;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pharmacy.database.SQLConnection;
import pharmacy.notifications.PharmacyNotification;

public class DrugFXMLController implements Initializable {

    @FXML
    private JFXTextField drug_code;
    @FXML
    private JFXTextField drug_name;
    @FXML
    private JFXTextField drug_amount;
    @FXML
    private JFXTextField drug_tabs;
    @FXML
    private JFXTextField drug_retail_price;
    @FXML
    private JFXTextField drug_total_price;
    @FXML
    private DatePicker drug_expire_date;
    @FXML
    private JFXComboBox<String> drug_category;
    
    // Table
    @FXML
    private TableView<DrugData> drugTableData;
    @FXML
    private TableColumn<DrugData, String> drug_code_col;
    @FXML
    private TableColumn<DrugData, String> drug_name_col;
    @FXML
    private TableColumn<DrugData, Integer> drug_amount_col;
    @FXML
    private TableColumn<DrugData, Integer> drug_tabs_col;
    @FXML
    private TableColumn<DrugData, Integer> drug_retail_price_col;
    @FXML
    private TableColumn<DrugData, Integer> drug_total_price_col;
    @FXML
    private TableColumn<DrugData, String> drug_category_col;
    @FXML
    private TableColumn<DrugData, String> drug_expire_date_col;
    
    DrugDao drugDao = new DrugDao();
    
    ObservableList<String> categories = FXCollections.observableArrayList("Headache", "Stomache");
    ObservableList<DrugData> drugData = FXCollections.observableArrayList();
    
    String date;
    
    Connection connection;
    ResultSet resultSet;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
        // Connect Database
        connection = SQLConnection.SQLConnector();
        
        // load categories
        //loadCategoryData();
        
        drug_category.setItems(categories);
        loadDrugData();
        
        drugTableData.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                drug_code.setText(drugTableData.getSelectionModel().getSelectedItem().getDRUG_CODE());
                drug_name.setText(drugTableData.getSelectionModel().getSelectedItem().getDRUG_NAME());
                drug_amount.setText(drugTableData.getSelectionModel().getSelectedItem().getDRUG_AMOUNT() + "");
                drug_tabs.setText(drugTableData.getSelectionModel().getSelectedItem().getDRUG_TABS() + "");
                drug_retail_price.setText(drugTableData.getSelectionModel().getSelectedItem().getDRUG_RETAIL_PRICE() + "");
                drug_total_price.setText(drugTableData.getSelectionModel().getSelectedItem().getDRUG_TOTAL_PRICE() + "");
                drug_category.setValue(drugTableData.getSelectionModel().getSelectedItem().getDRUG_CATEGORY());
                
                //DateTimeFormatter formator = DateTimeFormatter.ofPattern("");
                String date = drugTableData.getSelectionModel().getSelectedItem().getDRUG_EXPIRE_DATE();
                drug_expire_date.setValue(LocalDate.parse(date));
                
                System.out.println(LocalDate.parse(date));
                
                
            }
        });
    }
    
    public void loadCategoryData(){
        // Fill Drug combo with data
        String sql = "select drug_category from drugs";
        
        try {
            resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()){
                categories.add(resultSet.getString("drug_category"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DrugFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void AddDrug(ActionEvent event){
        LocalDate ld = drug_expire_date.getValue();
        date = ld.toString();
        try {
            drugDao.AddDrug(drug_code.getText(), drug_name.getText(),
                Integer.parseInt(drug_amount.getText()), Integer.parseInt(drug_tabs.getText()), Integer.parseInt(drug_retail_price.getText()), Integer.parseInt(drug_total_price.getText()),
                drug_category.getValue().toString(),date);
            
            new PharmacyNotification().addNotification("Drug");
            
            loadDrugData();
            
        } catch (Exception e) {
            System.out.println("Not Added");
        }
    }
    
    public void EditDrug(ActionEvent event){
        LocalDate ld = drug_expire_date.getValue();
        date = ld.toString();
        try {
            drugDao.EditDrug(drug_code.getText(), drug_name.getText(),
                Integer.parseInt(drug_amount.getText()), Integer.parseInt(drug_tabs.getText()), Integer.parseInt(drug_retail_price.getText()), Integer.parseInt(drug_total_price.getText()),
                drug_category.getValue().toString(), date);
            
            new PharmacyNotification().editNotification("Drug");
            
            loadDrugData();
        } catch (Exception e) {
            System.out.println("Not Updated");
        }
    }
    
    public void DeleteDrug(ActionEvent event){
        try {
            drugDao.DeleteDrug(drug_code.getText());
            
            new PharmacyNotification().deleteNotification("Drug");
            
            loadDrugData();
        } catch (Exception e) {
            System.out.println("Not Deleted");
        }
    }
    
    public void Clear(){
        drug_code.setText("");
        drug_name.setText("");
        drug_amount.setText("");
        drug_tabs.setText("");
        drug_retail_price.setText("");
        drug_total_price.setText("");
        drug_category.setValue("");
        drug_expire_date.setValue(null);
    }
    
    public void loadDrugData(){
        drugData = drugDao.loadDrugData();
        drug_code_col.setCellValueFactory(new PropertyValueFactory("DRUG_CODE"));
        drug_name_col.setCellValueFactory(new PropertyValueFactory("DRUG_NAME"));
        drug_amount_col.setCellValueFactory(new PropertyValueFactory("DRUG_AMOUNT"));
        drug_tabs_col.setCellValueFactory(new PropertyValueFactory("DRUG_TABS"));
        drug_retail_price_col.setCellValueFactory(new PropertyValueFactory("DRUG_RETAIL_PRICE"));
        drug_total_price_col.setCellValueFactory(new PropertyValueFactory("DRUG_TOTAL_PRICE"));
        drug_category_col.setCellValueFactory(new PropertyValueFactory("DRUG_CATEGORY"));
        drug_expire_date_col.setCellValueFactory(new PropertyValueFactory("DRUG_EXPIRE_DATE"));
        
        drugTableData.setItems(null);
        drugTableData.setItems(drugData);
    }  
    
}
