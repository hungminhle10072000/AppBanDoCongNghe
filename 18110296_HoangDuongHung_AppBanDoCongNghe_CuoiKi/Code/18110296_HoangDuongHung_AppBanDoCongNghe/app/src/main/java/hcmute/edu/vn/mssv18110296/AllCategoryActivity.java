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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110296.dao.BrandDao;
import hcmute.edu.vn.mssv18110296.dao.CategoryDao;
import hcmute.edu.vn.mssv18110296.model.Category;

public class AllCategoryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private CategoryDao categoryDao;
    private ListView lstCategories;
    private ImageButton btn_back;
    private ImageButton btn_add;
    private TextView lblnocontent;
    private ArrayList<Category> arrCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        lstCategories = (ListView) findViewById(R.id.lst_danhmuc);
        lblnocontent = (TextView) findViewById(R.id.lbl_no_content);
        btn_back = (ImageButton) findViewById(R.id.btn_admin_back);
        btn_add = (ImageButton) findViewById(R.id.btn_admin_add_danhmuc);

        ///get action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Danh mục");
        setSupportActionBar(toolbar);

        ///create data source
        categoryDao = new CategoryDao(this);
        categoryDao.open();

        ///read data source
        viewAllCategories();

        ///Bat su kien ibutton
        btn_back.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        lstCategories.setOnItemClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_admin_back:
                Intent admin_home = new Intent(this, AdminMainActivity.class);
                startActivity(admin_home);
                break;
            case R.id.btn_admin_add_danhmuc:
                Intent admin_add_category = new Intent(this, AdminAddCategory.class);
                startActivity(admin_add_category);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_admin, menu);
        return true;
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

  private void viewAllCategories() {
        //create new thread to get all notes in background task
        new Thread(new Runnable() {
            @Override
            public void run() {
                ///read all note from DB
                arrCategories = categoryDao.getAllCategories();
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            //update UI - display notes on listView
            if (arrCategories != null && arrCategories.size() > 0) {
                lblnocontent.setVisibility(View.INVISIBLE);
                lstCategories.setVisibility(View.VISIBLE);

                /// view all note to listView
                CategoryAdapter adapter = new CategoryAdapter(AllCategoryActivity.this, arrCategories);
                lstCategories.setAdapter(adapter);
            } else {
                lstCategories.setVisibility(View.INVISIBLE);
                lblnocontent.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), AdminViewCatelogy.class);
        intent.putExtra("id",arrCategories.get(i).getId());
        intent.putExtra("name",arrCategories.get(i).getName());
        intent.putExtra("image",arrCategories.get(i).getImage());

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        categoryDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAllCategories();
    }

}