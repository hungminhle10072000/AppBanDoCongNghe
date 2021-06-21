package hcmute.edu.vn.mssv18110296;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.dao.BrandDao;
import hcmute.edu.vn.mssv18110296.dao.CategoryDao;
import hcmute.edu.vn.mssv18110296.model.Brand;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class AdminViewCatelogy extends AppCompatActivity {
    //constant
    public final int PICK_PHOTO_FOR_NOTE = 0 ;

    private Toolbar toolbar;
    private ImageView img_attach;
    private EditText edt_name_cate;
    private BitmapDrawable drawable;
    private int id;
    private BrandDao brandDao;
    private CategoryDao categoryDao;
    private Bitmap bmpAttach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_catelogy);

        ///get action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Chỉnh sửa danh mục");
        setSupportActionBar(toolbar);

        //get value of category
        id = getIntent().getExtras().getInt("id");
        String name = getIntent().getExtras().getString("name");
        String image = getIntent().getExtras().getString("image");

        /// find view id
        edt_name_cate = (EditText) findViewById(R.id.edt_name_cate);
        img_attach = (ImageView) findViewById(R.id.img_attach);

        ///set value
        edt_name_cate.setText(name);
        Util.setBitmapToImage(this, Config.FOLDER_IMAGES, image, img_attach);

        ///tao phuong thuc cap nhat
        categoryDao = new CategoryDao(this);
        brandDao = new BrandDao(this);
        brandDao.open();
        categoryDao.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_catelogy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                ArrayList<Brand> arrBrand = brandDao.getListBrand_Category(id);
                if(arrBrand.size() > 0){
                    Toast.makeText(getApplicationContext(),"Có nhãn hiệu thuộc danh mục này ! \n Không thể xóa danh mục này.",Toast.LENGTH_SHORT).show();
                } else {
                    categoryDao.deleteCategory(id);
                    this.finish();
                }
                break;
            case R.id.update:

                if(edt_name_cate.getText().toString().trim().length() <= 0){
                    Toast.makeText(this, "Yêu cầu nhập tên danh mục!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                //lay anh tu image view de cap nhat
                drawable = (BitmapDrawable)img_attach.getDrawable();
                bmpAttach = drawable.getBitmap();

                ///dat ten file anh
                String datetime = Util.getCurrentDateTime();
                String imageName = Util.convertStringDatetimeToFileName(datetime) + ".png";

                //update category
                categoryDao.update(id,edt_name_cate.getText().toString().trim(),imageName);

                //save image to SD card
                if(bmpAttach != null){
                    Util.saveImageToSDCard(getApplicationContext(),bmpAttach, Config.FOLDER_IMAGES, imageName);
                }
                this.finish();
                break;
            case R.id.attach_image:
                pickImage();
                break;
            case R.id.back:
                Intent quaylai = new Intent(this, AllCategoryActivity.class);
                startActivity(quaylai);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //Select image form gallery
    private void pickImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_PHOTO_FOR_NOTE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_PHOTO_FOR_NOTE && resultCode == RESULT_OK ){
            if(data == null){
                //Display an error
                Toast.makeText(this, "Không có ảnh nào được chọn !", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                //get image from result
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                BufferedInputStream bufferedInputStream =  new BufferedInputStream(inputStream);
                bmpAttach = BitmapFactory.decodeStream(bufferedInputStream);

                ///show image in screen
                img_attach.setImageBitmap(bmpAttach);
                img_attach.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        categoryDao.close();
        brandDao.close();
    }

}