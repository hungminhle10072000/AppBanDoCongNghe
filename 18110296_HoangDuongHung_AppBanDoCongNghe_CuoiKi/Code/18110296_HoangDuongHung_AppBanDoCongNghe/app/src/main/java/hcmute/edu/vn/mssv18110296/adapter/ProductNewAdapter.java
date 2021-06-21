package hcmute.edu.vn.mssv18110296.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110296.DetailProduct;
import hcmute.edu.vn.mssv18110296.R;
import hcmute.edu.vn.mssv18110296.model.Product;
import hcmute.edu.vn.mssv18110296.util.Config;
import hcmute.edu.vn.mssv18110296.util.Util;

public class ProductNewAdapter extends RecyclerView.Adapter<ProductNewAdapter.ItemHolder> {

    Context context;
    ArrayList<Product> arraysanpham;

    public ProductNewAdapter(Context context, ArrayList<Product> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sanphammoinhat,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product = arraysanpham.get(position);
        holder.txttensanpham.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá : " + decimalFormat.format(product.getPrice()) + " Đ");

        ///set image
        Util.setBitmapToImage(context, Config.FOLDER_IMAGES,product.getImage(),holder.imginhsanpham);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imginhsanpham;
        public TextView txttensanpham,txtgiasanpham;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            ///find view id
            imginhsanpham = itemView.findViewById(R.id.imv_sanphammoinhat);
            txtgiasanpham = itemView.findViewById(R.id.txt_giasanphammoinhat);
            txttensanpham = itemView.findViewById(R.id.txt_tensanphammoinhat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DetailProduct.class).putExtra("thongtinsanpham", (Parcelable) arraysanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

}
