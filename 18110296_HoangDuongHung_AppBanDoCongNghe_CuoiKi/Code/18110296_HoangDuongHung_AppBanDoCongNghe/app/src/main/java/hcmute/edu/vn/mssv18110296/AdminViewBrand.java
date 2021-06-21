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
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.adapter.SpinnerCategoryAdapter;
import hcmute.edu.vn.mssv18110296.dao.BrandDao;
import hcmute.edu.vn.mssv18110296.dao.CategoryDao;
import hcmute.edu.vn.mssv18110296.dao.ProductDao;
import hcmute.edu.vn.mssv18110296.model.Category;
import hcmute.edu.vn.mssv18110296.model.Product;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class AdminViewBrand extends AppCompatActivity {

    //constant
    public final int PICK_PHOTO_FOR_NOTE = 0 ;

    private Toolbar toolbar;
    private BrandDao brandDao;
    private Bitmap bmpAttach;
    private ImageView img_attach;
    private BitmapDrawable drawable;
    private int id;
    private int id_category;
    private EditText edt_name_brand;
    private CategoryDao categoryDao;
    private ProductDao productDao;
    private ArrayList<Category> arrCategories = new ArrayList<>();
    private Spinner spn_danhmuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_brand);

        ///get action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Chỉnh sửa nhãn hiệu");
        setSupportActionBar(toolbar);

        //get value of brand
        id = getIntent().getExtras().getInt("id");
        id_category = getIntent().getExtras().getInt("id_category");
        String name = getIntent().getExtras().getString("name");
        String image = getIntent().getExtras().getString("image");

        /// find view id
        edt_name_brand = (EditText) findViewById(R.id.edt_name_brand);
        img_attach = (ImageView) findViewById(R.id.img_attach);
        spn_danhmuc = (Spinner) findViewById(R.id.spn_danhmuc);

        spn_danhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_category = arrCategories.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///set value
        edt_name_brand.setText(name);
        Util.setBitmapToImage(this, Config.FOLDER_IMAGES, image, img_attach);

        ///Lay du lieu cho spinner
        categoryDao = new CategoryDao(this);
        productDao = new ProductDao(this);
        productDao.open();
        categoryDao.open();
        viewAllCategories();

        ///tao phuong thuc cap nhat
        brandDao = new BrandDao(this);
        brandDao.open();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                ArrayList<Product> arrPro = productDao.getListPro_Brand(id);
                if (arrPro.size() > 0){
                    Toast.makeText(getApplicationContext(),"Có sản phẩm thuộc nhãn hiệu này ! \n Không thể xóa nhãn hiệu.", Toast.LENGTH_SHORT).show();
                } else {
                    brandDao.deleteBrand(id);
                    this.finish();
                }
                break;
            case R.id.update:

                if(edt_name_brand.getText().toString().trim().length() <= 0){
                    Toast.makeText(this, "Yêu cầu nhập tên nhãn hiệu!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                //lay anh tu image view de cap nhat
                drawable = (BitmapDrawable)img_attach.getDrawable();
                bmpAttach = drawable.getBitmap();

                ///dat ten file anh
                String datetime = Util.getCurrentDateTime();
                String imageName = Util.convertStringDatetimeToFileName(datetime) + ".png";

                //update brand
                brandDao.update(id,edt_name_brand.getText().toString().trim(),id_category,imageName);

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
                Intent quaylai = new Intent(this, AdminAllBrand.class);
                startActivity(quaylai);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_catelogy, menu);
        return true;
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

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            SpinnerCategoryAdapter spn_adapter = new SpinnerCategoryAdapter(getApplicationContext(),arrCategories);
            spn_danhmuc.setAdapter(spn_adapter);
            for(int i=0; i<arrCategories.size(); i++){
                if(id_category == arrCategories.get(i).getId()){
                    spn_danhmuc.setSelection(i);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        categoryDao.close();
        productDao.close();
        brandDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAllCategories();
    }

}