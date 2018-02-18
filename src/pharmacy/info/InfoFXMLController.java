package pharmacy.info;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pharmacy.database.SQLConnection;

public class InfoFXMLController implements Initializable {
    
    @FXML
    private TableView<InfoData> infoTableData;
    
    @FXML
    private TableColumn<InfoData, Integer> bill_id_col;
    @FXML
    private TableColumn<InfoData, String> bill_date_col;
    @FXML
    private TableColumn<InfoData, String> drug_name_col;
    @FXML
    private TableColumn<InfoData, Integer> drug_tabs_col;
    @FXML
    private TableColumn<InfoData, Integer> drug_amount_col;
    @FXML
    private TableColumn<InfoData, Integer> drug_retail_price_col;
    @FXML
    private TableColumn<InfoData, Integer> drug_total_price_col;
    @FXML
    private TableColumn<InfoData, String> user_name_col;
    
    private ObservableList<InfoData> infoData;
    
    // GLOBALS
    Connection connection;
    ResultSet resultSet;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = SQLConnection.SQLConnector();
        
        infoData = FXCollections.observableArrayList();
        loadTableData();
    }
    
    public void loadTableData(){
        String sql = "select * from bills";
        
        try {
            resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()){
                infoData.add(new InfoData(resultSet.getInt("bill_id"), resultSet.getString("bill_date"), resultSet.getString("drug_name"),
                        resultSet.getInt("drug_amount"), resultSet.getInt("drug_tabs"), resultSet.getInt("drug_retail_price"),
                        resultSet.getInt("drug_total_price"), resultSet.getString("user_name")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InfoFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        bill_id_col.setCellValueFactory(new PropertyValueFactory("BILL_ID"));
        bill_date_col.setCellValueFactory(new PropertyValueFactory("BILL_DATE"));
        drug_name_col.setCellValueFactory(new PropertyValueFactory("DRUG_NAME"));
        drug_amount_col.setCellValueFactory(new PropertyValueFactory("DRUG_AMOUNT"));
        drug_tabs_col.setCellValueFactory(new PropertyValueFactory("DRUG_TABS"));
        drug_retail_price_col.setCellValueFactory(new PropertyValueFactory("DRUG_RETAIL_PRICE"));
        drug_total_price_col.setCellValueFactory(new PropertyValueFactory("DRUG_TOTAL_PRICE"));
        user_name_col.setCellValueFactory(new PropertyValueFactory("USER_NAME"));
        
        infoTableData.setItems(null);
        infoTableData.setItems(infoData);
        
    }
    
}
