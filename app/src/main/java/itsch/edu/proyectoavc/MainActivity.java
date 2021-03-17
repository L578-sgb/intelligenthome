package itsch.edu.proyectoavc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
EditText etUser, etContra;
Button btEntrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUser = findViewById(R.id.etUser);
        etContra = findViewById(R.id.etContra);
        btEntrar = findViewById(R.id.btEntrar);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    private void Login() {
        String url = Uri.parse(Config.URL + "login.php")
                .buildUpon()
                .appendQueryParameter("user", etUser.getText().toString())
                .appendQueryParameter("pass", etContra.getText().toString())
                .build().toString();
        JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        respuesta(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error de Red", Toast.LENGTH_SHORT).show();

            }
        }
        );
        RequestQueue cola = Volley.newRequestQueue(this);
        cola.add(peticion);
    }

    private void respuesta(JSONObject response) {
        try {
            if (response.getString("login").compareTo("s") == 0) {
                startActivity(new Intent(this,MainActivity2.class));
            } else {
                Toast.makeText(this, "Error de usuario/contraseña", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){}
    }
}