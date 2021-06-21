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
import hcmute.edu.vn.mssv18110296.dao.BrandDao;
import hcmute.edu.vn.mssv18110296.model.Brand;

public class AdminAllBrand extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private ImageButton btn_back;
    private ImageButton btn_add;
    private TextView lblnocontent;
    private ListView lstBrands;
    private BrandDao brandDao;
    private ArrayList<Brand> arrBrands = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_brand);

        lstBrands = (ListView) findViewById(R.id.lst_nhanhieu);
        btn_back = (ImageButton) findViewById(R.id.btn_admin_back);
        btn_add = (ImageButton) findViewById(R.id.btn_admin_add_brand);
        lblnocontent = (TextView) findViewById(R.id.lbl_no_content);

        ///Bat su kien click cua button
        btn_back.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        lstBrands.setOnItemClickListener(this);

        ///get action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Nhãn hiệu");
        setSupportActionBar(toolbar);

        ///create data source
        brandDao = new BrandDao(this);
        brandDao.open();

        ///read data source
        viewAllBrands();



    }

    private void viewAllBrands() {
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

            //update UI
            if (arrBrands != null && arrBrands.size() > 0) {
                lblnocontent.setVisibility(View.INVISIBLE);
                lstBrands.setVisibility(View.VISIBLE);

                /// view all note to listView
                BrandAdapter adapter = new BrandAdapter(AdminAllBrand.this, arrBrands);
                lstBrands.setAdapter(adapter);
            } else {
                lstBrands.setVisibility(View.INVISIBLE);
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
            case R.id.btn_admin_add_brand:
                Intent admin_add_category = new Intent(this,AdminAddBrand.class);
                startActivity(admin_add_category);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.admin_tt_taikhoan:
                Intent tt_taikhoan = new Intent(this, UserInfoAccountActivity.class);
                startActivity(tt_taikhoan);
                break;
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
        brandDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAllBrands();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), AdminViewBrand.class);
        intent.putExtra("id",arrBrands.get(i).getId());
        intent.putExtra("name",arrBrands.get(i).getName());
        intent.putExtra("id_category",arrBrands.get(i).getId_category());
        intent.putExtra("image",arrBrands.get(i).getImage());

        startActivity(intent);
    }
}