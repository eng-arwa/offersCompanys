package offersApp.offerscompanys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterMarketer extends RecyclerView.Adapter<HolderMarkter>{
    private Context context;
    private List<DataMarkter> dataList;

    public AdapterMarketer(Context context, List<DataMarkter> dataList){
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public HolderMarkter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_markter, parent, false);
        return new HolderMarkter(view);

    }



    @Override
    public void onBindViewHolder(@NonNull HolderMarkter holder, int position) {
        try {
            holder.fullName.setText(dataList.get(position).getFullName());
            holder.phone.setText(dataList.get(position).getPhone());
            holder.adress.setText(dataList.get(position).getAdress());
            holder.email.setText(dataList.get(position).getEmail());
            holder.identityNumber.setText(dataList.get(position).getIdentityNumber());
        }catch (Exception error){
            Toast.makeText(context, "error load data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchDataList(ArrayList<DataMarkter> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}
class HolderMarkter extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView fullName, datausertype,adress, phone,email,identityNumber,password;
    CardView recCard;
    ImageView reclogo;
    ImageButton deletedMarkter;

    public HolderMarkter(@NonNull View itemView) {
        super(itemView);
        recCard = itemView.findViewById(R.id.markterCard);
        deletedMarkter=itemView.findViewById(R.id.D_markter);
        fullName = (TextView) itemView.findViewById(R.id.fullName);
        phone = (TextView) itemView.findViewById(R.id.phone);
        adress =(TextView) itemView.findViewById(R.id.adress);
        email =(TextView) itemView.findViewById(R.id.email);
        identityNumber=(TextView) itemView.findViewById(R.id.identityNumber);


    }
}