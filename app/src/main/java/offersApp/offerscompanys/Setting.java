package offersApp.offerscompanys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import offersApp.offerscompanys.databinding.ActivitySettingBinding;

public class Setting extends AppCompat {

    private AppBarConfiguration appBarConfiguration;
   // private ActivitySettingBinding binding;
   Button arButton,enButton;
   TextView loginText;

   //FloatingActionButton fabBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        enButton= findViewById(R.id.enButton);
        arButton = findViewById(R.id.arButton);
        loginText = findViewById(R.id.loginTextSetting);


        LanguageManager lang = new LanguageManager(this);


        //  start navigation


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationSetting);


        bottomNavigationView.setSelectedItemId(R.id.homepage);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homepage:
                    //return true;
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                case R.id.setting:
//                    startActivity(new Intent(getApplicationContext(), Setting.class));
//
//
//
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
                    return true;
                case R.id.share:
                    share();
                    return true;
                case R.id.account:
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.add:

            }
            return false;
        });
//  end navigation



        enButton.setOnClickListener(view ->
        {

            lang.updateResource("en");
            recreate();

        });

        arButton.setOnClickListener(view ->
        {

            lang.updateResource("ar");
            recreate();


        });



//        signupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Setting.this, SignupActivity.class);
//                startActivity(intent);
//                  finish();
//            }
//        });





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