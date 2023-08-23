package com.example.shopv2.service.upload;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import com.example.shopv2.service.MealCalendarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UploadService {

    private MealCalendarService mealCalendarService;
    public void uploadFile(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            InputStreamReader in = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(in);
            List<String> listOfStrings = reader.lines().collect(Collectors.toList());
            in.close();
            listOfStrings.remove(0);
            List<MealCalendarRequest> collect = listOfStrings.stream()
                    .map(data -> data.split(";"))
                    .map(array -> MealCalendarRequest.builder()
                            .dataMeal(OffsetDateTime.parse(array[0]))
                            .day(Days.valueOf(array[1]))
                            .idRecipes(Integer.valueOf(array[2]))
                            .time(MealTime.valueOf(array[3]))
                            .build())
                    .collect(Collectors.toList());

            collect.stream()
                    .forEach(x -> mealCalendarService.saveMealCalendar(x));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
