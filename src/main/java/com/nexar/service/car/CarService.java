package com.nexar.service.car;

import com.nexar.controller.car.dto.CarDto;
import com.nexar.dao.model.car.Car;
import com.nexar.dao.repository.car.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Page<CarDto> findAll(Pageable pageable) {
        return carRepository.findAll(pageable).map(CarDto::map);
    }

    public CarDto find(UUID id) {
        return CarDto.map(get(id));
    }


    private Car get(UUID id) {
        return carRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car with id: " + id + " not found"));
    }
}
