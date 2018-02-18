package pharmacy.supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.database.SQLConnection;

public class SupplierDao {
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    private ObservableList<SupplierData> supplierData;
    
    public SupplierDao(){
        connection = SQLConnection.SQLConnector();
    }
    
    public void AddSupplier(String supplier_id, String supplier_name, String supplier_address,
            int supplier_phone, String supplier_company)
    {
        String sqlInsert = "INSERT INTO suppliers(supplier_id, supplier_name, supplier_address, supplier_phone, supplier_company) VALUES (?,?,?,?,?)";
        
        String sql = "insert into suppliers (supplier_id, supplier_name, supplier_address, supplier_phone, supplier_company) values (?,?,?,?,?)";
        
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplier_id);
            preparedStatement.setString(2, supplier_name);
            preparedStatement.setString(3, supplier_address);
            preparedStatement.setInt(4, supplier_phone);
            preparedStatement.setString(5, supplier_company);
            
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void EditSupplier(String supplier_id, String supplier_name, String supplier_address,
            int supplier_phone, String supplier_company)
    {
        String sqlUpdate = "UPDATE suppliers SET supplier_name=?,supplier_address=?,supplier_phone=?,supplier_company=? WHERE supplier_id=?";
        
        
        try {
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, supplier_name);
            preparedStatement.setString(2, supplier_address);
            preparedStatement.setInt(3, supplier_phone);
            preparedStatement.setString(4, supplier_company);
            preparedStatement.setString(5, supplier_id);
            
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void DeleteSupplier(String supplier_id){
        String sqlDelete = "DELETE FROM suppliers WHERE supplier_id=?";
        
        try {
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setString(1, supplier_id);
            
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ObservableList<SupplierData> loadSupplierData(){
        supplierData = FXCollections.observableArrayList();
        String sqlSelect = "SELECT * FROM suppliers";
        
        try {
            resultSet = connection.createStatement().executeQuery(sqlSelect);
            while (resultSet.next()){
                supplierData.add(new SupplierData(resultSet.getString("supplier_id"), resultSet.getString("supplier_name"),
                        resultSet.getString("supplier_address"), resultSet.getInt("supplier_phone"),
                        resultSet.getString("supplier_company")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return supplierData;
        
    }
}
