package hcmute.edu.vn.mssv18110296;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110296.dao.AccountDao;
import hcmute.edu.vn.mssv18110296.model.Account;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_tieptuc;
    ImageView btn_back_forget;
    EditText edt_email,edt_username;
    private AccountDao accountDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        btn_tieptuc = (Button) findViewById(R.id.btn_tieptuc);
        btn_tieptuc.setOnClickListener(this);
        btn_back_forget = (ImageView) findViewById(R.id.btn_back_forget);
        btn_back_forget.setOnClickListener(this);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_username = (EditText) findViewById(R.id.edt_username);

        accountDao = new AccountDao(this);
        accountDao.open();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tieptuc:
                String Email = edt_email.getText().toString();
                String Username= edt_username.getText().toString();
                int kt = 0;
                if (Username.trim().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Yêu cầu nhập tên đăng nhập !",Toast.LENGTH_SHORT).show();
                    return;
                }
                kt = accountDao.getcountAccount(Username);
                if(kt == 0){
                    Toast.makeText(getApplicationContext(), "Không tồn tại tên đăng nhập này !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Email.trim().length() <=0 || !Email.contains("@")){
                    Toast.makeText(getApplicationContext(), "Yêu cầu nhập tài khoản gmail !",Toast.LENGTH_SHORT).show();
                    return;
                }
                Account account = accountDao.check_email(Username);
                if(!account.getEmail().equals(Email)){
                    Toast.makeText(getApplicationContext(), "Đây không phải tài khoản gmail của tài khoản !",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), ResetPassword.class);
                intent.putExtra("email",Email);
                intent.putExtra("username",Username);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_back_forget:
                finish();
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            edt_email.setText(data.getStringExtra("ActivityResult"));
        }
    }
}