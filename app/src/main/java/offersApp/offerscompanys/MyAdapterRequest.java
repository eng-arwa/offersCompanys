package offersApp.offerscompanys;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyAdapterRequest extends RecyclerView.Adapter<MyViewHolderRequest> {
    ValueEventListener eventListener;
    private Context context;
    Object user ;
    int i=0;
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
        try {
            holder.fullName.setText(dataList.get(position).getFullName());
            holder.phone.setText(dataList.get(position).getPhone());
            holder.adress.setText(dataList.get(position).getAdress());
            holder.email.setText(dataList.get(position).getEmail());
            holder.identityNumber.setText(dataList.get(position).getIdentityNumber());

        }catch (Exception error){}


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
        holder.rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              try {
                  String key=dataList.get(holder.getAdapterPosition()).getKey();
                  final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RequestSignup");

                  reference.child(key).removeValue();
              }catch (Exception error){}

            }
        });
        holder.approve.setOnClickListener(new View.OnClickListener() {

            private Object object;

            @Override
            public void onClick(View view) {

                Locale locale = new Locale("fr", "FR");
                String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
                String date = dateFormat.format(new Date());
                DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
                String key=dataList.get(holder.getAdapterPosition()).getKey();
                String time =timeFormat.format(new Date());

                DataMarkter dataClass = new DataMarkter(
                        dataList.get(holder.getAdapterPosition()).getFullName(),
                        dataList.get(holder.getAdapterPosition()).getPhone(),
                        dataList.get(holder.getAdapterPosition()).getPassword(),
                        dataList.get(holder.getAdapterPosition()).getAdress(),
                        dataList.get(holder.getAdapterPosition()).getEmail(),
                        dataList.get(holder.getAdapterPosition()).getIdentityNumber());

                Toast.makeText(context, dataList.get(holder.getAdapterPosition()).getDatausertype(), Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference("Users").child(time)
                        .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
                                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RequestSignup");
                                    reference.child(key).removeValue();

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "failer", Toast.LENGTH_SHORT).show();
                            }
                        });




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
        phone = itemView.findViewById(R.id.phone_m);
        email = itemView.findViewById(R.id.Requestemail);
        identityNumber= itemView.findViewById(R.id.Requestidentity);
        adress = itemView.findViewById(R.id.Requestadress);

    }
}
