package hcmute.edu.vn.mssv18110296;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110296.adapter.SpinnerBrandAdapter;
import hcmute.edu.vn.mssv18110296.adapter.User_ProductAdapter;
import hcmute.edu.vn.mssv18110296.dao.BrandDao;
import hcmute.edu.vn.mssv18110296.dao.ProductDao;
import hcmute.edu.vn.mssv18110296.model.Brand;
import hcmute.edu.vn.mssv18110296.model.Product;

public class User_Product_Category_Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Toolbar mytoolbar;
    private Spinner spn_hang;
    private BrandDao brandDao;
    private ArrayList<Brand> arrBrands = new ArrayList<>();
    private ProductDao productDao;
    private ArrayList<Product> arrProducts = new ArrayList<>();
    int id_danhmuc;
    private TextView lblnocontent;
    private ListView lst_user_sanpham;
    int id_brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__product__category_);

        //get value of category
        id_danhmuc = getIntent().getExtras().getInt("id_danhmuc");
        String name = getIntent().getExtras().getString("ten_danhmuc");

        ///find match id
        spn_hang = (Spinner) findViewById(R.id.spn_hang);
        lblnocontent = (TextView) findViewById(R.id.lbl_no_content);
        lst_user_sanpham = (ListView) findViewById(R.id.lst_user_sanpham);

        ///set action bar
        mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mytoolbar.setTitle(name);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ///Lay du lieu cho spinner
        brandDao = new BrandDao(this);
        brandDao.open();
        viewAllBrands();

        ///set value spinner
        SetSpinner();

        ///lay du lieu san pham
        productDao = new ProductDao(this);
        productDao.open();

        ///read data source
        viewAllProducts();

        //bat su kien spinner
        spn_hang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_brand = arrBrands.get(position).getId();
                viewAllProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///bat su kien item click
        lst_user_sanpham.setOnItemClickListener(this);
    }

    private void SetSpinner() {
        SpinnerBrandAdapter spn_adapter = new SpinnerBrandAdapter(getApplicationContext(),arrBrands);
        spn_hang.setAdapter(spn_adapter);
    }

    private void viewAllBrands() {
/*        arrBrands = brandDao.getAllBrands();*/
        arrBrands = brandDao.getListBrand_Category(id_danhmuc);
        if (arrBrands.size() > 0){
            id_brand = arrBrands.get(0).getId();
        }
    }

    private void viewAllProducts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ///read all note from DB
/*                arrProducts = productDao.getAllProducts();*/
                arrProducts = productDao.getListPro_Brand(id_brand);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            //update UI
            if (arrProducts != null && arrProducts.size() > 0) {
                lblnocontent.setVisibility(View.INVISIBLE);
                lst_user_sanpham.setVisibility(View.VISIBLE);

                /// view all note to listView
                User_ProductAdapter adapter = new User_ProductAdapter(getApplicationContext(), arrProducts);
                lst_user_sanpham.setAdapter(adapter);
            } else {
                lst_user_sanpham.setVisibility(View.INVISIBLE);
                lblnocontent.setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.giohang:
                Intent giohang = new Intent(getApplicationContext(),User_Cart.class);
                startActivity(giohang);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        brandDao.close();
        productDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAllBrands();
        viewAllProducts();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(),DetailProduct.class).putExtra("thongtinsanpham", (Parcelable) arrProducts.get(position));
        startActivity(intent);
    }
}