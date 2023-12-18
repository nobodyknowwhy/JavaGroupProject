package org.gdufs.controller;

import org.gdufs.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {
    @Autowired
    private PdfService pdfService;
//    @GetMapping("/exportPdf")
//    public ResponseEntity<byte[]> exportPdf() {
//        byte[] pdfBytes = pdfService.generatePdf();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "example.pdf");
//        return ResponseEntity.ok().headers(headers).body(pdfBytes);
//    }
}
