package ro.nubloca.Networking;


import com.google.gson.annotations.SerializedName;

public class TaraElem {


    private int ordinea;

    private int id;

    public int getOrdinea() {
        return ordinea;
    }

    public void setOrdinea(int ordinea) {
        this.ordinea = ordinea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl_steag() {
        return url_steag;
    }

    public void setUrl_steag(String url_steag) {
        this.url_steag = url_steag;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    private String url_steag;

    private String nume;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    private String cod;

}
