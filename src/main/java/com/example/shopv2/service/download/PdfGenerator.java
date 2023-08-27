package com.example.shopv2.service.download;

import com.example.shopv2.model.Basket;
import com.example.shopv2.repository.BasketRepository;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.FileOutputStream;
import java.util.*;

@Service
public class PdfGenerator {

    private final BasketRepository basketRepository;
    private final SpringTemplateEngine springTemplateEngine;
    @Autowired
    public PdfGenerator(BasketRepository basketRepository, SpringTemplateEngine springTemplateEngine) {
        this.basketRepository = basketRepository;
        this.springTemplateEngine = springTemplateEngine;
    }

    public void htmlToPdf(String processedHtml) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream);
            DefaultFontProvider defaultFont =
                    new DefaultFontProvider(false, true, false);
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(defaultFont);
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
        List<Basket> basketList = new ArrayList<>();
        Basket basket = Basket
                .builder()
                .idUser(22L)
                .build();
        basketList.add(basket);

        Context context = new Context();
        Map<String, Object> data = new HashMap<>();
        data.put("basketss", basketList);
        context.setVariables(data);

        String finalHtml = springTemplateEngine.process("pdfTemplate", context);

        htmlToPdf(finalHtml);
    }


}
