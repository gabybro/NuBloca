package ro.nubloca.Networking;

/**
 * Created by GABY_ on 17.05.2016.
 */
public class Order {

    private String Name;
    private String id;
    private int[] ids_tip;
    private int Ordinea;

    public String getOrderName() {
        return Name;
    }

    public void setOrderName(String orderName) {
        this.Name = orderName;
    }

    public String getOrderId() {
        return id;
    }

    public void setOrderId(String orderId) {
        this.id = orderId;
    }

    public int[] getOrderIdsTip() {
        return ids_tip;
    }

    public void setOrderIdsTip(int[] orderIdsTip) {
        this.ids_tip = orderIdsTip;
    }
    public int getOrderOrdinea() {
        return Ordinea;
    }

    public void setOrderOrdinea(int orderOrdinea) {
        this.Ordinea = orderOrdinea;
    }
}