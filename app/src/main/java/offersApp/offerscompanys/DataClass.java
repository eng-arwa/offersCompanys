package offersApp.offerscompanys;


public class DataClass {

    private String dataTitle;
    private String dataDesc;
    private String dataLang;
    private String dataImage;
    private String dataPricebefore;
    private String dataPriceafter;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataLang() {
        return dataLang;
    }

    public String getDataImage() {
        return dataImage;
    }
    public String getDataPricebefore() {
        return dataPricebefore;
    }

    public String getDataPriceafter() {
        return dataPriceafter;
    }

    public DataClass(String dataTitle, String dataDesc, String dataLang, String dataImage,
                  String   datapricebefore,String datapriceafter) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
        this.dataPricebefore = datapricebefore;
        this.dataPriceafter = datapriceafter;


    }
    public DataClass(){

    }
}