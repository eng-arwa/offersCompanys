package offersApp.offerscompanys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
    AdapterMarketer adapter;
    ImageButton deletemarkter;
    TextView countmarkter;
    SearchView searchView;
    SharedPreferences pref;
    Button normalUserBtn;
    private AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketer_list);
        countmarkter=findViewById(R.id.countmarkter);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();


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
                case R.id.Request:

                    startActivity(new Intent(getApplicationContext(), RequestSignUp.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
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

//        end navigation
        recyclerView = findViewById(R.id.recyclerListMarketer);
      searchView=findViewById(R.id.searchmaeketer);
//
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MarketerList.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(MarketerList.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new AdapterMarketer(MarketerList.this, dataList);
        recyclerView.setAdapter(adapter);
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){



                 try {
                     if(itemSnapshot.child("datausertype").getValue().toString().equals("marketer")){
//
                         DataMarkter dataClass = itemSnapshot.getValue(DataMarkter.class);
                         dataClass.setKey(itemSnapshot.getKey());


                         dataList.add(dataClass);
                         String c= String.valueOf(adapter.getItemCount());
                         countmarkter.setText(c.toString());

                     }
                 }catch (Exception error){}

                    adapter.notifyDataSetChanged();
                    dialog.dismiss();


                }

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
        ArrayList<DataMarkter> searchList = new ArrayList<>();
        for (DataMarkter dataClass: dataList){
            if (dataClass.getFullName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }

}