package offersApp.offerscompanys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import offersApp.offerscompanys.model.MarketerMembershipRequestMarketer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDbRef;
    FirebaseUser user;
    //String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        start
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);





        bottomNavigationView.setSelectedItemId(R.id.account);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homepage:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.setting:
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.share:
                    startActivity(new Intent(getApplicationContext(), activity_upload.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.account:
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.add:
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
//        end

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

//         fAuth.getCurrentUser();
        user = FirebaseAuth.getInstance().getCurrentUser();

//        assert user != null;
//        String uid = user.getUid();

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);
        gotoRegister = findViewById(R.id.RegisterButton);






        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), Register.class));
               // setContentView(R.layout.activity_signup);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                Query userType= reference.orderByChild("Marketer");




                //DatabaseReference userType = FirebaseDatabase.getInstance().getReference("Users").child("Marketer").orderByChild("isMarketer").equalTo(1);


//                         .setValue(databaseReference).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            Toast.makeText(Login.this, "Your Request has been sent to Admin to be approved", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Login.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });


//
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                     //Log.d("TAG", "onSuccess:IsAdmin " + userType.get());
//
//                        if (snapshot.exists()){
//
//                           // String dbUserTypeAsAdmin = snapshot.child("Admin").child("isAdmin").getValue(String.class);
//                           // String dbUserTypeAsMarketer = snapshot.child("Marketer").child("isMarketer").getValue(String.class);
//
////                            String fullName = snapshot.child("Marketer").child(user.getUid()).child("fullName").getValue(String.class);
//                            String isMarketer = snapshot.child("isMarketer").getValue(String.class);
//
////                            String isAdmin = snapshot.child("Admin").child(user.getUid()).child("isAdmin").getValue(String.class);
//
//                            if (isMarketer != null){
//
//
//                                Toast.makeText(Login.this, "You Logged as Marketer", Toast.LENGTH_LONG).show();
//
//                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                                    finish();
//                            }
//
//
////                            if (isAdmin != null){
////
////
////                                Toast.makeText(Login.this, "You Logged as Admin", Toast.LENGTH_LONG).show();
////
////                                startActivity(new Intent(getApplicationContext(),Admin.class));
////                                    finish();
////                            }
//
//
////                            for (DataSnapshot itemSnapshot: snapshot.getChildren()){
////                                for (DataSnapshot item : itemSnapshot.getChildren()) {
////                                    MarketerMembershipRequestMarketer marketerMembershipRequestMarketer = item.getValue(MarketerMembershipRequestMarketer.class);
////                                    marketerMembershipRequestMarketer.setKey(item.getKey());
////                                    dataList.add(marketerMembershipRequestMarketer);
////                                }
////                            }
////                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
////                            finish();
//
////                            if (dbUserTypeAsAdmin != null){
////
////
////                                Toast.makeText(Login.this, "You Logged as Admin", Toast.LENGTH_LONG).show();
////
////                                startActivity(new Intent(getApplicationContext(),Admin.class));
////                                    finish();
////                            }
//
//
////                                if (dbUserTypeAsMarketer != null){
////                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
////                                    finish();
////                                }
//
//
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


                checkField(email);
                checkField(password);

                if (valid){



                    fAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("UserType");

                               Query UserTypAdmin = databaseReference.orderByChild("UserType").equalTo("isAdmin");

                            if (UserTypAdmin != null) {


                                startActivity(new Intent(getApplicationContext(), Admin.class));
                                finish();
//                                Query UserTyps = databaseReference.orderByChild("UserType").equalTo("isMarketer");

                            }

                                if(databaseReference != null){


                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();

                                }
                           // }



                            ValueEventListener valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String UserTyp = "";
//                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                        UserTyp = ds.child("UserType").getValue(String.class);

                                    //Query UserTyps = databaseReference.orderByChild("UserType").equalTo("isMarketer");

                                   // Toast.makeText(Login.this+ UserTyps , "Logged Successfully", Toast.LENGTH_LONG).show();


                                    //   Query UserTypAdmin = databaseReference.orderByChild("UserType").equalTo("isAdmin");

//
                                   // UserTyp = dataSnapshot.child("UserType").getValue(String.class);




                                    //Log.d("TAG", UserTyp);
                                    //textView.setText(totalIncome);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    //  Log.d("TAG", databaseError.getMessage()); //Don't ignore errors!

                                }


                            };
                            databaseReference.addListenerForSingleValueEvent(valueEventListener);






                                      // String isMarketer = authResult.getUser().getUid().child("isMarketer").getValue(String.class);



//                            if (authResult != null && databaseReference == 'isMarketer' && FirebaseAuth.getInstance().getCurrentUser() != null ){
//
//                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                                  finish();
//
//                            }



//                            if (authResult != null && databaseReference.equals("isAdmin") && FirebaseAuth.getInstance().getCurrentUser() != null) {
//
//                                startActivity(new Intent(getApplicationContext(),Admin.class));
//                                finish();
//
//                            }



                            //Toast.makeText(Login.this, IsAdmin.toString(), Toast.LENGTH_LONG).show();


//                            Log.d("TAG", "onSuccess:IsAdmin " + IsAdmin);

                            Toast.makeText(Login.this, "Logged Successfully", Toast.LENGTH_LONG).show();

                           // checkUserAccessLevel(authResult.getUser().getUid());


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

       // Query IsAdmin= reference.orderByChild("isAdmin").equalTo("1");






        //extract data from db document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());

                // identify User Access



            }
        });


        //realtime get data

//        DatabaseReference rootRef = FirebaseDatabase.getInstance("Users").getReference(uid);
//        DatabaseReference isMarketer = rootRef.child("Users").child("isMarketer");
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String totalIncome = "";
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    totalIncome = totalIncome + ds.child("Income").getValue(String.class) + " ";
//
//                }
//                Log.d("TAG", totalIncome);
//                //textView.setText(totalIncome);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//              //  Log.d("TAG", databaseError.getMessage()); //Don't ignore errors!
//
//            }
//
//
//        };
//        juneRef.addListenerForSingleValueEvent(valueEventListener);


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