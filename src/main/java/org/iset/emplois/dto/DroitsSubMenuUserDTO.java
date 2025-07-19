package org.iset.emplois.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroitsSubMenuUserDTO {
    private Long subMenuId;
    private boolean canAdd;
    private boolean canUpdate;
    private boolean canSearch;
    private boolean canDelete;
    private boolean canPrint;

    // Getters et Setters
}

