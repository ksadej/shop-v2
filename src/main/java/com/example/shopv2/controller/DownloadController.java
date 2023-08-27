package com.example.shopv2.controller;

import com.example.shopv2.service.download.DownloadService;
import com.example.shopv2.service.download.DownloadTypes;
import com.example.shopv2.service.download.PdfGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping
public class DownloadController {

    private final DownloadService downloadService;
    private final PdfGenerator pdfGenerator;

    public DownloadController(DownloadService downloadService, PdfGenerator pdfGenerator) {
        this.downloadService = downloadService;
        this.pdfGenerator = pdfGenerator;
    }

    @GetMapping("/download/basket")
    public void downloadBasket(HttpServletResponse response){
        downloadService.fileToDownload(response, DownloadTypes.BASKET, null);
    }

    @GetMapping("/download/basket/filter")
    public void downloadFilteredBasket(HttpServletResponse response, @RequestParam Map<String, String> filter){
        downloadService.fileToDownload(response, DownloadTypes.BASKET, filter);
    }

    @GetMapping("/download/calendar")
    public void downloadCalendar(HttpServletResponse response){
        downloadService.fileToDownload(response, DownloadTypes.MEALCALENDAR, null);
    }

    @GetMapping("/download/test")
    public void getPdf(){
        pdfGenerator.generateHtmlToPdf();
    }
}
