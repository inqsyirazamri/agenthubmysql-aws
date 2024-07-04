package src.g11.agenthub.data_transfer;

public class AgentDto {
    private int AgentId;
    private String AgentCode;
    private String fullName;
    private String Email;
    private String phone;
    private double balance;

    public int getAgentId() {
        return AgentId;
    }

    public void setAgentId(int AgentId) {
        this.AgentId = AgentId;
    }

    public String getAgentCode() {
        return AgentCode;
    }

    public void setAgentCode(String AgentsCode) {
        this.AgentCode = AgentsCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
