package parcelableserializable.parcelableserializable.com.parcelableserializabledemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bluelinelabs.logansquare.LoganSquare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import parcelableserializable.parcelableserializable.com.parcelableserializabledemo.logansquare.LoganResponse;
import parcelableserializable.parcelableserializable.com.parcelableserializabledemo.logansquare.LoganSquareTest;
import parcelableserializable.parcelableserializable.com.parcelableserializabledemo.parcelable.ParcelableTest;
import parcelableserializable.parcelableserializable.com.parcelableserializabledemo.serializable.SerializableTest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1, button2, button3, button4, button5;

    private TextView textView;

    private ArrayList<ParcelableTest> parcelableTestsList = new ArrayList<>();

    private ArrayList<SerializableTest> serializableTestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Intent intent = new Intent(this, SecondActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("keytime", System.currentTimeMillis());
                bundle.putSerializable("keyseri", serializableTestList);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.button2:
                serializableTestList = SerializableparsObject();
                break;
            case R.id.button3:
                Intent intent2 = new Intent(this, SecondActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putLong("keytime", System.currentTimeMillis());
                bundle2.putParcelableArrayList("keyparce", parcelableTestsList);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
            case R.id.button4:
                parcelableTestsList = ParcelableparsObject2();
                break;
            case R.id.button5:
                ParaseLoganSquare();
                break;
        }
    }

    private ArrayList<SerializableTest> SerializableparsObject() {
        ArrayList<SerializableTest> serializableTestList = new ArrayList<>();
        try {
            InputStream read = getAssets().open("scenery.json");
            String info = new String(inputStream2String(read));
            long timeStart = System.currentTimeMillis();
            JSONArray jsonArray = new JSONArray(info);
            for (int i = 0; i < jsonArray.length(); i++) {
                SerializableTest serializableTest = new SerializableTest();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                serializableTest.sceneryId = jsonObject.getString("sceneryId");
                serializableTest.sceneryName = jsonObject.getString("sceneryName");
                serializableTest.address = jsonObject.getString("address");
                serializableTest.addressServiceH5Map = jsonObject.getString("addressServiceH5Map");
                serializableTest.cityId = jsonObject.getString("cityId");
                serializableTest.cityName = jsonObject.getString("cityName");
                serializableTest.grade = jsonObject.getString("grade");
                serializableTest.tcPrice = jsonObject.getString("tcPrice");
                serializableTest.facePrice = jsonObject.getString("facePrice");
                serializableTest.traffic = jsonObject.getString("traffic");
                serializableTest.shopping = jsonObject.getString("shopping");
                serializableTest.ticketPrice = jsonObject.getString("ticketPrice");
                serializableTest.themeID = jsonObject.getString("themeID");
                serializableTest.themeName = jsonObject.getString("themeName");
                serializableTest.longitude = jsonObject.getString("longitude");
                serializableTest.latitude = jsonObject.getString("latitude");
                serializableTest.viewcount = jsonObject.getString("viewcount");
                serializableTest.nBigReasonUrl = jsonObject.getString("nBigReasonUrl");
                serializableTest.productType = jsonObject.getString("productType");
                serializableTest.poiUrl = jsonObject.getString("poiUrl");
                serializableTest.cooperationLogoUrl = jsonObject.getString("cooperationLogoUrl");
                serializableTest.historyUrl = jsonObject.getString("historyUrl");
                serializableTest.homeUrl = jsonObject.getString("homeUrl");
                serializableTest.videoUrl = jsonObject.getString("videoUrl");
                serializableTest.goWithUrl = jsonObject.getString("goWithUrl");
                serializableTestList.add(serializableTest);
            }
            textView.setText("Serializable pars Object: " + (System.currentTimeMillis() - timeStart) + "--->" + serializableTestList.size());

        } catch (IOException e) {

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return serializableTestList;
    }

    private ArrayList<ParcelableTest> ParcelableparsObject2() {
        ArrayList<ParcelableTest> parcelableTestsList = new ArrayList<>();
        try {
            InputStream read = getAssets().open("scenery.json");
            String info = new String(inputStream2String(read));
            long timeStart = System.currentTimeMillis();
            JSONArray jsonArray = new JSONArray(info);
            Parcel parcel = Parcel.obtain();
            for (int i = 0; i < jsonArray.length(); i++) {
                ParcelableTest serializableTest = ParcelableTest.CREATOR.createFromParcel(parcel);
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                serializableTest.sceneryId = jsonObject.getString("sceneryId");
                serializableTest.sceneryName = jsonObject.getString("sceneryName");
                serializableTest.address = jsonObject.getString("address");
                serializableTest.addressServiceH5Map = jsonObject.getString("addressServiceH5Map");
                serializableTest.cityId = jsonObject.getString("cityId");
                serializableTest.cityName = jsonObject.getString("cityName");
                serializableTest.grade = jsonObject.getString("grade");
                serializableTest.tcPrice = jsonObject.getString("tcPrice");
                serializableTest.facePrice = jsonObject.getString("facePrice");
                serializableTest.traffic = jsonObject.getString("traffic");
                serializableTest.shopping = jsonObject.getString("shopping");
                serializableTest.ticketPrice = jsonObject.getString("ticketPrice");
                serializableTest.themeID = jsonObject.getString("themeID");
                serializableTest.themeName = jsonObject.getString("themeName");
                serializableTest.longitude = jsonObject.getString("longitude");
                serializableTest.latitude = jsonObject.getString("latitude");
                serializableTest.viewcount = jsonObject.getString("viewcount");
                serializableTest.nBigReasonUrl = jsonObject.getString("nBigReasonUrl");
                serializableTest.productType = jsonObject.getString("productType");
                serializableTest.poiUrl = jsonObject.getString("poiUrl");
                serializableTest.cooperationLogoUrl = jsonObject.getString("cooperationLogoUrl");
                serializableTest.historyUrl = jsonObject.getString("historyUrl");
                serializableTest.homeUrl = jsonObject.getString("homeUrl");
                serializableTest.videoUrl = jsonObject.getString("videoUrl");
                serializableTest.goWithUrl = jsonObject.getString("goWithUrl");
                parcelableTestsList.add(serializableTest);
            }
            parcel.recycle();
            textView.setText("Parcelable pars Object:" + (System.currentTimeMillis() - timeStart) + "--->" + parcelableTestsList.size());

        } catch (IOException e) {

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parcelableTestsList;
    }

    public ArrayList<LoganSquareTest> ParaseLoganSquare() {
        // Parse from an InputStream
        try {
            InputStream read = getAssets().open("logansquare.json");
            long timeStart = System.currentTimeMillis();
            LoganResponse loganResponse = LoganSquare.parse(read, LoganResponse.class);
            textView.setText("Parcelable pars Object:" + (System.currentTimeMillis() - timeStart) + "--->" + loganResponse.respones.size());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private String inputStream2String(InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}
