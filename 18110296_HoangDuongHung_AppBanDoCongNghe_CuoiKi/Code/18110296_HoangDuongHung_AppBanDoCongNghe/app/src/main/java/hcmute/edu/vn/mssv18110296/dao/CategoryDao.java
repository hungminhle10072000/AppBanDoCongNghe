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

import hcmute.edu.vn.mssv18110296.model.Category;
import hcmute.edu.vn.mssv18110296.util.DatabaseHelper;

public class CategoryDao {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String[] allColumns = {DatabaseHelper.KEY_ID,DatabaseHelper.KEY_NAME,DatabaseHelper.KEY_IMAGE};

    private Context context;

    public CategoryDao (Context context){
        this.context = context;
        sqLiteOpenHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException{
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() throws SQLiteException {
        sqLiteOpenHelper.close();
    }

    ///add category
    public void addCategory(String name, String image){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NAME, name);
        values.put(DatabaseHelper.KEY_IMAGE, image);

        //insert
        sqLiteDatabase.insert(DatabaseHelper.TABLE_Category,null,values);
    }

    ///delete category
    public void deleteCategory (int id) {
        sqLiteDatabase.delete(DatabaseHelper.TABLE_Category, DatabaseHelper.KEY_ID + "=" + id,null);
        Toast.makeText(context, "Xóa danh mục thành công .", Toast.LENGTH_SHORT).show();
    }

    ///update category
    public boolean update (int id, String name, String image){
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("image",image);
        sqLiteDatabase.update(DatabaseHelper.TABLE_Category,values,DatabaseHelper.KEY_ID + "=" + id,null);
        Toast.makeText(context, "Cập nhật danh mục thành công .", Toast.LENGTH_SHORT).show();
        return true;
    }

    ///get all category
    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> arr = new ArrayList<Category>();

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Category, allColumns, null, null, null,null,null);
        if (cursor == null){
            return null;
        } else {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Category model = cursorToModel (cursor);
                arr.add(model);
                cursor.moveToNext();
            }
        }
        return arr;
    }


    ///get NoteModel from cursor
    private Category cursorToModel(Cursor cursor){

        Category categoryModel = new Category();
        categoryModel.setId(cursor.getInt(0));
        categoryModel.setName(cursor.getString(1));
        categoryModel.setImage(cursor.getString(2));

        return categoryModel;
    }
}
