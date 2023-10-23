package com.example.projetws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projetws.adapter.EtudiantAdapter;
import com.example.projetws.beans.Etudiant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {
    private List<Etudiant> studentList;
    private RecyclerView recyclerView;
    private EtudiantAdapter adapter;
    private static final String TAG = "StudentListActivity";
    private String insertUrl = "http://192.168.1.119/PhpP/ws/loadEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ListEtudiant");
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentList = new ArrayList<>();
        adapter = new EtudiantAdapter(studentList, this);
        recyclerView.setAdapter(adapter);

        fetchStudentData();
    }

    private void fetchStudentData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, insertUrl,
                response -> {
                    Log.d(TAG, "Réponse JSON brute : " + response); // Vérifiez la réponse JSON brute
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String nom = jsonObject.getString("nom");
                            String prenom = jsonObject.getString("prenom");
                            String ville = jsonObject.getString("ville");
                            String sexe = jsonObject.getString("sexe");
                            Etudiant etudiant = new Etudiant(id, nom, prenom, ville, sexe);
                            studentList.add(etudiant);
                        }
                        adapter.notifyDataSetChanged(); // Mettez à jour votre RecyclerView avec la liste d'étudiants
                    } catch (JSONException e) {
                        Log.e(TAG, "Erreur de conversion de la réponse en tableau JSON : " + e.getMessage());
                    }
                },
                error -> Log.e(TAG, "Erreur de chargement des étudiants : " + error.toString())
        );
        requestQueue.add(stringRequest);
    }

}
