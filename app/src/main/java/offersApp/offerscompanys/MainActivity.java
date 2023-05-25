package offersApp.offerscompanys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import offersApp.offerscompanys.DataClass;
import offersApp.offerscompanys.MyAdapter;
import offersApp.offerscompanys.R;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    DataClass androidData;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);

        searchView.clearFocus();
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();

        androidData = new DataClass("Camera", R.string.camera, "Java", R.drawable.ic_camera);
        dataList.add(androidData);

        androidData = new DataClass("RecyclerView", R.string.recyclerview, "Kotlin", R.drawable.recycler_detail);
        dataList.add(androidData);

        androidData = new DataClass("Date Picker", R.string.date, "Java", R.drawable.ic_date);
        dataList.add(androidData);

        androidData = new DataClass("EditText", R.string.edit, "Kotlin", R.drawable.ic_edit);
        dataList.add(androidData);

        androidData = new DataClass("Rating Bar", R.string.rating, "Java", R.drawable.ic_rating);
        dataList.add(androidData);

        adapter = new MyAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);
    }

    private void searchList(String text){
        List<DataClass> dataSearchList = new ArrayList<>();
        for (DataClass data : dataList){
            if (data.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }
    }

}