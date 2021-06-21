package hcmute.edu.vn.mssv18110296;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110296.dao.AccountDao;
import hcmute.edu.vn.mssv18110296.dao.OrderDao;
import hcmute.edu.vn.mssv18110296.dao.OrderDetailDao;
import hcmute.edu.vn.mssv18110296.dao.ProductDao;
import hcmute.edu.vn.mssv18110296.model.Cart;
import hcmute.edu.vn.mssv18110296.model.Product;

public class ThongTinKH_ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private Button btn_xacnhan;
    private Button btn_quayve;
    private EditText edt_tennguoimua,edt_diachiemail,edt_sdt,edt_diachi;
    private SharedPreferences sharedPreferences;
    private String prefname="my_data";
    int id_khachhang;
    private String tennguoimua,email,sdt,diachi;
    private OrderDao orderDao;
    private OrderDetailDao orderDetailDao;
    private ProductDao productDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_k_h__thanh_toan);
        sharedPreferences = getSharedPreferences(prefname,MODE_PRIVATE);
        orderDao = new OrderDao(this);
        orderDetailDao = new OrderDetailDao(this);
        productDao = new ProductDao(this);
        orderDao.open();
        productDao.open();
        orderDetailDao.open();

        MatchId();
        LayThongTinUser();
        SetValue();
    }

    private void SetValue() {
        edt_tennguoimua.setText(tennguoimua);
        edt_diachiemail.setText(email);
        edt_sdt.setText(sdt);
        edt_diachi.setText(diachi);
    }

    private void LayThongTinUser() {
        id_khachhang = sharedPreferences.getInt("id_user", 0);
        tennguoimua =sharedPreferences.getString("name","");
        email = sharedPreferences.getString("email","");
        sdt = sharedPreferences.getString("numberphone","");
        diachi = sharedPreferences.getString("address","");
    }

    private void MatchId() {
        back = (ImageView) findViewById(R.id.back_reg);
        btn_xacnhan = (Button) findViewById(R.id.btn_xacnhan);
/*
        btn_quayve = (Button) findViewById(R.id.btn_quayve);
*/
        edt_tennguoimua = (EditText) findViewById(R.id.edt_dk_tennguoidung);
        edt_diachiemail = (EditText) findViewById(R.id.edt_dk_diachiemail);
        edt_diachi = (EditText) findViewById(R.id.edt_dk_diachi);
        edt_sdt = (EditText) findViewById(R.id.edt_dk_sdt);
        btn_xacnhan.setOnClickListener(this);
/*        btn_quayve.setOnClickListener(this);*/
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_reg:
                finish();
                break;
            case R.id.btn_xacnhan:
                if(edt_tennguoimua.getText().toString().trim().length() <= 0){
                    Toast.makeText(getApplicationContext(),"Yêu cầu nhập tên người mua !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edt_diachiemail.getText().toString().trim().length() <= 0){
                    Toast.makeText(getApplicationContext(),"Yêu cầu nhập email người mua !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edt_diachi.getText().toString().trim().length() <= 0){
                    Toast.makeText(getApplicationContext(),"Yêu cầu nhập địa chỉ người mua !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edt_sdt.getText().toString().trim().length() <= 0){
                    Toast.makeText(getApplicationContext(),"Yêu cầu nhập số điện thoại người mua !", Toast.LENGTH_SHORT).show();
                    return;
                }
                orderDao.addOrder(id_khachhang,edt_tennguoimua.getText().toString(),edt_sdt.getText().toString().trim(),
                                    edt_diachiemail.getText().toString().trim(),edt_diachi.getText().toString().trim());
                int new_order = orderDao.new_order();
                for ( Cart item: Home_User.arrayListCart) {
                    Product product = productDao.findProduct(item.getId_product());
                    orderDetailDao.addOrderDetail(new_order,item.getId_product(),item.getName_product(), item.getQuantity_product(),item.getPrice_product());
                    productDao.update(product.getId(), product.getQuantity() - item.getQuantity_product());
                }
                Home_User.arrayListCart.clear();
                Intent quayvetrangchu = new Intent(getApplicationContext(), Home_User.class);
                startActivity(quayvetrangchu);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orderDao.close();
        orderDetailDao.close();
        productDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}