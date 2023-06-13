package offersApp.offerscompanys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class AddAdmin extends AppCompatActivity {

    EditText fullName,emailuser,userpassword,userphone,userIdentityNumebr,useradress;
    Button registerBtn;
    TextView goToLogin;
    boolean valid = true;
    FirebaseAuth fAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);


        fullName = findViewById(R.id.name);
        emailuser = findViewById(R.id.email);
        userpassword = findViewById(R.id.password);
        userphone = findViewById(R.id.phone);
        useradress=findViewById(R.id.adress);
        userIdentityNumebr=findViewById(R.id.IdentityNumber);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(fullName);
                checkField(emailuser);
                checkField(userphone);
                checkField(useradress);
                checkField(userIdentityNumebr);
                checkField(userpassword);
                checkemail(emailuser);




                if( checkField(fullName)&& checkField(emailuser)&& checkField(useradress)&&checkField(userphone)&&
                        checkField(userIdentityNumebr)&&checkField(userpassword)
                ){



                    String name=fullName.getText().toString();
                    String email=emailuser.getText().toString();
                    String phone=userphone.getText().toString();
                    String identitynumber =userIdentityNumebr.getText().toString();
                    String password=userpassword.getText().toString();
                    String adress=useradress.getText().toString();
                    String datausertype = "admin";


                    DataAddAdmin dataAddAdmin = new DataAddAdmin(name,phone,email,adress,identitynumber,password,datausertype);
                    Locale locale = new Locale("fr", "FR");

                    DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
                    String time =timeFormat.format(new Date());


                    FirebaseDatabase.getInstance().getReference("Users").child(time).setValue(dataAddAdmin).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){




                                Toast.makeText(AddAdmin.this, R.string.AdminRegisteredSuccessfully, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();





                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddAdmin.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();


                        }
                    });




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
    public boolean checkemail(EditText textField){

        if(textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        }else {
            if (textField.getText().toString().trim().matches(emailPattern)) {
                valid = true;
            } else {
                textField.setError("Error");
                valid = false;
            }
        }

        return valid;
    }

}