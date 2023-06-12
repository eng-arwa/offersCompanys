package offersApp.offerscompanys;

public class DataAddAdmin {

    private  String fullName;
    private String dataphone;
    private String dataemail;
    private  String password;
    private String datausertype;
    private String dataidentity;
    private String dataadress;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDataphone() {
        return dataphone;
    }

    public void setDataphone(String dataphone) {
        this.dataphone = dataphone;
    }

    public String getDataemail() {
        return dataemail;
    }

    public void setDataemail(String dataemail) {
        this.dataemail = dataemail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatausertype() {
        return datausertype;
    }

    public void setDatausertype(String datausertype) {
        this.datausertype = datausertype;
    }

    public String getDataidentity() {
        return dataidentity;
    }

    public void setDataidentity(String dataidentity) {
        this.dataidentity = dataidentity;
    }

    public String getDataadress() {
        return dataadress;
    }

    public void setDataadress(String dataadress) {
        this.dataadress = dataadress;
    }

    public DataAddAdmin(String name, String phone, String email, String adress, String identitynumber, String password, String datausertype) {
        this.fullName = name;
        this.dataphone = phone;
        this.dataemail = email;
        this.password = password;
        this.datausertype=datausertype;
        this.dataidentity = identitynumber;
        this.dataadress = adress;
    }

    public DataAddAdmin() {

    }
}



