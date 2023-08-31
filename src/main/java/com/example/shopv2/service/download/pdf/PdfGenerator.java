package com.example.shopv2.service.download.pdf;

import com.example.shopv2.mapper.BasketMapper;
import com.example.shopv2.model.Basket;
import com.example.shopv2.service.BasketService;
import com.example.shopv2.service.dto.BasketDTO;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PdfGenerator {

    private final BasketService basketService;
    private final SpringTemplateEngine springTemplateEngine;

    @Autowired
    public PdfGenerator(BasketService basketService, SpringTemplateEngine springTemplateEngine) {
        this.basketService = basketService;
        this.springTemplateEngine = springTemplateEngine;
    }


    public void htmlToPdf(String processedHtml) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream);
            ConverterProperties converterProperties = new ConverterProperties();

            HtmlConverter.convertToPdf(processedHtml, pdfwriter, converterProperties);
            FileOutputStream fout = new FileOutputStream("F://pdfResult.pdf");

            byteArrayOutputStream.writeTo(fout);
            byteArrayOutputStream.close();
            byteArrayOutputStream.flush();
            fout.close();
        } catch(Exception ex) {
        }
    }

    public void generateHtmlToPdf() {
//        List<Basket> basketList = new ArrayList<>();
//        Basket basket = Basket
//                .builder()
//                .idUser(22L)
//                .build();
//        basketList.add(basket);

        List<BasketDTO> basketList = basketService.getBasketByUser();
        System.out.println(basketList);
        //budowa pliku HTML przy użyciu thymeleaf template engine
        Context context = new Context();
        Map<String, Object> data = new HashMap<>();
        data.put("basketss", basketList);
        context.setVariables(data);

        //konwersja pliku HTML pdfTemplate.html z danymi wraz z context
        String finalHtml = springTemplateEngine.process("pdfTemplate", context);

        htmlToPdf(finalHtml);
    }


}
