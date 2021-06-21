package hcmute.edu.vn.mssv18110296;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110296.R;
import hcmute.edu.vn.mssv18110296.dao.AccountDao;
import hcmute.edu.vn.mssv18110296.model.Account;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {

    ImageView btn_back_reset;
    String ActivityResult;
    Button btn_gui;
    EditText txt_matkhau, txt_matkhaunhaplai;
    String matkhau,matkhaunhaplai,username;
    private AccountDao accountDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        accountDao = new AccountDao(this);
        accountDao.open();
        btn_back_reset = (ImageView) findViewById(R.id.btn_back_reset);
        btn_gui = (Button) findViewById(R.id.btn_gui);
        txt_matkhau = (EditText) findViewById(R.id.txt_matkhau);
        txt_matkhaunhaplai = (EditText) findViewById(R.id.txt_matkhaunhaplai);
        btn_gui.setOnClickListener(this);
        ActivityResult = getIntent().getStringExtra("email");
        btn_back_reset.setOnClickListener(this);
        username = getIntent().getExtras().getString("username");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back_reset:
                Intent backIntent = new Intent();
                backIntent.putExtra("ActivityResult",ActivityResult);
                setResult(RESULT_OK,backIntent);
                finish();
                break;
            case R.id.btn_gui:
                matkhau = txt_matkhau.getText().toString().trim();
                matkhaunhaplai = txt_matkhaunhaplai.getText().toString().trim();
                if (matkhau.length() <= 0){
                    Toast.makeText(getApplicationContext(), "Yêu cầu nhập mật khẩu !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (matkhaunhaplai.length() <= 0 || !matkhau.equals(matkhaunhaplai)){
                    Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không khớp !",Toast.LENGTH_SHORT).show();
                    return;
                }
                Account account = accountDao.check_email(username);
                accountDao.update(account.getId(), matkhau);
                Toast.makeText(getApplicationContext(),"Cập nhật mật khẩu thành công",Toast.LENGTH_LONG).show();
                Intent trang_login = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(trang_login);
            default:
                break;
        }
    }
}