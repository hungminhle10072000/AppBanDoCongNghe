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
import hcmute.edu.vn.mssv18110296.model.Product;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class User_ProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> arr;

    public User_ProductAdapter(Context context, ArrayList<Product> arr) {
        this.context = context;
        this.arr = arr;
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
        View rowView =  (View) inflater.inflate(R.layout.user_list_item_product, parent, false);

        ///find view
        ImageView imv_product = (ImageView) rowView.findViewById(R.id.imv_product);
        TextView tv_name_product = (TextView) rowView.findViewById(R.id.tv_name_product);
        TextView txt_price_product = (TextView) rowView.findViewById(R.id.txt_price_product);
        TextView txt_des = (TextView) rowView.findViewById(R.id.txt_des);

        /// update value
        Product model = arr.get(position);
        tv_name_product.setText(model.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txt_price_product.setText("Giá : " + decimalFormat.format(model.getPrice()) + " Đ");
        txt_des.setText(model.getDescribe());


        ///set image
        Util.setBitmapToImage(context, Config.FOLDER_IMAGES,model.getImage(),imv_product);
        return rowView;
    }
}
