package hcmute.edu.vn.mssv18110296.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.R;
import hcmute.edu.vn.mssv18110296.model.Brand;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class SpinnerBrandAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Brand> brands;

    public SpinnerBrandAdapter(Context context, ArrayList<Brand> brands) {
        this.context = context;
        this.brands = brands;
    }

    @Override
    public int getCount() {
        return brands.size();
    }

    @Override
    public Object getItem(int i) {
        return brands.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)brands.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View rowView =  (View) inflater.inflate(R.layout.spinner_brand, viewGroup, false);

        ImageView imv_nhanhieu = (ImageView) rowView.findViewById(R.id.imv_anhnhanhieu);
        TextView txt_tennhanhieu = (TextView) rowView.findViewById(R.id.txt_tennhanhieu);

        Brand model = brands.get(i);
        txt_tennhanhieu.setText(model.getName());
        Util.setBitmapToImage(context, Config.FOLDER_IMAGES,model.getImage(),imv_nhanhieu);

        return rowView;
    }
}
