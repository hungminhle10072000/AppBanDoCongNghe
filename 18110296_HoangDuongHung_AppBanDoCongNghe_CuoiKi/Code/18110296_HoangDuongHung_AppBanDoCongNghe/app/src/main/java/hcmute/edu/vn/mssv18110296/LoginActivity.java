package hcmute.edu.vn.mssv18110296;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110296.dao.AccountDao;
import hcmute.edu.vn.mssv18110296.model.Account;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtDangKy, txtQuenMatKhau;
    Button btn_dangnhap;
    String tb_dangki;
    private AccountDao accountDao;
    EditText editusername, editpassword;
    private SharedPreferences sharedPreferences;
    private String prefname="my_data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(prefname,MODE_PRIVATE);
        try {
            tb_dangki = getIntent().getExtras().getString("thongbaothanhcong");
            if(tb_dangki != null){
                Toast.makeText(getApplicationContext(),tb_dangki,Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
           e.printStackTrace();
        } finally {
            editusername = (EditText) findViewById(R.id.editusername);
            editpassword = (EditText) findViewById(R.id.editpassword);
            txtDangKy = (TextView) findViewById(R.id.textdangky);
            txtDangKy.setOnClickListener(this);
            txtQuenMatKhau = (TextView) findViewById(R.id.textquenmatkhau);
            txtQuenMatKhau.setOnClickListener(this);
            btn_dangnhap = (Button) findViewById(R.id.btndangnhap);
            btn_dangnhap.setOnClickListener(this);
            accountDao = new AccountDao(this);
            accountDao.open();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btndangnhap:
                Account kq = accountDao.login(editusername.getText().toString().trim(),editpassword.getText().toString().trim());
                if (kq == null){
                    Toast.makeText(this,"Tên đăng nhập hoặc mật khẩu bạn nhập không chính xác!",Toast.LENGTH_LONG).show();
                }
                else if (kq.getRole() == 1){
                    Intent intent_manhinhchinh = new Intent(getApplicationContext(), AdminMainActivity.class);
                    LuuThongTinDangNhap(kq);
                    startActivity(intent_manhinhchinh);
                }
                else if (kq.getRole() == 2){
                    Intent intent_home_user = new Intent(getApplicationContext(), Home_User.class);
                    LuuThongTinDangNhap(kq);
                    startActivity(intent_home_user);
                }
                break;
            case R.id.textdangky:
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.textquenmatkhau:
                Intent intent_qmk = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(intent_qmk);
                break;
            default:
                break;
        }
    }

    protected void LuuThongTinDangNhap(Account account){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id_user",account.getId());
        editor.putString("name",account.getName());
        editor.putString("username",account.getUsername());
        editor.putString("password",account.getPassword());
        editor.putString("email",account.getEmail());
        editor.putString("address",account.getAddress());
        editor.putString("numberphone",account.getNumber_phone());
        editor.putString("image",account.getImage());
        editor.putInt("role",account.getRole());
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accountDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}