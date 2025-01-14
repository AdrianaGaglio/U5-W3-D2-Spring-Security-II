package epicode.it.businesstrips.entities.preference;

import epicode.it.businesstrips.entities.preference.dto.PreferenceCreateRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class PreferenceSvc {
    private final PreferenceRepo preferenceRepo;

    public List<Preference> getAll() {
        return preferenceRepo.findAll();
    }

    public Page<Preference> getAllPageable(Pageable pageable) {
        return preferenceRepo.findAll(pageable);
    }

    public Preference getById(Long id) {
        return preferenceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Preference not found"));
    }

    public int count() {
        return (int) preferenceRepo.count();
    }

    public String delete(Long id) {
        Preference e = getById(id);
        preferenceRepo.delete(e);
        return "Preference deleted successfully";
    }

    public String delete(Preference e) {
        Preference foundPreference = getById(e.getId());
        preferenceRepo.delete(foundPreference);
        return "Preference deleted successfully";
    }

    public Preference create(@Valid PreferenceCreateRequest request) {
        Preference preference = new Preference();
        BeanUtils.copyProperties(request, preference);
        preference.setType(PreferenceType.valueOf(request.getType().toUpperCase()));
        return preferenceRepo.save(preference);
    }


}
