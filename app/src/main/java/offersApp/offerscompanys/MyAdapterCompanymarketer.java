package offersApp.offerscompanys;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterCompanymarketer extends RecyclerView.Adapter<MyViewHolderCompanymarketer> {

    private Context context;
    private List<DataAdmin> dataList;

    public MyAdapterCompanymarketer(Context context, List<DataAdmin> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolderCompanymarketer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem_company, parent, false);
        return new MyViewHolderCompanymarketer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCompanymarketer holder, int position) {
        Glide.with(context).load(dataList.get(position).getDatalogo()).into(holder.reclogo);
        holder.recname.setText(dataList.get(position).getDataname());


        holder.recadress.setText(dataList.get(position).getDataadress());




    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataAdmin> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolderCompanymarketer extends RecyclerView.ViewHolder{

    ImageView reclogo;
    TextView recname,recpassword , rectype,recidentity, recphone,recemail,recadress,CreatedBy;
    CardView recCard;
    String datausertype;
ImageButton deletedCompany;
    public MyViewHolderCompanymarketer(@NonNull View itemView) {
        super(itemView);

        reclogo = itemView.findViewById(R.id.reclogo);
        recCard = itemView.findViewById(R.id.CompanyCard);
        recname = itemView.findViewById(R.id.recname);


        recadress = itemView.findViewById(R.id.recadress);


    }
}
