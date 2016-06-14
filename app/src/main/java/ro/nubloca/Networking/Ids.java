package ro.nubloca.Networking;

import org.json.JSONArray;

/**
 * Created by gaby on 6/1/16.
 */
public class Ids {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;

    public String tip;

    public int editabil_user;

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

    public int getMax_length() {
        return max_length;
    }

    public void setMax_length(int max_length) {
        this.max_length = max_length;
    }

    public String getValori() {
        return valori;
    }

    public void setValori(String valori) {
        this.valori = valori;
    }

    public int max_length;

    public String valori;

}
