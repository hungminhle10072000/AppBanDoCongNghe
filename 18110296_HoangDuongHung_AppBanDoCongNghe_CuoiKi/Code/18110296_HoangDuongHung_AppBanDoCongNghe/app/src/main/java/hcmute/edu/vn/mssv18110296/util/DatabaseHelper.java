package hcmute.edu.vn.mssv18110296.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String LOG = "DatabaseHelper";

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "CuaHangCongNghe.sqlite";


    public static final String TABLE_Account = "Account";
    public static final String TABLE_Product = "Product";
    public static final String TABLE_Category = "Category";
    public static final String TABLE_Order = "Orders";
    public static final String TABLE_Order_Detail = "Order_Detail";
    public static final String TABLE_Brand = "Brand";



    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_DESCRIBE = "describe";

    // Account Table - column names
    public static final String KEY_userName = "username";
    public static final String KEY_passwordUser = "password";
    public static final String KEY_emailUser = "email";
    public static final String KEY_addressUser = "address";
    public static final String KEY_numberPhoneUser = "number_phone";
    public static final String KEY_roleAccount = "role";

    /// Brand table - columns names
    public static final String KEY_BRAND_DM = "id_category";


    // Product Table - column names
    public static final String KEY_price_product = "price";
/*    public static final String KEY_id_category = "id_category";*/
    public static final String Key_PRODUCT_id_brand = "id_brand";
    public static final String Key_Quantity = "quantity";

    // Order Table - column names
    public static final String KEY_name_customer = "name_customer";
    public static final String KEY_phone_number = "phone_number";
    public static final String KEY_email = "email";
    public static final String Key_Id_Customer = "id_nguoidung";
    public static final String Key_Adrress = "address";
    public static final String Key_Date = "date";

    // Order_Detail Table - column names
    public static final String KEY_id_order = "id_order";
    public static final String KEY_id_product = "id_product";
    public static final String KEY_name_product = "name_product";
    public static final String KEY_quantity_product = "quantity_product";


    // User table create statement
    public static final String CREATE_TABLE_Account = "CREATE TABLE "
            + TABLE_Account + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME
            + " TEXT," + KEY_userName + " TEXT," + KEY_passwordUser + " TEXT," + KEY_emailUser + " TEXT,"
            + KEY_addressUser + " TEXT," + KEY_numberPhoneUser + " TEXT," + KEY_IMAGE + " TEXT," +  KEY_roleAccount + " INTEGER" + ")";

    // Category table create statement
    public static final String CREATE_TABLE_Category = "CREATE TABLE "
            + TABLE_Category + "(" + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + KEY_NAME
            + " TEXT," + KEY_IMAGE + " TEXT" + ")";

    /// Brand table create statement
    public static final String CREATE_TABLE_Brand = "CREATE TABLE " + TABLE_Brand + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT," + KEY_BRAND_DM + " INTEGER," + KEY_IMAGE + " TEXT" + ")";

    // Product table create statement
    public static final String CREATE_TABLE_Product = "CREATE TABLE "
            + TABLE_Product + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_price_product + " INTEGER,"
            + KEY_DESCRIBE + " TEXT," + Key_Quantity + " INTEGER," + Key_PRODUCT_id_brand + " INTEGER," + KEY_IMAGE + " TEXT" + ")";

    // Order table create statement
    public static final String CREATE_TABLE_Order = "CREATE TABLE " + TABLE_Order + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Key_Id_Customer + " INTEGER,"
            + KEY_name_customer + " TEXT," + KEY_phone_number + " TEXT," + KEY_email + " TEXT," + Key_Adrress + " TEXT," + Key_Date + " DATE" + ")";

    // Order detail create statement
    public static final String CREATE_TABLE_Order_Detail = "CREATE TABLE " + TABLE_Order_Detail + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_id_order + " INTEGER," + KEY_id_product + " INTEGER," + KEY_name_product + " TEXT,"
            + KEY_quantity_product + " INTEGER," + KEY_price_product + " LONG" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Account);
        db.execSQL(CREATE_TABLE_Category);
        db.execSQL(CREATE_TABLE_Product);
        db.execSQL(CREATE_TABLE_Order);
        db.execSQL(CREATE_TABLE_Order_Detail);
        db.execSQL(CREATE_TABLE_Brand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Product);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Account);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Category);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Order);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Order_Detail);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Brand);
        onCreate(db);
    }
}


