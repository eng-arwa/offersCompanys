package offersApp.offerscompanys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCompany extends AppCompatActivity {
    ImageView uploadImage;
    Button saveButton;
    EditText companyphone,passwordcopmany,companyemail,companyidentitiy,companytype,companyadress, companyname,BackLink;
    String imageURL,CreatedBy;
    SharedPreferences pref;
    boolean valid,stateimage=false;

    FirebaseAuth fAuth;
    Uri uri;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

//        start
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        uploadImage = findViewById(R.id.uploadImage);
        companyname = findViewById(R.id.companyname);
        companyadress = findViewById(R.id.companyadress);
        companyemail = findViewById(R.id.comapnyemail);
        companyphone = findViewById(R.id.companyphone);
        companytype =findViewById(R.id.companytype);
        companyidentitiy =findViewById(R.id.companyidentity);
        CreatedBy=pref.getString("nameuserlogined", null).toString();
        passwordcopmany=findViewById(R.id.passwordcopmany);
        saveButton = findViewById(R.id.saveButton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            stateimage=true;
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(AddCompany.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stateimage){
                    saveData();}
                else{

                Toast.makeText(AddCompany.this, "No Image Selected", Toast.LENGTH_SHORT).show();}


//

                }

        });


    }
    public void saveData(){
        checkField(companyadress);
        checkField(companyemail);
        checkField(companyphone);
        checkField(companyname);
        checkField(companyidentitiy);
        checkField(companyadress);
        checkField(passwordcopmany);
        checkField(companytype);
        checkemail(companyemail);



        if(valid){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                    .child(uri.getLastPathSegment());
            AlertDialog.Builder builder = new AlertDialog.Builder(AddCompany.this);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageURL = urlImage.toString();
                    if(!imageURL.isEmpty()){
                        uploadData();
                        dialog.dismiss();


                    }
                    else{
                        Toast.makeText(AddCompany.this, " not selected Image", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(AddCompany.this, " not selected Image ", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    public void uploadData(){

        //         FirebaseUser user =fAuth.getCurrentUser();

        //    DocumentReference df = fStore.collection("Users").document(user.getUid());

        // StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(user.getUid());

        // Map<String,Object> userInfo = new HashMap<>();

//        userInfo.put("FullName", fullName.getText().toString());
//        userInfo.put("UserEmail", email.getText().toString());
//        userInfo.put("Password", password.getText().toString());
//        userInfo.put("Phone", phone.getText().toString());



        String c_name = companyname.getText().toString();
        String c_type = companytype.getText().toString();
        String c_adress = companyadress.getText().toString();
        String c_phone = companyphone.getText().toString();
        String c_email = companyemail.getText().toString();
        String c_identity= companyidentitiy.getText().toString();
        String c_CreatedBy= CreatedBy.toString();
        String c_password=passwordcopmany.getText().toString();
        DataAdmin dataClass = new DataAdmin(c_name,c_password, c_type, c_phone,imageURL,c_email,c_adress,c_identity,c_CreatedBy);
        //String isUser ;

        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        Locale locale = new Locale("fr", "FR");
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date = dateFormat.format(new Date());
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
        String time =timeFormat.format(new Date());
        FirebaseDatabase.getInstance().getReference("Companys").child(c_name)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddCompany.this, "Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCompany.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        FirebaseDatabase.getInstance().getReference("Users").child(time)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddCompany.this, "Saved", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(AddCompany.this,MainActivity.class);
                            startActivity(intent);
                            finish();



                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCompany.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
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