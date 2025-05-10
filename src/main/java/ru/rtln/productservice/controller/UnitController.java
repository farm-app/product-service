package ru.rtln.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.rtln.productservice.annotation.HasProductManagementAuthority;
import ru.rtln.productservice.dto.UnitDto;
import ru.rtln.productservice.service.UnitService;

import java.util.List;

@RestController
@RequestMapping("/units")
@RequiredArgsConstructor
@HasProductManagementAuthority
@Tag(name = "Units", description = "Units API")
public class UnitController {

    private final UnitService unitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление единицы измерения")
    public UnitDto addUnit(@Validated @RequestBody UnitDto unitDto) {
        return unitService.create(unitDto);
    }

    @GetMapping
    @Operation(summary = "Получение всех единиц измерения")
    public List<UnitDto> getAllUnits() {
        return unitService.getAll();
    }
}