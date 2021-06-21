package hcmute.edu.vn.mssv18110296.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.model.Order_Detail;
import hcmute.edu.vn.mssv18110296.util.DatabaseHelper;

public class OrderDetailDao {

    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private OrderDao orderDao;
    private Context context;

    public OrderDetailDao(Context context) {
        this.context = context;
        sqLiteOpenHelper = new DatabaseHelper(context);
        orderDao = new OrderDao(context);
        orderDao.open();
    }

    public void open() throws SQLException {
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() throws SQLiteException {
        sqLiteOpenHelper.close();
    }

    ///add order
    public void addOrderDetail (int id_order, int id_product, String name_product, int quantity_product, long price_product){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_id_order, id_order);
        values.put(DatabaseHelper.KEY_id_product, id_product);
        values.put(DatabaseHelper.KEY_name_product, name_product);
        values.put(DatabaseHelper.KEY_quantity_product, quantity_product);
        values.put(DatabaseHelper.KEY_price_product, price_product);

        ///insert
        sqLiteDatabase.insert(DatabaseHelper.TABLE_Order_Detail,null,values);
    }

    ///find with id_pro
    public ArrayList<Order_Detail> arrOrder_Detail (int id_pro) {
        ArrayList<Order_Detail> arr = new ArrayList<Order_Detail>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Order_Detail, null, DatabaseHelper.KEY_id_product + "=" + id_pro, null, null,null,null);
        if (cursor == null){
            return null;
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Order_Detail model = cursorToModel (cursor);

                ///find id order
                if (orderDao.count_findbyid(model.getId_order()) > 0){
                    arr.add(model);
                }
                cursor.moveToNext();
            }
        }
        return arr;
    }


    ///find with id_order
    public ArrayList<Order_Detail> getAllOrderdetail_idOrder (int id_order){
        ArrayList<Order_Detail> arr = new ArrayList<Order_Detail>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Order_Detail, null, DatabaseHelper.KEY_id_order + " = " + id_order, null,null,null,null);

        if(cursor == null){
            return null;
        } else {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Order_Detail model = cursorToModel( cursor );
                arr.add(model);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    private Order_Detail cursorToModel(Cursor cursor) {
        Order_Detail order_detail = new Order_Detail();
        order_detail.setId(cursor.getInt(0));
        order_detail.setId_order(cursor.getInt(1));
        order_detail.setId_product(cursor.getInt(2));
        order_detail.setName_product(cursor.getString(3));
        order_detail.setQuantity_product(cursor.getInt(4));
        order_detail.setPrice_product(cursor.getLong(5));
        return order_detail;
    }

}
