package pharmacy.supplier;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SupplierData {
    private final SimpleStringProperty SUPPLIER_ID;
    private final SimpleStringProperty SUPPLIER_NAME;
    private final SimpleStringProperty SUPPLIER_ADDRESS;
    private final SimpleIntegerProperty SUPPLIER_PHONE;
    private final SimpleStringProperty SUPPLIER_COMPANY;
    
    public SupplierData(String supplier_id, String supplier_name, String supplier_address,
                        int supplier_phone, String supplier_company)
    {
        SUPPLIER_ID = new SimpleStringProperty(supplier_id);
        SUPPLIER_NAME = new SimpleStringProperty(supplier_name);
        SUPPLIER_ADDRESS = new SimpleStringProperty(supplier_address);
        SUPPLIER_PHONE = new SimpleIntegerProperty(supplier_phone);
        SUPPLIER_COMPANY = new SimpleStringProperty(supplier_company);
    }

    public String getSUPPLIER_ID() {
        return SUPPLIER_ID.get();
    }

    public String getSUPPLIER_NAME() {
        return SUPPLIER_NAME.get();
    }

    public String getSUPPLIER_ADDRESS() {
        return SUPPLIER_ADDRESS.get();
    }

    public Integer getSUPPLIER_PHONE() {
        return SUPPLIER_PHONE.get();
    }

    public String getSUPPLIER_COMPANY() {
        return SUPPLIER_COMPANY.get();
    }
}
