package ru.rtln.productservice.service;

import ru.rtln.productservice.dto.UnitDto;

import java.util.List;

public interface UnitService {

    UnitDto getByName(String unit);

    UnitDto create(UnitDto unitDto);

    List<UnitDto> getAll();
}
