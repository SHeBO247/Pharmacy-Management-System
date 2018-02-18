package pharmacy.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.database.SQLConnection;

public class UserDao {
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    private ObservableList<UserData> userData;
    
    public UserDao(){
        connection = SQLConnection.SQLConnector();
    }
    
    public void AddUser(String user_id, String user_fullname, String user_name,
            String user_password, String user_address, int user_phone,
            String user_type, int user_salary)
    {
        String sql = "insert into users (user_id, user_fullname, user_address, user_phone, user_salary, user_type, user_name, user_password) values (?,?,?,?,?,?,?,?)";
        
        try {
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, user_id);
            preparedStatement.setString(2, user_fullname);
            preparedStatement.setString(3, user_address);
            preparedStatement.setInt(4, user_phone);
            preparedStatement.setInt(5, user_salary);
            preparedStatement.setString(6, user_type);
            preparedStatement.setString(7, user_name);
            preparedStatement.setString(8, user_password);
            
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void EditUser(String user_id, String user_fullname, String user_name,
            String user_password, String user_address, int user_phone,
            String user_type, int user_salary)
    {   
        String sql = "update users set user_fullname=?, user_address=?, user_phone=?, user_salary=?, user_type=?, user_name=?, user_password=? where user_id=?";
        
        try {
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, user_fullname);
            preparedStatement.setString(2, user_address);
            preparedStatement.setInt(3, user_phone);
            preparedStatement.setInt(4, user_salary);
            preparedStatement.setString(5, user_type);
            preparedStatement.setString(6, user_name);
            preparedStatement.setString(7, user_password);
            preparedStatement.setString(8, user_id);
            
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void DeleteUser(String user_id){
        String sqlDelete = "DELETE FROM `users` WHERE `user_id`=?";
        
        String sql = "delete from users where user_id=?";
        
        try {
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, user_id);
            
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ObservableList<UserData> loadUserData(){
        userData = FXCollections.observableArrayList();
        String sql = "select * from users";
        
        try {
            resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()){
                userData.add(new UserData(resultSet.getString("user_id"), resultSet.getString("user_fullname"),
                        resultSet.getString("user_name"), resultSet.getString("user_password"), resultSet.getString("user_address"),
                        resultSet.getInt("user_phone"), resultSet.getString("user_type"), resultSet.getInt("user_salary")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userData;
        
    }
}
