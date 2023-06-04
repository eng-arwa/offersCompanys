package offersApp.offerscompanys.model;

public class MarketerMembershipRequestAdmin {
    private String FullName;
    private String  Email;
    private String Password;
    private String Phone;
    private String IdentityNumber;
    private String Address;
    private String IsApproved;
   private String IsAdmin;






    public MarketerMembershipRequestAdmin(String isApproved) {
        IsApproved = isApproved;
    }



    public MarketerMembershipRequestAdmin(String fullName, String email, String password, String phone, String identityNumber, String address, String isAdmin) {
        FullName = fullName;
        Email = email;
        Password = password;
        Phone = phone;
        IdentityNumber = identityNumber;
        Address = address;
        IsAdmin = isAdmin;
    }

    public void setIsApproved(String isApproved) {
        IsApproved = isApproved;
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



    public void setIsAdmin(String isAdmin) {
        IsAdmin = isAdmin;
    }


    public String getIsAdmin() {
        return IsAdmin;
    }


}
