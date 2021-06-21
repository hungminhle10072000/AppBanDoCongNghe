package hcmute.edu.vn.mssv18110296;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.adapter.User_DetailOrdersAdapter;
import hcmute.edu.vn.mssv18110296.dao.OrderDetailDao;
import hcmute.edu.vn.mssv18110296.dao.ProductDao;
import hcmute.edu.vn.mssv18110296.model.Order_Detail;
import hcmute.edu.vn.mssv18110296.model.Product;

public class UserDetailOrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    int id_order;
    String name_customer;
    String phone_number;
    String email;
    String address;
    private String prefname="my_data";
    private SharedPreferences sharedPreferences;
    int role_user;
    private ListView lst_detail;
    private TextView txt_hoten, txt_sodienthoai, txt_email, txt_diachi;
    private OrderDetailDao orderDetailDao;
    private ArrayList<Order_Detail> arrOrderDetail = new ArrayList<>();
    private ProductDao productDao;
    private Toolbar toolbar;
    private TextView txt_kq_tongdon;
    long tongdon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_order);
        sharedPreferences = getSharedPreferences(prefname,MODE_PRIVATE);

        ///create data source
        orderDetailDao = new OrderDetailDao(this);
        productDao = new ProductDao(this);
        productDao.open();
        orderDetailDao.open();

        ///action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Chi tiết đơn hàng");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ///get value intent
        GetValueIntent();
        MacthId();
        SetValue();
        lst_detail.setOnItemClickListener(this);
    }

    private void SetValue() {
        txt_hoten.setText(name_customer);
        txt_sodienthoai.setText(phone_number);
        txt_email.setText(email);
        txt_diachi.setText(address);
        arrOrderDetail = orderDetailDao.getAllOrderdetail_idOrder(id_order);
        User_DetailOrdersAdapter adapter = new User_DetailOrdersAdapter(getApplicationContext(), arrOrderDetail);
        lst_detail.setAdapter(adapter);
        for (Order_Detail item : arrOrderDetail){
            tongdon += item.getPrice_product();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        System.out.println("kq:" + tongdon);
        txt_kq_tongdon.setText(decimalFormat.format(tongdon) + " Đ");
    }

    private void MacthId() {
        txt_hoten = (TextView) findViewById(R.id.txt_hoten);
        txt_sodienthoai = (TextView) findViewById(R.id.txt_sodienthoai);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_diachi = (TextView) findViewById(R.id.txt_diachi);
        lst_detail = (ListView) findViewById(R.id.list_sp_donhang);
        txt_kq_tongdon = (TextView) findViewById(R.id.txt_kq_tong);
    }

    private void GetValueIntent() {
        id_order = getIntent().getExtras().getInt("id");
        name_customer = getIntent().getExtras().getString("name_customer");
        phone_number = getIntent().getExtras().getString("phone_number");
        email = getIntent().getExtras().getString("email");
        address = getIntent().getExtras().getString("address");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orderDetailDao.close();
        productDao.close();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int id_pro = arrOrderDetail.get(position).getId_product();
        Product product = productDao.findProduct(id_pro);
        role_user = sharedPreferences.getInt("role",0);
        if(role_user == 2){
            Intent intent = new Intent(getApplicationContext(),DetailProduct.class).putExtra("thongtinsanpham", (Parcelable) product);
            startActivity(intent);
        }
        if (role_user == 1){
            Intent intent = new Intent(getApplicationContext(),AdminViewDetailProductOrdersActivity.class).putExtra("thongtinsanpham", (Parcelable) product);
            startActivity(intent);
        }

    }
}