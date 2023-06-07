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
import androidx.navigation.ui.AppBarConfiguration;
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



public class MarketerList extends AppCompatActivity {
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<DataMarkter> dataList;
    MyAdapterMarkter adapter;
    ImageButton deletemarkter;
    SearchView searchView;
    Button normalUserBtn;
    private AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketer_list);

        //        start
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation2);

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
        recyclerView = findViewById(R.id.recyclerListMarketer);
//
//
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MarketerList.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(MarketerList.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new MyAdapterMarkter(MarketerList.this, dataList);
        recyclerView.setAdapter(adapter);
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataMarkter dataClass = itemSnapshot.getValue(DataMarkter.class);


                   if(itemSnapshot.child("datausertype").getValue().toString().equals("marketer")){
                    Toast.makeText(MarketerList.this, itemSnapshot.getValue(DataMarkter.class).toString(), Toast.LENGTH_LONG).show();
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(itemSnapshot.getValue(DataMarkter.class));
                    }



                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
//         deletemarkter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("offers");
//                DatabaseReference reference1;
//                Toast.makeText(MarketerList.this, "clicked", Toast.LENGTH_SHORT).show();
////                reference.child(key).removeValue();
//            }
//        });

    }
}