package com.example.projetws.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetws.R;
import com.example.projetws.beans.Etudiant;
import com.example.projetws.updateEtudiant;

import java.util.List;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder> {
    private List<Etudiant> etudiantList;
    private Context context;

    public EtudiantAdapter(List<Etudiant> etudiantList, Context context) {
        this.etudiantList = etudiantList;
        this.context = context;
    }

    @NonNull
    @Override
    public EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.etudiant_item, parent, false);
        return new EtudiantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantViewHolder holder, int position) {
        Etudiant etudiant = etudiantList.get(position);
        holder.bind(etudiant);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation de suppression");
                builder.setMessage("Voulez-vous vraiment supprimer cet étudiant ?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code pour supprimer l'étudiant de la liste
                        etudiantList.remove(position);
                        notifyDataSetChanged();
                        // Autre code pour supprimer l'étudiant de la base de données ou de l'API
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        // Ajouter un OnClickListener pour le bouton de modification
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Etudiant etudiant = etudiantList.get(position);
                Intent intent = new Intent(v.getContext(), updateEtudiant.class);
                intent.putExtra("id", etudiant.getId()); // Assurez-vous que la classe Etudiant possède une méthode getId() pour récupérer l'ID de l'étudiant
                intent.putExtra("nom", etudiant.getNom());
                intent.putExtra("prenom", etudiant.getPrenom());
                intent.putExtra("ville", etudiant.getVille());
                intent.putExtra("sexe", etudiant.getSexe());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return etudiantList.size();
    }

    static class EtudiantViewHolder extends RecyclerView.ViewHolder {
        private TextView nomTextView;
        private TextView prenomTextView;
        private TextView villeTextView;
        private TextView sexeTextView;
        private Button deleteButton;
        private Button editButton;

        public EtudiantViewHolder(@NonNull View itemView) {
            super(itemView);
            nomTextView = itemView.findViewById(R.id.nom1);
            prenomTextView = itemView.findViewById(R.id.prenom2);
            villeTextView = itemView.findViewById(R.id.ville1);
            sexeTextView = itemView.findViewById(R.id.sexe1);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }

        public void bind(Etudiant etudiant) {
            nomTextView.setText("Nom: " + etudiant.getNom());
            prenomTextView.setText("Prenom: " + etudiant.getPrenom());
            villeTextView.setText("Ville: " + etudiant.getVille());
            sexeTextView.setText("Sexe: " + etudiant.getSexe());
        }
    }
}
