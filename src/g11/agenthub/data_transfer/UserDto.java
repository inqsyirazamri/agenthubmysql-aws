package src.g11.agenthub.data_transfer;

// import java.io.File;

public class UserDto {
    private int id;
    private String fullName;
    private String Email;
    private String phone;
    private String username;
    private String password;
    private String Role;
    // private File image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    // public File getImage() {
    // return image;
    // }

    // public void setImage(File image) {
    // this.image = image;
    // }
}
