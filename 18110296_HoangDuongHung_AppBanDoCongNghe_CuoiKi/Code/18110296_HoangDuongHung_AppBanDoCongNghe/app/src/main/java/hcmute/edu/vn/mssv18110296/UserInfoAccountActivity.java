package hcmute.edu.vn.mssv18110296;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110296.dao.AccountDao;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class UserInfoAccountActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_bosuutap,btn_capnhat;
    Button btn_camera;
    BitmapDrawable drawable;
    private Bitmap bmpAttach;
    private AccountDao accountDao;
    private SharedPreferences sharedPreferences;
    Bitmap bitmap;
    ImageView img_avatar;
    ImageView back;
    TextView edt_dk_tennguoidung,edt_dk_tendangnhap,edt_dk_matkhau,edt_dk_nhaplaimatkhau,edt_dk_diachiemail,edt_dk_diachi,edt_dk_sdt;
    private static final int pic_id = 123;
    private static final int camera_id = 900;
    private int id;
    private int role_user;
    private String prefname="my_data";
    private String name,username,password,email,address,password2,numberphone,role;
    String imageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_account);
        sharedPreferences = getSharedPreferences(prefname,MODE_PRIVATE);
        accountDao = new AccountDao(this);
        accountDao.open();
        MatchId();
        GetInfo();
        SetValue();
    }

    private void GetInfo() {
        id = sharedPreferences.getInt("id_user", 0);
        name =sharedPreferences.getString("name","");
        username = sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");
        email = sharedPreferences.getString("email","");
        numberphone = sharedPreferences.getString("numberphone","");
        address = sharedPreferences.getString("address","");
        imageName = sharedPreferences.getString("image","");
        role_user = sharedPreferences.getInt("role",0);
    }

    private void SetValue() {
        edt_dk_tennguoidung.setText(name);
        edt_dk_tendangnhap.setText(username);
        edt_dk_matkhau.setText(password);
        edt_dk_diachiemail.setText(email);
        edt_dk_sdt.setText(numberphone);
        edt_dk_diachi.setText(address);
        edt_dk_nhaplaimatkhau.setText(password);
        Util.setBitmapToImage(this, Config.FOLDER_IMAGES, imageName, img_avatar);
    }

    private void MatchId() {
        ///find id
        edt_dk_tennguoidung = (TextView) findViewById(R.id.edt_dk_tennguoidung);
        edt_dk_tendangnhap = (TextView) findViewById(R.id.edt_dk_tendangnhap);
        edt_dk_matkhau = (TextView) findViewById(R.id.edt_dk_matkhau);
        edt_dk_nhaplaimatkhau = (TextView) findViewById(R.id.edt_dk_nhaplaimatkhau);
        edt_dk_diachiemail = (TextView) findViewById(R.id.edt_dk_diachiemail);
        edt_dk_diachi = (TextView) findViewById(R.id.edt_dk_diachi);
        edt_dk_sdt = (TextView) findViewById(R.id.edt_dk_sdt);

        img_avatar = (ImageView) findViewById(R.id.avatar);
        img_avatar.setOnClickListener(this);
        back = (ImageView) findViewById(R.id.back_reg);
        back.setOnClickListener(this);
        btn_bosuutap = (Button) findViewById(R.id.btn_bosuutap);
        btn_bosuutap.setOnClickListener(this);
        btn_capnhat = (Button) findViewById(R.id.tbn_capnhat);
        btn_capnhat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bosuutap:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh đại diện"), pic_id);
                break;
            case R.id.avatar:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent2, camera_id);
                break;
            case R.id.back_reg:
                if (role_user == 1 ){
                    Intent quayve_admin = new Intent(getApplicationContext(), AdminMainActivity.class);
                    startActivity(quayve_admin);
                } else if (role_user == 2){
                    Intent intent3 = new Intent(getApplicationContext(), Home_User.class);
                    startActivity(intent3);
                }
                break;
            case R.id.tbn_capnhat:

                ///lay gia tri cua edit text
                name = edt_dk_tennguoidung.getText().toString().trim();
                username = edt_dk_tendangnhap.getText().toString().trim();
                password = edt_dk_matkhau.getText().toString().trim();
                email = edt_dk_diachiemail.getText().toString().trim();
                address = edt_dk_diachi.getText().toString().trim();
                password2 = edt_dk_nhaplaimatkhau.getText().toString().trim();
                numberphone = edt_dk_sdt.getText().toString().trim();

                if (name.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Yêu cầu nhập tên người dùng !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (username.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Yêu cầu nhập tên đăng nhập !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Yêu cầu nhập mật khẩu !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_dk_nhaplaimatkhau.getText().toString().trim().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Yêu cầu nhập lại mật khẩu !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals(password2) == false) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không khớp !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.length() <= 0 || !email.contains("@")) {
                    Toast.makeText(getApplicationContext(), "Yêu cầu bạn nhập địa chỉ email !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (address.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Yêu cầu bạn nhập địa chỉ !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (numberphone.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Yêu cầu bạn nhập số điện thoại !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!username.equals(sharedPreferences.getString("username","").toString().trim())){
                    if (accountDao.getcountAccount(username) > 0) {
                        Toast.makeText(getApplicationContext(), "Tên đăng nhập này đã có người sử dụng ! \n Mời bạn nhập tên đăng nhập khác.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                //lay anh tu image view de cap nhat
                drawable = (BitmapDrawable) img_avatar.getDrawable();
                bmpAttach = drawable.getBitmap();

                ///Dat ten anh
                String datetime = Util.getCurrentDateTime();
                imageName = Util.convertStringDatetimeToFileName(datetime) + ".png";

                //save image to SD card
                if (bmpAttach != null) {
                    if (bmpAttach != null) {
                        Util.saveImageToSDCard(getApplicationContext(), bmpAttach, Config.FOLDER_IMAGES, imageName);
                    }

                    ///Cap nhat thong tin
                    accountDao.update(id, name, username, password, email, address, numberphone, imageName,role_user);
                    Toast.makeText(getApplicationContext(), "Cập nhật tài khoản thành công", Toast.LENGTH_LONG).show();
                    if (role_user == 1){
                        Intent capnhatthancong = new Intent(getApplicationContext(), AdminMainActivity.class);
                        startActivity(capnhatthancong);
                    } else {
                        Intent capnhatthancong = new Intent(getApplicationContext(), Home_User.class);
                        startActivity(capnhatthancong);
                    }


                    /// Luu thong tin hinh anh
                    drawable = (BitmapDrawable) img_avatar.getDrawable();
                    bitmap = drawable.getBitmap();
                    if (bitmap != null) {
                        Util.saveImageToSDCard(getApplicationContext(), bitmap, Config.FOLDER_IMAGES, imageName);
                    }

                    ///Cap nhat lai thong tin sharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.putInt("id_user", id);
                    editor.putString("name", name);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putString("email", email);
                    editor.putString("address", address);
                    editor.putString("numberphone", numberphone);
                    editor.putString("image", imageName);
                    editor.putInt("role",role_user);
                    editor.commit();
                    break;
                }
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pic_id) {
            if (resultCode == Activity.RESULT_OK) {
                img_avatar.setImageURI(data.getData());
            }
        } else if (requestCode == camera_id) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                img_avatar.setImageBitmap(bitmap);
            }
        }
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