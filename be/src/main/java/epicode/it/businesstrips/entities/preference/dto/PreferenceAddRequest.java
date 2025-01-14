package epicode.it.businesstrips.entities.preference.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PreferenceAddRequest {

    @NotNull(message = "ID is required")
    private Long reservationId;

    @NotNull(message = "Preference type is required")
    @NotBlank(message = "Preference type is required")
    private String type;

    @NotNull(message = "Preference name is required")
    @NotBlank(message = "Preference name is required")
    private String name;

    private String location;
}
