package com.example.pruebadesarrollador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String URL_IMAGE = "https://static.abc.es/media/cultura/2016/11/17/picasso-kte--620x349@abc.jpg";
    private ImageView IMG;
    private Button btnConsultar;
    private RequestQueue queue;
    private String url = "http://beta.yappapp.mx/test/json/apps";
    public static final int TIMEOUT = 40000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setUpView();

        loadImage();

        btnConsultar =  findViewById(R.id.btnConsultar);
        btnConsultar.setOnClickListener(this);

    }

    private void setUpView(){
        IMG = findViewById(R.id.imgPicasso);
    }

    private void loadImage(){
        Picasso.get().load(URL_IMAGE).into(IMG);
    }

    private void loadVolley(){
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = response.getJSONObject("data");
                    String d1 = (String) obj.getString("dato1");
                    String d2= (String) obj.getString("dato2");
                    String d3= (String) obj.getString("dato3");
                    String msg= (String) response.get("msg").toString();
                    String code= (String) response.get("code").toString();
                    Toast.makeText(SecondActivity.this, "Dato1: " + d1+ " Dato2: "+d2+" Dato 3: "+d3+ " MSG: "+msg+ " Code: "+code, Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(SecondActivity.this, "" + e, Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SecondActivity.this, " " + error, Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConsultar:
                loadVolley();
                break;
        }
    }
}
