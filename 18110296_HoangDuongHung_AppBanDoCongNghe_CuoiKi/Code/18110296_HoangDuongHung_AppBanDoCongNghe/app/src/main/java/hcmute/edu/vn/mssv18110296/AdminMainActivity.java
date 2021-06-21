package hcmute.edu.vn.mssv18110296;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AdminMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btn_qldanhmuc,btn_qlsanpham,btn_qldonhang,btn_qlnhanhieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        btn_qldanhmuc = (Button) findViewById(R.id.btn_qldanhmuc);
        btn_qlsanpham = (Button) findViewById(R.id.btn_qlsanpham);
        btn_qldonhang = (Button) findViewById(R.id.btn_qldonhang);
        btn_qlnhanhieu = (Button) findViewById(R.id.btn_qlnhanhieu);

        btn_qldanhmuc.setOnClickListener(this);
        btn_qlnhanhieu.setOnClickListener(this);
        btn_qlsanpham.setOnClickListener(this);
        btn_qldonhang.setOnClickListener(this);

        ///get action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Trang chủ");
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_qldanhmuc:
                Intent intent_category = new Intent(this, AllCategoryActivity.class);
                startActivity(intent_category);
                break;
            case R.id.btn_qlnhanhieu:
                Intent intent_nhanhieu = new Intent(this, AdminAllBrand.class);
                startActivity(intent_nhanhieu);
                break;
            case R.id.btn_qlsanpham:
                Intent intent_sanpham = new Intent(this, AdminAllProduct.class);
                startActivity(intent_sanpham);
                break;
            case R.id.btn_qldonhang:
                Intent danhsachdonhang = new Intent(getApplicationContext(), UserHistoryOrder_Activity.class);
                startActivity(danhsachdonhang);
                break;
//            case R.id.btn_qldonhang:
//                Intent danhsachdonhang = new Intent(getApplicationContext(), UserHistoryOrder_Activity.class);
//                startActivity(danhsachdonhang);
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
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}