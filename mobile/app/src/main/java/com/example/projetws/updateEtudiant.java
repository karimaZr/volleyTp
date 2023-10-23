package com.example.projetws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class updateEtudiant extends AppCompatActivity {
    private static final String TAG = "updateEtudiant";
    RequestQueue requestQueue;
    private EditText nom;
    private EditText prenom;
    private Spinner ville;
    private RadioButton m;
    private RadioButton f;
    private Button update;
    private String updateUrl = "http://192.168.1.119/PhpP/ws/updateEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_etudiant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("UpdateEtudiant");
        // Récupérer les éléments d'interface utilisateur pour la mise à jour
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        ville = findViewById(R.id.ville);
        m = findViewById(R.id.m);
        f = findViewById(R.id.f);
        update = findViewById(R.id.update);

        // Récupérer les données transmises
        Intent intent = getIntent();
        if (intent != null) {
            int etudiantId = intent.getIntExtra("id", 0); // 0 est une valeur par défaut si l'ID n'est pas trouvé
            String nomValue = intent.getStringExtra("nom");
            String prenomValue = intent.getStringExtra("prenom");
            String villeValue = intent.getStringExtra("ville");
            String sexeValue = intent.getStringExtra("sexe");

            // Mettre à jour les champs avec les valeurs récupérées
            nom.setText(nomValue);
            prenom.setText(prenomValue);
            // Mettre à jour le Spinner
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.villes, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ville.setAdapter(adapter);
            if (villeValue != null) {
                int spinnerPosition = adapter.getPosition(villeValue);
                ville.setSelection(spinnerPosition);
            }

            // Mettre à jour les RadioButtons
            if (sexeValue != null) {
                if (sexeValue.equals("homme")) {
                    m.setChecked(true);
                } else if (sexeValue.equals("femme")) {
                    f.setChecked(true);
                }
            }
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST,
                            updateUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Gérer la réponse en cas de succès
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Gérer les erreurs de requête
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            String sexe = m.isChecked() ? "homme" : "femme";
                            HashMap<String, String> params = new HashMap<>();
                            params.put("id", String.valueOf(etudiantId)); // Envoyer l'ID de l'étudiant à mettre à jour
                            params.put("nom", nom.getText().toString());
                            params.put("prenom", prenom.getText().toString());
                            params.put("ville", ville.getSelectedItem().toString());
                            params.put("sexe", sexe);
                            return params;
                        }
                    };
                    requestQueue.add(request);
                }
            });
        }
    }
}
