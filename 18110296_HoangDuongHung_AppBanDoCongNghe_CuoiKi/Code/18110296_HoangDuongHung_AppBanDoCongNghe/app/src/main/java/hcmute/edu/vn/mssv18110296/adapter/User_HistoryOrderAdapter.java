package hcmute.edu.vn.mssv18110296.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import hcmute.edu.vn.mssv18110296.R;
import hcmute.edu.vn.mssv18110296.model.Orders;

public class User_HistoryOrderAdapter extends BaseAdapter {

    private String[] arrColor = {"#CDDC39","#FF3D00","#FFFF00","#AEEA00","#00B8D4","#311B92","#FF1744","#4A148C","#F44336"};
    private Context context;
    private ArrayList<Orders> arr;
    private Random rand;

    public User_HistoryOrderAdapter(Context context, ArrayList<Orders> arr) {
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
        View rowView =  (View) inflater.inflate(R.layout.user_list_item_history_order, parent, false);

        ///find view
        LinearLayout layoutMark = (LinearLayout) rowView.findViewById(R.id.mark);
        TextView madonhang = (TextView) rowView.findViewById(R.id.madonhang);
        TextView date = (TextView) rowView.findViewById(R.id.kq_date);

        /// update value
        Orders model = arr.get(position);
        layoutMark.setBackgroundColor(Color.parseColor(arrColor[rand.nextInt(arrColor.length)]));
        madonhang.setText(String.valueOf(arr.get(position).getId()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str_date = format.format(arr.get(position).getDate());
        date.setText(str_date);

        return rowView;
    }
}
