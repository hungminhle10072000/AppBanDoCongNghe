package hcmute.edu.vn.mssv18110296;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.adapter.SpinnerBrandAdapter;
import hcmute.edu.vn.mssv18110296.dao.BrandDao;
import hcmute.edu.vn.mssv18110296.dao.ProductDao;
import hcmute.edu.vn.mssv18110296.model.Brand;
import hcmute.edu.vn.mssv18110296.model.Product;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class AdminViewDetailProductOrdersActivity extends AppCompatActivity {

    //constant
    public final int PICK_PHOTO_FOR_NOTE = 0 ;
    private ImageView imageAttach;
    private EditText nameProduct;
    private EditText quantityProduct;
    private EditText priceProduct;
    private BitmapDrawable drawable;
    private EditText desProduct;
    private Toolbar toolbar;
    private Bitmap bmpAttach;
    private BrandDao brandDao;
    private ProductDao productDao;
    private ArrayList<Brand> arrBrands = new ArrayList<>();
    private Spinner spn_nhanhieu;
    private int id,id_brand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_detail_product_orders);

        ///get action bar
        ///action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Chi tiết sản phẩm");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ///find id
        imageAttach = (ImageView) findViewById(R.id.img_attach);
        nameProduct = (EditText) findViewById(R.id.edt_name_product);
        quantityProduct = (EditText) findViewById(R.id.edt_quantity_product);
        spn_nhanhieu = (Spinner) findViewById(R.id.spn_nhanhieu);
        priceProduct = (EditText) findViewById(R.id.edt_price_product);
        desProduct = (EditText) findViewById(R.id.edt_des_pro);

        spn_nhanhieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_brand = arrBrands.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //get value of product
        Product sanpham = (Product) getIntent().getExtras().getParcelable("thongtinsanpham");

        ///set value
        id = sanpham.getId();
        String image = sanpham.getImage();
        id_brand = sanpham.getId_brand();
        nameProduct.setText(sanpham.getName());
        quantityProduct.setText(String.valueOf(sanpham.getQuantity()));
        priceProduct.setText(String.valueOf(sanpham.getPrice()));
        desProduct.setText(sanpham.getDescribe());
        Util.setBitmapToImage(this, Config.FOLDER_IMAGES, image, imageAttach);

        ///Lay du lieu cho spinner
        brandDao = new BrandDao(this);
        brandDao.open();
        viewAllBrands();

    }

    private void viewAllBrands(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ///read all note from DB
                arrBrands = brandDao.getAllBrands();
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            SpinnerBrandAdapter spn_adapter = new SpinnerBrandAdapter(getApplicationContext(),arrBrands);
            spn_nhanhieu.setAdapter(spn_adapter);
            for(int i=0; i<arrBrands.size(); i++){
                if(id_brand == arrBrands.get(i).getId()){
                    spn_nhanhieu.setSelection(i);
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        brandDao.close();
        brandDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAllBrands();
    }

}