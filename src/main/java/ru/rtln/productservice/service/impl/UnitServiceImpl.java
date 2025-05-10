package ru.rtln.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtln.productservice.aop.annotation.Loggable;
import ru.rtln.productservice.dto.UnitDto;
import ru.rtln.productservice.entity.Unit;
import ru.rtln.productservice.mapper.UnitMapper;
import ru.rtln.productservice.repository.UnitRepository;
import ru.rtln.productservice.service.UnitService;

import java.util.List;
import java.util.Optional;

import static ru.rtln.productservice.exception.UnitException.Code.UNIT_ALREADY_EXISTS;
import static ru.rtln.productservice.exception.UnitException.Code.UNIT_NOT_FOUND;

/**
 * Реализация {@link UnitService}
 */
@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    @Override
    @Transactional(readOnly = true)
    @Loggable
    public UnitDto getByName(String unit) {
        return unitRepository.findByNameIgnoreCase(unit)
                .map(unitMapper::toDto)
                .orElseThrow(() -> UNIT_NOT_FOUND.getWith(String.format("Единица измерения c названием: [%s] не найдена", unit))
                        .setMessageParam(String.valueOf(unit)));
    }

    @Override
    @Transactional
    @Loggable
    public UnitDto create(UnitDto unitDto) {
        Optional<Unit> mayBeUnitDto = unitRepository.findByNameIgnoreCase(unitDto.getName());
        if (mayBeUnitDto.isPresent()) {
            throw UNIT_ALREADY_EXISTS.getWith(String.format("Единица измерения c названием: [%s] уже существует", unitDto.getName()))
                    .setMessageParam(String.valueOf(unitDto.getName()));
        }
        Unit unit = unitMapper.toEntity(unitDto);
        unitRepository.save(unit);
        return unitMapper.toDto(unit);
    }

    @Override
    @Transactional(readOnly = true)
    @Loggable
    public List<UnitDto> getAll() {
        return unitRepository.findAll().stream()
                .map(unitMapper::toDto)
                .toList();
    }
}
