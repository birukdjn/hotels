package com.gobet.hotels.controller;

import com.gobet.hotels.dto.metadata.ZoneRequest;
import com.gobet.hotels.dto.metadata.ZoneResponse;
import com.gobet.hotels.service.MetadataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Zone Controller", description = "Zone metadata APIs")
@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final MetadataService metadataService;

    @GetMapping
    public List<ZoneResponse> getAll(@RequestParam(required = false) Long regionId) {
        return metadataService.getZones(regionId);
    }

    @GetMapping("/{id}")
    public ZoneResponse getById(@PathVariable Long id) {
        return metadataService.getZoneById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneResponse create(@Valid @RequestBody ZoneRequest request) {
        return metadataService.createZone(request);
    }

    @PutMapping("/{id}")
    public ZoneResponse update(@PathVariable Long id, @Valid @RequestBody ZoneRequest request) {
        return metadataService.updateZone(id, request);
    }

    @PatchMapping("/{id}")
    public ZoneResponse patch(@PathVariable Long id, @Valid @RequestBody ZoneRequest request) {
        return metadataService.updateZone(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        metadataService.deleteZone(id);
    }
}
