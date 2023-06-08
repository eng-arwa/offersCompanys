package offersApp.offerscompanys;


public class DataMarkter {

    private String fullName;
    private String phone;
    private  String datalogo;

    private String password;
    private String adress;
    private  String datausertype;

    private String email;
    private String identityNumber;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getDatalogo() {
        return datalogo;
    }

    public String getFullName() {
        return fullName;
    }
    public String getPhone() {
        return phone;
    }
    public String getPassword() {
        return password;
    }
    public String getAdress() {
        return adress;
    }
    public String getDatausertype() {
        return datausertype;
    }
    public String getEmail() {
        return email;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }







    public DataMarkter(String dataname, String dataphone, String dataemail,
                       String dataadress, String dataidentity,String password) {
        this.fullName= dataname;
        this.phone = dataphone;
        this.password=password;
        this.adress=dataadress;
        this.datausertype="marketer";
        this.email = dataemail;
        this.identityNumber=dataidentity;

    }
    public DataMarkter(String dataname, String dataphone, String dataemail,
                       String dataadress, String dataidentity) {
        this.fullName= dataname;
        this.phone = dataphone;
        this.email = dataemail;
        this.adress=dataadress;
        this.identityNumber=dataidentity;

    }
    public DataMarkter(String dataname,
                       String dataadress) {
        this.fullName= dataname;

        this.adress=dataadress;

    }

    public DataMarkter(){}
}