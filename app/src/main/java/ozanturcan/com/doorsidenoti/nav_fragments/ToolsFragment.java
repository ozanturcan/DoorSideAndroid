package ozanturcan.com.doorsidenoti.nav_fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.WriterException;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import ozanturcan.com.doorsidenoti.MainActivity;
import ozanturcan.com.doorsidenoti.QrCode.Contents;
import ozanturcan.com.doorsidenoti.QrCode.QrCodeEncoder;
import ozanturcan.com.doorsidenoti.R;

import static android.content.ContentValues.TAG;
import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by DELL on 23.10.2017.
 * Çalışmayan bölüm neresi?burası
 */

public class ToolsFragment extends Fragment implements View.OnClickListener, ZXingScannerView.ResultHandler{
    private ZXingScannerView zXingScannerView;
    private Button scann;
    private Button generate;
    private String LOG_TAG = "GenerateQRCode";

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootview=inflater.inflate(R.layout.fragment_tools,container,false);

        scann=(Button) rootview.findViewById(R.id.ScannButton);
        scann.setOnClickListener(this);

        generate=(Button) rootview.findViewById(R.id.GenerateButton);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.GenerateButton:
                        EditText qrInput = (EditText) rootview.findViewById(R.id.qrInput);
                        String qrInputText = qrInput.getText().toString();
                        Log.v(LOG_TAG, qrInputText);

                        //Find screen size
                        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
                        Display display = manager.getDefaultDisplay();
                        Point point = new Point();
                        display.getSize(point);
                        int width = point.x;
                        int height = point.y;
                        int smallerDimension = width < height ? width : height;
                        smallerDimension = smallerDimension * 3 / 4;

                        //Encode with a QR Code image
                        QrCodeEncoder qrCodeEncoder = new QrCodeEncoder(qrInputText,
                                null,
                                Contents.Type.TEXT,
                                BarcodeFormat.QR_CODE.toString(),
                                smallerDimension);
                        try {
                            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                            ImageView myImage = (ImageView) rootview.findViewById(R.id.imageView1);
                            myImage.setImageBitmap(bitmap);

                           String testString =  BitMapToString(bitmap);
                            Log.d(TAG, "onClick: " + testString);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }


                        break;

                    // More buttons go here (if any) ...

                }

            }
        });
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

//    QR code' üzerinden gelen String Değer Bu Şekilde    DeviceID:1-CurrentToken:xhYnbgnV
        String[] test =   result.getText().split("-");
        HashMap<String,String> QrResult = new HashMap<String, String>();
        for (String item: test)
        {
            String[] test2 = item.split(":");
            if (test2[0].equals("DeviceID")){
                QrResult.put("DeviceID",test2[1]);
            }
                if (test2[0].equals("CurrentToken")) {
                    QrResult.put("CurrentToken",test2[1]);
                }


        }
//        if (result.getText().equals("CurrentToken")){
//          QR üzerinden gelen veri HashMap üzerinden ContainsKey ile kontrol ediliyor.
        if (QrResult.containsKey("CurrentToken")){
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
//
// Intent ıntent=new Intent(getActivity(),IntentQrScanActivity.class);
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
