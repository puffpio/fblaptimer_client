package com.pio.fblaptimer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;
import com.facebook.android.*;
import com.facebook.android.Facebook.*;

public class MainActivity extends Activity {

	Facebook fb;
	private SharedPreferences prefs;
	private static final String TAG = "MainActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        fb = new Facebook(getString(R.string.facebook_app_id));
        
        // get existing access token
        prefs = getPreferences(MODE_PRIVATE);
        String access_token = prefs.getString("access_token", null);
        long expires = prefs.getLong("access_expires", 0);
        if (access_token != null) {
        	fb.setAccessToken(access_token);
        }
        if (expires != 0) {
        	fb.setAccessExpires(expires);
        }
        
        if (!fb.isSessionValid()) {
	        fb.authorize(this, new String[] { "publish_actions" }, new DialogListener() {
	            @Override
	            public void onComplete(Bundle values) {}
	
	            @Override
	            public void onFacebookError(FacebookError error) {}
	
	            @Override
	            public void onError(DialogError e) {}
	
	            @Override
	            public void onCancel() {}
	        });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fb.authorizeCallback(requestCode, resultCode, data);
    }

    
    public void startRecord(View view) {
    	Log.i(TAG, "started");
    }
    
    public void stopRecord(View view) {
    	Log.i(TAG, "stopped");
    }
}
