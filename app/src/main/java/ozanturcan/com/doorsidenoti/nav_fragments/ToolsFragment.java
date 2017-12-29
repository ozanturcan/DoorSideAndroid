package ozanturcan.com.doorsidenoti.nav_fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import ozanturcan.com.doorsidenoti.R;

import static android.content.ContentValues.TAG;

/**
 * Created by DELL on 23.10.2017.
 */

public class ToolsFragment extends Fragment implements View.OnClickListener, ZXingScannerView.ResultHandler{
    private ZXingScannerView zXingScannerView;
    private Button scann;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_tools,container,false);

        scann=(Button) rootview.findViewById(R.id.ScannButton);
        scann.setOnClickListener(this);
        return rootview;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: 1231");
        zXingScannerView =new ZXingScannerView(getActivity().getApplicationContext());
        this.getActivity().setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (zXingScannerView !=null)
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {


        if (result.getText().equals("CurrentToken")){
            Log.d("Resulz:","Result geldi baba!");
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this.getActivity(), R.style.Theme_AppCompat_Dialog_Alert);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                builder = new AlertDialog.Builder(this.getActivity().getApplicationContext(), android.R.style.Theme_Material_Dialog_Alert);
//            } else {
//                builder = new AlertDialog.Builder(this.getActivity().getApplicationContext());
//            }
            builder.setTitle("Uyarı")
                    .setMessage("Mesaj Göndermek İstiyor Musunuz?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            Toast.makeText(getActivity().getApplicationContext(),"Tamam seni yönlendiriyorum",Toast.LENGTH_SHORT).show();
//                           Intent ıntent=new Intent(getActivity(),IntentQrScanActivity.class);
//                           startActivity(ıntent);


                        }
                    })
                    .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            Toast.makeText(getActivity().getApplicationContext(),"Hoca da çok meraklıydı zaten",Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        Toast.makeText(getActivity().getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        zXingScannerView.resumeCameraPreview(this);

    }



}
