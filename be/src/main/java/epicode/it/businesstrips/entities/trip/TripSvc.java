package epicode.it.businesstrips.entities.trip;

import epicode.it.businesstrips.entities.employee.EmployeeSvc;
import epicode.it.businesstrips.entities.reservation.Reservation;
import epicode.it.businesstrips.entities.reservation.ReservationNoTripResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class TripSvc {
    private final TripRepo tripRepo;
    private final EmployeeSvc employeeSvc;

    public List<TripWithReservationResponse> getAll() {
        List<Trip> trips = tripRepo.findAll();
        List<TripWithReservationResponse> response = new ArrayList<>();
        for (Trip t : trips) {
            TripWithReservationResponse tripResponse = new TripWithReservationResponse();
            BeanUtils.copyProperties(t, tripResponse);
            List<ReservationNoTripResponse> reservations = new ArrayList<>();
            for (Reservation r : t.getReservations()) {
                ReservationNoTripResponse reservationResponse = new ReservationNoTripResponse();
                BeanUtils.copyProperties(r, reservationResponse);
                reservationResponse.setEmployee(employeeSvc.getByIdResponse(r.getEmployee().getId()));

                reservations.add(reservationResponse);
            }
            tripResponse.setReservations(reservations);
            response.add(tripResponse);
        }
        return response;
    }

    public Page<Trip> getAllPageable(Pageable pageable) {
        return tripRepo.findAll(pageable);
    }

    public Trip getById(Long id) {
        return tripRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Trip not found"));
    }

    public TripWithReservationResponse getByIdResponse(Long id) {
        Trip trip = getById(id);
        TripWithReservationResponse response = new TripWithReservationResponse();
        BeanUtils.copyProperties(trip, response);
        List<ReservationNoTripResponse> reservations = new ArrayList<>();
        for (Reservation r : trip.getReservations()) {
            ReservationNoTripResponse reservationResponse = new ReservationNoTripResponse();
            BeanUtils.copyProperties(r, reservationResponse);
            reservationResponse.setEmployee(employeeSvc.getByIdResponse(r.getEmployee().getId()));
            reservations.add(reservationResponse);
        }
        response.setReservations(reservations);
        if (response.getReservations().isEmpty()) {
            response.setReservations(null);
        }
        return response;
    }

    public int count() {
        return (int) tripRepo.count();
    }

    public String delete(Long id) {
        Trip e = getById(id);
        tripRepo.delete(e);
        return "Trip deleted successfully";
    }

    public String delete(Trip e) {
        Trip foundTrip = getById(e.getId());
        tripRepo.delete(foundTrip);
        return "Trip deleted successfully";
    }

    public TripWithReservationResponse create(@Valid TripCreateRequest request) {
        if (tripRepo.existsByDestinationAndDate(request.getDestination(), request.getDate()))
            throw new EntityExistsException("Trip already exists");

        Trip trip = new Trip();
        BeanUtils.copyProperties(request, trip);
        trip = tripRepo.save(trip);

        TripWithReservationResponse response = new TripWithReservationResponse();
        BeanUtils.copyProperties(trip, response);
        return response;
    }

    public TripWithReservationResponse update(Long id, @Valid TripUpdateRequest request) {
        Trip trip = getById(id);

        trip.setDate(request.getDate() != null ? request.getDate() : trip.getDate());
        trip.setDestination(request.getDestination() != null ? request.getDestination() : trip.getDestination());
        trip.setMaxCapacity(request.getMaxCapacity() > 0 ? request.getMaxCapacity() : trip.getMaxCapacity());

        trip = tripRepo.save(trip);
        TripWithReservationResponse response = new TripWithReservationResponse();
        BeanUtils.copyProperties(trip, response);
        List<ReservationNoTripResponse> reservations = new ArrayList<>();
        for (Reservation r : trip.getReservations()) {
            ReservationNoTripResponse reservationResponse = new ReservationNoTripResponse();
            BeanUtils.copyProperties(r, reservationResponse);
            reservationResponse.setEmployee(employeeSvc.getByIdResponse(r.getEmployee().getId()));
            reservations.add(reservationResponse);
        }
        response.setReservations(reservations);
        return response;
    }

    public Trip updateStatus(Long id, String status) {
        Trip trip = getById(id);
        trip.setStatus(TripStatus.valueOf(status.toUpperCase()));
        return tripRepo.save(trip);
    }

    public List<TripWithReservationResponse> findByDestination(String destination) {
        List<Trip> trips = tripRepo.findByDestination(destination);
        List<TripWithReservationResponse> response = new ArrayList<>();
        for (Trip t : trips) {
            TripWithReservationResponse tripResponse = new TripWithReservationResponse();
            BeanUtils.copyProperties(t, tripResponse);
            List<ReservationNoTripResponse> reservations = new ArrayList<>();
            for (Reservation r : t.getReservations()) {
                ReservationNoTripResponse reservationResponse = new ReservationNoTripResponse();
                BeanUtils.copyProperties(r, reservationResponse);
                reservationResponse.setEmployee(employeeSvc.getByIdResponse(r.getEmployee().getId()));
                reservations.add(reservationResponse);
            }
            tripResponse.setReservations(reservations);
            response.add(tripResponse);
        }
        return response;
    }


}