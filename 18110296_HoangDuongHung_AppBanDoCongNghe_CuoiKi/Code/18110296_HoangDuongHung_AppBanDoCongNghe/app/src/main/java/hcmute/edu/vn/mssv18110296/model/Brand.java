package hcmute.edu.vn.mssv18110296.model;

public class Brand {
    private int id;
    private String name;
    private String image;
    private int id_category;

    public Brand() {
    }

    public Brand(int id, String name, String image, int id_category) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.id_category = id_category;
    }

    public Brand(String name, String image, int id_category) {
        this.name = name;
        this.image = image;
        this.id_category = id_category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }
}
