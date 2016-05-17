package ro.nubloca.Networking;

/**
 * Created by GABY_ on 17.05.2016.
 */
public class Order {

    private String Name;
    private String id;

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
}