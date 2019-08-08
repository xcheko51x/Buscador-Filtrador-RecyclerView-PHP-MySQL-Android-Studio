package com.xcheko51x.buscadorrecyclerviewmysql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etBuscador;
    RecyclerView rvLista;
    AdaptadorUsuarios adaptador;
    List<Usuario> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBuscador = findViewById(R.id.etBuscador);
        etBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });

        rvLista = findViewById(R.id.rvLista);
        rvLista.setLayoutManager(new GridLayoutManager(this, 1));

        listaUsuarios = new ArrayList<>();

        obtenerUsuarios();

        adaptador = new AdaptadorUsuarios(MainActivity.this, listaUsuarios);
        rvLista.setAdapter(adaptador);
    }

    public void obtenerUsuarios() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.URL_USUARIOS),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Usuarios");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                listaUsuarios.add(
                                        new Usuario(
                                                jsonObject1.getString("idUsuario"),
                                                jsonObject1.getString("nombre"),
                                                jsonObject1.getString("telefono"),
                                                jsonObject1.getString("email"),
                                                jsonObject1.getString("usuario"),
                                                jsonObject1.getString("contrasena")

                                        )
                                );
                            }

                            adaptador = new AdaptadorUsuarios(MainActivity.this, listaUsuarios);
                            rvLista.setAdapter(adaptador);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );

        requestQueue.add(stringRequest);
    }

    public void filtrar(String texto) {
        ArrayList<Usuario> filtrarLista = new ArrayList<>();

        for(Usuario usuario : listaUsuarios) {
            if(usuario.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(usuario);
            }
        }

        adaptador.filtrar(filtrarLista);
    }
}
