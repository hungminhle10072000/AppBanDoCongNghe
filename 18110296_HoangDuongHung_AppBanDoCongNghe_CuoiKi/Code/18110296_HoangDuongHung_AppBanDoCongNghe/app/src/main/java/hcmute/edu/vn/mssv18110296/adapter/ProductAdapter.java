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
import hcmute.edu.vn.mssv18110296.model.Category;
import hcmute.edu.vn.mssv18110296.model.Product;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> arr;

    public ProductAdapter(Context context, ArrayList<Product> arr) {
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
        View rowView =  (View) inflater.inflate(R.layout.list_item_admin_product, parent, false);

        ///find view
        ImageView imv_product = (ImageView) rowView.findViewById(R.id.imv_product);
        TextView tv_name_product = (TextView) rowView.findViewById(R.id.tv_name_product);
        TextView txt_price_product = (TextView) rowView.findViewById(R.id.txt_price_product);
        TextView txt_quantity = (TextView) rowView.findViewById(R.id.txt_quantity);

        /// update value
        Product model = arr.get(position);
        tv_name_product.setText(model.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txt_price_product.setText(decimalFormat.format(model.getPrice()) + " Đ");
        txt_quantity.setText(String.valueOf(model.getQuantity()));

        ///set image
        Util.setBitmapToImage(context, Config.FOLDER_IMAGES,model.getImage(),imv_product);
        return rowView;
    }
}
