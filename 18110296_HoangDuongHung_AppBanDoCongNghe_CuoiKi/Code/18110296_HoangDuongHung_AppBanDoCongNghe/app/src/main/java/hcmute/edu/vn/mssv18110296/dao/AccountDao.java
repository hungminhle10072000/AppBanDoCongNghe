package hcmute.edu.vn.mssv18110296.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.model.Account;
import hcmute.edu.vn.mssv18110296.model.Product;
import hcmute.edu.vn.mssv18110296.util.DatabaseHelper;

public class AccountDao {

    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    private static final String TABLE_Account = "Account";

    public AccountDao(Context context) {
        this.context = context;
        sqLiteOpenHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() throws SQLiteException {
        sqLiteOpenHelper.close();
    }


    ///add account
    public void addAccount (String name, String username, String password, String email, String address , String numberPhone, String image){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NAME, name);
        values.put(DatabaseHelper.KEY_userName, username);
        values.put(DatabaseHelper.KEY_passwordUser, password);
        values.put(DatabaseHelper.KEY_emailUser, email);
        values.put(DatabaseHelper.KEY_addressUser, address);
        values.put(DatabaseHelper.KEY_numberPhoneUser, numberPhone);
        values.put(DatabaseHelper.KEY_IMAGE, image);
        values.put(DatabaseHelper.KEY_roleAccount, 2);

        //insert
        sqLiteDatabase.insert(DatabaseHelper.TABLE_Account,null,values);
    }

    ///Lay ra 1 account
    public int getcountAccount(String username){
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from Account where username = ?", new String[]{username + ""});
        return cursor.getCount();
    }

    ///CheckEmail
    public Account check_email (String username){
        Cursor cursor = sqLiteDatabase.rawQuery("Select id from Account where username =? ",new String[]{username + ""});
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            Account account = account_id(id);
            return account;
        }
        return null;
    }

    ///Kiem tra dang nhap
    public Account login (String username, String password){
        Cursor cursor = sqLiteDatabase.rawQuery("Select id from Account where username =? AND password = ?",new String[]{username + "", password + ""});
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            Account account = account_id(id);
            return account;
        }
        return null;
    }

    ///Tim kiem theo id
    public Account account_id (int id){
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_Account, null, DatabaseHelper.KEY_ID + " = " + id, null,null,null,null,null);
        if(cursor == null){
            return null;
        } else {
            cursor.moveToFirst();
            Account model = cursorToModel(cursor);
            return model;
        }
    }

    public boolean update (int id, String password){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_ID, id);
        values.put(DatabaseHelper.KEY_passwordUser, password);
        sqLiteDatabase.update(DatabaseHelper.TABLE_Account,values,DatabaseHelper.KEY_ID + "=" + id,null);
        return true;
    }

    ///update product
    public boolean update (int id, String name, String username, String password, String email, String address , String numberPhone, String image,int role){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_ID, id);
        values.put(DatabaseHelper.KEY_NAME, name);
        values.put(DatabaseHelper.KEY_userName, username);
        values.put(DatabaseHelper.KEY_passwordUser, password);
        values.put(DatabaseHelper.KEY_emailUser, email);
        values.put(DatabaseHelper.KEY_addressUser, address);
        values.put(DatabaseHelper.KEY_numberPhoneUser, numberPhone);
        values.put(DatabaseHelper.KEY_IMAGE, image);
        values.put(DatabaseHelper.KEY_roleAccount, role);

        sqLiteDatabase.update(DatabaseHelper.TABLE_Account,values,DatabaseHelper.KEY_ID + "=" + id,null);
        return true;
    }


    ///get model from cursor
    private Account cursorToModel(Cursor cursor){

        Account accountModel = new Account();
        accountModel.setId(cursor.getInt(0));
        accountModel.setName(cursor.getString(1));
        accountModel.setUsername(cursor.getString(2));
        accountModel.setPassword(cursor.getString(3));
        accountModel.setEmail(cursor.getString(4));
        accountModel.setAddress(cursor.getString(5));
        accountModel.setNumber_phone(cursor.getString(6));
        accountModel.setImage(cursor.getString(7));
        accountModel.setRole(cursor.getInt(8));
        return accountModel;
    }

}
