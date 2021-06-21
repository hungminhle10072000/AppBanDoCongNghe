package hcmute.edu.vn.mssv18110296;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

/////-------------------------------------------------
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.adapter.ProductNewAdapter;
import hcmute.edu.vn.mssv18110296.dao.ProductDao;
import hcmute.edu.vn.mssv18110296.model.Cart;
import hcmute.edu.vn.mssv18110296.model.Product;

public class Home_User extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    public static ArrayList<Cart> arrayListCart ;
    private SharedPreferences sharedPreferences;
    private String prefname="my_data";

    ///Danh sach san pham
    RecyclerView recyclerView;

    ////flipper
    private float startX;
    ViewFlipper v_flipper;

    private ProductDao productDao;
    private ArrayList<Product> arrProducts = new ArrayList<>();
    ProductNewAdapter productNewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__user);
        sharedPreferences = getSharedPreferences(prefname,MODE_PRIVATE);

        ///danh sach san pham
        productDao = new ProductDao(this);
        productDao.open();
        arrProducts = productDao.newproductArrayList();

        ///find id
        MatchId();

        ///view filipper
        int images[] = {R.drawable.qc1, R.drawable.qc2, R.drawable.qc3, R.drawable.qc4, R.drawable.qc5, R.drawable.qc6, R.drawable.qc7};

        v_flipper = findViewById(R.id.v_flipper);

        for (int image : images){
            flipperImages(image);
        }

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.lichsu_muahang:
                        Intent lichsumuahng = new Intent(getApplicationContext(), UserHistoryOrder_Activity.class);
                        startActivity(lichsumuahng);
                        break;
                    case  R.id.nav_home:
                        Intent intent = new Intent(Home_User.this, Home_User.class);
                        startActivity(intent);
                        break;
                    case  R.id.sp_danhmuc:
                        Intent itent_danhmuc = new Intent(getApplicationContext(), User_Category_Activity.class);
                        startActivity(itent_danhmuc);
                        break;
                    case R.id.tatca_sanpham:
                        Intent intent_tatcasanpham = new Intent(getApplicationContext(), User_All_Product_Activity.class);
                        startActivity(intent_tatcasanpham);
                        break;
                    case R.id.user_thoat:
                        Intent user_thoat = new Intent(getApplicationContext(), LoginActivity.class);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Home_User.arrayListCart.clear();
                        startActivity(user_thoat);
                        break;
                    case R.id.thongtin_taikhoan:
                        Intent thongtintaikhoan = new Intent(getApplicationContext(), UserInfoAccountActivity.class);
                        startActivity(thongtintaikhoan);
                        break;
                }
                return false;
            }
        });
    }

    private void MatchId() {
        recyclerView =findViewById(R.id.recycler_view);
        productNewAdapter = new ProductNewAdapter(getApplicationContext(), arrProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(productNewAdapter);

        if (arrayListCart !=null){

        } else {
            arrayListCart = new ArrayList<>();
        }
    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Trang chủ");
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

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

    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(5000);
        v_flipper.setAutoStart(true);

        v_flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();

                        //swipe right
                        if (startX < endX) {
                            Home_User.this.v_flipper.showNext();
                        }

                        //swipe left
                        if (startX > endX) {
                            Home_User.this.v_flipper.showPrevious();
                        }

                        break;
                }
                return true;
            }
        });

        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setInAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}