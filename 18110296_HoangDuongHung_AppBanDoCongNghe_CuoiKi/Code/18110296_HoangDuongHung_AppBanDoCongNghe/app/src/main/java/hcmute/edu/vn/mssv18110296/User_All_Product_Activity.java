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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.adapter.User_ProductAdapter;
import hcmute.edu.vn.mssv18110296.dao.ProductDao;
import hcmute.edu.vn.mssv18110296.model.Product;

public class User_All_Product_Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Toolbar mytoolbar;
    private ProductDao productDao;
    private ArrayList<Product> arrProducts = new ArrayList<>();
    private TextView lblnocontent;
    private ListView lst_user_sanpham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__all__product_);

        ///find match id
        lblnocontent = (TextView) findViewById(R.id.lbl_no_content);
        lst_user_sanpham = (ListView) findViewById(R.id.lst_user_sanpham);

        ///set action bar
        mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mytoolbar.setTitle("Sản phẩm");
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ///lay du lieu san pham
        productDao = new ProductDao(this);
        productDao.open();

        ///read data source
        viewAllProducts();

        ///bat su kien list view
        lst_user_sanpham.setOnItemClickListener(this);
    }

    private void viewAllProducts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ///read all note from DB
                arrProducts = productDao.user_getAllProducts();
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
        productDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAllProducts();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(),DetailProduct.class).putExtra("thongtinsanpham", (Parcelable) arrProducts.get(position));
        startActivity(intent);
    }
}