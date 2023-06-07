package offersApp.offerscompanys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import offersApp.offerscompanys.model.MarketerMembershipRequestAdmin;
import offersApp.offerscompanys.model.MarketerMembershipRequestMarketer;
import offersApp.offerscompanys.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class Register extends AppCompatActivity {

    //EditText fullName,email,password,phone;
    EditText fullName,email,password,phone,identityNumber,address;

    Button registerBtn;
    TextView goToLogin;
    boolean valid = true;
    DatabaseReference databaseReference;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        identityNumber = findViewById(R.id.IdentityNumber);
        address = findViewById(R.id.adress);
        registerBtn = findViewById(R.id.registerBtnExr);
        goToLogin = findViewById(R.id.goToLogin);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(fullName);
                checkField(email);
                checkField(password);
                checkField(phone);
                checkField(identityNumber);
                checkField(address);


                if (valid) {
                    // registration process

                   // uploadDataToAdminToApprove();
//                    approvedUsers();
                    createUser();

                   // approvedUsers();

                    // databaseReference = FirebaseDatabase.getInstance().getReference("MarketerMembershipRequest");


                    //if (){}


                    // start
//                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                        @Override
//                        public void onSuccess(AuthResult authResult) {
//                            FirebaseUser user = fAuth.getCurrentUser();
//                            Toast.makeText(Register.this, "Account Created", Toast.LENGTH_LONG).show();
//                            DocumentReference df = fStore.collection("Users").document(user.getUid());
//                            Map<String, Object> userInfo = new HashMap<>();
//
//                            userInfo.put("FullName", fullName.getText().toString());
//                            userInfo.put("UserEmail", email.getText().toString());
//                            userInfo.put("Password", password.getText().toString());
//                            userInfo.put("Phone", phone.getText().toString());
//
//                            // specify if the user is admin
//                            userInfo.put("isUser", "1");
//
//                            df.set(userInfo);
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            finish();
//
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                            Toast.makeText(Register.this, "Failed to Create Account", Toast.LENGTH_LONG).show();
//
//
//                        }
//                    });
                }





                    // end

//                    // realtime
//
//                    // on below line we are creating a new user by passing email and password.
//                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
//                        // on below line we are checking if the task is success or not.
//
//
//
//
//
//                            // specify if the user is admin
//
//
//
//                        if (task.isSuccessful()) {
//
//
//
//
//                            // in on success method we are hiding our progress bar and opening a login activity.
//                            //loadingPB.setVisibility(View.GONE);
//                            Toast.makeText(Register.this, "User Registered..", Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(Register.this, Login.class);
//                            startActivity(i);
//                            finish();
//                        } else {
//
//                            // in else condition we are displaying a failure toast message.
//                            //loadingPB.setVisibility(View.GONE);
//                            Toast.makeText(Register.this, "Fail to register user..", Toast.LENGTH_SHORT).show();
//                        }
//                    });

               // }

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


        public void uploadDataToAdminToApprove(){
        String FullName = fullName.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Phone = phone.getText().toString();
        String IdentityNumber = identityNumber.getText().toString();
        String Address = address.getText().toString();
        String IsApproved = "0";
        String IsMarketer = "1";

           // MarketerMembershipRequestAdmin mrketerMembershipRequest = new MarketerMembershipRequestAdmin(FullName, Email, Password, Phone,IdentityNumber,Address,IsApproved,IsMarketer);

//            MarketerMembershipRequestMarketer mrketerMembershipRequest = new MarketerMembershipRequestMarketer(FullName, Email, Password, Phone,IdentityNumber,Address,IsApproved,IsMarketer);
            MarketerMembershipRequestMarketer mrketerMembershipRequest = new MarketerMembershipRequestMarketer(FullName, Email, Password, Phone,IdentityNumber,Address,IsApproved,IsMarketer);


            //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("MarketerMembershipRequest").child(currentDate)
                .setValue(mrketerMembershipRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "Your Request has been sent to Admin to be approved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
public void createUser(){
fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()){
            //createAdminUser();
            createNewUser();
            Toast.makeText(Register.this, "Your Account has created Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(Register.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();


    }
});

}

    public void createAdminUser(){


        String FullName = fullName.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Phone = phone.getText().toString();
        String IdentityNumber = identityNumber.getText().toString();
        String Address = address.getText().toString();
        //String IsApproved = "1";
      // String IsMarketer = "1";
       String IsAdmin = "1";

  MarketerMembershipRequestAdmin mrketerMembershipRequest = new MarketerMembershipRequestAdmin(FullName, Email, Password, Phone,IdentityNumber,Address,IsAdmin);

                 //  MarketerMembershipRequestMarketer mrketerMembershipRequest = new MarketerMembershipRequestMarketer(FullName, Email, Password, Phone,IdentityNumber,Address,IsMarketer);

                   //We are changing the child from title to currentDate,

        // because we will be updating title as well and it may affect child value.
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Users").child("Admin").child(fAuth.getUid())
                .setValue(mrketerMembershipRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
//                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Your Request has been sent to Admin to be approved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                });



    }

    public void createMarketerUser(){


        String FullName = fullName.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Phone = phone.getText().toString();
        String IdentityNumber = identityNumber.getText().toString();
        String Address = address.getText().toString();
        //String IsApproved = "1";
         String IsMarketer = "1";
        //String IsAdmin = "1";

       // MarketerMembershipRequestAdmin mrketerMembershipRequest = new MarketerMembershipRequestAdmin(FullName, Email, Password, Phone,IdentityNumber,Address,IsAdmin);

         MarketerMembershipRequestMarketer mrketerMembershipRequest = new MarketerMembershipRequestMarketer(FullName, Email, Password, Phone,IdentityNumber,Address,IsMarketer);

        //We are changing the child from title to currentDate,

        // because we will be updating title as well and it may affect child value.
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Users").child("Marketer").child(fAuth.getUid())
                .setValue(mrketerMembershipRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
//                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Your Request has been sent to Admin to be approved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                });



    }

    public void createNewUser(){


        String FullName = fullName.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Phone = phone.getText().toString();
        String IdentityNumber = identityNumber.getText().toString();
        String Address = address.getText().toString();
        //String IsApproved = "1";
//        String UserType = "isMarketer";
        String UserType = "isAdmin";

        //String IsAdmin = "1";

        // MarketerMembershipRequestAdmin mrketerMembershipRequest = new MarketerMembershipRequestAdmin(FullName, Email, Password, Phone,IdentityNumber,Address,IsAdmin);

        User user = new User(FullName, Email, Password, Phone,IdentityNumber,Address,UserType);

        //We are changing the child from title to currentDate,

        // because we will be updating title as well and it may affect child value.
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Users").child(fAuth.getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
//                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Register.this, "You have registered Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                });



    }




}