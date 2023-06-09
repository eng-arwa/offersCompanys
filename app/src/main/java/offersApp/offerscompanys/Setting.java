package offersApp.offerscompanys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;
import java.util.Set;

public class Setting extends AppCompat {

    private AppBarConfiguration appBarConfiguration;
   // private ActivitySettingBinding binding;
   Button arButton,enButton;
   TextView loginText;

    SharedPreferences pref;




    //FloatingActionButton fabBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        enButton= findViewById(R.id.enButton);
        arButton = findViewById(R.id.arButton);
        loginText = findViewById(R.id.loginTextSetting);

//        SharedPreferences.Editor editor = pref.edit();



        LanguageManager lang = new LanguageManager(this);


        //  start navigation


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationsetting);


        bottomNavigationView.setSelectedItemId(R.id.setting);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homepage:
                    //return true;
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                case R.id.setting:
//
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
                    Map<String, ?> entries = pref.getAll();//get all entries from shared preference
                    Set<String> keys = entries.keySet();//set all key entries into an array of string type

                    //first option
                    if (!keys.isEmpty()) {
                        //do your staff here
                        Toast.makeText(this, pref.getString("typeuserlogined", null), Toast.LENGTH_SHORT).show();

                        if (pref.getString("typeuserlogined", null).equals("marketer")) {
                            startActivity(new Intent(getApplicationContext(), MarketerPanel.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                            return true;
                        } else if (pref.getString("typeuserlogined", null).equals("admin")) {
                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                            return true;
                        } else if (pref.getString("typeuserlogined", null).equals("company")) {
                            startActivity(new Intent(getApplicationContext(), AddCompany.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                            return true;
                        }


                    } else if (keys.isEmpty()) {
                        Toast.makeText(this, "You no login in yet", Toast.LENGTH_SHORT).show();
                    }


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