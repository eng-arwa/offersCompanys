package offersApp.offerscompanys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
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
    SearchView searchView;
    Button normalUserBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_sign_up);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationtequest);

        bottomNavigationView.setSelectedItemId(R.id.Marketer);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Marketer:
                    return true;
                case R.id.companyslist:
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.addcompany:

                    startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;

                case R.id.logout:

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;



            }
            return false;
        });

//        end navigation
        recyclerView = findViewById(R.id.recyclerView);
//        View inflatedView = getLayoutInflater().inflate(R.layout.recycler_markter,null);
//        deletemarkter = (ImageButton) inflatedView.findViewById(R.id.D_markter);


//        fab = findViewById(R.id.fab);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(RequestSignUp.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(RequestSignUp.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<DataMarkter>();
        adapter = new MyAdapterRequest(RequestSignUp.this, dataList);
        recyclerView.setAdapter(adapter);
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference("RequestSignup");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    for(DataSnapshot item:itemSnapshot.getChildren()) {
                        DataMarkter dataClass = item.getValue(DataMarkter.class);
                        dataClass.setKey(itemSnapshot.getKey());
                        Toast.makeText(RequestSignUp.this, dataClass.toString(), Toast.LENGTH_SHORT).show();
                    }
//                    dataList.add(dataClass);

                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

    }
}