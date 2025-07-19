package org.iset.emplois.dto;

import lombok.AllArgsConstructor;
import lombok.Data; 

@Data
@AllArgsConstructor 
public class MenuSubMenuDTO {

    private Long subMenuId;
    private String menuTitle;
    private String subMenuTitle;

    private boolean canAdd=true;
    private boolean canUpdate=true;
    private boolean canSearch=true;
    private boolean canDelete=true;
    private boolean canPrint=true;


}