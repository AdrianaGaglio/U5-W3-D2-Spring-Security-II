package epicode.it.businesstrips.entities.reservation.dto;

import epicode.it.businesstrips.entities.employee.EmployeeSvc;
import epicode.it.businesstrips.entities.reservation.Reservation;
import epicode.it.businesstrips.entities.trip.Trip;
import epicode.it.businesstrips.entities.trip.dto.TripResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationResponseMapper {
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private EmployeeSvc employeeSvc;

    public ReservationResponse toReservationResponse(Reservation r) {
        ReservationResponse response = modelMapper.map(r, ReservationResponse.class);
        response.setEmployee(employeeSvc.getByIdResponse(r.getEmployee().getId()));
        response.setTrip(toTripResponse(r.getTrip()));
        return response;
    }

    public TripResponse toTripResponse(Trip t ) {
        TripResponse response = modelMapper.map(t, TripResponse.class);
        return response;
    }

    public List<ReservationResponse> toReservationResponseList(List<Reservation> r) {
        return r.stream().map(this::toReservationResponse).toList();
    }
}
