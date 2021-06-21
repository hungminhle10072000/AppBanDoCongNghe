package hcmute.edu.vn.mssv18110296.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.model.Brand;
import hcmute.edu.vn.mssv18110296.model.Product;
import hcmute.edu.vn.mssv18110296.util.DatabaseHelper;

public class ProductDao {

    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String[] allColumns = {DatabaseHelper.KEY_ID,DatabaseHelper.KEY_NAME,DatabaseHelper.KEY_price_product,DatabaseHelper.KEY_DESCRIBE,DatabaseHelper.Key_Quantity,DatabaseHelper.Key_PRODUCT_id_brand,DatabaseHelper.KEY_IMAGE};

    private Context context;

    public ProductDao(Context context) {
        this.context = context;
        sqLiteOpenHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() throws SQLiteException {
        sqLiteOpenHelper.close();
    }

    ///add product
    public void addProduct (String name, Integer price, String des,Integer quantity ,Integer id_brand, String image){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NAME, name);
        values.put(DatabaseHelper.KEY_price_product, price);
        values.put(DatabaseHelper.KEY_DESCRIBE, des);
        values.put(DatabaseHelper.Key_Quantity, quantity);
        values.put(DatabaseHelper.Key_PRODUCT_id_brand, id_brand);
        values.put(DatabaseHelper.KEY_IMAGE, image);

        ///insert
        sqLiteDatabase.insert(DatabaseHelper.TABLE_Product,null,values);
    }


    ///update product
    public boolean update (int id, String name, Integer price, String des,Integer quantity, Integer id_brand, String image){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NAME, name);
        values.put(DatabaseHelper.KEY_price_product, price);
        values.put(DatabaseHelper.KEY_DESCRIBE, des);
        values.put(DatabaseHelper.Key_Quantity, quantity);
        values.put(DatabaseHelper.Key_PRODUCT_id_brand, id_brand);
        values.put(DatabaseHelper.KEY_IMAGE, image);

        sqLiteDatabase.update(DatabaseHelper.TABLE_Product,values,DatabaseHelper.KEY_ID + "=" + id,null);
        Toast.makeText(context, "Cập nhật sản phẩm thành công .", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean update (int id,Integer quantity){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Key_Quantity, quantity);
        sqLiteDatabase.update(DatabaseHelper.TABLE_Product,values,DatabaseHelper.KEY_ID + "=" + id,null);
        Toast.makeText(context, "Thanh toán giỏ hàng thành công .", Toast.LENGTH_LONG).show();
        return true;
    }

    ///delete product
    public void deleteProduct (int id){
        sqLiteDatabase.delete(DatabaseHelper.TABLE_Product, DatabaseHelper.KEY_ID + "=" +id,null);
        Toast.makeText(context, "Xóa sản phẩm thành công .", Toast.LENGTH_SHORT).show();
    }

    ///find prodcut
    public Product findProduct (int id){
        Product product = null;
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Product, allColumns, allColumns[0] + " = " + id, null,null,null,null);
        if(cursor == null){
            return null;
        } else {
            cursor.moveToFirst();
            product = cursorToModel( cursor );
        }
        return product;
    }


    ///get all product
    public  ArrayList<Product> user_getAllProducts() {
        ArrayList<Product> arr = new ArrayList<Product>();
        int para = 0;
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Product, allColumns, allColumns[4] + " > " + para, null,null,null,null);
        if(cursor == null){
            return null;
        } else {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Product model = cursorToModel( cursor );
                arr.add(model);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    ///get all product
    public  ArrayList<Product> getAllProducts() {
        ArrayList<Product> arr = new ArrayList<Product>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Product, allColumns, null, null,null,null,null);
        if(cursor == null){
            return null;
        } else {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Product model = cursorToModel( cursor );
                arr.add(model);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    ///get list new product
    public ArrayList<Product> newproductArrayList() {
        ArrayList<Product> arr = new ArrayList<Product>();
        int para = 0;
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Product, allColumns, allColumns[4] + " > " + para, null,null,null,allColumns[0]+" DESC",String.valueOf(10));

        if(cursor == null){
            return null;
        } else {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Product model = cursorToModel( cursor );
                arr.add(model);
                cursor.moveToNext();
            }
        }
        return arr;
    }


    ///get list product with id_brand
    public ArrayList<Product> getListPro_Brand(int id){
        ArrayList<Product> arr = new ArrayList<Product>();
        String whereClause = "quantity > ? AND id_brand = ?";
        String[] whereArgs = new String[] {
                String.valueOf(0),
                String.valueOf(id)
        };
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Product, allColumns, whereClause, whereArgs, null,null,null);
        if (cursor == null){
            return null;
        } else {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Product model = cursorToModel (cursor);
                arr.add(model);
                cursor.moveToNext();
            }
        }
        return arr;
    }


    ///get model from cursor
    private Product cursorToModel(Cursor cursor){

        Product proModel = new Product();
        proModel.setId(cursor.getInt(0));
        proModel.setName(cursor.getString(1));
        proModel.setPrice(cursor.getInt(2));
        proModel.setDescribe(cursor.getString(3));
        proModel.setQuantity(cursor.getInt(4));
        proModel.setId_brand(cursor.getInt(5));
        proModel.setImage(cursor.getString(6));
        return proModel;
    }

}
