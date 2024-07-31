package com.tli.patient.api.controllers;

import com.tli.patient.api.models.request.PatientRequest;
import com.tli.patient.api.models.response.PatientResponse;
import com.tli.patient.domain.entities.PatientEntity;
import com.tli.patient.infraestructure.services.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping(path="/patients")
public class PatientController {
    public PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> create(@Validated @RequestBody PatientRequest rq, UriComponentsBuilder ucb) {
        PatientResponse patientEntity = patientService.create(rq);
        URI location = ucb.path("/patients/{}").buildAndExpand(patientEntity.getId()).toUri();
        return ResponseEntity.created(location).body(patientEntity);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<PatientResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(patientService.get(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PatientResponse> update(@PathVariable("id") Long id, @Validated @RequestBody PatientRequest rq) {
        return ResponseEntity.ok(patientService.update(rq, id));
    }
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

