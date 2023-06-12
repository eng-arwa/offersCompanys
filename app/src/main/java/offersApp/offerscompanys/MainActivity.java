package offersApp.offerscompanys;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
//    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener,eventListener2;
    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    SearchView searchView;
    TextView count;
    Button normalUserBtn;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        count = findViewById(R.id.count);

        //  start navigation

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);


        bottomNavigationView.setSelectedItemId(R.id.homepage);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homepage:
                    return true;
                case R.id.setting:
                    Intent intent=new Intent(MainActivity.this,Setting.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    return true;
                case R.id.share:
                    share();
                    return true;
                case R.id.account:
                    Intent intent2=new Intent(MainActivity.this,Login.class);
                    startActivity(intent2);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    return true;
                case R.id.add:

                    Map<String, ?> entries = pref.getAll();//get all entries from shared preference
                    Set<String> keys = entries.keySet();//set all key entries into an array of string type

                    //first option
                    if (!keys.isEmpty()) {
                        //do your staff here

                        if (pref.getString("typeuserlogined", null).equals("marketer")) {

                            Intent intent1=new Intent(MainActivity.this,MarketerPanel.class);
                            startActivity(intent1);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                            return true;
                        } else if (pref.getString("typeuserlogined", null).equals("admin")) {

                            Intent intent3=new Intent(MainActivity.this,AdminActivity.class);
                            startActivity(intent3);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                            return true;
                        } else if (pref.getString("typeuserlogined", null).toLowerCase().equals("company")) {
                            Intent intent4=new Intent(MainActivity.this,companyPanel.class);
                            startActivity(intent4);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                        }


                    } else if (keys.isEmpty()) {
                        Toast.makeText(this, "You no login in yet", Toast.LENGTH_SHORT).show();
                    }


            }
            return false;
        });
//  end navigation


        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);
//main
//end main
        searchView.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new MyAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference("offers");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
//                    for (DataSnapshot item : itemSnapshot.getChildren()) {
//
//                        DataClass dataClass = item.getValue(DataClass.class);
////                        dataClass.setKey(item.getKey());
////                        dataList.add(dataClass);
//
//                    }
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                    String c = String.valueOf(adapter.getItemCount());
                    count.setText(c.toString());
                }

                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override

            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
        View inflatedView = getLayoutInflater().inflate(R.layout.recycler_item, null);
        TextView text = (TextView) inflatedView.findViewById(R.id.recLang);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                String getstring = text.getText().toString();
                Toast.makeText(MainActivity.this, getstring, Toast.LENGTH_SHORT).show();
            }
        });


//        Toast.makeText(this, (String)count, Toast.LENGTH_SHORT).show();

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
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass: dataList){
          if (dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase())){
              searchList.add(dataClass);
           }
        }
        adapter.searchDataList(searchList);
    }
    public void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }




}