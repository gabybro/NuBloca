package ro.nubloca.Networking;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GABY_ on 28.05.2016.
 */
public class Response26 {

    /**
     * id : 19
     * id_tip_element : 14
     * valoare_demo_imagine : B
     * ordinea : 1
     */

    @SerializedName("id")
    private int id;

    @SerializedName("id_tip_element")
    private int id_tip_element;

    @SerializedName("valoare_demo_imagine")
    private String valoare_demo_imagine;

    @SerializedName("ordinea")
    private int ordinea;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tip_element() {
        return id_tip_element;
    }

    public void setId_tip_element(int id_tip_element) {
        this.id_tip_element = id_tip_element;
    }

    public String getValoare_demo_imagine() {
        return valoare_demo_imagine;
    }

    public void setValoare_demo_imagine(String valoare_demo_imagine) {
        this.valoare_demo_imagine = valoare_demo_imagine;
    }

    public int getOrdinea() {
        return ordinea;
    }

    public void setOrdinea(int ordinea) {
        this.ordinea = ordinea;
    }
}
