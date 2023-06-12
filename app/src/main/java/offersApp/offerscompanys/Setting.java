package offersApp.offerscompanys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Setting extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
   // private ActivitySettingBinding binding;
   Button arButton,enButton;
   TextView loginText, languageTextSetting;
   EditText nameaccount,passaccount,adressaccount,emailaccount,phoneaccount;
   String naccount,paccount,adaccount,emaccount,phoccount,identity ,logo,type,created;
   Button edit;
//    LinearLayout parentaccount;

    SharedPreferences pref;
boolean   valid = true, state=false;
    LinearLayout one;




    //FloatingActionButton fabBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Locale locale = new Locale("fr", "FR");
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        LanguageManager lang = new LanguageManager(this);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date = dateFormat.format(new Date());
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
        String time =timeFormat.format(new Date());
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        enButton= findViewById(R.id.enButton);
        arButton = findViewById(R.id.arButton);
        loginText = findViewById(R.id.loginTextSetting);
        languageTextSetting = findViewById(R.id.languageTextSetting);
        nameaccount=findViewById(R.id.nameacount);
        phoneaccount=findViewById(R.id.phoneacount);
        emailaccount=findViewById(R.id.emailacount);
        passaccount=findViewById(R.id.passacount);
        adressaccount=findViewById(R.id.adressacount);
        edit=findViewById(R.id.edit);

//        disable fileds
        disableEditText(nameaccount);disableEditText(passaccount);disableEditText(emailaccount);
        disableEditText(phoneaccount);disableEditText(adressaccount);
//        parentaccount=(LinearLayout)findViewById(R.id.parentaccount);

//        SharedPreferences.Editor editor = pref.edit();





//  end navigation
        Map<String, ?> entries = pref.getAll();//get all entries from shared preference
        Set<String> keys = entries.keySet();//set all key entries into an array of string type

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

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();


            }
            return false;
        });
        if (keys.isEmpty()) {
             one = (LinearLayout) findViewById(R.id.parentaccount);
            one.setVisibility(View.GONE);

        }


        //first option
       else if (!keys.isEmpty()) {
            DatabaseReference root = FirebaseDatabase.getInstance().getReference();
            DatabaseReference users = root.child("Users").child(pref.getString("keyuser", null));
            DataMarkter user = new DataMarkter();
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshotinstace : snapshot.getChildren()) {
//                    if(pref.getString("typeuserlogined", null).toLowerCase().equals("marketer")) {

                        if (snapshotinstace.getKey().equals("fullName")) {
                            naccount = snapshotinstace.getValue().toString();
                            nameaccount.setText(naccount);


                        } else if (snapshotinstace.getKey().equals("address") || snapshotinstace.getKey().equals("adress") || snapshotinstace.getKey().equals("dataadress")) {
                            adaccount = snapshotinstace.getValue().toString();
                            Toast.makeText(Setting.this, snapshotinstace.getValue().toString(), Toast.LENGTH_SHORT).show();
                        } else if (snapshotinstace.getKey().equals("phone") || snapshotinstace.getKey().equals("dataphone")) {
                            phoccount = snapshotinstace.getValue().toString();
                            phoneaccount.setText(paccount);

                        } else if (snapshotinstace.getKey().equals("password")) {
                            paccount = snapshotinstace.getValue().toString();
//                        passaccount.setText(paccount);
                        } else if (snapshotinstace.getKey().equals("email") || snapshotinstace.getKey().equals("dataemail")) {
                            emaccount = snapshotinstace.getValue().toString();
//
                        } else if (snapshotinstace.getKey().equals("identityNumber") || snapshotinstace.getKey().equals("dataidentity")) {
                            identity = snapshotinstace.getValue().toString();
                        } else if (snapshotinstace.getKey().equals("datalogo")) {
                            logo = snapshotinstace.getValue().toString();
                        } else if (snapshotinstace.getKey().equals("datatype")) {
                            type = snapshotinstace.getValue().toString();
                        } else if (snapshotinstace.getKey().equals("dataCreatedBy")) {
                            created = snapshotinstace.getValue().toString();
                        }
//
//                    }
//                    if(pref.getString("typeuserlogined", null).toLowerCase().equals("marketer")) {
//
//                    }
                        nameaccount.setText(naccount);
                        passaccount.setText(paccount);
                        emailaccount.setText(emaccount);
                        phoneaccount.setText(paccount);
                        adressaccount.setText(adaccount);


                    }
                    //


                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }



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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!state){
                    state=true;
                    edit.setText("save");
                    undisableEditText(nameaccount);
                    undisableEditText(passaccount);
                    undisableEditText(emailaccount);
                    undisableEditText(phoneaccount);
                    undisableEditText(adressaccount);

                }
                else if(state) {
                    checkField(nameaccount);
                    checkField(passaccount);
                    checkField(emailaccount);
                    checkField(adressaccount);
                    checkField(phoneaccount);
                    if(valid){
                        naccount=nameaccount.getText().toString();
                        phoccount=phoneaccount.getText().toString();
                        emaccount=emailaccount.getText().toString();
                        adaccount=adressaccount.getText().toString();
                        paccount=passaccount.getText().toString();


                        if(pref.getString("typeuserlogined", null).toLowerCase().equals("marketer") ){
                            DataMarkter dataClass = new DataMarkter(naccount, phoccount, emaccount, adaccount,identity,paccount);


                            FirebaseDatabase.getInstance().getReference("Users").child(pref.getString("keyuser", null))
                                    .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(Setting.this, "Updated", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), Login.class));
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Setting.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else if(pref.getString("typeuserlogined", null).toLowerCase().equals("company")){
                            DataAdmin dataClass = new DataAdmin(naccount, paccount,type,phoccount,logo, emaccount, adaccount,identity,created);

                            Toast.makeText(Setting.this, "company", Toast.LENGTH_SHORT).show();

                            FirebaseDatabase.getInstance().getReference("Users").child(pref.getString("keyuser", null))
                                    .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){

                                                Toast.makeText(Setting.this, "Updated", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), Login.class));
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Setting.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else if( pref.getString("typeuserlogined", null).toLowerCase().equals("admin")){
                            DataAddAdmin dataClass = new DataAddAdmin(naccount, phoccount, emaccount, adaccount,identity,paccount,pref.getString("typeuserlogined", null));


                            FirebaseDatabase.getInstance().getReference("Users").child(pref.getString("keyuser", null))
                                    .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(Setting.this, "Updated", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), Login.class));
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Setting.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }

                    else{
                        Toast.makeText(Setting.this, "Pleas Fill all filed", Toast.LENGTH_SHORT).show();

                    }

                }



            }
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

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
//        editText.setKeyListener(null);
//        editText.setBackgroundColor(Color.TRANSPARENT);
    }
    private void undisableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setFocusableInTouchMode(true);

    }
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