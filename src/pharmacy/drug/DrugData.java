package pharmacy.drug;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DrugData {
    private final SimpleStringProperty DRUG_CODE;
    private final SimpleStringProperty DRUG_NAME;
    private final SimpleIntegerProperty DRUG_AMOUNT;
    private final SimpleIntegerProperty DRUG_TABS;
    private final SimpleIntegerProperty DRUG_RETAIL_PRICE;
    private final SimpleIntegerProperty DRUG_TOTAL_PRICE;
    private final SimpleStringProperty DRUG_CATEGORY;
    private final SimpleStringProperty DRUG_EXPIRE_DATE;
    
    public DrugData(String drug_code, String drug_name, int drug_amount, int drug_tabs,
                        int drug_retail_price, int drug_total_price, String drug_category, String drug_expire_date)
    {
        DRUG_CODE         = new SimpleStringProperty(drug_code);
        DRUG_NAME         = new SimpleStringProperty(drug_name);
        DRUG_AMOUNT       = new SimpleIntegerProperty(drug_amount);
        DRUG_TABS         = new SimpleIntegerProperty(drug_tabs);
        DRUG_RETAIL_PRICE = new SimpleIntegerProperty(drug_retail_price);
        DRUG_TOTAL_PRICE  = new SimpleIntegerProperty(drug_total_price);
        DRUG_CATEGORY     = new SimpleStringProperty(drug_category);
        DRUG_EXPIRE_DATE  = new SimpleStringProperty(drug_expire_date);
    }

    public String getDRUG_CODE() {
        return DRUG_CODE.get();
    }

    public String getDRUG_NAME() {
        return DRUG_NAME.get();
    }

    public Integer getDRUG_TABS() {
        return DRUG_TABS.get();
    }

    public Integer getDRUG_AMOUNT() {
        return DRUG_AMOUNT.get();
    }

    public Integer getDRUG_RETAIL_PRICE() {
        return DRUG_RETAIL_PRICE.get();
    }

    public Integer getDRUG_TOTAL_PRICE() {
        return DRUG_TOTAL_PRICE.get();
    }

    public String getDRUG_CATEGORY() {
        return DRUG_CATEGORY.get();
    }
    
    public String getDRUG_EXPIRE_DATE() {
        return DRUG_EXPIRE_DATE.get();
    }
}
