package com.example.shopv2.service.download;

import com.example.shopv2.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@Service
public class ResponseDownloadedService {
//Metoda szablonowa jako wzorzec do wykorzystania
    public void toResponse(HttpServletResponse response, StringBuffer stringBuffer){
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename=fileNameToDownload.csv");

//        List<Basket> cardByUserId = basketService.getCardByUser();
//
//        StringBuffer stringBuffer = new StringBuffer("Id\n");
//        cardByUserId.forEach(x -> stringBuffer
//                .append(x.getId())
//                .append("\n"));
        try {
            InputStream inputStream = new ByteArrayInputStream(stringBuffer.toString().getBytes("UTF-8"));
            ServletOutputStream servletOutputStream = response.getOutputStream();

            byte[] outputBytes = new byte[512];

            while (inputStream.read(outputBytes, 0, 512) != -1) {
                servletOutputStream.write(outputBytes, 0, 512);
            }

            inputStream.close();
            servletOutputStream.flush();
            servletOutputStream.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
