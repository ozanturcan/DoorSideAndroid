package ozanturcan.com.doorsidenoti;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ozanturcan.com.doorsidenoti.Operations.FirebaseDatabaseOperations;
import ozanturcan.com.doorsidenoti.Operations.RandomTokenGenerate;

public class MessageActivity extends FirebaseDatabaseOperations implements View.OnClickListener {

    FirebaseDatabaseOperations firebaseDatabaseOperations = new FirebaseDatabaseOperations().getAnInnerClass();

    @BindView(R.id.messageText)
    EditText messageText;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.dateTextview)
    TextView dateTextview;
    @BindView(R.id.sendButton)
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

//        message=(EditText) findViewById(R.id.messageText);
//        date=(TextView) findViewById(R.id.text);
//        sendMessage=(Button) findViewById(R.id.sendButton);
//
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment dialogfragment = new DatePickerDialogTheme3();
//
//                dialogfragment.show(getFragmentManager(), "Theme 3");
//            }
//        });
//
//        sendMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

    @Override
    public void onClick(View v) {

    }

    @OnClick({ R.id.messageText, R.id.text, R.id.dateTextview, R.id.sendButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {



            case R.id.text:
                DialogFragment dialogfragment = new DatePickerDialogTheme3();
                dialogfragment.show(getFragmentManager(), "Theme 3");
                break;
            case R.id.sendButton:
                RandomTokenGenerate randomTokenGenerate= new RandomTokenGenerate(8);
                randomTokenGenerate.nextString();
                String messageToken= randomTokenGenerate.nextString();
                firebaseDatabaseOperations.SendMessage(messageText.getText().toString(),messageToken);
                break;
        }
    }

    public static class DatePickerDialogTheme3 extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_DARK,this,year,month,day);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){

            TextView textview = (TextView)getActivity().findViewById(R.id.dateTextview);

            textview.setText(day + ":" + (month+1) + ":" + year);


        }
    }


}
