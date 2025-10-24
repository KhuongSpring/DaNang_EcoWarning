package com.example.collector_data_service.service;

import com.example.collector_data_service.domain.entity.Asset;
import com.example.collector_data_service.domain.entity.Metric;
import com.example.collector_data_service.domain.entity.Observation;
import com.example.collector_data_service.helper.ParseResult;
import com.example.collector_data_service.repository.AssetRepository;
import com.example.collector_data_service.repository.MetricRepository;
import com.example.collector_data_service.repository.ObservationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ObservationInitializationService {

    private static final Logger log = LoggerFactory.getLogger(ObservationInitializationService.class);

    private final AssetRepository assetRepository;
    private final MetricRepository metricRepository;
    private final ObservationRepository observationRepository;

    private Asset getOrCreateDefaultCityAsset() {
        String defaultAssetName = "Thành phố Đà Nẵng";
        return assetRepository.findByName(defaultAssetName).orElseGet(() -> {
            Asset asset = new Asset();
            asset.setName(defaultAssetName);
            asset.setAssetType("Hành chính");
            return assetRepository.save(asset);
        });
    }

    private Metric getOrCreateMetric(String name, String category, String unit) {
        return metricRepository.findByName(name).orElseGet(() -> {
            log.info("Creating new Metric: '{}' in category '{}'", name, category);
            Metric metric = new Metric();
            metric.setName(name);
            metric.setCategory(category);
            metric.setUnit(unit);
            return metricRepository.save(metric);
        });
    }

    @Transactional
    public ParseResult parseMonthlyCityWideMetric(InputStream is, String metricName, String metricCategory) throws Exception {
        int recordsProcessed = 0;
        int recordsInserted = 0;
        List<Observation> observationsToSave = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            CSVParser csvParser = CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().withTrim().parse(reader);

            Pattern pattern = Pattern.compile("(\\d{4}).*?\\((.*?)\\)");
            Asset defaultAsset = getOrCreateDefaultCityAsset();
            String unit = "";

            List<String> headers = csvParser.getHeaderNames();
            for (String header : headers) {
                Matcher matcher = pattern.matcher(header);
                if (matcher.find()) {
                    unit = matcher.group(2).trim();
                    break;
                }
            }

            Metric metric = getOrCreateMetric(metricName, metricCategory, unit);

            for (CSVRecord record : csvParser) {
                String firstColumn = record.get(0);
                recordsProcessed++;

                String monthStr = firstColumn.replaceAll("[^0-9]", "");
                if (monthStr.isEmpty()) continue;
                int month = Integer.parseInt(monthStr);

                for (String header : headers) {
                    Matcher matcher = pattern.matcher(header);
                    if (matcher.find()) {
                        int year = Integer.parseInt(matcher.group(1));
                        String valueStr = record.get(header).trim();
                        if (valueStr.isEmpty()) continue;

                        Observation obs = new Observation();
                        obs.setAsset(defaultAsset);
                        obs.setMetric(metric);
                        obs.setRecordTime(LocalDateTime.of(year, month, 1, 0, 0));
                        obs.setValue(Double.parseDouble(valueStr.replace(",", ".")));
                        observationsToSave.add(obs);
                        recordsInserted++;
                    }
                }
            }
            observationRepository.saveAll(observationsToSave);
        }
        log.info("Successfully parsed {}. Processed rows: {}, Inserted observations: {}", metricName, recordsProcessed, recordsInserted);
        return new ParseResult(recordsProcessed, recordsInserted);
    }

    // Muc nuoc mot so song chinh
    @Transactional
    public ParseResult parseRiverWaterLevels(InputStream is) throws Exception {
        int recordsProcessed = 0;
        int recordsInserted = 0;
        List<Observation> observationsToSave = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String[] HEADERS = {"Phân loại", "Đơn vị tính", "Năm 2019", "Năm 2020", "Năm 2021", "Năm 2022", "Sơ bộ 2023"};
            CSVFormat customFormat = CSVFormat.DEFAULT.builder()
                    .setDelimiter(';').setHeader(HEADERS).setSkipHeaderRecord(true).setTrim(true).build();

            CSVParser csvParser = new CSVParser(reader, customFormat);
            Pattern yearPattern = Pattern.compile("(\\d{4})");

            for (CSVRecord record : csvParser) {
                recordsProcessed++;
                String metricName = record.get("Phân loại");
                String unit = record.get("Đơn vị tính");
                Asset targetAsset = null;

                if (metricName.toLowerCase().contains("sông")) {
                    String[] parts = metricName.split("sông");
                    if (parts.length > 1) {
                        String riverName = "Sông" + parts[1].trim();
                        Optional<Asset> assetOpt = assetRepository.findByName(riverName);
                        if (assetOpt.isPresent()) {
                            targetAsset = assetOpt.get();
                        } else {
                            log.warn("Asset not found for river: '{}'. Skipping row.", riverName);
                            continue;
                        }
                    }
                } else {
                    targetAsset = getOrCreateDefaultCityAsset();
                }

                if (targetAsset == null) continue;

                Metric metric = getOrCreateMetric(metricName, "Thủy văn", unit);

                for (String header : HEADERS) {
                    Matcher matcher = yearPattern.matcher(header);
                    if (matcher.find()) {
                        int year = Integer.parseInt(matcher.group(1));
                        String valueStr = record.get(header).trim();
                        if (valueStr.isEmpty()) continue;

                        Observation obs = new Observation();
                        obs.setAsset(targetAsset);
                        obs.setMetric(metric);
                        obs.setRecordTime(LocalDateTime.of(year, 1, 1, 0, 0));
                        obs.setValue(Double.parseDouble(valueStr.replace(",", ".")));
                        observationsToSave.add(obs);
                        recordsInserted++;
                    }
                }
            }
            observationRepository.saveAll(observationsToSave);
        }
        log.info("Successfully parsed River Water Levels. Processed rows: {}, Inserted observations: {}", recordsProcessed, recordsInserted);
        return new ParseResult(recordsProcessed, recordsInserted);
    }

    // Mot so chi tieu thong ke moi truong
    @Transactional
    public ParseResult parseEnvironmentalStats(InputStream is) throws Exception {
        int recordsProcessed = 0;
        int recordsInserted = 0;
        List<Observation> observationsToSave = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String[] HEADERS = {"Phân loại", "Năm 2019 (ĐVT:%)", "Năm 2020 (ĐVT:%)", "Năm 2021 (ĐVT:%)", "Sơ bộ 2022 (ĐVT:%)"};
            CSVFormat customFormat = CSVFormat.DEFAULT.builder()
                    .setDelimiter(';')
                    .setHeader(HEADERS)
                    .setSkipHeaderRecord(true)
                    .setTrim(true)
                    .build();

            CSVParser csvParser = new CSVParser(reader, customFormat);

            Pattern pattern = Pattern.compile("(\\d{4})");
            Asset defaultAsset = getOrCreateDefaultCityAsset();
            String unit = "%";

            for (CSVRecord record : csvParser) {
                recordsProcessed++;
                String metricName = record.get("Phân loại");
                if (metricName.isEmpty()) continue;

                Metric metric = getOrCreateMetric(metricName, "Môi trường", unit);

                for (String header : HEADERS) {
                    Matcher matcher = pattern.matcher(header);
                    if (matcher.find()) {
                        int year = Integer.parseInt(matcher.group(1));
                        String valueStr = record.get(header).trim();
                        if (valueStr.isEmpty()) continue;

                        Observation obs = new Observation();
                        obs.setAsset(defaultAsset);
                        obs.setMetric(metric);
                        obs.setRecordTime(LocalDateTime.of(year, 1, 1, 0, 0));
                        obs.setValue(Double.parseDouble(valueStr.replace(",", ".")));
                        observationsToSave.add(obs);
                        recordsInserted++;
                    }
                }
            }
            observationRepository.saveAll(observationsToSave);
        }
        log.info("Successfully parsed Environmental Stats. Processed rows: {}, Inserted observations: {}", recordsProcessed, recordsInserted);
        return new ParseResult(recordsProcessed, recordsInserted);
    }

    // Thiet hai do thien tai
    @Transactional
    public ParseResult parseDisasterDamage(InputStream is) throws Exception {
        int recordsProcessed = 0;
        int recordsInserted = 0;
        List<Observation> observationsToSave = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String[] HEADERS = {"Phân theo", "Phân loại", "Đơn vị tính", "Năm 2018", "Năm 2020", "Năm 2021", "Sơ bộ 2022"};
            CSVFormat customFormat = CSVFormat.DEFAULT.builder()
                    .setDelimiter(';').setHeader(HEADERS).setSkipHeaderRecord(true).setTrim(true).build();

            CSVParser csvParser = new CSVParser(reader, customFormat);
            Pattern yearPattern = Pattern.compile("(\\d{4})");
            Asset defaultAsset = getOrCreateDefaultCityAsset();

            for (CSVRecord record : csvParser) {
                recordsProcessed++;
                String category = record.get("Phân theo");
                String name = record.get("Phân loại");
                String unit = record.get("Đơn vị tính");

                if (category.isEmpty() && name.isEmpty()) continue;

                String metricName = name.equalsIgnoreCase(category) ? name : category + " - " + name;

                Metric metric = getOrCreateMetric(metricName, "Thiệt hại thiên tai", unit);

                for (String header : HEADERS) {
                    Matcher matcher = yearPattern.matcher(header);
                    if (matcher.find()) {
                        int year = Integer.parseInt(matcher.group(1));
                        String valueStr = record.get(header).trim();
                        if (valueStr.isEmpty() || valueStr.equals("-")) continue;

                        Observation obs = new Observation();
                        obs.setAsset(defaultAsset);
                        obs.setMetric(metric);
                        obs.setRecordTime(LocalDateTime.of(year, 1, 1, 0, 0));
                        obs.setValue(Double.parseDouble(valueStr.replace(",", ".")));
                        observationsToSave.add(obs);
                        recordsInserted++;
                    }
                }
            }
            observationRepository.saveAll(observationsToSave);
        }
        log.info("Successfully parsed Disaster Damage. Processed rows: {}, Inserted observations: {}", recordsProcessed, recordsInserted);
        return new ParseResult(recordsProcessed, recordsInserted);
    }
}