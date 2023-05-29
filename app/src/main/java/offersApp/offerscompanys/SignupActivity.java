package offersApp.offerscompanys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText fullName,email,password,phone,IdentityNumebr,adress;
    Button goToLogin;
    TextView registerBtn;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        adress=findViewById(R.id.adress);
        IdentityNumebr=findViewById(R.id.IdentityNumber);
        registerBtn = findViewById(R.id.registerBtn);
        goToLogin = findViewById(R.id.goToLogin);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(fullName);
                checkField(email);
                checkField(password);
                checkField(phone);
                checkField(adress);
                checkField(IdentityNumebr);
                checkField(password);


                if (valid){
                    // registration process

                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user =fAuth.getCurrentUser();
                            Toast.makeText(SignupActivity.this, "Account Created", Toast.LENGTH_LONG).show();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();

                            userInfo.put("FullName", fullName.getText().toString());
                            userInfo.put("UserEmail", email.getText().toString());
                            userInfo.put("Password", password.getText().toString());
                            userInfo.put("Phone", phone.getText().toString());
                            userInfo.put("adress", adress.getText().toString());
                            userInfo.put("IdentityNumber", IdentityNumebr.getText().toString());

                            // specify if the user is admin
                            userInfo.put("isUser", "1");

                            df.set(userInfo);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(SignupActivity.this, "Failed to Create Account", Toast.LENGTH_LONG).show();


                        }
                    });

                    // realtime

                    // on below line we are creating a new user by passing email and password.
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                        // on below line we are checking if the task is success or not.





                        // specify if the user is admin



                        if (task.isSuccessful()) {




                            // in on success method we are hiding our progress bar and opening a login activity.
                            //loadingPB.setVisibility(View.GONE);
                            Toast.makeText(SignupActivity.this, "User Registered..", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignupActivity.this, Login.class);
                            startActivity(i);
                            finish();
                        } else {

                            // in else condition we are displaying a failure toast message.
                            //loadingPB.setVisibility(View.GONE);
                            Toast.makeText(SignupActivity.this, "Fail to register user..", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });



        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
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