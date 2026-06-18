package com.gobet.hotels.controller;

import com.gobet.hotels.dto.metadata.CityRequest;
import com.gobet.hotels.dto.metadata.CityResponse;
import com.gobet.hotels.service.MetadataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "City Controller", description = "City metadata APIs")
@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final MetadataService metadataService;

    @GetMapping
    public List<CityResponse> getAll(@RequestParam(required = false) Long zoneId) {
        return metadataService.getCities(zoneId);
    }

    @GetMapping("/{id}")
    public CityResponse getById(@PathVariable Long id) {
        return metadataService.getCityById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityResponse create(@Valid @RequestBody CityRequest request) {
        return metadataService.createCity(request);
    }

    @PutMapping("/{id}")
    public CityResponse update(@PathVariable Long id, @Valid @RequestBody CityRequest request) {
        return metadataService.updateCity(id, request);
    }

    @PatchMapping("/{id}")
    public CityResponse patch(@PathVariable Long id, @Valid @RequestBody CityRequest request) {
        return metadataService.updateCity(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        metadataService.deleteCity(id);
    }
}
