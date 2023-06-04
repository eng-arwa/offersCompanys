package offersApp.offerscompanys.model;

public class MarketerMembershipRequestMarketer {
    private String FullName;
    private String  Email;
    private String Password;
    private String Phone;
    private String IdentityNumber;
    private String Address;
    private String IsApproved;
    private String IsMarketer;

    public MarketerMembershipRequestMarketer(String fullName, String email, String password, String phone, String identityNumber, String address, String isMarketer) {
        FullName = fullName;
        Email = email;
        Password = password;
        Phone = phone;
        IdentityNumber = identityNumber;
        Address = address;
        IsMarketer = isMarketer;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getFullName() {
        return FullName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhone() {
        return Phone;
    }

    public String getIdentityNumber() {
        return IdentityNumber;
    }

    public String getAddress() {
        return Address;
    }

    public String getIsApproved() {
        return IsApproved;
    }

    public String getIsMarketer() {
        return IsMarketer;
    }

    public MarketerMembershipRequestMarketer(String fullName, String email, String password, String phone, String identityNumber, String address, String isApproved, String isMarketer) {
        FullName = fullName;
        Email = email;
        Password = password;
        Phone = phone;
        IdentityNumber = identityNumber;
        Address = address;
        IsApproved = isApproved;
        IsMarketer = isMarketer;
    }
}
