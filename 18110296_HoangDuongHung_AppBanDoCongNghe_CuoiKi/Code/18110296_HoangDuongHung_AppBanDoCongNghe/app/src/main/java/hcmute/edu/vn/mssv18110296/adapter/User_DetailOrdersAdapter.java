package hcmute.edu.vn.mssv18110296.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.R;
import hcmute.edu.vn.mssv18110296.dao.ProductDao;
import hcmute.edu.vn.mssv18110296.model.Order_Detail;
import hcmute.edu.vn.mssv18110296.model.Product;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class User_DetailOrdersAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Order_Detail> arr;
    private ProductDao productDao;

    public User_DetailOrdersAdapter(Context context, ArrayList<Order_Detail> arr) {
        this.context = context;
        this.arr = arr;
        productDao = new ProductDao(context);
        productDao.open();
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)arr.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View rowView =  (View) inflater.inflate(R.layout.user_list_item_sp_order, parent, false);

        ///find view
        ImageView imv_product = (ImageView) rowView.findViewById(R.id.imv_product);
        TextView tv_name_product = (TextView) rowView.findViewById(R.id.tv_name_product);
        TextView txt_price_product = (TextView) rowView.findViewById(R.id.txt_price_product);
        TextView txt_quantity = (TextView) rowView.findViewById(R.id.txt_soluong);

        /// update value
        Order_Detail model = arr.get(position);

        ///find Product
        Product product = productDao.findProduct(model.getId_product());

        ///set value
        tv_name_product.setText(model.getName_product());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txt_price_product.setText("Giá : " + decimalFormat.format(product.getPrice()) + " Đ");
        txt_quantity.setText("Số lượng : " + String.valueOf(model.getQuantity_product()));
        Util.setBitmapToImage(context, Config.FOLDER_IMAGES,product.getImage(),imv_product);
        return rowView;
    }
}
