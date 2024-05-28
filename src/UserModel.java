import java.util.Date;

public class UserModel {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String accountNo;
    private Date openingDate;

    UserModel(String username,String password,String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    UserModel(String firstname, String lastname, String accountNo,Date openingDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.accountNo = accountNo;
        this.openingDate = openingDate;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public String getAccountNo() {
        return accountNo;
    }

    public String getOpeningDate() {
        return String.valueOf(openingDate);
    }
}
