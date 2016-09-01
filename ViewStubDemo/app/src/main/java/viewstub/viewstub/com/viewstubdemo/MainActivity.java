package viewstub.viewstub.com.viewstubdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button button;

    // view stub 的layout
    private ImageView viewstub_one;

    private Button one,two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        final ViewStub viewStub = (ViewStub) findViewById(R.id.viewstub);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Process: viewstub.viewstub.com.viewstubdemo, PID: 21427
                java.lang.IllegalStateException: ViewStub must have a non-null ViewGroup viewParent
                at android.view.ViewStub.inflate(ViewStub.java:287)
                at viewstub.viewstub.com.viewstubdemo.MainActivity$1.onClick(MainActivity.java:31)
                at android.view.View.performClick(View.java:5204)
                at android.view.View$PerformClick.run(View.java:21155)
                at android.os.Handler.handleCallback(Handler.java:739)
                at android.os.Handler.dispatchMessage(Handler.java:95)
                at android.os.Looper.loop(Looper.java:148)
                at android.app.ActivityThread.main(ActivityThread.java:5422)
                at java.lang.reflect.Method.invoke(Native Method)
                at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:726)
                at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:616)*/

                // 必须判断getparent是否为null
                if(viewStub.getParent() != null){
                    View inflateView = viewStub.inflate();
                    viewstub_one = (ImageView) inflateView.findViewById(R.id.viewstub_one);
                }
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewstub_one != null) {
                    viewstub_one.setVisibility(View.GONE);
                }
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewstub_one != null) {
                    viewstub_one.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
