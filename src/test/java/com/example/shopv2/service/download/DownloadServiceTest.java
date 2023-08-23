package com.example.shopv2.service.download;

import com.example.shopv2.mapper.BasketMapper;
import com.example.shopv2.service.BasketService;
import com.example.shopv2.service.MealCalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DownloadServiceTest {

    @Mock
    private BasketService basketService;
    @Mock
    private MealCalendarService mealCalendarService;
    private final MealCalendarDownloadBuilder mealCalendarDownloadBuilder = new MealCalendarDownloadBuilder();
    private final BasketDownloadBuilder basketDownloadBuilder = new BasketDownloadBuilder();
    private final ResponseDownloadedService responseDownloadedService = new ResponseDownloadedService();
    private final BasketMapper basketMapper = new BasketMapper();
    private DownloadService downloadService;

    @BeforeEach
    public void setup(){
        downloadService = new DownloadService(
                basketDownloadBuilder,
                responseDownloadedService,
                mealCalendarDownloadBuilder,
                basketService,
                mealCalendarService,
                basketMapper
        );
    }

    @Test
    void fileToDownload_checkIfThereIsFieldWithHeadersInResponse() throws UnsupportedEncodingException {
        //given
        MockHttpServletResponse response = new MockHttpServletResponse();
        String header = "Id;Date added;Added By";
        //when
        downloadService.fileToDownload(response, DownloadTypes.BASKET, null);

        //then
        assertThat(response.getContentAsString()).contains(header);

    }

}