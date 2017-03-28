package com.jaypee.dheerain.sms_sender;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button send;
    int count=0;
    SmsManager smsManager = SmsManager.getDefault();
    String number;
    TextView contactNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerManager mgr = (PowerManager)getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyWakeLock");
        wakeLock.acquire();
        setContentView(R.layout.activity_main);
        send= (Button) findViewById(R.id.add);
        contactNumber= (TextView) findViewById(R.id.contact);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);

            }
        });
    }

    private void startService() {
        Intent intent=new Intent(MainActivity.this,MessageService.class);
        intent.putExtra("number",number);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Uri contactData = data.getData();
                Cursor cursor =  managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                number =       cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //contactName.setText(name);
                contactNumber.setText(number);
                startService();
                //contactEmail.setText(email);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    /*@Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        int count =0;
        if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN && event.getEventTime()>2000 && count<1){
            Toast.makeText(getBaseContext(), "volume down button pressed for 2 seconds", Toast.LENGTH_SHORT).show();
            smsManager.sendTextMessage(contactNumber.getText().toString(), null, "sms message", null, null);
            count++;
            return super.onKeyLongPress(keyCode, event);

        }

        if(keyCode==KeyEvent.KEYCODE_VOLUME_UP && event.getEventTime()>2000){
            Toast.makeText(getBaseContext(), "volume up button pressed for 2 seconds", Toast.LENGTH_SHORT).show();
            return super.onKeyLongPress(keyCode, event);
        }

        return super.onKeyLongPress(keyCode, event);
    }*/

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN && event.getRepeatCount()==3){
            count++;
            //Toast.makeText(getBaseContext(), "volume down button pressed for 3 time", Toast.LENGTH_SHORT).show();
            smsManager.sendTextMessage(contactNumber.getText().toString(), null, "sms message", null, null);

            return true;

        }

        if(keyCode==KeyEvent.KEYCODE_VOLUME_UP && event.getEventTime()>2000){
            Toast.makeText(getBaseContext(), "volume up button pressed for 2 seconds", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    /*@Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        Toast.makeText(this, "hello helo "+String.valueOf(repeatCount), Toast.LENGTH_SHORT).show();
        if(keyCode==KeyEvent.KEYCODE_VOLUME_UP && repeatCount==3){
            Toast.makeText(getBaseContext(), "volume up button pressed for 2 seconds", Toast.LENGTH_SHORT).show();
            smsManager.sendTextMessage(contactNumber.getText().toString(), null, "Tu chutiya  message", null, null);
            return super.onKeyLongPress(keyCode, event);
        }

        return super.onKeyMultiple(keyCode, repeatCount, event);
    }*/
}
