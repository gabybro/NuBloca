package ro.nubloca.Networking;


import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

public class StandElem {

    public byte[] getSteag() {
        return steag;
    }

    public void setSteag(byte[] steag) {
        this.steag = steag;
    }

    public int getPositionExemplu() {
        return positionExemplu;
    }

    public void setPositionExemplu(int positionExemplu) {
        this.positionExemplu = positionExemplu;
    }

    private int positionExemplu=-1;

    public byte[] getImgReprezent() {
        return imgReprezent;
    }

    public void setImgReprezent(byte[] imgReprezent) {
        this.imgReprezent = imgReprezent;
    }

    private byte[] imgReprezent;

    private byte[] steag;

    public byte[] getBackgDemo() {
        return backgDemo;
    }

    public void setBackgDemo(byte[] backgDemo) {
        this.backgDemo = backgDemo;
    }

    public byte[] getPlateDemo() {
        return plateDemo;
    }

    public void setPlateDemo(byte[] plateDemo) {
        this.plateDemo = plateDemo;
    }

    private byte[] backgDemo;

    private byte[] plateDemo;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int size;

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

        public int getDemo_id_tip_loc(int location)
        {
            return demo_id_tip_element[location];
        }

        public void setDemo_id_tip_loc(int value, int location)
        {
            demo_id_tip_element[location]=value;
        }

        public int[] getDemo_id() {
            return demo_id;
        }

        public void setDemo_id(int[] demo_id) {
            this.demo_id = demo_id;
        }

        public int[] getDemo_id_tip_element() {
            return demo_id_tip_element;
        }

        public void setDemo_id_tip_element(int[] demo_id_tip_element) {
            this.demo_id_tip_element = demo_id_tip_element;
        }

        public int[] getDemo_ordinea() {
            return demo_ordinea;
        }

        public void setDemo_ordinea(int[] demo_ordinea) {
            this.demo_ordinea = demo_ordinea;
        }

        public String[] getDemo_valoare() {
            return demo_valoare;
        }

        public void setDemo_valoare(String[] demo_valoare) {
            this.demo_valoare = demo_valoare;
        }

        private int[] demo_id;

        private int[] demo_id_tip_element;

        private int[] demo_ordinea;

        private String[] demo_valoare;

        private int id;

        private String foto_background;

        public String getUrl_imagine() {
            return url_imagine;
        }

        public void setUrl_imagine(String url_imagine) {
            this.url_imagine = url_imagine;
        }

        public String getFoto_background() {
            return foto_background;
        }

        public void setFoto_background(String foto_background) {
            this.foto_background = foto_background;
        }

        private String url_imagine;



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

        private String[] tip_tip;

        private int[] tip_editabil;

        private int[] tip_maxlength;

        public String[] getTip_tip() {
            return tip_tip;
        }

        public void setTip_tip(String[] tip_tip) {
            this.tip_tip = tip_tip;
        }

        public String[] getTip_valori() {
            return tip_valori;
        }

        public void setTip_valori(String[] tip_valori) {
            this.tip_valori = tip_valori;
        }

        public JSONArray[] getTip_valori_json() {
            return tip_valori_json;
        }

        public void setTip_valori_json(JSONArray[] tip_valori_json) {
            this.tip_valori_json = tip_valori_json;
        }

        private JSONArray[] tip_valori_json;

        private String[] tip_valori;

        public int getTip_size() {
            return tip_size;
        }

        public void setTip_size(int tip_size) {
            this.tip_size = tip_size;

        }

        public int[] getTip_idd() {
            return tip_idd;
        }

        public int getTip_idd_loc(int location)
        {
            return tip_idd[location];
        }

        public void setTip_idd_loc(int value, int location)
        {
            tip_idd[location]=value;
        }


        public void setTip_idd(int[] tip_idd) {
            this.tip_idd = tip_idd;
        }


        public List<String[]> getLista_cod() {
            return lista_cod;
        }

        public void setLista_cod(List<String[]> lista_cod) {
            this.lista_cod = lista_cod;
        }

        private List<String []> lista_cod;

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



        private int tip_size;

        @Override
        public int compareTo(TipNumar o) {
            return new Double(this.ordinea).compareTo(new Double(o.ordinea));
        }
    }

}
