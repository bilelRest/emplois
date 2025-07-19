package org.iset.emplois.model;

public enum TypeUE {
FONDAMENTALE("FONDAMENTALE")
,TRANSVERSALE("TRANSVERSALE")
,OPTIONELLE("OPTIONELLE");

private final String description;
TypeUE(String description) {
    this.description = description;
}

public String getDescription() {
    return description;
}

}
