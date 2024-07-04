package src.g11.agenthub.data_transfer;

// import src.g11.agenthub.data_access.CustomerDao;
// import java.sql.ResultSet;

public class CustomerDto {
    private int cid;
    private String customerCode;
    private String customerName;
    private String phone;
    private String shippingAddress;
    private String salesAgentId;
    private String customerNotes;
    // private int sid;

    // public CustomerDto(int cid, String customerName, String phone, String
    // shippingAddress, int salesAgentId,
    // String customerNotes) {
    // this.cid = cid;
    // this.customerName = customerName;
    // this.phone = phone;
    // this.shippingAddress = shippingAddress;
    // this.salesAgentId = salesAgentId;
    // this.customerNotes = customerNotes;
    // }

    // public CustomerDto() {
    // }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getSalesAgentId() {
        return salesAgentId;
    }

    public void setSalesAgentId(String salesAgentId) {
        this.salesAgentId = salesAgentId;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    // public ResultSet getCustomersInfo() {
    // ResultSet rs = new CustomerDao().getCustomersInfo();
    // return rs;
    // }

    // public String getCustomerName(int cid) {
    // CustomerDao customerDao = new CustomerDao();
    // return customerDao.getCustomerName(String.valueOf(cid));
    // }

    // public String getCustomerPhone(int cid) {
    // CustomerDao customerDao = new CustomerDao();
    // return customerDao.getCustomerPhone(String.valueOf(cid));
    // }

    // public String getCustomerShippingAddress(int cid) {
    // CustomerDao customerDao = new CustomerDao();
    // return customerDao.getCustomerShippingAddress(String.valueOf(cid));
    // }

}