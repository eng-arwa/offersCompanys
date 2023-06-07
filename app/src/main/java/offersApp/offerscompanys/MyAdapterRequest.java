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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterRequest extends RecyclerView.Adapter<MyViewHolderRequest> {

    private Context context;
    private List<DataMarkter> dataList;

    public MyAdapterRequest(Context context, List<DataMarkter> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolderRequest onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_request, parent, false);
        return new MyViewHolderRequest(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderRequest holder, int position) {
        holder.fullName.setText(dataList.get(position).getFullName());
        holder.phone.setText(dataList.get(position).getPhone());
        holder.email.setText(dataList.get(position).getEmail());
        holder.adress.setText(dataList.get(position).getAdress());
        holder.password.setText(dataList.get(position).getPassword());
        holder.identityNumber.setText(dataList.get(position).getIdentityNumber());


        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detailactivity.class);
                intent.putExtra("name", dataList.get(holder.getAdapterPosition()).getFullName());
                intent.putExtra("phone", dataList.get(holder.getAdapterPosition()).getPhone());
                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("email", dataList.get(holder.getAdapterPosition()).getEmail());
                intent.putExtra("identity", dataList.get(holder.getAdapterPosition()).getIdentityNumber());

                intent.putExtra("adress", dataList.get(holder.getAdapterPosition()).getAdress());

                context.startActivity(intent);
            }
        });
        holder.rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key=dataList.get(holder.getAdapterPosition()).getKey();
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RequestSignup");

                reference.child(key).removeValue();
            }
        });
        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key=dataList.get(holder.getAdapterPosition()).getKey();
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RequestSignup").child(key);


//               end
            }
        });


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

class MyViewHolderRequest extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView fullName, adress, phone,email,identityNumber,password;
    CardView recCard;
    ImageButton deletedMarkter;
    Button approve,rejected;

    public MyViewHolderRequest(@NonNull View itemView) {
        super(itemView);

        recCard = itemView.findViewById(R.id.RequestCard);
        approve=itemView.findViewById(R.id.approved);
        rejected=itemView.findViewById(R.id.rejacted);
        fullName = itemView.findViewById(R.id.Requestname);
        phone = itemView.findViewById(R.id.Requestphone);
        email = itemView.findViewById(R.id.Requestemail);
        identityNumber= itemView.findViewById(R.id.Requestidentity);
        adress = itemView.findViewById(R.id.Requestadress);

    }
}
