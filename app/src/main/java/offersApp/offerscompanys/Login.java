package offersApp.offerscompanys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    EditText email,password;
    Button loginBtn;
    TextView gotoRegister;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);
        gotoRegister = findViewById(R.id.RegisterButton);




        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), SignupActivity));
                setContentView(R.layout.activity_signup);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(email);
                checkField(password);

                if (valid){

                    fAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            Toast.makeText(Login.this, "Logged Successfully", Toast.LENGTH_LONG).show();

                            checkUserAccessLevel(authResult.getUser().getUid());


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(Login.this, "There is an error in login", Toast.LENGTH_LONG).show();


                        }
                    });

                }

            }
        });

    }

    private void checkUserAccessLevel(String uid) {

        DocumentReference df = fStore.collection("Users").document(uid);

        //extract data from db document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());

                // identify User Access

                if (documentSnapshot.getString("isAdmin") != null){
                    startActivity(new Intent(getApplicationContext(),Admin.class));
                    finish();
                }

                if (documentSnapshot.getString("isUser") != null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
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

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }
    }
}