package helpinghand.com.helpinghands;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {

    private EditText etemail;
    private EditText etpassword;
    private Button forgottv,registertv;
    private Button loginbtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setTitle("Login below");
        etemail=(EditText)findViewById(R.id.etEmail);
        etpassword=(EditText)findViewById(R.id.etPassword);
        loginbtn=(Button)findViewById(R.id.bLogin);
        forgottv=(Button) findViewById(R.id.forgot_password);
        registertv=(Button) findViewById(R.id.RegisterUserButton);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");
        progressDialog=new ProgressDialog(this);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userlogin();
            }
        });
        forgottv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginScreen.this,ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });
        registertv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginScreen.this,RegisterSelect.class);
                startActivity(intent);

            }
        });


    }
    private  void userlogin(){

        final String Email=etemail.getText().toString().trim();
        String Password=etpassword.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {

            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)) {

            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        databaseReference=firebaseDatabase.getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                   String dbemail=dataSnapshot1.child("email").getValue().toString();
                    if(dbemail.equals(Email)){

                        String type=dataSnapshot1.child("type").getValue().toString();
                        if(type.equals("Orphan")){

                            Toast.makeText(LoginScreen.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),OrphanProfileActivity.class);
                            intent.putExtra("email",Email);
                            startActivity(intent);
                            break;
                        }
                        else if(type.equals("Donor")){

                            Toast.makeText(LoginScreen.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),DonorProfileActivity.class);
                            intent.putExtra("email",Email);
                            startActivity(intent);
                            break;

                        }

                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        progressDialog.setMessage("Please Wait.....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                    }
                });
    }

}
