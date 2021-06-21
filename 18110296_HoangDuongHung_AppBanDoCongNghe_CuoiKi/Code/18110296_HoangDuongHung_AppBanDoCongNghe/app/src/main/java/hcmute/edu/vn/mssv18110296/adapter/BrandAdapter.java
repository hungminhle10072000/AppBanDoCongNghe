package hcmute.edu.vn.mssv18110296.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import hcmute.edu.vn.mssv18110296.R;
import hcmute.edu.vn.mssv18110296.model.Brand;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class BrandAdapter extends BaseAdapter {

    private String[] arrColor = {"#CDDC39","#FF3D00","#FFFF00","#AEEA00","#00B8D4","#311B92","#FF1744","#4A148C","#F44336"};
    private Context context;
    private ArrayList<Brand> arr;
    private Random rand;

    public BrandAdapter(Context context, ArrayList<Brand> arr) {
        this.context = context;
        this.arr = arr;

        rand = new Random();
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)arr.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View rowView =  (View) inflater.inflate(R.layout.list_item_admin_brand, parent, false);

        ///find view
        ImageView imv_brand = (ImageView) rowView.findViewById(R.id.imv_brand);
        LinearLayout layoutMark = (LinearLayout) rowView.findViewById(R.id.mark);

        /// update value
        Brand model = arr.get(position);
        layoutMark.setBackgroundColor(Color.parseColor(arrColor[rand.nextInt(arrColor.length)]));

        ///set image
        Util.setBitmapToImage(context, Config.FOLDER_IMAGES,model.getImage(),imv_brand);
        return rowView;
    }
}
