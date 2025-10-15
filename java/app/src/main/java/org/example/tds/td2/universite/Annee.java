package org.example.tds.td2.universite;

import java.util.Set;
import java.util.HashSet;

public class Annee {
    private Set<UE> ues;
    private Set<Etudiant> etudiants;

    public Annee(Set<UE> ues) {
        this.ues = new HashSet<>(ues); // éviter fuite données
        this.etudiants = new HashSet<>();
    }

    public Set<UE> ues() {
        return new HashSet<>(ues); // éviter fuite données
    }

    public Set<Etudiant> etudiants() {
        return new HashSet<>(etudiants); // éviter fuite données
    }

    public void inscrire(Etudiant e) {
        etudiants.add(e); // pas fonctionnel !
    }
}
