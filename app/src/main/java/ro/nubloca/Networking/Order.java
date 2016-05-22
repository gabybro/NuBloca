package ro.nubloca.Networking;

import org.json.JSONArray;

/**
 * Created by GABY_ on 17.05.2016.
 */
public class Order {

    private String Name;
    private int id;
    private JSONArray ids_tip;
    private int Ordinea;

    public String getOrderName() {
        return Name;
    }

    public void setOrderName(String orderName) {
        this.Name = orderName;
    }

    public int getOrderId() {
        return id;
    }

    public void setOrderId(int orderId) {
        this.id = orderId;
    }

    public JSONArray getOrderIdsTip() {
        return ids_tip;
    }

    public void setOrderIdsTip(JSONArray orderIdsTip) {
        this.ids_tip = orderIdsTip;
    }

    public int getOrderOrdinea() {
        return Ordinea;
    }

    public void setOrderOrdinea(int orderOrdinea) {
        this.Ordinea = orderOrdinea;
    }
}