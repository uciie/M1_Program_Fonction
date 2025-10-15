package org.example.tds.td2.universite;

public class Matiere {
    private String nom;

    public Matiere(String nom) {
        this.nom = nom;
    }

    public String nom() {
        return this.nom;
    }

    @Override
    public String toString() {
        return this.nom;
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
        Matiere other = (Matiere) obj;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        return true;
    }
}
