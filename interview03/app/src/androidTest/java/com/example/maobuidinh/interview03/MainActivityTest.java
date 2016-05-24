package com.example.maobuidinh.interview03;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mao.buidinh on 5/23/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
        public MainActivityTest()
        {
            super(MainActivity.class);
        }
        public void testActivityExists() {
            MainActivity activity = getActivity();
            assertNotNull(activity);
        }

        public void testButton()
        {
            MainActivity activity = getActivity();
            final EditText nameEditText =
                    (EditText) activity.findViewById(R.id.edit_first_msg);

            // Send string input value
            getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {
                    nameEditText.requestFocus();
                }
            });

            getInstrumentation().waitForIdleSync();
            getInstrumentation().sendStringSync("Jake");
            getInstrumentation().waitForIdleSync();

            // Tap "Greet" button
            // ----------------------

            Button greetButton =
                    (Button) activity.findViewById(R.id.button_first);

            TouchUtils.clickView(this, greetButton);

            // Verify greet message
            // ----------------------

            TextView greetMessage = (TextView) activity.findViewById(R.id.txtTitle);
            String actualText = greetMessage.getText().toString();
            assertEquals("Hello, Jake!", actualText);
        }
}
