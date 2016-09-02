package parcelableserializable.parcelableserializable.com.parcelableserializabledemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import parcelableserializable.parcelableserializable.com.parcelableserializabledemo.parcelable.ParcelableTest;
import parcelableserializable.parcelableserializable.com.parcelableserializabledemo.serializable.SerializableTest;

public class SecondActivity extends AppCompatActivity {

    private TextView textView2;
    private ArrayList<SerializableTest> serializableTestList = new ArrayList<>();
    private ArrayList<ParcelableTest> parcelableTestsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.senond);
        textView2 = (TextView) findViewById(R.id.textView2);
        serializableTestList = (ArrayList<SerializableTest>) getIntent().getExtras().getSerializable("keyseri");

        long startTime = getIntent().getLongExtra("keytime", 0);

        String serStr = "";
        if (serializableTestList != null && serializableTestList.size() > 0) {
            serStr = serializableTestList.get(1).sceneryName;
        }

        parcelableTestsList = getIntent().getExtras().getParcelableArrayList("keyparce");
        String parStr = "";
        if (parcelableTestsList != null && parcelableTestsList.size() > 0) {
            parStr = parcelableTestsList.get(1).sceneryName;
        }

        String str = "TimeCost: parcelTime" + "-->" + (System.currentTimeMillis() - startTime) + "-->" + (parcelableTestsList != null ? parcelableTestsList.size() : 0) + "-->" + parStr + "\n"
                + "TimeCost: serilizableTime " + "-->" + (System.currentTimeMillis() - startTime) + "-->" + (serializableTestList != null ? serializableTestList.size() : 0) + "-->" + serStr;
        textView2.setText(str);
    }
}
