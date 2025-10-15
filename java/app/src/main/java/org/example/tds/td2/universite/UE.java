package org.example.tds.td2.universite;

import java.util.Map;
import java.util.HashMap;

public class UE {
    private String nom;
    private Map<Matiere, Integer> ects;

    public UE(String nom, Map<Matiere, Integer> ects) {
        this.nom = nom;
        this.ects = new HashMap<>(ects); // éviter fuite données
    }

    public String nom() {
        return this.nom;
    }

    public Map<Matiere, Integer> ects() {
        return new HashMap<>(this.ects); // éviter fuite données
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        UE other = (UE) obj;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return this.nom();
    }
}
