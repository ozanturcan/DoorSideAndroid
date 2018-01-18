package ozanturcan.com.doorsidenoti;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ozanturcan.com.doorsidenoti.Operations.FirebaseDatabaseOperations;

public class IntentQrScanActivity extends AppCompatActivity {
    FirebaseDatabaseOperations firebaseDatabaseOperations = new FirebaseDatabaseOperations().getAnInnerClass();
    EditText name;
    EditText phone;
    EditText mail;
    EditText subject;
    EditText message;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_qr_scan);

        name=(EditText) findViewById(R.id.edt_name);
        phone=(EditText) findViewById(R.id.edt_phone);
        mail=(EditText) findViewById(R.id.edt_mail);
        subject=(EditText) findViewById(R.id.edt_subject);
        message=(EditText) findViewById(R.id.edt_message);
        sendButton=(Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabaseOperations.sendTokenMessage(name.getText().toString(),phone.getText().toString(),mail.getText().toString(),subject.getText().toString(),message.getText().toString());

            }
        });
    }


    public void onBackPressed() {
        super.onBackPressed();
    }
}
