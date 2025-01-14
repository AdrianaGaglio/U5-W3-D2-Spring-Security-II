package epicode.it.businesstrips.entities.preference;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PreferenceCreateRequest {
    @NotNull(message = "Preference type is required")
    @NotBlank(message = "Preference type is required")
    private String type;

    @NotNull(message = "Preference name is required")
    @NotBlank(message = "Preference name is required")
    private String name;

    private String location;
}
