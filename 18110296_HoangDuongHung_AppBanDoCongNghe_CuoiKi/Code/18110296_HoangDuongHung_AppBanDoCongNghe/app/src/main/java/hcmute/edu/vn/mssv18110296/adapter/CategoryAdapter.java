package hcmute.edu.vn.mssv18110296.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import hcmute.edu.vn.mssv18110296.R;
import hcmute.edu.vn.mssv18110296.model.Category;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class CategoryAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<Category> arr;
    public ArrayList<Category> categorieFilter;


    public CategoryAdapter (Context context, ArrayList<Category> arr){
        this.context = context;
        this.categorieFilter = arr;
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
        View rowView =  (View) inflater.inflate(R.layout.list_item_admin_category, parent, false);

        ///find view
        ImageView imv_category = (ImageView) rowView.findViewById(R.id.imv_category);
        TextView tv_name_category = (TextView) rowView.findViewById(R.id.tv_name_category);

        /// update value
        Category model = arr.get(position);
        tv_name_category.setText(model.getName());

        ///set image
        Util.setBitmapToImage(context,Config.FOLDER_IMAGES,model.getImage(),imv_category);
        return rowView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = arr.size();
                    filterResults.values = arr;
                } else {
/*                    String searchStr = constraint.toString().toLowerCase();*/
                    ArrayList<Category> resultData = new ArrayList<>();

                    for (Category item : arr){
                        if(item.getName().toString().trim().contains(constraint)){
                            resultData.add(item);
                        }
                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                categorieFilter = (ArrayList<Category>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
