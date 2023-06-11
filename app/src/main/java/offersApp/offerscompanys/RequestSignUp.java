package offersApp.offerscompanys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RequestSignUp extends AppCompatActivity {
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<DataMarkter> dataList;
    MyAdapterRequest adapter;
    ImageButton deletemarkter;
    TextView countRequest;
    SearchView searchView;
    Button normalUserBtn;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_sign_up);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        countRequest=findViewById(R.id.countRequest);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationtequest);

        bottomNavigationView.setSelectedItemId(R.id.Request);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Marketer:
                    startActivity(new Intent(getApplicationContext(), MarketerList.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.companyslist:
                case R.id.addcompany:
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.Request:

                    return true;

                case R.id.logout:

                    editor.clear();
                    editor.commit();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;


            }
            return false;
        });

        recyclerView = findViewById(R.id.recyclerViewrequest);
//
//
        GridLayoutManager gridLayoutManager = new GridLayoutManager(RequestSignUp.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(RequestSignUp.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new MyAdapterRequest(RequestSignUp.this, dataList);
        recyclerView.setAdapter(adapter);
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference("RequestSignup");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    dataList.clear();
                    for (DataSnapshot itemSnapshot: snapshot.getChildren()){



                     try {
                         if(itemSnapshot.child("datausertype").getValue().toString().equals("marketer")){
//
                             DataMarkter dataClass = itemSnapshot.getValue(DataMarkter.class);
                             dataClass.setKey(itemSnapshot.getKey());

                             Toast.makeText(RequestSignUp.this,dataClass.toString(), Toast.LENGTH_LONG).show();

                             dataList.add(dataClass);
                             String c= String.valueOf(adapter.getItemCount());
                             countRequest.setText(c.toString());

                         }
                     }catch (Exception error){}

                        adapter.notifyDataSetChanged();
                        dialog.dismiss();


                    }
                }catch (Exception error){
                    
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

    }

}