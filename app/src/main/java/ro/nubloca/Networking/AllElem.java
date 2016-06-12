package ro.nubloca.Networking;

import org.json.JSONArray;

/**
 * Created by gaby on 6/1/16.
 */
public class AllElem implements Comparable<AllElem> {
    public String[] getLista_cod() {
        return lista_cod;
    }

    public void setLista_cod(String[] lista_cod) {
        this.lista_cod = lista_cod;
    }

    private String[] lista_cod;

    private int id;

    private int id_tip_element;

    private int ordinea;

    private String tip;

    public String getValoare_demo_imagine() {
        return valoare_demo_imagine;
    }

    public void setValoare_demo_imagine(String valoare_demo_imagine) {
        this.valoare_demo_imagine = valoare_demo_imagine;
    }

    private String valoare_demo_imagine;


    private int editabil_user;

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

    public int getOrdinea() {
        return ordinea;
    }

    public void setOrdinea(int ordinea) {
        this.ordinea = ordinea;
    }

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

    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    public String getValoriString() {
        return valoriString;
    }

    public void setValoriString(String valoriString) {
        this.valoriString = valoriString;
    }

    public JSONArray getValoriArray() {
        return valoriArray;
    }

    public void setValoriArray(JSONArray valoriArray) {
        this.valoriArray = valoriArray;
    }

    private int maxlength;

    private String valoriString;

    private JSONArray valoriArray;

    @Override
    public int compareTo(AllElem o) {
        return new Double(this.ordinea).compareTo(new Double(o.ordinea));
    }

}
