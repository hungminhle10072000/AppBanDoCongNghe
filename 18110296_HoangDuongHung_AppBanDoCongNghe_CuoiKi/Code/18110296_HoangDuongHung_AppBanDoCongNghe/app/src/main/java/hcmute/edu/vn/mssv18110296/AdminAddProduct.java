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
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.adapter.SpinnerBrandAdapter;
import hcmute.edu.vn.mssv18110296.adapter.SpinnerCategoryAdapter;
import hcmute.edu.vn.mssv18110296.dao.BrandDao;
import hcmute.edu.vn.mssv18110296.dao.ProductDao;
import hcmute.edu.vn.mssv18110296.model.Brand;
import hcmute.edu.vn.mssv18110296.model.Category;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class AdminAddProduct extends AppCompatActivity {

    //constant
    public final int PICK_PHOTO_FOR_NOTE = 0 ;
    private ImageView imageAttach;
    private EditText nameProduct;
    private EditText quantityProduct;
    private EditText priceProduct;
    private EditText desProduct;
    private Toolbar toolbar;
    private Bitmap bmpAttach;
    private BrandDao brandDao;
    private ProductDao productDao;
    private ArrayList<Brand> arrBrands = new ArrayList<>();
    private Spinner spn_nhanhieu;
    private int id_brand;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        ///find id
        imageAttach = (ImageView) findViewById(R.id.img_attach);
        nameProduct = (EditText) findViewById(R.id.edt_name_product);
        quantityProduct = (EditText) findViewById(R.id.edt_quantity_product);
        spn_nhanhieu = (Spinner) findViewById(R.id.spn_nhanhieu);
        priceProduct = (EditText) findViewById(R.id.edt_price_product);
        desProduct = (EditText) findViewById(R.id.edt_des_pro);


        ///get action bar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Thêm sản phẩm");
        setSupportActionBar(toolbar);

        ///create data source
        productDao = new ProductDao(this);
        productDao.open();

        ///Lay du lieu cho spinner
        brandDao = new BrandDao(this);
        brandDao.open();
        viewAllBrands();

        spn_nhanhieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_brand = arrBrands.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void viewAllBrands(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ///read all note from DB
                arrBrands = brandDao.getAllBrands();
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:

                if(nameProduct.getText().toString().trim().length() <= 0){
                    Toast.makeText(this, "Yêu cầu nhập tên sản phẩm !", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(priceProduct.getText().toString().trim().length() <= 0){
                    Toast.makeText(this, "Yêu cầu nhập giá sản phẩm !", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(Integer.parseInt(priceProduct.getText().toString().trim()) < 0){
                    Toast.makeText(this, "Yêu cầu nhập giá sản phẩm > 0 !", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if(quantityProduct.getText().toString().trim().length() <= 0 ){
                    Toast.makeText(this, "Yêu cầu nhập số lượng sản phẩm !", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if(Integer.parseInt(quantityProduct.getText().toString().trim()) <= 0){
                    Toast.makeText(this, "Yêu cầu nhập số lượng sản phẩm >= 0 !", Toast.LENGTH_SHORT).show();
                    return true;
                }

                //Dat ten file anh
                String datetime = Util.getCurrentDateTime();
                String imageName = Util.convertStringDatetimeToFileName(datetime) + ".png";

                //save brand
                productDao.addProduct(nameProduct.getText().toString(),Integer.parseInt(priceProduct.getText().toString()),desProduct.getText().toString(),
                        Integer.parseInt(quantityProduct.getText().toString()),id_brand,imageName);

                //save image to SD card
                if(bmpAttach != null){
                    Util.saveImageToSDCard(getApplicationContext(),bmpAttach, Config.FOLDER_IMAGES, imageName);
                }
                this.finish();
                Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
                break;
            case R.id.attach_image:
                pickImage();
                break;
            case R.id.back:
                Intent quaylai = new Intent(getApplicationContext(), AdminAllProduct.class);
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

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            SpinnerBrandAdapter spn_adapter = new SpinnerBrandAdapter(getApplicationContext(),arrBrands);
            spn_nhanhieu.setAdapter(spn_adapter);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        brandDao.close();
        brandDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAllBrands();
    }
}