package pharmacy.drug;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.database.SQLConnection;

public class DrugDao {
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    private ObservableList<DrugData> drugData;
    
    public DrugDao(){
        connection = SQLConnection.SQLConnector();
    }
    
    public void AddDrug(String drug_code, String drug_name, int drug_tabs,
            int drug_amount, int drug_retail_price, int drug_total_price, String drug_category, String drug_expire_date)
    {
        String sqlInsert = "INSERT INTO drugs(drug_code, drug_name, drug_amount,"
                + " drug_tabs, drug_retail_price, drug_total_price, drug_category, drug_expire_date) VALUES (?,?,?,?,?,?,?,?)";
        
        try {
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, drug_code);
            preparedStatement.setString(2, drug_name);
            preparedStatement.setInt(3, drug_amount);
            preparedStatement.setInt(4, drug_tabs);
            preparedStatement.setInt(5, drug_retail_price);
            preparedStatement.setInt(6, drug_total_price);
            preparedStatement.setString(7, drug_category);
            preparedStatement.setString(8, drug_expire_date);
            
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(DrugDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DrugDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void EditDrug(String drug_code, String drug_name, int drug_amount,
            int drug_tabs, int drug_retail_price, int drug_total_price, String drug_category, String drug_expire_date)
    {
        String sqlUpdate = "UPDATE drugs SET drug_name=?,drug_amount=?,"
                + "drug_tabs=?,drug_retail_price=?,drug_total_price=?,drug_expire_date=?, drug_category=? WHERE drug_code=?";
        
        try {
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, drug_name);
            preparedStatement.setInt(2, drug_amount);
            preparedStatement.setInt(3, drug_tabs);
            preparedStatement.setInt(4, drug_retail_price);
            preparedStatement.setInt(5, drug_total_price);
            preparedStatement.setString(6, drug_expire_date);
            preparedStatement.setString(7, drug_category);
            preparedStatement.setString(8, drug_code);
            
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(DrugDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DrugDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void DeleteDrug(String drug_code){
        String sqlDelete = "DELETE FROM drugs WHERE drug_code=?";
        
        try {
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setString(1, drug_code);
            
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DrugDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DrugDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ObservableList<DrugData> loadDrugData(){
        drugData = FXCollections.observableArrayList();
        String sqlSelect = "SELECT * FROM drugs";
        
        try {
            resultSet = connection.createStatement().executeQuery(sqlSelect);
            while (resultSet.next()){
                drugData.add(new DrugData(resultSet.getString("drug_code"), resultSet.getString("drug_name"),
                        resultSet.getInt("drug_amount"), resultSet.getInt("drug_tabs"), resultSet.getInt("drug_retail_price"),
                        resultSet.getInt("drug_total_price"),resultSet.getString("drug_category"),  resultSet.getString("drug_expire_date")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DrugDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return drugData;
        
    }
}
