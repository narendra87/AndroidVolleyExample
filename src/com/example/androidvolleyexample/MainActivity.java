package com.example.androidvolleyexample;



import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	//Method
    private final static int GET = 1;
    private final static int POST = 2;
    private final static int DELETE = 3;
    private final static int PUT = 4;
    //Controls
	
   	private ProgressDialog pDialog;
   	private EditText setUrl;
   	private TextView txtJson;
  //URL
    private String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Get controls of layout
				TextView txtConnect = (TextView)findViewById(R.id.txtConnect);
		        setUrl = (EditText)findViewById(R.id.editURL);
				Button btnGet = (Button)findViewById(R.id.btnGet);
				Button btnPost = (Button)findViewById(R.id.btnPost);
				 txtJson = (TextView)findViewById(R.id.txtjson);
				btnGet.setOnClickListener(this);
				btnPost.setOnClickListener(this);
				if(isConnected()){
					txtConnect.setText("Connected");
				}else{
					txtConnect.setText("Not connected");
				}	
				//Progress dialog
			    pDialog = new ProgressDialog(this);
				pDialog.setMessage("Loading...");
		
		
		
		
		
	}
	//onClick Buttons
		@Override
		public void onClick(View v) {
			// Tag used to cancel the request
			String tag_json_obj = "json_obj_req";
			pDialog.show();     
			//Get url
			url = setUrl.getText().toString();
			switch (v.getId()) {
			case R.id.btnGet:
		        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
		                url, null,
		                new Response.Listener<JSONObject>() {
		 
		                    @Override
		                    public void onResponse(JSONObject response) {
		                        Log.d("GET", response.toString());
		                        pDialog.hide();
		                        txtJson.setText(response.toString());
		                        
		                    }
		                }, new Response.ErrorListener() {
		 
		                    @Override
		                    public void onErrorResponse(VolleyError error) {
		                        VolleyLog.d("GET", "Error: " + error.getMessage());
		                        // hide the progress dialog
		                        pDialog.hide();
		                    }
		                });
		     // Adding request to request queue
		 AppController.getInstance().addToRequestQueue(jsonObjReq,tag_json_obj);
		
	
				break;
			}
		
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	// check network connection
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                return true;
            else
                return false;  
    }
}
