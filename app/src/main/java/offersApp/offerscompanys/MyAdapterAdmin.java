package offersApp.offerscompanys;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterAdmin extends RecyclerView.Adapter<MyViewHolderAdmin> {

    private Context context;
    private List<DataAdmin> dataList;

    public MyAdapterAdmin(Context context, List<DataAdmin> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolderAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem_admin, parent, false);
        return new MyViewHolderAdmin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderAdmin holder, int position) {
        Glide.with(context).load(dataList.get(position).getDatalogo()).into(holder.reclogo);
        holder.recname.setText(dataList.get(position).getDataname());
        holder.rectype.setText(dataList.get(position).getDatatype());
        holder.recphone.setText(dataList.get(position).getDataphone());
        holder.recemail.setText(dataList.get(position).getDataemail());

        holder.recadress.setText(dataList.get(position).getDataadress());
        holder.recidentity.setText(dataList.get(position).getDataidentity());
        holder.CreatedBy.setText(dataList.get(position).getDataCreatedBy());


        holder.deletedCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key=dataList.get(holder.getAdapterPosition()).getKey();
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Companys");

                new AlertDialog.Builder(context)
                        .setTitle(R.string.Confirmation)
                        .setMessage(R.string.ConfirmationMsgOfCompany)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(context.getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

                reference.child(key).removeValue();
            }
        });
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

class MyViewHolderAdmin extends RecyclerView.ViewHolder{

    ImageView reclogo;
    TextView recname,recpassword , rectype,recidentity, recphone,recemail,recadress,CreatedBy;
    CardView recCard;
    String datausertype;
ImageButton deletedCompany;
    public MyViewHolderAdmin(@NonNull View itemView) {
        super(itemView);

        reclogo = itemView.findViewById(R.id.reclogo);
        deletedCompany=itemView.findViewById(R.id.deleteCompany);
        recCard = itemView.findViewById(R.id.AdminCard);
        recname = itemView.findViewById(R.id.recname);
        recphone = itemView.findViewById(R.id.recphone);
        rectype = itemView.findViewById(R.id.rectype);
        recidentity = itemView.findViewById(R.id.recidentitynumber);
        recemail = itemView.findViewById(R.id.recemail);
        recadress = itemView.findViewById(R.id.recadress);
        CreatedBy=itemView.findViewById(R.id.RecCreatedBy);


    }
}
