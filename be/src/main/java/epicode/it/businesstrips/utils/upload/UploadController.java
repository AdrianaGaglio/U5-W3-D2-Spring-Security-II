package epicode.it.businesstrips.utils.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {
    private final UploadSvc uploadSvc;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(MultipartFile file) throws Exception {
        return ResponseEntity.ok(uploadSvc.uploadFile(file));
    }
}
