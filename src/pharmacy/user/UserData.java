package pharmacy.user;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserData {
    private final SimpleStringProperty USER_ID;
    private final SimpleStringProperty USER_FULLNAME;
    private final SimpleStringProperty USER_NAME;
    private final SimpleStringProperty USER_PASSWORD;
    private final SimpleStringProperty USER_ADDRESS;
    private final SimpleIntegerProperty USER_PHONE;
    private final SimpleStringProperty USER_TYPE;
    private final SimpleIntegerProperty USER_SALARY;
    
    public UserData(String user_id, String user_fullname, String user_name,
            String user_password, String user_address, int user_phone,
            String user_type, int user_salary){
        USER_ID = new SimpleStringProperty(user_id);
        USER_FULLNAME = new SimpleStringProperty(user_fullname);
        USER_NAME = new SimpleStringProperty(user_name);
        USER_PASSWORD = new SimpleStringProperty(user_password);
        USER_ADDRESS = new SimpleStringProperty(user_address);
        USER_PHONE = new SimpleIntegerProperty(user_phone);
        USER_TYPE = new SimpleStringProperty(user_type);
        USER_SALARY = new SimpleIntegerProperty(user_salary);
    }

    public String getUSER_ID() {
        return USER_ID.get();
    }

    public String getUSER_FULLNAME() {
        return USER_FULLNAME.get();
    }

    public String getUSER_NAME() {
        return USER_NAME.get();
    }

    public String getUSER_PASSWORD() {
        return USER_PASSWORD.get();
    }

    public String getUSER_ADDRESS() {
        return USER_ADDRESS.get();
    }

    public Integer getUSER_PHONE() {
        return USER_PHONE.get();
    }

    public String getUSER_TYPE() {
        return USER_TYPE.get();
    }

    public Integer getUSER_SALARY() {
        return USER_SALARY.get();
    }
}
