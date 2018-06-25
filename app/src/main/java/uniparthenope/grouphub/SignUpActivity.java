package uniparthenope.grouphub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.jar.Attributes;


public class SignUpActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private String mail, password, nome;
    private EditText Email, Password, Nome;
    private Button register, login;
    FirebaseUser user;
    public static FirebaseDatabase mDatabase;
    public static String Email_Logged;
    public static int Device_Width;



    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.sign_up_activity);


        if (mDatabase == null){
            mDatabase = FirebaseDatabase.getInstance();
            //FirebaseDatabase.getInstance().setPersistenceEnabled(true); //Rivedi domani
        }

        mAuth = FirebaseAuth.getInstance();

        register = (Button)findViewById(R.id.register);
        login = (Button)findViewById(R.id.login);

        Email = (EditText)findViewById(R.id.register_email);
        Password = (EditText)findViewById(R.id.password);
        Nome = (EditText)findViewById(R.id.name_register);

        if (mAuth.getCurrentUser() != null) {
            Email_Logged = mAuth.getCurrentUser().getEmail();
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = Email.getText().toString().trim();
                String getName = Nome.getText().toString().trim();
                String getPassword = Password.getText().toString().trim();
                callRegister(getEmail, getPassword);
            }
        });
    }

    private void callRegister(String Email, String Password){
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "Registrazione Fallita", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            userProfile();



                            FirebaseUser user = mAuth.getCurrentUser();
                            String UserID=user.getEmail().replace("@","").replace(".","");
                            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

                            DatabaseReference ref1= mRootRef.child("Users").child(UserID);

                            ref1.child("Name").setValue(Nome.getText().toString().trim());
                            ref1.child("Image_Url").setValue("Null");
                            ref1.child("Email").setValue(user.getEmail());




                            Log.d("TESTING", "Registrazione effettuata con successo" + task.isSuccessful());
                            Toast.makeText(SignUpActivity.this, "Account Creato ", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Accoun Creato");
                        }
                    }
                });
    }

    private void userProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null)
        {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(Nome.getText().toString().trim())
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TESTING", "User profile updated.");
                            }
                        }
                    });
        }
    }

}