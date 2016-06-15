package ro.nubloca.Networking;


import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

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

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }


    public String getUrl_steag() {
        return url_steag;
    }

    public void setUrl_steag(String url_steag) {
        this.url_steag = url_steag;
    }

    private String url_steag;

    private String cod;

    private int ordinea;

    private String nume;


    public List<TipNumar> getTipNumar() {
        return tipNumar;
    }

    public void setTipNumar(List<TipNumar> tipNumar) {
        this.tipNumar = tipNumar;
    }

    public List<TipNumar> tipNumar;





    public static class TipNumar implements Comparable<TipNumar>{



        private int id;



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


        public String getNume() {
            return nume;
        }

        public void setNume(String nume) {
            this.nume = nume;
        }

        private String nume;


        public int getOrdinea() {
            return ordinea;
        }

        public void setOrdinea(int ordinea) {
            this.ordinea = ordinea;
        }

        private int ordinea;

        private int[] tip_idd;

        private String tip_tip;

        private int[] tip_editabil;

        private int[] tip_maxlength;

        private String tip_valori;

        public int getTip_size() {
            return tip_size;
        }

        public void setTip_size(int tip_size) {
            this.tip_size = tip_size;

        }

        public int[] getTip_idd() {
            return tip_idd;
        }

        public void setTip_idd(int[] tip_idd) {
            this.tip_idd = tip_idd;
        }

        public String getTip_tip() {
            return tip_tip;
        }

        public void setTip_tip(String tip_tip) {
            this.tip_tip = tip_tip;
        }

        public int[] getTip_editabil() {
            return tip_editabil;
        }

        public void setTip_editabil(int[] tip_editabil) {
            this.tip_editabil = tip_editabil;
        }

        public int[] getTip_maxlength() {
            return tip_maxlength;
        }

        public void setTip_maxlength(int[] tip_maxlength) {
            this.tip_maxlength = tip_maxlength;
        }

        public String getTip_valori() {
            return tip_valori;
        }

        public void setTip_valori(String tip_valori) {
            this.tip_valori = tip_valori;
        }

        private int tip_size;

        @Override
        public int compareTo(TipNumar o) {
            return new Double(this.ordinea).compareTo(new Double(o.ordinea));
        }
    }

}
