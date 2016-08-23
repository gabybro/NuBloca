package ro.nubloca.extras;

import android.app.Application;

/**
 * Created by GABY_ on 06.06.2016.
 */
public class GlobalVar extends Application {
    private int id_tara = 147;
    public static final String urlShare = "http://nubloca.ro";

    public int getNume_tip_inmatriculare_id() {
        return nume_tip_inmatriculare_id;
    }


    public int[] getIds_tipuri_inmatriculare_tipuri_elemente() {
        return Ids_tipuri_inmatriculare_tipuri_elemente;
    }

    public void setIds_tipuri_inmatriculare_tipuri_elemente(int[] ids_tipuri_inmatriculare_tipuri_elemente) {
        Ids_tipuri_inmatriculare_tipuri_elemente = ids_tipuri_inmatriculare_tipuri_elemente;
    }

    int[] Ids_tipuri_inmatriculare_tipuri_elemente;


    public void setNume_tip_inmatriculare_id(int nume_tip_inmatriculare_id) {
        this.nume_tip_inmatriculare_id = nume_tip_inmatriculare_id;
    }

    private int nume_tip_inmatriculare_id = 1;

    public int getPositionExemplu() {
        return positionExemplu;
    }

    public void setPositionExemplu(int positionExemplu) {
        this.positionExemplu = positionExemplu;
    }

    private int positionExemplu = -1;

    public int getId_shared() {
        return id_shared;
    }

    public String getName_tip_inmatriculare() {
        return name_tip_inmatriculare;
    }

    public String getCountry_select() {
        return country_select;
    }

    public void setCountry_select(String country_select) {
        this.country_select = country_select;
    }

    public String country_select = "Romania";


    public void setName_tip_inmatriculare(String name_tip_inmatriculare) {
        this.name_tip_inmatriculare = name_tip_inmatriculare;
    }

    private String name_tip_inmatriculare = "";

    public void setId_shared(int id_shared) {
        this.id_shared = id_shared;
    }

    private int id_shared = 0;

    public void setId_tara(int id_tara) {
        this.id_tara = id_tara;
    }

    public int getId_tara() {
        return id_tara;
    }
}
