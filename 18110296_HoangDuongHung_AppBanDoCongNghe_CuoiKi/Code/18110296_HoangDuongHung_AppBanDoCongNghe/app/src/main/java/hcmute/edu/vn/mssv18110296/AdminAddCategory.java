package hcmute.edu.vn.mssv18110296;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import hcmute.edu.vn.mssv18110296.dao.CategoryDao;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class AdminAddCategory extends AppCompatActivity {

    //constant
    public final int PICK_PHOTO_FOR_NOTE = 0 ;

    private Toolbar toolbar;
    private ImageView imageAttach;
    private Bitmap bmpAttach;
    private EditText nameCategory;

    private CategoryDao categoryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_category);

        //find view id
        nameCategory = (EditText) findViewById(R.id.edt_name_cate);
        imageAttach = (ImageView) findViewById(R.id.img_attach);

        ///get action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Thêm danh mục");
        setSupportActionBar(toolbar);

        /// create data source
        categoryDao = new CategoryDao(this);
        categoryDao.open();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:

                if(nameCategory.getText().toString().trim().length() <= 0){
                    Toast.makeText(this, "Yêu cầu nhập tên danh mục!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                //Dat ten file anh
                String datetime = Util.getCurrentDateTime();
                String imageName = Util.convertStringDatetimeToFileName(datetime) + ".png";

                //save category
                categoryDao.addCategory(nameCategory.getText().toString(),imageName);

                //save image to SD card
                if(bmpAttach != null){
                    Util.saveImageToSDCard(getApplicationContext(),bmpAttach, Config.FOLDER_IMAGES, imageName);
                }
                this.finish();
                Toast.makeText(this, "Thêm danh mục thành công", Toast.LENGTH_LONG).show();
                break;
            case R.id.attach_image:
                pickImage();
                break;
            case R.id.back:
                Intent quaylai = new Intent(getApplicationContext(), AllCategoryActivity.class);
                startActivity(quaylai);
                break;
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
                imageAttach.setImageBitmap(bmpAttach);
                imageAttach.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        categoryDao.close();
    }

}