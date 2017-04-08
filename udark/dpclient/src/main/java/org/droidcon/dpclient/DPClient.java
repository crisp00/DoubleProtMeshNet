/*
 * Copyright (c) 2017 Vladimir L. Shabanov <virlof@gmail.com>
 *
 * Licensed under the Underdark License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://underdark.io/LICENSE.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.droidcon.dpclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DPClient extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 1;
    private static final String TAG = DPClient.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpclient);

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.);
        //setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        TextView urlToShare = (TextView)findViewById(R.id.urlToShare);
        Button button = (Button)findViewById(R.id.shareLink);
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                Bundle extras = getIntent().getExtras();
                String value1 = extras.getString(Intent.EXTRA_TEXT);
                urlToShare.setText(value1);
            }
        } else {
            urlToShare.setText("");
            button.setVisibility(View.INVISIBLE);
            /*
            TextView shareYourMusic = (TextView)findViewById(R.id.shareYourMusic);
            shareYourMusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    //intent.setType("image/*");
                    intent.setType("audio/mpeg");

                    startActivityForResult(intent, READ_REQUEST_CODE);
                }
            });
            */
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quit:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Uri: " + uri.toString());

                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            inputStream));
                    // Now we have the input stream
                    /*
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    */
                    reader.close();
                    inputStream.close();
                } catch (Exception ex) {

                }
            }
        }
    }

}
