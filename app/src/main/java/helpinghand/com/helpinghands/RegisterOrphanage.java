package helpinghand.com.helpinghands;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterOrphanage extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private EditText email;
    private EditText Password;
    private EditText Confirm;
    private EditText regno;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_orphanage);

        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(this);
        Password=(EditText)findViewById(R.id.Password);
        Confirm=(EditText)findViewById(R.id.Confirm);
        email=(EditText)findViewById(R.id.etEmailId);
        auth=FirebaseAuth.getInstance();
        regno=(EditText)findViewById(R.id.srn);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");
    }

    @Override
    public void onClick(View v) {
        if (!((Password.getText().toString()).equals(Confirm.getText().toString()))){

            Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
    }else{

            final String emailId=email.getText().toString();
            String password=Password.getText().toString();
            auth.createUserWithEmailAndPassword(emailId,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

/* String registrationum=regno.getText().toString();
                        String id=databaseReference.push().getKey();
                        DatabaseReference ref = databaseReference.child(id);
                        ref.child("Email").setValue(emailId);
                        ref.child("type").setValue("Orphan");
                        ref.child("Soceity_reg_num").setValue(registrationum);*/
                    if(task.isSuccessful()){

                        String registrationum=regno.getText().toString();
                        String id=databaseReference.push().getKey();
                        DatabaseReference ref = databaseReference.child(id);
                        ref.child("email").setValue(emailId);
                        ref.child("type").setValue("Orphan");
                        ref.child("Soceity_reg_num").setValue(registrationum);
                        Toast.makeText(RegisterOrphanage.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterOrphanage.this,LoginScreen.class);
                        startActivity(intent);
                    }
                    else{

                        Toast.makeText(RegisterOrphanage.this, "Registered UnSuccess", Toast.LENGTH_SHORT).show();


                    }
                }
            });

        }
    }

}