package org.iset.emplois.model;

public enum TypeParcours {
LICENCE("LICENCE")
,MASTER_PROFESSIONNEL("MASTER PROFESSIONNEL");
private final String description;
TypeParcours(String description) {
    this.description = description;
}

public String getDescription() {
    return description;
}

}
