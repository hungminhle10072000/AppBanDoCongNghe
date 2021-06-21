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

import hcmute.edu.vn.mssv18110296.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110296.dao.CategoryDao;
import hcmute.edu.vn.mssv18110296.model.Category;

public class User_Category_Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private Toolbar mytoolbar;
    private CategoryDao categoryDao;
    private ListView lstCategories;
    private TextView lblnocontent;
    private ArrayList<Category> arrCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__category_);

        lstCategories = (ListView) findViewById(R.id.lst_danhmuc);
        lblnocontent = (TextView) findViewById(R.id.lbl_no_content);

        ///set action bar
        mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mytoolbar.setTitle("Danh mục sản phẩm");
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ///create data source
        categoryDao = new CategoryDao(this);
        categoryDao.open();

        ///read data source
        viewAllCategories();

        lstCategories.setOnItemClickListener(this);

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
                CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(), arrCategories);
                lstCategories.setAdapter(adapter);
            } else {
                lstCategories.setVisibility(View.INVISIBLE);
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
        categoryDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAllCategories();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent danhsach_sanpham = new Intent(getApplicationContext(), User_Product_Category_Activity.class);
        danhsach_sanpham.putExtra("id_danhmuc",arrCategories.get(position).getId());
        danhsach_sanpham.putExtra("ten_danhmuc",arrCategories.get(position).getName());
        startActivity(danhsach_sanpham);
    }
}