package offersApp.offerscompanys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class Login extends AppCompatActivity {
//    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//    SharedPreferences.Editor editor = pref.edit();
    EditText username,password;
    Button loginBtn;
    TextView gotoRegister;
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
    String fullname,keyuser,userpassword,usertype, statelogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        username = findViewById(R.id.username);
        password = findViewById(R.id.userpass);
        loginBtn = findViewById(R.id.loginButton);
        gotoRegister = findViewById(R.id.RegisterButton);



        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SignupActivity.class));
               // setContentView(R.layout.activity_signup);
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






//                                     if(!(name.trim().equals(fullname)) && !(pass.trim().equals(userpassword))){
//
//
//                                    statelogin="sorry you are not login yet";
//
//                                     }
                                     if(name.trim().equals(fullname) && !pass.trim().equals(userpassword)){
                                        statelogin="Passwotd Incooret";
                                    }
                                    else if(!(name.trim().equals(fullname)) && pass.trim().equals(userpassword)){

                                       statelogin="name Incooret";
                                    }
                                    else if (name.trim().equals(fullname) && pass.trim().equals(userpassword)) {
                                        editor.clear();
                                        editor.commit();
                                        keyuser=snapshotinstace.getKey();
                                        editor.putBoolean("isLogined", true); // Storing boolean - true/false
                                        editor.putString("nameuserlogined", fullname); // Storing string
                                        editor.putString("passworduserlogined", userpassword); // Storing integer
                                        editor.putString("typeuserlogined",  usertype); // Storing float// Storing long
                                        editor.putString("keyuser",  keyuser); // Storing float// Storing long

                                        editor.commit(); // commit changes

                                        if (usertype.trim().equals("marketer") || usertype.trim().equals("admin") || usertype.trim().equals("company")){

                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));



                                        }

                                        break step;
                                    }







                            }
                            Toast.makeText(Login.this, statelogin, Toast.LENGTH_SHORT).show();


                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }


                    });

//                    end



                }



            }
        });

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