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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterMarkter extends RecyclerView.Adapter<MyViewHolderMarkter> {

    private Context context;
    private List<DataMarkter> dataList;

    public MyAdapterMarkter(Context context, List<DataMarkter> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolderMarkter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_markter, parent, false);
        return new MyViewHolderMarkter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMarkter holder, int position) {
        holder.fullName.setText(dataList.get(position).getFullName());
        holder.phone.setText(dataList.get(position).getPhone());
        holder.password.setText(dataList.get(position).getPassword());
        holder.adress.setText(dataList.get(position).getAdress());
        holder.datausertype.setText(dataList.get(position).getDatausertype());
        holder.email.setText(dataList.get(position).getEmail());
        holder.identityNumber.setText(dataList.get(position).getIdentityNumber());


//        holder.recCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, Detailactivity.class);
//                intent.putExtra("name", dataList.get(holder.getAdapterPosition()).getFullName());
//                intent.putExtra("phone", dataList.get(holder.getAdapterPosition()).getPhone());
//                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
//                intent.putExtra("email", dataList.get(holder.getAdapterPosition()).getEmail());
//                intent.putExtra("identity", dataList.get(holder.getAdapterPosition()).getIdentityNumber());
//
//                intent.putExtra("adress", dataList.get(holder.getAdapterPosition()).getAdress());
//
//                context.startActivity(intent);
//            }
//        });
        holder.deletedMarkter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key=dataList.get(holder.getAdapterPosition()).getKey();
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MarketerMembershipRequest");

                reference.child(key).removeValue();
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

class MyViewHolderMarkter extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView fullName, datausertype,adress, phone,email,identityNumber,password;
    CardView recCard;
    ImageButton deletedMarkter;

    public MyViewHolderMarkter(@NonNull View itemView) {
        super(itemView);

        recCard = itemView.findViewById(R.id.markterCard);
        deletedMarkter=itemView.findViewById(R.id.D_markter);
        fullName = itemView.findViewById(R.id.namemarkter);
        phone = itemView.findViewById(R.id.phone_m);
        email = itemView.findViewById(R.id.emailMARKTER);
        identityNumber= itemView.findViewById(R.id.identityMarkter);
        adress = itemView.findViewById(R.id.adressMarkter);

    }
}
