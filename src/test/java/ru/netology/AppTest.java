package ru.netology;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {

        System.out.println("sofsof");
        assertTrue( true );
    }
    @Test
    public void test_MessageSenderImpl_RUSSIA() {
        GeoService geoService= Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("172.0.32.11"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        LocalizationService localizationService =Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");
        String message = messageSender.send(headers);
        String expected="Добро пожаловать";
        Assertions.assertEquals(expected,message);
    }

    @Test
    public void test_MessageSenderImpl_USA() {
        GeoService geoService= Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("96.0.32.11"))
                .thenReturn(new Location("USA", Country.USA, null, 0));
        LocalizationService localizationService =Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.0.32.11");
        String message = messageSender.send(headers);
        String expected="Welcome";
        Assertions.assertEquals(expected,message);
    }

    @ParameterizedTest
    @ValueSource(strings = {"172.0.32.11"})
    public void test_Location_byIp(String ip) {
        GeoServiceImpl geoservice=new GeoServiceImpl();
        Assertions.assertEquals(geoservice.byIp("172.0.32.11"),new Location("Moscow", Country.RUSSIA, null, 0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"RUSSIA"})
    public void test_Locale_Country(Country country) {
        LocalizationServiceImpl localizationService=new LocalizationServiceImpl();
        Assert.assertEquals(localizationService.locale(country),"Добро пожаловать");
    }

}