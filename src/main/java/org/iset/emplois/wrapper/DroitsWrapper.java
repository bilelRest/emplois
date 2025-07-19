package org.iset.emplois.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.iset.emplois.dto.DroitsSubMenuUserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroitsWrapper {
    private List<DroitsSubMenuUserDTO> droits = new ArrayList<>();
}

