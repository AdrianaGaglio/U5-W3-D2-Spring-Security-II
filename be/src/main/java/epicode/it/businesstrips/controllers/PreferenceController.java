package epicode.it.businesstrips.controllers;

import epicode.it.businesstrips.entities.preference.Preference;
import epicode.it.businesstrips.entities.preference.PreferenceAddRequest;
import epicode.it.businesstrips.entities.preference.PreferenceCreateRequest;
import epicode.it.businesstrips.entities.preference.PreferenceSvc;
import epicode.it.businesstrips.entities.reservation.Reservation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preferences")
@RequiredArgsConstructor
public class PreferenceController {
    private final PreferenceSvc preferenceSvc;

    @GetMapping
    public ResponseEntity<List<Preference>> getAll() {
        return ResponseEntity.ok(preferenceSvc.getAll());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Preference>> getAllPageable(Pageable pageable) {
        return ResponseEntity.ok(preferenceSvc.getAllPageable(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Preference> getById(@PathVariable Long id) {
        return ResponseEntity.ok(preferenceSvc.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(preferenceSvc.delete(id), HttpStatus.NO_CONTENT);
    }

}
