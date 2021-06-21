package hcmute.edu.vn.mssv18110296.model;

public class Account {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String address;
    private String number_phone;
    private String image;
    private int role;

    public Account() {
    }

    public Account(int id, String name, String username, String password, String email, String address, String number_phone, String image, int role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.number_phone = number_phone;
        this.image = image;
        this.role = role;
    }

    public Account(String name, String username, String password, String email, String address, String number_phone, String image, int role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.number_phone = number_phone;
        this.image = image;
        this.role = role;
    }

    public Account(String name, String username, String password, String email, String address, String number_phone, String image) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.number_phone = number_phone;
        this.image = image;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber_phone() {
        return number_phone;
    }

    public void setNumber_phone(String number_phone) {
        this.number_phone = number_phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
