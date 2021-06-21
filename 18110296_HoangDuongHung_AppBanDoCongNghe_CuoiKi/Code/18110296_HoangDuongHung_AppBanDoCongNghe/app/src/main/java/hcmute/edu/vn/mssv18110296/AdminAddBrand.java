package hcmute.edu.vn.mssv18110296;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110296.adapter.SpinnerCategoryAdapter;
import hcmute.edu.vn.mssv18110296.dao.BrandDao;
import hcmute.edu.vn.mssv18110296.dao.CategoryDao;
import hcmute.edu.vn.mssv18110296.model.Category;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class AdminAddBrand extends AppCompatActivity {
    //constant
    public final int PICK_PHOTO_FOR_NOTE = 0 ;
    private ImageView imageAttach;
    private EditText nameBrand;
    private Toolbar toolbar;
    private Bitmap bmpAttach;
    private BrandDao brandDao;
    private CategoryDao categoryDao;
    private ArrayList<Category> arrCategories = new ArrayList<>();
    private Spinner spn_danhmuc;
    private int id_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_brand);

        ///tim id
        spn_danhmuc = (Spinner) findViewById(R.id.spn_danhmuc);
        nameBrand = (EditText) findViewById(R.id.edt_name_brand);
        imageAttach = (ImageView) findViewById(R.id.img_attach);

        ///get action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Thêm nhãn hiệu");
        setSupportActionBar(toolbar);

        ///create data source
        brandDao = new BrandDao(this);
        brandDao.open();

        ///Lay du lieu cho spinner
        categoryDao = new CategoryDao(this);
        categoryDao.open();
        viewAllCategories();

        spn_danhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_category = arrCategories.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:

                if(nameBrand.getText().toString().trim().length() <= 0){
                    Toast.makeText(this, "Yêu cầu nhập tên nhãn hiệu!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                //Dat ten file anh
                String datetime = Util.getCurrentDateTime();
                String imageName = Util.convertStringDatetimeToFileName(datetime) + ".png";

                //save brand
                brandDao.addBrand(nameBrand.getText().toString(),id_category,imageName);

                //save image to SD card
                if(bmpAttach != null){
                    Util.saveImageToSDCard(getApplicationContext(),bmpAttach, Config.FOLDER_IMAGES, imageName);
                }
                this.finish();
                Toast.makeText(this, "Thêm nhãn hiệu thành công", Toast.LENGTH_LONG).show();
                break;
            case R.id.attach_image:
                pickImage();
                break;
            case R.id.back:
                Intent quaylai = new Intent(getApplicationContext(), AdminAllBrand.class);
                startActivity(quaylai);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_category, menu);
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

    private void viewAllCategories() {
        //create new thread to get all notes in background task
        new Thread(new Runnable() {
            @Override
            public void run() {
                ///read all note from DB
                arrCategories = categoryDao.getAllCategories();
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            SpinnerCategoryAdapter spn_adapter = new SpinnerCategoryAdapter(getApplicationContext(),arrCategories);
            spn_danhmuc.setAdapter(spn_adapter);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        categoryDao.close();
        brandDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAllCategories();
    }

}