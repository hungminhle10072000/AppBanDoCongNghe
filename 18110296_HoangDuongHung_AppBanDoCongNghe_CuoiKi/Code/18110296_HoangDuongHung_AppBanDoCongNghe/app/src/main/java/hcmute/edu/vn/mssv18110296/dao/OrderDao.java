package hcmute.edu.vn.mssv18110296.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import hcmute.edu.vn.mssv18110296.model.Account;
import hcmute.edu.vn.mssv18110296.model.Orders;
import hcmute.edu.vn.mssv18110296.util.DatabaseHelper;

public class OrderDao {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public OrderDao(Context context) {
        this.context = context;
        sqLiteOpenHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() throws SQLiteException {
        sqLiteOpenHelper.close();
    }

    public int new_order (){
        ArrayList<Orders> arr = new ArrayList<Orders>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Order, null, null, null,null,null,null);
        if (cursor == null){
            return 0;
        } else {
            cursor.moveToLast();
            Orders model = cursorToModel( cursor );
            int kq = model.getId();
            return kq;
        }
    }

    ///delete product
    public void deleteOrder (int id){
        sqLiteDatabase.delete(DatabaseHelper.TABLE_Order, DatabaseHelper.KEY_ID + "=" +id,null);
        Toast.makeText(context, "Xóa lịch sử đơn hàng thành công .", Toast.LENGTH_SHORT).show();
    }

    ///add order
    public void addOrder (int id_customer, String name, String phone, String email, String address){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Key_Id_Customer, id_customer);
        values.put(DatabaseHelper.KEY_name_customer, name);
        values.put(DatabaseHelper.KEY_phone_number, phone);
        values.put(DatabaseHelper.KEY_email, email);
        values.put(DatabaseHelper.Key_Adrress, address);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(DatabaseHelper.Key_Date, dateFormat.format(new Date()));


        ///insert
        sqLiteDatabase.insert(DatabaseHelper.TABLE_Order,null,values);
    }


    ///get all
    public ArrayList<Orders> admin_getAllOrders() {
        ArrayList<Orders> arr = new ArrayList<Orders>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Order, null, null, null, null,null,null);
        if (cursor == null){
            return null;
        } else {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Orders model = cursorToModel (cursor);
                arr.add(model);
                cursor.moveToNext();
            }
        }
        return arr;
    }


    ///get all
    public ArrayList<Orders> getAllOrders_id(int id_nguoidung) {
        ArrayList<Orders> arr = new ArrayList<Orders>();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Order, null, DatabaseHelper.Key_Id_Customer + "=" + id_nguoidung, null, null,null,null);
        if (cursor == null){
            return null;
        } else {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Orders model = cursorToModel (cursor);
                arr.add(model);
                cursor.moveToNext();
            }
        }
        return arr;

    }

    public int count_findbyid (int id){
        Orders orders = new Orders();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Order, null, DatabaseHelper.KEY_ID + "=" + id, null, null,null,null);
        if (cursor.getCount() > 0){
            return 1;
        } else {
            return 0;
        }
    }

    public static Date getDate(Cursor cursor, String columnName) {
        String dateString = cursor.getString(cursor.getColumnIndex(columnName));
        if (dateString == null) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    ///get model from cursor
    private Orders cursorToModel(Cursor cursor){

        Orders model = new Orders();
        model.setId(cursor.getInt(0));
        model.setId_nguoidung(cursor.getInt(1));
        model.setName_customer(cursor.getString(2));
        model.setPhone_number(cursor.getString(3));
        model.setEmail(cursor.getString(4));
        model.setAddress(cursor.getString(5));
        model.setDate(getDate(cursor, "date"));
        return model;
    }

}
