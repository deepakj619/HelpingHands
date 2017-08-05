package helpinghand.com.helpinghands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterSelect extends AppCompatActivity implements View.OnClickListener {
    private Button btn_reg_orphan;
    private Button btn_reg_donor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_select);
        btn_reg_orphan=(Button) findViewById(R.id.btn_reg_orphan);
        btn_reg_orphan.setOnClickListener(this);
        btn_reg_donor=(Button)findViewById(R.id.btn_reg_donor);
        btn_reg_donor.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        if(v==btn_reg_orphan) {
            Intent i = new Intent(getApplicationContext(), RegisterOrphanage.class);
            startActivity(i);
        }
        else if(v==btn_reg_donor){

            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);

        }

    }

}