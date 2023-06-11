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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class activity_upload extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    EditText uploadTopic, uploadDesc, uploadLang,uploadpriceafter,uploadpricebefore;
    String imageURL;
    Uri uri;
    boolean valid,stateimage = false;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_upload);
         pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        //        start
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
//
//
//        bottomNavigationView.setSelectedItemId(R.id.add);
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.homepage:
//                    startActivity(new Intent(getApplicationContext(), companyPanel.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//                case R.id.setting:
//                    startActivity(new Intent(getApplicationContext(), Setting.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//                case R.id.share:
//                   share();
//                    return true;
//                case R.id.account:
//                    startActivity(new Intent(getApplicationContext(), Login.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//                case R.id.add:
//                    return true;
//            }
//            return false;
//        });
//        end
        uploadImage = findViewById(R.id.uploadImage);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadTopic = findViewById(R.id.uploadTopic);
        uploadLang = findViewById(R.id.uploadLang);
        uploadpriceafter = findViewById(R.id.uploadpriceafter);
        uploadpricebefore = findViewById(R.id.uploadpricebefore);
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
                            Toast.makeText(activity_upload.this, "No Image Selected", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(activity_upload.this, "No Image Selected", Toast.LENGTH_SHORT).show();}



            }
        });
    }
    public void saveData(){
        checkField(uploadDesc);
        checkField(uploadLang);
        checkField(uploadTopic);

      if (valid){
          StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                  .child(uri.getLastPathSegment());
          AlertDialog.Builder builder = new AlertDialog.Builder(activity_upload.this);
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
                      Toast.makeText(activity_upload.this, " not selected Image", Toast.LENGTH_SHORT).show();
                  }
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  dialog.dismiss();
              }
          });
      }
    }
//    public void uploadData(){
//        String title = uploadTopic.getText().toString();
//        String desc = uploadDesc.getText().toString();
//        String lang = uploadLang.getText().toString();
//        DataClass dataClass = new DataClass(title, desc, lang, imageURL);
//        //We are changing the child from title to currentDate,
//        // because we will be updating title as well and it may affect child value.
//        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//        FirebaseDatabase.getInstance().getReference("Android Tutorials").child(currentDate)
//                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            Toast.makeText(activity_upload.this, "Saved", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(activity_upload.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }


    public void uploadData(){

 //         FirebaseUser user =fAuth.getCurrentUser();

        //    DocumentReference df = fStore.collection("Users").document(user.getUid());

       // StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(user.getUid());

        // Map<String,Object> userInfo = new HashMap<>();

//        userInfo.put("FullName", fullName.getText().toString());
//        userInfo.put("UserEmail", email.getText().toString());
//        userInfo.put("Password", password.getText().toString());
//        userInfo.put("Phone", phone.getText().toString());

        String title = uploadTopic.getText().toString();
        String desc = uploadDesc.getText().toString();
        String lang = uploadLang.getText().toString();
        String Priceafter = uploadpriceafter.getText().toString();
        String Pricebefore = uploadpricebefore.getText().toString();
        DataClass dataClass = new DataClass(title, desc, lang, imageURL,Pricebefore,Priceafter);
        //String isUser ;

        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        Locale locale = new Locale("fr", "FR");
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date = dateFormat.format(new Date());
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
        String time =timeFormat.format(new Date());

        FirebaseDatabase.getInstance().getReference("offers").child(pref.getString("nameuserlogined", null) + time )
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(activity_upload.this, "Saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity_upload.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
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
