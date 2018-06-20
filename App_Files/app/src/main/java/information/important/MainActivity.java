package information.important;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.res.AssetManager;
import java.io.InputStream;
import android.content.Context;

public class MainActivity extends AppCompatActivity {
    public static int testing = 0;
    public static boolean next_q = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public void buttonClick(View v)
    {
        Context context = this;
        InputStream is = context.getResources().openRawResource(R.raw.questions);
        String[] lines = convertStreamToString(is).split(System.getProperty("line.separator"));
        TextView tv = (TextView)findViewById(R.id.textView1);
        Button bt = (Button)findViewById(R.id.button);
        int n_lines = lines.length;
        if (next_q) {
            tv.setText(lines[testing]);
            next_q = false;
            bt.setText("الإجابة");
            bt.setTextColor(Color.rgb(0, 255, 100));
            if (testing < n_lines-1) testing++;
        } else {
            boolean end_of_answer = false;
            String foo = "";
            for (; testing < n_lines-1; testing++) {
                if (lines[testing].trim().length() > 0 && end_of_answer) {
                    next_q = true;
                    bt.setText("السؤال التالي");
                    bt.setTextColor(Color.rgb(255, 0, 0));
                    break;
                }
                if (lines[testing].trim().length() == 0 && !end_of_answer) {
                    end_of_answer = true;
                } else {
                    if (foo=="")  foo = lines[testing];
                    else foo = foo+"\n"+lines[testing];
                }
            }
            tv.setText(foo);
        }
        if (testing == n_lines-1) {
            testing = 0;
            next_q = true;
            bt.setText("إعادة الأسئلة");
            bt.setTextColor(Color.rgb(0, 125, 255));
        }
    }
}

