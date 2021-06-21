package hcmute.edu.vn.mssv18110296.model;

import java.util.Date;

public class Orders {
    private int id;
    private int id_nguoidung;
    private String name_customer;
    private String phone_number;
    private String email;
    private String address;
    private Date date;

    public Orders() {
    }

    public Date getDate() {
        return date;
    }

    public Orders(int id_nguoidung, String name_customer, String phone_number, String email, String address, Date date) {
        this.id_nguoidung = id_nguoidung;
        this.name_customer = name_customer;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
        this.date = date;
    }

    public Orders(int id, int id_nguoidung, String name_customer, String phone_number, String email, String address, Date date) {
        this.id = id;
        this.id_nguoidung = id_nguoidung;
        this.name_customer = name_customer;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
        this.date = date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId_nguoidung() {
        return id_nguoidung;
    }

    public void setId_nguoidung(int id_nguoidung) {
        this.id_nguoidung = id_nguoidung;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_customer() {
        return name_customer;
    }

    public void setName_customer(String name_customer) {
        this.name_customer = name_customer;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
