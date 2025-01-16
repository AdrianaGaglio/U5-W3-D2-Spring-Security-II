package epicode.it.businesstrips.entities.reservation;

import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.employee.EmployeeSvc;
import epicode.it.businesstrips.entities.preference.Preference;
import epicode.it.businesstrips.entities.preference.dto.PreferenceAddRequest;
import epicode.it.businesstrips.entities.preference.dto.PreferenceCreateRequest;
import epicode.it.businesstrips.entities.preference.PreferenceSvc;
import epicode.it.businesstrips.entities.reservation.dto.ReservationCreateRequest;
import epicode.it.businesstrips.entities.reservation.dto.ReservationResponse;
import epicode.it.businesstrips.entities.reservation.dto.ReservationResponseMapper;
import epicode.it.businesstrips.entities.trip.Trip;
import epicode.it.businesstrips.entities.trip.dto.TripResponse;
import epicode.it.businesstrips.entities.trip.TripSvc;
import epicode.it.businesstrips.exceptions.MaxCapacityException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationSvc {
    private final ReservationRepo reservationRepo;
    private final EmployeeSvc employeeSvc;
    private final TripSvc tripSvc;
    private final PreferenceSvc preferenceSvc;
    private final ReservationResponseMapper mapper;

    public Reservation save(Reservation e) {
        return reservationRepo.save(e);
    }

    public TripResponse getTripResponse(Trip trip) {
        TripResponse response = new TripResponse();
        BeanUtils.copyProperties(trip, response);
        return response;
    }

    public List<ReservationResponse> getAll() {
        List<ReservationResponse> response = mapper.toReservationResponseList(reservationRepo.findAll());
        return response;
    }

    public Page<ReservationResponse> getAllPageable(Pageable pageable) {
        Page<Reservation> reservations = reservationRepo.findAll(pageable);
        Page<ReservationResponse> response = reservations.map(r -> {
            ReservationResponse reservationResponse = mapper.toReservationResponse(r);
            return reservationResponse;
        });
        return response;
    }

    public Reservation getById(Long id) {
        return reservationRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }

    public ReservationResponse getByIdResponse(Long id) {
        ReservationResponse reservationResponse = mapper.toReservationResponse(getById(id));

        return reservationResponse;
    }

    public int count() {
        return (int) reservationRepo.count();
    }

    public String delete(Long id) {
        Reservation e = getById(id);
        reservationRepo.delete(e);
        return "Reservation deleted successfully";
    }

    public String delete(Reservation e) {
        Reservation foundReservation = getById(e.getId());
        reservationRepo.delete(foundReservation);
        return "Reservation deleted successfully";
    }

    public Reservation getByTripAndEmployee(Long tripId, Long employeeId) {
        return reservationRepo.findFirstByTripAndEmployee(tripSvc.getById(tripId), employeeSvc.getById(employeeId));
    }

    @Transactional
    public ReservationResponse create(ReservationCreateRequest request) {
        Reservation foundR = getByTripAndEmployee(request.getTripId(), request.getEmployeeId());
        if (foundR != null) throw new EntityExistsException("User already has a reservation for this trip");

        Reservation r = new Reservation();

        r.setRequestDate(request.getRequestDate() != null ? request.getRequestDate() : LocalDate.now());

        Trip t = tripSvc.getById(request.getTripId());
        if (t.getReservations().size() == t.getMaxCapacity()) throw new MaxCapacityException("Trip is full");
        r.setTrip(t);

        Employee e = employeeSvc.getById(request.getEmployeeId());
        r.setEmployee(e);

        if (request.getPreferences() != null && request.getPreferences().size() > 0) {
            for (PreferenceCreateRequest p : request.getPreferences()) {
                if (p.getLocation() == null || p.getLocation().isEmpty()) p.setLocation(t.getDestination());
                Preference newP = preferenceSvc.create(p);
                r.getPreferences().add(newP);
            }
        }

        r = reservationRepo.save(r);
        ReservationResponse response = mapper.toReservationResponse(r);

        return response;
    }

    public List<ReservationResponse> getByEmployee(Long id) {
        Employee e = employeeSvc.getById(id);
        List<ReservationResponse> responses = mapper.toReservationResponseList(reservationRepo.findByEmployeeOrderByRequestDateDesc(e));

        return responses;
    }

    @Transactional
    public ReservationResponse addPreference(Long id, @Valid PreferenceAddRequest request) {
        PreferenceCreateRequest pCreate = new PreferenceCreateRequest();
        BeanUtils.copyProperties(request, pCreate);
        Reservation r = getById(id);
        Preference p = preferenceSvc.create(pCreate);
        r.getPreferences().add(p);
        r = save(r);
        ReservationResponse response = mapper.toReservationResponse(r);

        return response;
    }

    public List<ReservationResponse> findByTrip(Long id) {
        Trip t = tripSvc.getById(id);
        List<ReservationResponse> response = mapper.toReservationResponseList(reservationRepo.findFirstByTrip(t));

        return response;
    }

    public List<ReservationResponse> findByDate(LocalDate date) {
        List<ReservationResponse> response = mapper.toReservationResponseList(reservationRepo.findAllByDate(date));
        return response;
    }

    public List<ReservationResponse> findByDestination(String destination) {
        List<ReservationResponse> response = mapper.toReservationResponseList(reservationRepo.findAllByDestination(destination));

        return response;
    }

    public List<ReservationResponse> findByDestinationAndDate(String destination, LocalDate date) {
        List<Reservation> r = reservationRepo.findAllByDestinationAndDate(destination, date);
        List<ReservationResponse> response = mapper.toReservationResponseList(reservationRepo.findAllByDestinationAndDate(destination, date));

        return response;
    }
}
