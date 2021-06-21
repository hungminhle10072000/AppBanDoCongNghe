package hcmute.edu.vn.mssv18110296;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110296.model.Cart;
import hcmute.edu.vn.mssv18110296.model.Product;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class DetailProduct extends AppCompatActivity {

    Toolbar mytoolbar;
    ImageView img_chitiet;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button btndatmua;
    ////Khoi tao gia tri
    int id = 0;
    String tenchitiet = "";
    int giachitiet = 0;
    String hinhanhmota = "";
    String mota = "";
    int sl_sanpham = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        MatchId();
        SetValue();
        CatchEventSpinner();
        EventButton();
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Home_User.arrayListCart.size() > 0){
                    int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i =0; i< Home_User.arrayListCart.size(); i++){
                        if (Home_User.arrayListCart.get(i).getId_product() == id){
                            Home_User.arrayListCart.get(i).setQuantity_product(Home_User.arrayListCart.get(i).getQuantity_product() + quantity);
                            if(Home_User.arrayListCart.get(i).getQuantity_product() >= 10){
                                Home_User.arrayListCart.get(i).setQuantity_product(10);
                            }
                            Home_User.arrayListCart.get(i).setPrice_product(giachitiet * Home_User.arrayListCart.get(i).getQuantity_product());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        quantity = Integer.parseInt(spinner.getSelectedItem().toString());
                        int newprice = quantity * giachitiet;
                        Home_User.arrayListCart.add(new Cart(id, tenchitiet, newprice, hinhanhmota,quantity));
                    }
                } else {
                    int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
                    int newprice = quantity * giachitiet;
                    Home_User.arrayListCart.add(new Cart(id, tenchitiet, newprice, hinhanhmota,quantity));
                }
                Intent intent = new Intent(getApplicationContext(), User_Cart.class);
                startActivity(intent);
            }
        });
    }


    private void CatchEventSpinner() {
        List<Integer> soluong = new ArrayList<Integer>();
        if (sl_sanpham > 10){
            sl_sanpham = 10;
        }
        if(sl_sanpham == 0){
            soluong.add(0);
        }
        for (int i=1 ; i<= sl_sanpham; i++){
            soluong.add(i);
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void SetValue() {

        //get value of product
        Product sanpham = (Product) getIntent().getExtras().getParcelable("thongtinsanpham");
        id = sanpham.getId();
        tenchitiet = sanpham.getName();
        giachitiet = sanpham.getPrice();
        hinhanhmota = sanpham.getImage();
        mota = sanpham.getDescribe();
        sl_sanpham = sanpham.getQuantity();

        txtten.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá : " + decimalFormat.format(giachitiet) + " Đ");
        txtmota.setText(mota);
        Util.setBitmapToImage(this, Config.FOLDER_IMAGES, hinhanhmota, img_chitiet);
    }

    private void MatchId() {
        ///find id
        mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mytoolbar.setTitle("Chi tiết sản phẩm");
        img_chitiet = (ImageView) findViewById(R.id.imv_chitietsanpham);
        txtten = (TextView) findViewById(R.id.txt_user_tenchitietsanpham);
        txtgia = (TextView) findViewById(R.id.txt_user_giachitietsanpham);
        txtmota = (TextView) findViewById(R.id.txt_motasanpham);
        spinner = (Spinner) findViewById(R.id.spinner);
        btndatmua = (Button) findViewById(R.id.btn_datmua);
    }
}