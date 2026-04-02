package com.glitch.main.rest.document;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DocumentController {

    @Value("${application.path.external}")
    private String appRoot;

    @Value("#{${documents.allowed}}")
    private List<String> allowedDocuments;

    @GetMapping("/documents/{filename}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getMethodName(@PathVariable String filename) {
        if (!allowedDocuments.contains(filename)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String absolutePath = String.format("%s/document/%s", appRoot, filename);
        FileSystemResource resource = new FileSystemResource(absolutePath);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", String.format("inline; filename=%s", filename))
                .body(resource);
    }
}
