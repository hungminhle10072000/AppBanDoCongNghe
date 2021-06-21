package hcmute.edu.vn.mssv18110296;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_bosuutap,btn_dangky;
    Button btn_camera;
    BitmapDrawable drawable;
    private AccountDao accountDao;
    Bitmap bitmap;
    ImageView img_avatar;
    ImageView back;
    TextView  edt_dk_tennguoidung,edt_dk_tendangnhap,edt_dk_matkhau,edt_dk_nhaplaimatkhau,edt_dk_diachiemail,edt_dk_diachi,edt_dk_sdt;
    private static final int pic_id = 123;
    private static final int camera_id = 900;
    private String name,username,password,email,address,password2,numberphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        accountDao = new AccountDao(this);
        accountDao.open();

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
        btn_dangky = (Button) findViewById(R.id.btn_dangki);
        btn_dangky.setOnClickListener(this);

/*        btn_camera = (Button) findViewById(R.id.btn_camera);*/
/*        btn_camera.setOnClickListener(this);*/
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
/*            case R.id.btn_camera:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent2, camera_id);
                break;*/
            case R.id.avatar:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent2, camera_id);
                break;
            case R.id.back_reg:
                Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_dangki:

                ///lay gia tri cua edit text
                name = edt_dk_tennguoidung.getText().toString().trim();
                username = edt_dk_tendangnhap.getText().toString().trim();
                password = edt_dk_matkhau.getText().toString().trim();
                email = edt_dk_diachiemail.getText().toString().trim();
                address = edt_dk_diachi.getText().toString().trim();
                password2 = edt_dk_nhaplaimatkhau.getText().toString().trim();
                numberphone = edt_dk_sdt.getText().toString().trim();

                if(name.length() <= 0){
                    Toast.makeText(getApplicationContext(),"Yêu cầu nhập tên người dùng !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(username.length() <= 0){
                    Toast.makeText(getApplicationContext(),"Yêu cầu nhập tên đăng nhập !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() <= 0){
                    Toast.makeText(getApplicationContext(),"Yêu cầu nhập mật khẩu !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edt_dk_nhaplaimatkhau.getText().toString().trim().length() <= 0){
                    Toast.makeText(getApplicationContext(),"Yêu cầu nhập lại mật khẩu !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals(password2)==false){
                    Toast.makeText(getApplicationContext(),"Mật khẩu nhập lại không khớp !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(email.length() <= 0 || !email.contains("@")){
                    Toast.makeText(getApplicationContext(),"Yêu cầu bạn nhập địa chỉ email !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(address.length() <= 0){
                    Toast.makeText(getApplicationContext(),"Yêu cầu bạn nhập địa chỉ !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(numberphone.length() <= 0){
                    Toast.makeText(getApplicationContext(),"Yêu cầu bạn nhập số điện thoại !",Toast.LENGTH_SHORT).show();
                    return;
                }

                String datetime = Util.getCurrentDateTime();
                String imageName = Util.convertStringDatetimeToFileName(datetime) + ".png";


                if(accountDao.getcountAccount(username) > 0){
                    Toast.makeText(getApplicationContext(),"Tên đăng nhập này đã có người sử dụng ! \n Mời bạn nhập tên đăng nhập khác.",Toast.LENGTH_SHORT).show();
                    return;
                }
                accountDao.addAccount(name,username,password,email,address,numberphone,imageName);
                Intent dki_thanhcong = new Intent(getApplicationContext(), LoginActivity.class);
                dki_thanhcong.putExtra("thongbaothanhcong","Bạn đã đăng kí thành công");
                startActivity(dki_thanhcong);

                /// Luu thong tin hinh anh
                drawable = (BitmapDrawable)img_avatar.getDrawable();
                bitmap = drawable.getBitmap();
                if(bitmap != null){
                    Util.saveImageToSDCard(getApplicationContext(),bitmap, Config.FOLDER_IMAGES, imageName);
                }

                break;
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

/*                //Xoay anh chup
                Bitmap bOutput;
                float degrees = -90;//rotation degree
                Matrix matrix = new Matrix();
                matrix.setRotate(degrees);
                bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                img_avatar.setImageBitmap(bOutput);*/
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