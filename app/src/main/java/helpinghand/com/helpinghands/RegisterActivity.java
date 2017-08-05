package helpinghand.com.helpinghands;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText etname, etemail, etphone, etpass;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        instilaiseView();
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                registerUser();

            }

        });


    }
    public void instilaiseView(){

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");
        registerButton = (Button) findViewById(R.id.RegisterButton);
        etname=(EditText)findViewById(R.id.etname);
        etemail=(EditText)findViewById(R.id.Email);
        etphone=(EditText)findViewById(R.id.Phone);
        etpass=(EditText)findViewById(R.id.etPassword);

    }

    public void registerUser(){

        final String name=etname.getText().toString();
        final String email=etemail.getText().toString();
        final String phone=etphone.getText().toString();
        final String password=etpass.getText().toString();
        if(TextUtils.isEmpty(name)){


            etname.setError("Name Can't be Empty");
            return;
        }
        if(TextUtils.isEmpty(email)){


            etemail.setError("Email Can't be Empty");
            return;
        }
        if(TextUtils.isEmpty(phone)){


            etphone.setError("Phone Can't be Empty");
            return;
        }
        if(TextUtils.isEmpty(password)){


            etpass.setError("Password Can't be Empty");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (!task.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    String[] s = task.getException().getMessage().split(":");
                    if (s.length == 1) {
                        builder.setMessage(s[0]);
                    } else {
                        builder.setMessage(s[1]);
                    }

                    builder.setPositiveButton("Close", null);
                    builder.create().show();

                } else{

                    String id=databaseReference.push().getKey();
                    DatabaseReference d =databaseReference.child(id);
                    d.child("email").setValue(email);
                    d.child("name").setValue(name);
                    d.child("phone").setValue(phone);
                    d.child("type").setValue("Donor");
                    Toast.makeText(RegisterActivity.this, "Succesfully Regsitered as User", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginScreen.class);
                    startActivity(intent);
                }

            }
        });

    }
}