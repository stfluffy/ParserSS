package com.ss.testtask.service.impl;

import com.ss.testtask.dto.DictionaryDto;
import com.ss.testtask.exception.ParsePageException;
import com.ss.testtask.model.Dictionary;
import com.ss.testtask.repository.DictionaryRepository;
import com.ss.testtask.service.ParserFromHtmlService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParserFromHtmlServiceImpl implements ParserFromHtmlService {

    private final DictionaryRepository dictionaryRepository;

    private final ModelMapper modelMapper;

    /**
     * Location of the folder with saved web pages.
     */
    private final String pagesFolder = "saveWebPages/";


    public DictionaryDto parsePage(String url) throws Exception {
        url = getUrl(url);
        Optional<Dictionary> getDictionary = dictionaryRepository.findByUrl(url);

        if (getDictionary.isPresent()) {
            return convertToDto(getDictionary.get());
        }

        if (savePageToFile(url, getFileName(url))) {
            return getDictionary(url, getFileName(url));
        }

        throw new ParsePageException();
    }

    /**
     * This method parses a web page stored in a folder,
     * searches for unique words and counts their number.
     * Then creates a new object DictionaryDto and returns it.
     *
     * @param url of cite after the method getUrl().
     * @param fileName after the method getFileName().
     * @return DictionaryDto
     */
    private DictionaryDto getDictionary(String url, String fileName) throws Exception {
        HashMap<String, String> words = new HashMap<>();

        String getParseText =
                Jsoup.parse(new File(pagesFolder + fileName + ".html"), "UTF-8")
                        .text();

        String[] parseText = Jsoup.parse(getParseText).text()
                .replaceAll("[^A-Za-zа-яёА-ЯЁ ]", " ")
                .replaceAll("\\s+", " ")
                .toLowerCase()
                .split(" ");

        for (String key : parseText) {
            int count = 0;
            for (String value : parseText) {
                if (key.equals(value)) {
                    count++;
                }
            }

            words.put(key, String.valueOf(count));
        }

        return convertToDto(dictionaryRepository.save(Dictionary.builder()
                .url(url)
                .fileName(pagesFolder + fileName + ".html")
                .words(words)
                .build()));
    }

    /**
     * This method downloads a web page,
     * creates a new file with the name of the site and saves it.
     *
     * @param url      of cite after the method getUrl().
     * @param fileName after the method getFileName().
     * @return boolean
     */
    private boolean savePageToFile(String url, String fileName) {
        try {
            String getText = Jsoup.connect(url)
                    .timeout(3000)
                    .get().toString();

            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(pagesFolder + fileName + ".html"));
            bufferedWriter.write(getText);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param url of site.
     * @return a string without a slash at the end, for the parser to work correctly.
     */
    private String getUrl(String url) {
        return StringUtils.stripEnd(url, "/");
    }

    /**
     * @param url of site.
     * @return a string without '/', and a protocol for writing the file name.
     */
    private String getFileName(String url) {
        return StringUtils.substringAfter(url, "://").replace("/", "-");
    }

    private DictionaryDto convertToDto(Dictionary dictionary) {
        return modelMapper.map(dictionary, DictionaryDto.class);
    }

}
