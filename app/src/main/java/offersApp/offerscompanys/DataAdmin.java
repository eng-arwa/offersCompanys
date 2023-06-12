package offersApp.offerscompanys;


public class DataAdmin {

    private String dataname;
    private String datalogo;
    private String dataCreatedBy;
    private String datatype;
    private String dataphone;
    private String dataemail;
    private String fullName;
    private  String password;

    private String datausertype;
    private String dataidentity;
    private String dataadress;
    private String key;



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataname() {
        return dataname;
    }
    public String getDatausertype() {
        return datausertype;
    }
    public String getFullName() {
        return fullName;
    }
    public String getPassword() {
        return password;
    }

    public String getDatatype() {
        return datatype;
    }
    public String getDataidentity() {
        return dataidentity;
    }

    public String getDataphone() {
        return dataphone;
    }
    public String getDataemail() {
        return dataemail;
    }

    public String getDataadress() {
        return dataadress;
    }
    public String getDatalogo() {
        return datalogo;
    }
    public String getDataCreatedBy() {
        return dataCreatedBy;
    }

    public DataAdmin(String dataname,String password, String datatype, String dataphone, String datalogo,String dataemail,
                     String dataadress, String dataidentity,String dataCreatedBy) {
        this.dataname = dataname;
        this.password = password;
        this.datatype = datatype;
        this.dataphone = dataphone;
        this.datalogo = datalogo;
        this.dataidentity=dataidentity;
        this.dataCreatedBy=dataCreatedBy;
        this.dataemail=dataemail;
        this.datausertype="company";
        this.dataadress=dataadress;
        this.fullName=dataname;
    }
    public DataAdmin(String dataname, String password,
                     String dataemail,String adress,String phone,String identity) {
        this.dataname = dataname;

        this.datalogo = password;
        this.dataemail=dataemail;
        this.dataadress=adress;
        this.dataphone=phone;
        this.dataidentity=identity;
        this.datausertype="company";


    }

    public DataAdmin(String name, String phone, String email, String adress, String identitynumber, String password) {
        this.fullName = name;
//        this.dataCreatedBy = dataCreatedBy;
        this.dataphone = phone;
        this.dataemail = email;
        this.password = password;
        this.datausertype="admin";
        this.dataidentity = identitynumber;
        this.dataadress = adress;
    }

    public DataAdmin(){

    }
}