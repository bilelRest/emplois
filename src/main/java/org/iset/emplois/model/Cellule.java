package org.iset.emplois.model;
import java.util.Objects;

public class Cellule {
    private int id;
    private int numeroLigne;
    private int taille;
    private int offset;
    private int position;

    public Cellule(int id, int taille, int position) {
        this.id = id;
        this.taille = taille;
        this.position = position; // position est figé
        this.offset = position;
        this.numeroLigne=0;
        // Par défaut, pas de décalage
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroLigne() {
        return numeroLigne;
    }

    public void setNumeroLigne(int numeroLigne) {
        this.numeroLigne = numeroLigne;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Cellule{id=" + id + ", taille=" + taille + ", offset=" + offset + ", position=" + position + ", numéro ligne=" + numeroLigne + "}";
    }
        // Equals et hashCode pour la clé composite
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cellule key = (Cellule) o;
            return numeroLigne == key.numeroLigne && offset == key.offset;
        }

        @Override
        public int hashCode() {
            return Objects.hash(numeroLigne, offset);
        }
}
