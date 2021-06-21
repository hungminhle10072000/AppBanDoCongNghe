package hcmute.edu.vn.mssv18110296;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.DecimalFormat;

import hcmute.edu.vn.mssv18110296.adapter.CartAdapter;
import hcmute.edu.vn.mssv18110296.model.Cart;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class User_Cart extends AppCompatActivity implements View.OnClickListener {

    ListView lvgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan, btntieptucmua;
    Toolbar mytoolbar;
    CartAdapter cartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__cart);
        MatchId();
        CheckData();
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TongTienGioHang();
        DeleteCart();
    }

    private void DeleteCart() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(User_Cart.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Home_User.arrayListCart.size() <= 0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        } else {
                            Home_User.arrayListCart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            TongTienGioHang();
                            if (Home_User.arrayListCart.size() <= 0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            } else {
                                txtthongbao.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                TongTienGioHang();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartAdapter.notifyDataSetChanged();
                        TongTienGioHang();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void TongTienGioHang() {
        long tongtien = 0;
        for (int i=0;i<Home_User.arrayListCart.size(); i++ ){
            tongtien += Home_User.arrayListCart.get(i).getPrice_product();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + "Đ");

    }

    private void CheckData() {
        if (Home_User.arrayListCart.size() <= 0){
            lvgiohang.deferNotifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        } else {
            lvgiohang.deferNotifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void MatchId() {
        lvgiohang = (ListView) findViewById(R.id.lstgiohang);
        txtthongbao = (TextView) findViewById(R.id.textviewthongbao);
        txttongtien = (TextView) findViewById(R.id.tongtien);
        btnthanhtoan = (Button) findViewById(R.id.btn_thanhtoangiohang);
        btntieptucmua = (Button) findViewById(R.id.btn_tieptucmuahang);
        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        cartAdapter = new CartAdapter(User_Cart.this, Home_User.arrayListCart);
        lvgiohang.setAdapter(cartAdapter);
        btntieptucmua.setOnClickListener(this);
        btnthanhtoan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_thanhtoangiohang:
                if(Home_User.arrayListCart.size() > 0){
                    Intent thanhtoan = new Intent(getApplicationContext(), ThongTinKH_ThanhToanActivity.class);
                    startActivity(thanhtoan);
                } else {

                    Toast.makeText(getApplicationContext(), "Giỏ hàng của bạn chưa có sản phẩm !",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_tieptucmuahang:
                Intent intent_vetrangchu = new Intent(getApplicationContext(), Home_User.class);
                startActivity(intent_vetrangchu);
                break;
        }
    }
}