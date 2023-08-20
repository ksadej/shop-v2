package com.example.shopv2.controller;

import com.example.shopv2.service.download.DownloadService;
import com.example.shopv2.service.download.ResponseDownloadedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
public class DownloadController {

    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping("/download/basket")
    public void downloadBasket(HttpServletResponse response){
        downloadService.fileToDownload(response);
    }
}
