package com.nexar.controller.car;

import com.nexar.controller.car.dto.CarDto;
import com.nexar.service.car.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@Slf4j
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<Page<CarDto>> getAll(@PageableDefault(size = 20) Pageable pageable) {
        log.info("Get all cars request, size: {}, page: {}", pageable.getPageSize(), pageable.getPageNumber());
        return ResponseEntity.ok(carService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> get(@PathVariable UUID id) {
        log.info("Get car request, id: {}", id);
        return ResponseEntity.ok(carService.find(id));
    }
}
