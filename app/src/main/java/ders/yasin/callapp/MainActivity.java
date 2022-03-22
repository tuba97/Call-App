package ders.yasin.callapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etPhone;
    ImageButton ibCall;
    String phoneNumber;
    final static int CALL_CODE=100;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int ID=item.getItemId();
        switch (ID){
            case R.id.menu_camera:
                Toast.makeText(getApplicationContext(),"Camera will open",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_share:
                Toast.makeText(getApplicationContext(),"Share window will open",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_exit:
                System.exit(0);
                //finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPhone=findViewById(R.id.et_Phone);
        ibCall=findViewById(R.id.ib_Call);
        ibCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber=etPhone.getText().toString();
                Call();
            }
        });
    }

    private void Call() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Info");
                builder.setMessage("This application needs CALL permision to call someone");
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CALL_PHONE},CALL_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CALL_CODE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                Call();
            else
                Toast.makeText(getApplicationContext(),"User denied the CALL permission",Toast.LENGTH_SHORT).show();
        }
    }


}