package pharmacy.info;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InfoData {
    private final SimpleIntegerProperty BILL_ID;
    private final SimpleStringProperty BILL_DATE;
    private final SimpleStringProperty DRUG_NAME;
    private final SimpleIntegerProperty DRUG_AMOUNT;
    private final SimpleIntegerProperty DRUG_TABS;
    private final SimpleIntegerProperty DRUG_RETAIL_PRICE;
    private final SimpleIntegerProperty DRUG_TOTAL_PRICE;
    private final SimpleStringProperty USER_NAME;

    public InfoData(int bill_id, String bill_date, String drug_name,
            int drug_amount, int drug_tabs, int drug_retail_price, int drug_total_price, String user_name)
    {
        BILL_ID = new SimpleIntegerProperty(bill_id);
        BILL_DATE = new SimpleStringProperty(bill_date);
        DRUG_NAME = new SimpleStringProperty(drug_name);
        DRUG_AMOUNT = new SimpleIntegerProperty(drug_amount);
        DRUG_TABS = new SimpleIntegerProperty(drug_tabs);
        DRUG_RETAIL_PRICE = new SimpleIntegerProperty(drug_retail_price);
        DRUG_TOTAL_PRICE = new SimpleIntegerProperty(drug_total_price);
        USER_NAME = new SimpleStringProperty(user_name);
    }
    
    
    public Integer getBILL_ID() {
        return BILL_ID.get();
    }

    public String getBILL_DATE() {
        return BILL_DATE.get();
    }

    public String getDRUG_NAME() {
        return DRUG_NAME.get();
    }

    public Integer getDRUG_AMOUNT() {
        return DRUG_AMOUNT.get();
    }

    public Integer getDRUG_TABS() {
        return DRUG_TABS.get();
    }

    public Integer getDRUG_RETAIL_PRICE() {
        return DRUG_RETAIL_PRICE.get();
    }

    public Integer getDRUG_TOTAL_PRICE() {
        return DRUG_TOTAL_PRICE.get();
    }
    
    public String getUSER_NAME() {
        return USER_NAME.get();
    }
    
}
