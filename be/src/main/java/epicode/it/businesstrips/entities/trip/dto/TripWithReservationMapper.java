package epicode.it.businesstrips.entities.trip.dto;

import epicode.it.businesstrips.entities.employee.EmployeeSvc;
import epicode.it.businesstrips.entities.employee.dto.EmployeeResponseMapper;
import epicode.it.businesstrips.entities.reservation.Reservation;
import epicode.it.businesstrips.entities.reservation.dto.ReservationNoTripResponse;
import epicode.it.businesstrips.entities.trip.Trip;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TripWithReservationMapper {
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private EmployeeSvc employeeSvc;

    public TripWithReservationResponse toTripWithReservationResponse(Trip t) {
        TripWithReservationResponse response = modelMapper.map(t, TripWithReservationResponse.class);
        response.setReservations(toReservationNoTripResponseList(t.getReservations()));
        return response;
    }

    public List<TripWithReservationResponse> toTripWithReservationResponseList(List<Trip> t) {
        return t.stream().map(this::toTripWithReservationResponse).toList();
    }


    public ReservationNoTripResponse toReservationNoTripResponse(Reservation r) {
        ReservationNoTripResponse response = modelMapper.map(r, ReservationNoTripResponse.class);
        response.setEmployee(employeeSvc.getByIdResponse(r.getEmployee().getId()));
        return response;
    }

    public List<ReservationNoTripResponse> toReservationNoTripResponseList(List<Reservation> r) {
        return r.stream().map(this::toReservationNoTripResponse).toList();
    }
}
