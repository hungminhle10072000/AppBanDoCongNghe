package hcmute.edu.vn.mssv18110296.model;

public class Cart {
    private int id_product;
    private String name_product;
    private long price_product;
    private String image_product;
    private int quantity_product;

    public Cart() {
    }

    public Cart(int id_product, String name_product, int price_product, String image_product, int quantity_product) {
        this.id_product = id_product;
        this.name_product = name_product;
        this.price_product = price_product;
        this.image_product = image_product;
        this.quantity_product = quantity_product;
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

    public long getPrice_product() {
        return price_product;
    }

    public void setPrice_product(long price_product) {
        this.price_product = price_product;
    }

    public String getImage_product() {
        return image_product;
    }

    public void setImage_product(String image_product) {
        this.image_product = image_product;
    }

    public int getQuantity_product() {
        return quantity_product;
    }

    public void setQuantity_product(int quantity_product) {
        this.quantity_product = quantity_product;
    }
}
