package br.com.ctw.apientregas.mapper;

import br.com.ctw.apientregas.dto.request.CreateMotoristaDto;
import br.com.ctw.apientregas.dto.response.ResponseMotoristaDto;
import br.com.ctw.apientregas.entities.MotoristaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MotoristaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entregas", ignore = true)
    MotoristaEntity toEntity(CreateMotoristaDto dto);

    ResponseMotoristaDto toResponse(MotoristaEntity entity);

    List<ResponseMotoristaDto> toListResponse(List<MotoristaEntity> entities);
}