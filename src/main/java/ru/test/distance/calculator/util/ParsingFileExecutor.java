package ru.test.distance.calculator.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.test.distance.calculator.dto.CitiesRequestDto;
import ru.test.distance.calculator.exception.InvalidFileException;

import java.io.InputStream;

@Component
public class ParsingFileExecutor {

    public CitiesRequestDto parseCitiesRequestDto(MultipartFile file) {
        CitiesRequestDto citiesRequestDto;
        try {
            InputStream is = file.getInputStream();
            XmlMapper xmlMapper = new XmlMapper();
            JavaType transactionType = xmlMapper.getTypeFactory().constructType(CitiesRequestDto.class);
            citiesRequestDto = xmlMapper.readValue(is, transactionType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidFileException("file is null");
        }
        return citiesRequestDto;
    }
}
