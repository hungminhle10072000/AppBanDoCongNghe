package hcmute.edu.vn.mssv18110296;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.adapter.BrandAdapter;
import hcmute.edu.vn.mssv18110296.adapter.User_HistoryOrderAdapter;
import hcmute.edu.vn.mssv18110296.dao.BrandDao;
import hcmute.edu.vn.mssv18110296.dao.OrderDao;
import hcmute.edu.vn.mssv18110296.model.Brand;
import hcmute.edu.vn.mssv18110296.model.Orders;

public class UserHistoryOrder_Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private TextView lblnocontent;
    private ListView lstOrders;
    private OrderDao orderDao;
    private ArrayList<Orders> arrOders = new ArrayList<>();
    private String prefname="my_data";
    private SharedPreferences sharedPreferences;
    int id, role_user;
    private String name,username,password,email,address,password2,numberphone,role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_order_);
        sharedPreferences = getSharedPreferences(prefname,MODE_PRIVATE);

        ///lay id nguoi dung
        GetInfoAccount();

        ///action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        if(role_user == 1){
            toolbar.setTitle("Danh sách đơn hàng");
        }
        if(role_user == 2){
            toolbar.setTitle("Lịch sử mua hàng");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ///find id
        lblnocontent = (TextView) findViewById(R.id.lbl_no_content);
        lstOrders = (ListView) findViewById(R.id.lst_donhang) ;


        ///create data source
        orderDao = new OrderDao(this);
        orderDao.open();

        ///read data source
        viewAllOrders();
        lstOrders.setOnItemClickListener(this);

        DeleteOrder();
    }

    private void DeleteOrder() {
        lstOrders.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserHistoryOrder_Activity.this);
                builder.setTitle("Xác nhận xóa lịch sử đơn hàng");
                builder.setMessage("Bạn có chắc muốn xóa lịch sử đơn hàng này ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderDao.deleteOrder(arrOders.get(position).getId());
                        viewAllOrders();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                return true;
            }
        });
    }


    private void viewAllOrders() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ///read all note from DB
                if(role_user == 1){
                    arrOders = orderDao.admin_getAllOrders();
                }

                if(role_user == 2){
                    arrOders = orderDao.getAllOrders_id(id);
                }

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
            if (arrOders != null && arrOders.size() > 0) {
                lblnocontent.setVisibility(View.INVISIBLE);
                lstOrders.setVisibility(View.VISIBLE);

                /// view all note to listView
                User_HistoryOrderAdapter adapter = new User_HistoryOrderAdapter(getApplicationContext(), arrOders);
                lstOrders.setAdapter(adapter);
            } else {
                lstOrders.setVisibility(View.INVISIBLE);
                lblnocontent.setVisibility(View.VISIBLE);
            }
        }
    };


    private void GetInfoAccount() {
        id = sharedPreferences.getInt("id_user", 0);
        name =sharedPreferences.getString("name","");
        username = sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");
        email = sharedPreferences.getString("email","");
        numberphone = sharedPreferences.getString("numberphone","");
        address = sharedPreferences.getString("address","");
        role_user = sharedPreferences.getInt("role",0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orderDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAllOrders();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), UserDetailOrderActivity.class);
        intent.putExtra("id",arrOders.get(position).getId());
        intent.putExtra("name_customer",arrOders.get(position).getName_customer());
        intent.putExtra("phone_number",arrOders.get(position).getPhone_number());
        intent.putExtra("email",arrOders.get(position).getEmail());
        intent.putExtra("address",arrOders.get(position).getAddress());
        startActivity(intent);
    }

}