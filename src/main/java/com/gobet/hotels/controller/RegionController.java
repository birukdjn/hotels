package com.gobet.hotels.controller;

import com.gobet.hotels.dto.metadata.RegionRequest;
import com.gobet.hotels.dto.metadata.RegionResponse;
import com.gobet.hotels.service.MetadataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Region Controller", description = "Region metadata APIs")
@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final MetadataService metadataService;

    @GetMapping
    public List<RegionResponse> getAll() {
        return metadataService.getAllRegions();
    }

    @GetMapping("/{id}")
    public RegionResponse getById(@PathVariable Long id) {
        return metadataService.getRegionById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegionResponse create(@Valid @RequestBody RegionRequest request) {
        return metadataService.createRegion(request);
    }

    @PutMapping("/{id}")
    public RegionResponse update(@PathVariable Long id, @Valid @RequestBody RegionRequest request) {
        return metadataService.updateRegion(id, request);
    }

    @PatchMapping("/{id}")
    public RegionResponse patch(@PathVariable Long id, @Valid @RequestBody RegionRequest request) {
        return metadataService.updateRegion(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        metadataService.deleteRegion(id);
    }
}
