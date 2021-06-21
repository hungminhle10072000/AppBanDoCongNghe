package hcmute.edu.vn.mssv18110296;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.adapter.BrandAdapter;
import hcmute.edu.vn.mssv18110296.adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110296.dao.BrandDao;
import hcmute.edu.vn.mssv18110296.dao.ProductDao;
import hcmute.edu.vn.mssv18110296.model.Brand;
import hcmute.edu.vn.mssv18110296.model.Product;

public class AdminAllProduct extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private ImageButton btn_back;
    private ImageButton btn_add;
    private TextView txt_quantity;
    private TextView lblnocontent;
    private ListView lstProduct;
    private ProductDao productDao;
    private ArrayList<Product> arrProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_product);

        ///find view
        lstProduct = (ListView) findViewById(R.id.lst_sanpham) ;
        txt_quantity = (TextView) findViewById(R.id.txt_quantity);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        btn_back = (ImageButton) findViewById(R.id.btn_admin_back);
        btn_add = (ImageButton) findViewById(R.id.btn_admin_add_product);
        lblnocontent = (TextView) findViewById(R.id.lbl_no_content);

        ///bat su kien click
        btn_back.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        lstProduct.setOnItemClickListener(this);

        ///get action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Sản phẩm");
        setSupportActionBar(toolbar);

        ///create data source
        productDao = new ProductDao(this);
        productDao.open();

        ///read data source
        viewAllProducts();
    }

    private void viewAllProducts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ///read all note from DB
                arrProducts = productDao.getAllProducts();
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
                lstProduct.setVisibility(View.VISIBLE);

                /// view all note to listView
                ProductAdapter adapter = new ProductAdapter(AdminAllProduct.this, arrProducts);
                lstProduct.setAdapter(adapter);
            } else {
                lstProduct.setVisibility(View.INVISIBLE);
                lblnocontent.setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_admin_back:
                Intent admin_home = new Intent(this, AdminMainActivity.class);
                startActivity(admin_home);
                break;
            case R.id.btn_admin_add_product:
                Intent admin_add_product = new Intent(this,AdminAddProduct.class);
                startActivity(admin_add_product);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.admin_tt_taikhoan:
                Intent thongtintaikhoan = new Intent(getApplicationContext(), UserInfoAccountActivity.class);
                startActivity(thongtintaikhoan);
            case R.id.admin_qldanhmuc:
                Intent intent =new Intent(this, AllCategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.admin_qlnhanhieu:
                Intent intent_nhanhieu = new Intent(this,AdminAllBrand.class);
                startActivity(intent_nhanhieu);
                break;
            case R.id.admin_qlsanpham:
                Intent intent_sanpham = new Intent(this,AdminAllProduct.class);
                startActivity(intent_sanpham);
                break;
            case R.id.admin_qldonhang:
                Intent danhsachdonhang = new Intent(getApplicationContext(), UserHistoryOrder_Activity.class);
                startActivity(danhsachdonhang);
                break;
            case R.id.admin_thoat:
                Intent intent_thoat =new Intent(this, LoginActivity.class);
                startActivity(intent_thoat);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_admin, menu);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), AdminViewProduct.class);
        intent.putExtra("id",arrProducts.get(i).getId());
        intent.putExtra(("name"),arrProducts.get(i).getName());
        intent.putExtra("price",arrProducts.get(i).getPrice());
        intent.putExtra("quantity",arrProducts.get(i).getQuantity());
        intent.putExtra("des",arrProducts.get(i).getDescribe());
        intent.putExtra("image",arrProducts.get(i).getImage());
        intent.putExtra("id_brand",arrProducts.get(i).getId_brand());
        startActivity(intent);
    }
}