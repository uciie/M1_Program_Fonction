package org.example.tds.td2.universite;

import java.util.Map;
import java.util.HashMap;

public class Etudiant {
    private String numero;
    private String prenom;
    private String nom;
    private Annee annee;
    private Map<Matiere, Double> notes;

    public Etudiant(String numero, String prenom, String nom, Annee annee) {
        this.numero = numero;
        this.prenom = prenom;
        this.nom = nom;
        this.annee = annee;
        annee.inscrire(this); // il faut y penser !
        this.notes = new HashMap<>();
    }

    public String numero() {
        return this.numero;
    }

    public String prenom() {
        return this.prenom;
    }

    public String nom() {
        return this.nom;
    }

    public Annee annee() {
        return this.annee;
    }

    public Map<Matiere, Double> notes() {
        return new HashMap<>(this.notes); // éviter fuite données
    }

    public void noter(Matiere matiere, Double note) {
        this.notes.put(matiere, note); // pas fonctionnel !
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numero == null) ? 0 : numero.hashCode());
        result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Etudiant other = (Etudiant) obj;
        if (numero == null) {
            if (other.numero != null)
                return false;
        } else if (!numero.equals(other.numero))
            return false;
        if (prenom == null) {
            if (other.prenom != null)
                return false;
        } else if (!prenom.equals(other.prenom))
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s %s %s", numero, prenom, nom));
        for (UE ue : this.annee().ues()) {
            builder.append(String.format("\n%s", ue));
            for (Matiere matiere : ue.ects().keySet()) {
                String note = (this.notes().get(matiere)) == null ? "DEF" : this.notes().get(matiere).toString();
                builder.append(String.format("\n%s (%d) : %s", matiere, ue.ects().get(matiere), note));
            }
        }
        return builder.toString();
    }
}
