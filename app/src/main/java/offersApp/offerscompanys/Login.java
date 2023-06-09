package offersApp.offerscompanys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class Login extends AppCompat {
    //    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//    SharedPreferences.Editor editor = pref.edit();
    EditText username,password;
    Button loginBtn;
    TextView gotoRegister ,back,logout;
    boolean valid = true;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    List<DataAdmin> dataList;
    AdapterMarketer adapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDbRef;
    FirebaseUser user;
    //String uid;
    String fullname,keyuser,userpassword,usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        start
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
//
//
//
//
//
//        bottomNavigationView.setSelectedItemId(R.id.account);
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.homepage:
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//                case R.id.setting:
//                    startActivity(new Intent(getApplicationContext(), Login.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//                case R.id.share:
//                    startActivity(new Intent(getApplicationContext(), activity_upload.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//                case R.id.account:
//                    startActivity(new Intent(getApplicationContext(), Login.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//                case R.id.add:
//                    startActivity(new Intent(getApplicationContext(), Login.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//            }
//            return false;
//        });
//        end



        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        username = findViewById(R.id.username);
        password = findViewById(R.id.userpass);
        loginBtn = findViewById(R.id.loginButton);
        gotoRegister = findViewById(R.id.RegisterButton);
        back=findViewById(R.id.Back);
        logout=findViewById(R.id.logout);



        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
//                // setContentView(R.layout.activity_signup);

                Intent intent = new Intent(Login.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                // setContentView(R.layout.activity_signup);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(username);
                checkField(password);
                if(checkField(username)&& checkField(password)){
                    String name=username.getText().toString();
                    String pass=password.getText().toString();


//                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");
//start
                    DatabaseReference root = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference users = root.child("Users");
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            step:
                            for(DataSnapshot snapshotinstace:snapshot.getChildren()){

                                Map<String, Object> Userdata =
                                        (Map<String, Object>) snapshotinstace.getValue();
                                keyuser=snapshotinstace.getKey();
                                fullname =(String) Userdata.get("fullName");
                                userpassword =(String) Userdata.get("password");
                                usertype =(String) Userdata.get("datausertype");





                                if(name.trim().equals(fullname) || pass.trim().equals(userpassword)){

                                    if(name.trim().equals(fullname) && !(pass.trim().equals(userpassword))){
//                                        Toast.makeText(Login.this, "Passwotd Incooret", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(!(name.trim().equals(fullname)) && pass.trim().equals(userpassword)){
//                                        Toast.makeText(Login.this, "name Incooret", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (name.trim().equals(fullname) && pass.trim().equals(userpassword)) {
                                        editor.clear();
                                        editor.commit();
                                        keyuser=snapshotinstace.getKey();
                                        Toast.makeText(Login.this, keyuser, Toast.LENGTH_SHORT).show();
                                        editor.putBoolean("isLogined", true); // Storing boolean - true/false
                                        editor.putString("nameuserlogined", fullname); // Storing string
                                        editor.putString("passworduserlogined", userpassword); // Storing integer
                                        editor.putString("typeuserlogined",  usertype); // Storing float// Storing long
                                        editor.putString("keyuser",  keyuser); // Storing float// Storing long

                                        editor.commit(); // commit changes

                                        if (usertype.trim().equals("marketer") || usertype.trim().equals("admin") || usertype.trim().equals("company") ){
//
                                            Intent intent=new Intent(Login.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();



                                        }

                                        break step;
                                    }


                                }
//                                else{
//
//                                    Toast.makeText(Login.this, "sorry you are not login yet", Toast.LENGTH_SHORT).show();
//
//                                }


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

//                    end


                }



            }
        });


//        enButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeLanguage("en");
//                recreate();
//
//            }
//        });
//
//        arButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeLanguage("ar");
//                recreate();
//
//            }
//        });

    }

//    private void changeLanguage(String language)
//    {
//        Locale locale = new Locale(language);
//
//        Locale.setDefault(Locale.forLanguageTag(locale.getLanguage()));
//
//        Configuration configuration= new Configuration();
//
//        configuration.locale = locale;
//
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            setSystemLocale(configuration, locale);
//        }else{
//            setSystemLocaleLegacy(configuration, locale);
//        }
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            getApplicationContext().getResources().updateConfiguration(configuration,getApplicationContext().getResources().getDisplayMetrics());
//
//    }
//
//        // After success we can handle them with shared preference
//
//        // Update Items
//        username.setText(getString(R.string.Username));
//        loginTitle.setText(getString(R.string.Login));
//
//        password.setText(getString(R.string.Password));
//
//
//
//
//    }
//
//

//    public static void setLanguage(String languageCode){
//        Locale locale = new Locale(languageCode);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            setSystemLocale(config, locale);
//        }else{
//            setSystemLocaleLegacy(config, locale);
//        }
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
//            getApplicationContext().getResources().updateConfiguration(config,
//                    getApplicationContext().getResources().getDisplayMetrics());
//    }






    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }

}