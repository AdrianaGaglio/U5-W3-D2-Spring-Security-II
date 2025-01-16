package epicode.it.businesstrips.controllers;

import epicode.it.businesstrips.entities.trip.*;
import epicode.it.businesstrips.entities.trip.dto.TripCreateRequest;
import epicode.it.businesstrips.entities.trip.dto.TripUpdateRequest;
import epicode.it.businesstrips.entities.trip.dto.TripWithReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripSvc tripSvc;

    @GetMapping
    public ResponseEntity<List<TripWithReservationResponse>> getAll() {
        return ResponseEntity.ok(tripSvc.getAll());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Trip>> getAllPageable(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(tripSvc.getAllPageable(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripWithReservationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tripSvc.getByIdResponse(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(tripSvc.delete(id), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<TripWithReservationResponse> create(@RequestBody TripCreateRequest request) {
        return new ResponseEntity<>(tripSvc.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripWithReservationResponse> update(@PathVariable Long id, @RequestBody TripUpdateRequest request) {
        return new ResponseEntity<>(tripSvc.update(id, request), HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<TripWithReservationResponse> update(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(tripSvc.updateStatus(id, status));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TripWithReservationResponse>> findByDestination(@RequestParam String destination) {
        return ResponseEntity.ok(tripSvc.findByDestination(destination));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count() {
        return ResponseEntity.ok(tripSvc.count());
    }

}
