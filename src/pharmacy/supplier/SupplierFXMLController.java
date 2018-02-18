package pharmacy.supplier;

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

public class SupplierFXMLController implements Initializable {

    @FXML
    private JFXTextField supplier_id;
    @FXML
    private JFXTextField supplier_name;
    @FXML
    private JFXTextField supplier_address;
    @FXML
    private JFXTextField supplier_phone;
    @FXML
    private JFXTextField supplier_company;
    
    // Table
    @FXML
    private TableView<SupplierData> supplierTableData;
    @FXML
    private TableColumn<SupplierData, String> supplier_id_col;
    @FXML
    private TableColumn<SupplierData, String> supplier_name_col;
    @FXML
    private TableColumn<SupplierData, String> supplier_address_col;
    @FXML
    private TableColumn<SupplierData, Integer> supplier_phone_col;
    @FXML
    private TableColumn<SupplierData, String> supplier_company_col;
    
    SupplierDao supplierDao = new SupplierDao();
    
    ObservableList<SupplierData> supplierData = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadSupplierData();
        
        supplierTableData.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                supplier_id.setText(supplierTableData.getSelectionModel().getSelectedItem().getSUPPLIER_ID());
                supplier_name.setText(supplierTableData.getSelectionModel().getSelectedItem().getSUPPLIER_NAME());
                supplier_address.setText(supplierTableData.getSelectionModel().getSelectedItem().getSUPPLIER_ADDRESS());
                supplier_phone.setText(supplierTableData.getSelectionModel().getSelectedItem().getSUPPLIER_PHONE() + "");
                supplier_company.setText(supplierTableData.getSelectionModel().getSelectedItem().getSUPPLIER_COMPANY());
            }
        });
    }
    
    public void AddSupplier(ActionEvent event){
        try {
            supplierDao.AddSupplier(supplier_id.getText(), supplier_name.getText(), supplier_address.getText(),
                Integer.parseInt(supplier_phone.getText()), supplier_company.getText());
            
            System.out.println("Added");
            
            loadSupplierData();
            
        } catch (Exception e) {
            System.out.println("Not Added");
        }
    }
    
    public void EditSupplier(ActionEvent event){
        try {
            supplierDao.EditSupplier(supplier_id.getText(), supplier_name.getText(), supplier_address.getText(),
                Integer.parseInt(supplier_phone.getText()), supplier_company.getText());
            
            System.out.println("Updated");
            loadSupplierData();
        } catch (Exception e) {
            System.out.println("Not Updated");
        }
    }
    
    public void DeleteSupplier(ActionEvent event){
        try {
            supplierDao.DeleteSupplier(supplier_id.getText());
            
            System.out.println("Deleted");
            loadSupplierData();
        } catch (Exception e) {
            System.out.println("Not Deleted");
        }
    }
    
    public void Clear(){
        supplier_id.setText("");
        supplier_name.setText("");
        supplier_address.setText("");
        supplier_phone.setText("");
        supplier_company.setText("");
    }
    
    public void loadSupplierData(){
        supplierData = supplierDao.loadSupplierData();
        supplier_id_col.setCellValueFactory(new PropertyValueFactory("SUPPLIER_ID"));
        supplier_name_col.setCellValueFactory(new PropertyValueFactory("SUPPLIER_NAME"));
        supplier_address_col.setCellValueFactory(new PropertyValueFactory("SUPPLIER_ADDRESS"));
        supplier_phone_col.setCellValueFactory(new PropertyValueFactory("SUPPLIER_PHONE"));
        supplier_company_col.setCellValueFactory(new PropertyValueFactory("SUPPLIER_COMPANY"));
        
        supplierTableData.setItems(null);
        supplierTableData.setItems(supplierData);
    }    
    
}
