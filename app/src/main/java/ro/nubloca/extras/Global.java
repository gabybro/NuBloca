package ro.nubloca.extras;

import android.app.Application;

import ro.nubloca.Networking.AllElem;
import ro.nubloca.Networking.TaraElem;
import ro.nubloca.Networking.TipElem;

/**
 * Created by gaby on 6/6/16.
 */
public class Global extends Application{


    public TipElem[] getTipElem() {
        return tipElem;
    }

    public void setTipElem(TipElem[] tipElem) {
        this.tipElem = tipElem;
    }

    private TipElem[] tipElem;

    public TaraElem getTaraElem() {
        return taraElem;
    }

    public void setTaraElem(TaraElem taraElem) {
        this.taraElem = taraElem;
    }

    private TaraElem taraElem;

    public AllElem[] getAllelem() {
        return allelem;
    }

    public void setAllelem(AllElem[] allelem) {
        this.allelem = allelem;
    }

    private AllElem[] allelem;

    public String getNume_tip_inmatriculare() {
        return nume_tip_inmatriculare;
    }

    public void setNume_tip_inmatriculare(String nume_tip_inmatriculare) {
        this.nume_tip_inmatriculare = nume_tip_inmatriculare;
    }

    private String nume_tip_inmatriculare="STANDARD";

    public int[] getIds_tipuri_inmatriculare_tipuri_elemente() {
        return Ids_tipuri_inmatriculare_tipuri_elemente;
    }

    public void setIds_tipuri_inmatriculare_tipuri_elemente(int[] ids_tipuri_inmatriculare_tipuri_elemente) {
        Ids_tipuri_inmatriculare_tipuri_elemente = ids_tipuri_inmatriculare_tipuri_elemente;
    }

    int [] Ids_tipuri_inmatriculare_tipuri_elemente;

    public String getGet_order_ids_tip() {
        return get_order_ids_tip;
    }

    public void setGet_order_ids_tip(String get_order_ids_tip) {
        this.get_order_ids_tip = get_order_ids_tip;
    }

    private String get_order_ids_tip="[1,2,3]";

    public int getNume_tip_inmatriculare_id() {
        return nume_tip_inmatriculare_id;
    }

    public void setNume_tip_inmatriculare_id(int nume_tip_inmatriculare_id) {
        this.nume_tip_inmatriculare_id = nume_tip_inmatriculare_id;
    }

    private int nume_tip_inmatriculare_id=0;

    public int getRadio_nume_tip_inmatriculare_id() {
        return radio_nume_tip_inmatriculare_id;
    }

    public void setRadio_nume_tip_inmatriculare_id(int radio_nume_tip_inmatriculare_id) {
        this.radio_nume_tip_inmatriculare_id = radio_nume_tip_inmatriculare_id;
    }

    private int radio_nume_tip_inmatriculare_id=0;

    public String getName_tip_inmatriculare() {
        return name_tip_inmatriculare;
    }

    public void setName_tip_inmatriculare(String name_tip_inmatriculare) {
        this.name_tip_inmatriculare = name_tip_inmatriculare;
    }

    private String name_tip_inmatriculare;

    public int getPositionExemplu() {
        return positionExemplu;
    }

    public void setPositionExemplu(int positionExemplu) {
        this.positionExemplu = positionExemplu;
    }

    private int positionExemplu;

    public int getId_shared() {
        return id_shared;
    }

    public void setId_shared(int id_shared) {
        this.id_shared = id_shared;
    }

    private int id_shared;

    public int getId_tara() {
        return id_tara;
    }

    public void setId_tara(int id_tara) {
        this.id_tara = id_tara;
    }

    private int id_tara=147;

    public String getCountry_select() {
        return country_select;
    }

    public void setCountry_select(String country_select) {
        this.country_select = country_select;
    }

    private String country_select="Romania";

    public String getArray() {
        return array;
    }

    public void setArray(String array) {
        this.array = array;
    }

    private String array;

    public int getId_exemplu() {
        return id_exemplu;
    }

    public void setId_exemplu(int id_exemplu) {
        this.id_exemplu = id_exemplu;
    }

    private int id_exemplu;

}
