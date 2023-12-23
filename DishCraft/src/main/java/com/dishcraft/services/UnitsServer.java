package com.dishcraft.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dishcraft.model.MeasureUnit;
import com.dishcraft.repositories.MeasureUnitRepository;

@Service
public class UnitsServer {

	private final MeasureUnitRepository measureUnitRepository;

	private UnitsServer(MeasureUnitRepository measureUnitRepository) {
		super();
		this.measureUnitRepository = measureUnitRepository;
	}
	
	public List<MeasureUnit> getMeasureUnits() {
		return measureUnitRepository.findAll();
	}
	
	public MeasureUnit getMeasureUnitById(Long id) {
		return measureUnitRepository.findById(id).orElse(null);
	}
}
