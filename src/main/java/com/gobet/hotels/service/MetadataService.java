package com.gobet.hotels.service;

import com.gobet.hotels.dto.metadata.*;
import com.gobet.hotels.entity.City;
import com.gobet.hotels.entity.Region;
import com.gobet.hotels.entity.Zone;
import com.gobet.hotels.exception.NotFoundException;
import com.gobet.hotels.repository.CityRepository;
import com.gobet.hotels.repository.RegionRepository;
import com.gobet.hotels.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetadataService {

    private final RegionRepository regionRepository;
    private final ZoneRepository zoneRepository;
    private final CityRepository cityRepository;

    // ─────────────────────────────────────────
    // REGION CRUD
    // ─────────────────────────────────────────

    public RegionResponse createRegion(RegionRequest request) {
        Region region = new Region();
        region.setName(request.getName());
        return mapRegion(regionRepository.save(region));
    }

    public RegionResponse updateRegion(Long id, RegionRequest request) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Region not found with id: " + id));
        region.setName(request.getName());
        return mapRegion(regionRepository.save(region));
    }

    public List<RegionResponse> getAllRegions() {
        return regionRepository.findByDeletedFalse()
                .stream()
                .map(this::mapRegion)
                .collect(Collectors.toList());
    }

    public RegionResponse getRegionById(Long id) {
        return mapRegion(regionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Region not found with id: " + id)));
    }

    public void deleteRegion(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Region not found with id: " + id));
        region.setDeleted(true);
        regionRepository.save(region);
    }

    // ─────────────────────────────────────────
    // ZONE CRUD
    // ─────────────────────────────────────────

    public ZoneResponse createZone(ZoneRequest request) {
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new NotFoundException("Region not found with id: " + request.getRegionId()));
        Zone zone = new Zone();
        zone.setName(request.getName());
        zone.setRegion(region);
        return mapZone(zoneRepository.save(zone));
    }

    public ZoneResponse updateZone(Long id, ZoneRequest request) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Zone not found with id: " + id));
        zone.setName(request.getName());
        if (request.getRegionId() != null) {
            Region region = regionRepository.findById(request.getRegionId())
                    .orElseThrow(() -> new NotFoundException("Region not found with id: " + request.getRegionId()));
            zone.setRegion(region);
        }
        return mapZone(zoneRepository.save(zone));
    }

    public List<ZoneResponse> getZones(Long regionId) {
        List<Zone> zones = (regionId != null)
                ? zoneRepository.findByRegionIdAndDeletedFalse(regionId)
                : zoneRepository.findByDeletedFalse();
        return zones.stream().map(this::mapZone).collect(Collectors.toList());
    }

    public ZoneResponse getZoneById(Long id) {
        return mapZone(zoneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Zone not found with id: " + id)));
    }

    public void deleteZone(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Zone not found with id: " + id));
        zone.setDeleted(true);
        zoneRepository.save(zone);
    }

    // ─────────────────────────────────────────
    // CITY CRUD
    // ─────────────────────────────────────────

    public CityResponse createCity(CityRequest request) {
        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(() -> new NotFoundException("Zone not found with id: " + request.getZoneId()));
        City city = new City();
        city.setName(request.getName());
        city.setZone(zone);
        return mapCity(cityRepository.save(city));
    }

    public CityResponse updateCity(Long id, CityRequest request) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("City not found with id: " + id));
        city.setName(request.getName());
        if (request.getZoneId() != null) {
            Zone zone = zoneRepository.findById(request.getZoneId())
                    .orElseThrow(() -> new NotFoundException("Zone not found with id: " + request.getZoneId()));
            city.setZone(zone);
        }
        return mapCity(cityRepository.save(city));
    }

    public List<CityResponse> getCities(Long zoneId) {
        List<City> cities = (zoneId != null)
                ? cityRepository.findByZoneIdAndDeletedFalse(zoneId)
                : cityRepository.findByDeletedFalse();
        return cities.stream().map(this::mapCity).collect(Collectors.toList());
    }

    public CityResponse getCityById(Long id) {
        return mapCity(cityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("City not found with id: " + id)));
    }

    public void deleteCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("City not found with id: " + id));
        city.setDeleted(true);
        cityRepository.save(city);
    }

    // ─────────────────────────────────────────
    // MAPPERS
    // ─────────────────────────────────────────

    private RegionResponse mapRegion(Region region) {
        RegionResponse res = new RegionResponse();
        res.setId(region.getId());
        res.setName(region.getName());
        return res;
    }

    private ZoneResponse mapZone(Zone zone) {
        ZoneResponse res = new ZoneResponse();
        res.setId(zone.getId());
        res.setName(zone.getName());
        res.setRegionId(zone.getRegion().getId());
        res.setRegionName(zone.getRegion().getName());
        return res;
    }

    private CityResponse mapCity(City city) {
        CityResponse res = new CityResponse();
        res.setId(city.getId());
        res.setName(city.getName());
        res.setZoneId(city.getZone().getId());
        res.setZoneName(city.getZone().getName());
        return res;
    }
}
