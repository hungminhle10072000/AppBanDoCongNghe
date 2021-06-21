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
import hcmute.edu.vn.mssv18110296.model.Category;
import hcmute.edu.vn.mssv18110296.util.DatabaseHelper;

public class BrandDao {

    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String[] allColumns = {DatabaseHelper.KEY_ID,DatabaseHelper.KEY_NAME,DatabaseHelper.KEY_IMAGE,DatabaseHelper.KEY_BRAND_DM};

    private Context context;

    public BrandDao(Context context) {
        this.context = context;
        sqLiteOpenHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() throws SQLiteException {
        sqLiteOpenHelper.close();
    }

    ///add brand
    public void addBrand (String name, Integer id_category, String image){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NAME, name);
        values.put(DatabaseHelper.KEY_BRAND_DM, id_category);
        values.put(DatabaseHelper.KEY_IMAGE, image);

        //insert
        sqLiteDatabase.insert(DatabaseHelper.TABLE_Brand,null,values);
    }

    ///update brand
    public boolean update (int id, String name, Integer id_category ,String image){

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NAME, name);
        values.put(DatabaseHelper.KEY_BRAND_DM, id_category);
        values.put(DatabaseHelper.KEY_IMAGE, image);

        sqLiteDatabase.update(DatabaseHelper.TABLE_Brand,values,DatabaseHelper.KEY_ID + "=" + id,null);
        Toast.makeText(context, "Cập nhật nhãn hiệu thành công .", Toast.LENGTH_SHORT).show();
        return true;
    }

    ///delete brand
    public void deleteBrand (int id) {
        sqLiteDatabase.delete(DatabaseHelper.TABLE_Brand, DatabaseHelper.KEY_ID + "=" + id,null);
        Toast.makeText(context, "Xóa nhãn hiệu thành công .", Toast.LENGTH_SHORT).show();
    }

    ///get list brand with id_category
    public ArrayList<Brand> getListBrand_Category(int id){
        ArrayList<Brand> arr = new ArrayList<Brand>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Brand, allColumns, allColumns[3] + " = " + id, null, null,null,null);
        if (cursor == null){
            return null;
        } else {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Brand model = cursorToModel (cursor);
                arr.add(model);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    ///get all
    public ArrayList<Brand> getAllBrands(){
        ArrayList<Brand> arr = new ArrayList<Brand>();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Brand, allColumns, null, null, null,null,null);
        if (cursor == null){
            return null;
        } else {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Brand model = cursorToModel (cursor);
                arr.add(model);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    ///get model from cursor
    private Brand cursorToModel(Cursor cursor){

        Brand brandModel = new Brand();
        brandModel.setId(cursor.getInt(0));
        brandModel.setName(cursor.getString(1));
        brandModel.setImage(cursor.getString(2));
        brandModel.setId_category(cursor.getInt(3));
        return brandModel;
    }


}
