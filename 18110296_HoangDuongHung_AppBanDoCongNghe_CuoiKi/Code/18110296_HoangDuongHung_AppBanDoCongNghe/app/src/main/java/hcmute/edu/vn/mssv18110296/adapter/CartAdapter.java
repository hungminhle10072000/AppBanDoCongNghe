package hcmute.edu.vn.mssv18110296.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.Home_User;
import hcmute.edu.vn.mssv18110296.R;
import hcmute.edu.vn.mssv18110296.User_Cart;
import hcmute.edu.vn.mssv18110296.dao.ProductDao;
import hcmute.edu.vn.mssv18110296.model.Cart;
import hcmute.edu.vn.mssv18110296.model.Product;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class CartAdapter extends BaseAdapter  {
    Context context;
    ArrayList<Cart> arrayListCart;
    ProductDao productDao;


    public CartAdapter(Context context, ArrayList<Cart> arrayListCart) {
        this.context = context;
        this.arrayListCart = arrayListCart;
    }


    @Override
    public int getCount() {
        return arrayListCart.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txttengiohang, txtgiagiohang ;
        public ImageView imggiohang;
        public Button btnminus, btnvalues, btnsum;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        productDao = new ProductDao(context);
        productDao.open();
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_cart, null);

            viewHolder = new ViewHolder();
            viewHolder.txttengiohang = convertView.findViewById(R.id.txt_tengiohang);
            viewHolder.txtgiagiohang = convertView.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang = convertView.findViewById(R.id.imv_giohang);
            viewHolder.btnminus = convertView.findViewById(R.id.btn_min);
            viewHolder.btnvalues = convertView.findViewById(R.id.btn_quantity);
            viewHolder.btnsum = convertView.findViewById(R.id.btn_sum);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Cart cart = (Cart) getItem(position);
        viewHolder.txttengiohang.setText(cart.getName_product());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(cart.getPrice_product()) + " Đ");
        Util.setBitmapToImage(context, Config.FOLDER_IMAGES, cart.getImage_product(), viewHolder.imggiohang);
        viewHolder.btnvalues.setText(String.valueOf(cart.getQuantity_product()));

        ///Kiem tra so luong san pham con
        int slsanpham_trongkh = productDao.findProduct(cart.getId_product()).getQuantity();

        int quantity = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if(quantity >= 10 || quantity >= slsanpham_trongkh){
            viewHolder.btnsum.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        } else if (quantity <= 1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        } else if (quantity >= 1){
            viewHolder.btnsum.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }

        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl_moinhat = Home_User.arrayListCart.get(position).getQuantity_product() - 1;
                int sl_hientai = Home_User.arrayListCart.get(position).getQuantity_product();
                long gia_hientai = Home_User.arrayListCart.get(position).getPrice_product();
                long gia_moinhat = (gia_hientai * sl_moinhat) / sl_hientai;
                Home_User.arrayListCart.get(position).setQuantity_product(sl_moinhat);
                Home_User.arrayListCart.get(position).setPrice_product(gia_moinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtgiagiohang.setText(decimalFormat.format(gia_moinhat) + " Đ");
                User_Cart.TongTienGioHang();
                if (sl_moinhat < 2){
                    viewHolder.btnsum.setVisibility(View.VISIBLE);
                    viewHolder.btnminus.setVisibility(View.INVISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(sl_moinhat));
                } else {
                    viewHolder.btnminus.setVisibility(View.VISIBLE);
                    viewHolder.btnsum.setVisibility(View.VISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(sl_moinhat));
                }
            }
        });

        viewHolder.btnsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl_moinhat = Home_User.arrayListCart.get(position).getQuantity_product() + 1;
                int sl_hientai = Home_User.arrayListCart.get(position).getQuantity_product();
                long gia_hientai = Home_User.arrayListCart.get(position).getPrice_product();
                long gia_moinhat = (gia_hientai * sl_moinhat) / sl_hientai;
                Home_User.arrayListCart.get(position).setQuantity_product(sl_moinhat);
                Home_User.arrayListCart.get(position).setPrice_product(gia_moinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtgiagiohang.setText(decimalFormat.format(gia_moinhat) + " Đ");
                User_Cart.TongTienGioHang();
                if (sl_moinhat > 9 || sl_moinhat == slsanpham_trongkh ){
                    viewHolder.btnsum.setVisibility(View.INVISIBLE);
                    viewHolder.btnminus.setVisibility(View.VISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(sl_moinhat));
                } else {
                    viewHolder.btnminus.setVisibility(View.VISIBLE);
                    viewHolder.btnsum.setVisibility(View.VISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(sl_moinhat));
                }
            }
        });

        return convertView;
    }

}
