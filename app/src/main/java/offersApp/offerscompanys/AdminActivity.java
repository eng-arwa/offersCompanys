package offersApp.offerscompanys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
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

import offersApp.offerscompanys.databinding.ActivityAdmin2Binding;

public class AdminActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    ImageButton deletecompany;
    RecyclerView recyclerView;
    List<DataAdmin> dataList;
    MyAdapterAdmin adapter;
    SearchView searchView;
    Button normalUserBtn;
    String imageUrl = "";
    CardView card;
    TextView countcompany;
    String key = "";
    private AppBarConfiguration appBarConfiguration;
    private ActivityAdmin2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdmin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        countcompany=findViewById(R.id.countcompany);

//        start
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation2);


        bottomNavigationView.setSelectedItemId(R.id.companyslist);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.companyslist:
                    return true;
                case R.id.Marketer:
                    startActivity(new Intent(getApplicationContext(), MarketerList.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.addcompany:

                    startActivity(new Intent(getApplicationContext(), AddCompany.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;

                case R.id.logout:

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        return true;
                case R.id.Request:

                    startActivity(new Intent(getApplicationContext(), RequestSignUp.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;



            }
            return false;
        });
//        end navigation
        recyclerView = findViewById(R.id.recyclerViewadmin);
        searchView=findViewById(R.id.searchcompanyadmin);
        card=findViewById(R.id.AdminCard);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AdminActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<DataAdmin>();
        adapter = new MyAdapterAdmin(AdminActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference("Companys");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){


                    DataAdmin dataClass = itemSnapshot.getValue(DataAdmin.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                    String c= String.valueOf(adapter.getItemCount());
                    countcompany.setText(c.toString());


                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

    }
    public void searchList(String text){
        ArrayList<DataAdmin> searchList = new ArrayList<>();
        for (DataAdmin dataClass: dataList){
            if (dataClass.getFullName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }

}