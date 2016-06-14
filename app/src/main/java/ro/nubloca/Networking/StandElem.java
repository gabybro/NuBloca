package ro.nubloca.Networking;


import org.json.JSONArray;

public class StandElem {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdinea() {
        return ordinea;
    }

    public void setOrdinea(int ordinea) {
        this.ordinea = ordinea;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }



    private int ordinea;

    private String nume;


    public Ids[] getIds_tip() {
        return ids_tip;
    }

    public void setIds_tip(Ids[] ids_tip) {
        this.ids_tip = ids_tip;
    }

    private Ids[] ids_tip;


}
