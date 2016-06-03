package ro.nubloca.Networking;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by GABY_ on 28.05.2016.
 */
public class Response {


    @SerializedName("id")
    private int id;

    @SerializedName("nume")
    private String nume;

    @SerializedName("ids_tipuri_inmatriculare_tipuri_elemente")
    private int[]  ids_tipuri_inmatriculare_tipuri_elemente;

    @SerializedName("id_tip_element")
    private int id_tip_element;

    @SerializedName("valoare_demo_imagine")
    private String valoare_demo_imagine;

    @SerializedName("ordinea")
    private int ordinea;

    @SerializedName("tip")
    private String tip;

    public String getValoare() {
        return valoare;
    }

    public void setValoare(String valoare) {
        this.valoare = valoare;
    }

    public JSONArray getValoareArr() {
        return valoareArr;
    }

    public void setValoareArr(JSONArray valoareArr) {
        this.valoareArr = valoareArr;
    }

    private String valoare;

    private JSONArray valoareArr;

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getEditabil_user() {
        return editabil_user;
    }

    public void setEditabil_user(int editabil_user) {
        this.editabil_user = editabil_user;
    }

    public int getMaxlength() {
        return maxlength;
    }

    @SerializedName("editabil_user")
    private int editabil_user;

    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    @SerializedName("maxlength")

    private int maxlength;

   // @SerializedName("valori")
    //private int valori;

    public int[]  getIds_tipuri_inmatriculare_tipuri_elemente() {
        return ids_tipuri_inmatriculare_tipuri_elemente;
    }

    public void setIds_tipuri_inmatriculare_tipuri_elemente(int[] ids_tipuri_inmatriculare_tipuri_elemente) {
        this.ids_tipuri_inmatriculare_tipuri_elemente = ids_tipuri_inmatriculare_tipuri_elemente;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }


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
