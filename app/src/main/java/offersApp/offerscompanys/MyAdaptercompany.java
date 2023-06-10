package offersApp.offerscompanys;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAdaptercompany extends RecyclerView.Adapter<MyViewHolderCompany> {

    private Context context;
    private List<DataClass> dataList;

    public MyAdaptercompany(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolderCompany onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_offers, parent, false);
        return new MyViewHolderCompany(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCompany holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getKey());
        holder.recTitle.setText(dataList.get(position).getDataTitle());
        holder.recDesc.setText(dataList.get(position).getDataDesc());
        holder.recLang.setText(dataList.get(position).getDataLang());
        holder.recbefore.setText(dataList.get(position).getDataPricebefore());
        holder.recafter.setText(dataList.get(position).getDataPriceafter());




        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detailactivity.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Language", dataList.get(holder.getAdapterPosition()).getDataLang());
                intent.putExtra("PriceBefore",dataList.get(holder.getAdapterPosition()).getDataPricebefore());
                intent.putExtra("PriceAfter", dataList.get(holder.getAdapterPosition()).getDataPriceafter());
                context.startActivity(intent);
            }
        });

        holder.deletedoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key=dataList.get(holder.getAdapterPosition()).getKey();
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("offers");

                reference.child(key).removeValue();
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolderCompany extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recTitle, recDesc, recLang,recbefore,recafter;
    CardView recCard;
    Button edit;
    ImageButton deletedoffer;

    public MyViewHolderCompany(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImageCompany);
        recCard = itemView.findViewById(R.id.offersCard);
        recDesc = itemView.findViewById(R.id.recDescCompany);
        recLang = itemView.findViewById(R.id.recLangCompany);
        recTitle = itemView.findViewById(R.id.recTitleCompany);
        recbefore = itemView.findViewById(R.id.rec_B);
        recafter = itemView.findViewById(R.id.rec_A);
        deletedoffer = itemView.findViewById(R.id.D_Offer);

    }
}
