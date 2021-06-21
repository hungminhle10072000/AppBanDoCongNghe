package hcmute.edu.vn.mssv18110296.model;

public class Order_Detail {
    private int id;
    private int id_order;
    private int id_product;
    private String name_product;
    private int quantity_product;
    private long price_product;


    public Order_Detail() {
    }


    public Order_Detail(int id_order, int id_product, String name_product, int quantity_product, long price_product) {
        this.id_order = id_order;
        this.id_product = id_product;
        this.name_product = name_product;
        this.quantity_product = quantity_product;
        this.price_product = price_product;
    }


    public Order_Detail(int id, int id_order, int id_product, String name_product, int quantity_product, long price_product) {
        this.id = id;
        this.id_order = id_order;
        this.id_product = id_product;
        this.name_product = name_product;
        this.quantity_product = quantity_product;
        this.price_product = price_product;
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public int getQuantity_product() {
        return quantity_product;
    }

    public void setQuantity_product(int quantity_product) {
        this.quantity_product = quantity_product;
    }

    public long getPrice_product() {
        return price_product;
    }

    public void setPrice_product(long price_product) {
        this.price_product = price_product;
    }

}
