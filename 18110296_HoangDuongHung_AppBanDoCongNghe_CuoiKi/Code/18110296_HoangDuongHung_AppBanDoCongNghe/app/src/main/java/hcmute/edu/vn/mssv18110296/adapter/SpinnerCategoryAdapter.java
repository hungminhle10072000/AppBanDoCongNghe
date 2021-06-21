package hcmute.edu.vn.mssv18110296.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.R;
import hcmute.edu.vn.mssv18110296.model.Brand;
import hcmute.edu.vn.mssv18110296.model.Category;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class SpinnerCategoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Category> categories;

    public SpinnerCategoryAdapter(Context context, ArrayList<Category> arr) {
        this.context =context;
        this.categories = arr;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)categories.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View rowView =  (View) inflater.inflate(R.layout.spinner_category, viewGroup, false);

        ImageView imv_danhmuc = (ImageView) rowView.findViewById(R.id.imv_anhdanhmuc);
        TextView txt_tendanhmuc = (TextView) rowView.findViewById(R.id.txt_tendanhmuc);

        Category model = categories.get(i);
        txt_tendanhmuc.setText(model.getName());
        Util.setBitmapToImage(context, Config.FOLDER_IMAGES,model.getImage(),imv_danhmuc);

        return rowView;
    }
}
