package org.example.healthcare.mapper;

import org.example.healthcare.dto.RendezVousDTO;
import org.example.healthcare.model.RendezVous;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RendezVousMapper {
    @Mapping(target = "idPatient", source = "patient.id")
    @Mapping(target = "idMedecin", source = "medecin.id")
    RendezVousDTO
    toDTO(RendezVous rendezVous);

    RendezVous toEntity(RendezVousDTO rendezVousDTO);
    List<RendezVousDTO>  toDTO(List<RendezVous> rendezVous);
}
