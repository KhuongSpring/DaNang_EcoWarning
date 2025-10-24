package com.example.collector_data_service.utils;

import com.example.collector_data_service.domain.entity.DataIngestionLog;
import com.example.collector_data_service.helper.ParseResult;
import com.example.collector_data_service.repository.AssetRepository;
import com.example.collector_data_service.repository.DataIngestionLogRepository;
import com.example.collector_data_service.repository.ObservationRepository;
import com.example.collector_data_service.service.AssetInitializationService;
import com.example.collector_data_service.service.ObservationInitializationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final AssetRepository assetRepository;
    private final DataIngestionLogRepository logRepository;
    private final ObservationRepository observationRepository;

    private final AssetInitializationService assetInitService;
    private final ObservationInitializationService observationInitService;

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Override
    public void run(String... args) throws Exception {
        try {
            if (assetRepository.count() > 0) {
                log.info("Assets data already initialized. Skipping.");
                return;
            }

            if (observationRepository.count() > 0) {
                log.info("Observation data already initialized. Skipping.");
                return;
            }
        } catch (DataAccessResourceFailureException e) {
            log.error("DATABASE CONNECTION FAILED. Cannot proceed with data initialization.", e);
            return;
        }

        final UUID runId = UUID.randomUUID();

        // ==== ASSET ====
        log.info("Starting Assets initialization from CSV files...");

        safeParse(runId, "open_data/moi_truong_va_thuy_van/danh_muc_cac_ho_ao.csv", assetInitService::parseAndSavePondsAndLakes);
        safeParse(runId, "open_data/moi_truong_va_thuy_van/danh_muc_cac_ho_thuy_loi.csv", assetInitService::parseAndSaveIrrigationLakes);
        safeParse(runId, "open_data/moi_truong_va_thuy_van/danh_muc_song_noi_tinh.csv", assetInitService::parseAndSaveRivers);

        safeParse(runId, "open_data/thien_tai/danh_sach_cac_khu_vuc_da_xay_ra_hien_tuong_sac_lo.csv", assetInitService::parseAndSaveLandslideAreas);

        safeParse(runId, "open_data/phong_chong_thien_tai/mot_so_thap_canh_bao_ngap.csv", assetInitService::parseAndSaveFloodWarningTowers);
        safeParse(runId, "open_data/phong_chong_thien_tai/mot_so_tram_canh_bao_lu_tu_dong.csv", assetInitService::parseAndSaveAutoFloodWarningStations);
        safeParse(runId, "open_data/phong_chong_thien_tai/mot_so_tram_do_mua_tu_dong.csv", assetInitService::parseAndSaveRainStations);
        safeParse(runId, "open_data/phong_chong_thien_tai/thong_tin_cac_nha_tru_bao.csv", assetInitService::parseAndSaveStormShelters);
        safeParse(runId, "open_data/phong_chong_thien_tai/tram_truc_canh_canh_bao_thien_tai_da_muc_tieu_ven_bien.csv", assetInitService::parseAndSaveCoastalWarningStations);

        log.info("Assets initialization finished for run ID: {}", runId);

        // ==== OBSERVATION ====

        log.info("Starting Observations initialization...");

        safeParse(runId, "open_data/moi_truong_va_thuy_van/do_am_khong_khi_trung_binh.csv",
                is -> observationInitService.parseMonthlyCityWideMetric(is, "Độ ẩm không khí trung bình", "Thời tiết"));

        safeParse(runId, "open_data/moi_truong_va_thuy_van/luong_mua.csv",
                is -> observationInitService.parseMonthlyCityWideMetric(is, "Lượng mưa", "Thời tiết"));

        safeParse(runId, "open_data/moi_truong_va_thuy_van/mot_so_chi_tieu_thong_ke_ve_moi_truong.csv",
                observationInitService::parseEnvironmentalStats);

        safeParse(runId, "open_data/moi_truong_va_thuy_van/muc_nuoc_mot_so_song_chinh.csv",
                observationInitService::parseRiverWaterLevels);

        safeParse(runId, "open_data/moi_truong_va_thuy_van/nhiet_do_khong_khi_trung_binh.csv",
                is -> observationInitService.parseMonthlyCityWideMetric(is, "Nhiệt độ không khí trung bình", "Thời tiết"));

        safeParse(runId, "open_data/moi_truong_va_thuy_van/so_gio_nang.csv",
                is -> observationInitService.parseMonthlyCityWideMetric(is, "Số giờ nắng", "Thời tiết"));

        safeParse(runId, "open_data/thien_tai/thiet_hai_do_thien_tai.csv",
                observationInitService::parseDisasterDamage);

        log.info("Observations initialization finished for run ID: {}", runId);

    }

    @FunctionalInterface
    interface DataParser {
        ParseResult process(InputStream is) throws Exception;
    }

    private void safeParse(UUID runId, String filePath, DataParser parser) {
        DataIngestionLog logEntry = new DataIngestionLog();
        logEntry.setRunId(runId);
        logEntry.setFileName(filePath);
        logEntry.setStartTime(LocalDateTime.now());

        ParseResult result = ParseResult.zero();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (is == null) {
                throw new java.io.FileNotFoundException("File not found in resources: " + filePath);
            }
            result = parser.process(is);

            logEntry.setStatus("SUCCESS");
        } catch (Exception e) {
            log.error("Failed to parse file {}: {}", filePath, e.getMessage());
            logEntry.setStatus("FAILED");
            logEntry.setErrorMessage(e.toString());
        } finally {
            logEntry.setRecordsProcessed(result.getRecordsProcessed());
            logEntry.setRecordsInserted(result.getRecordsInserted());
            logEntry.setEndTime(LocalDateTime.now());
            logRepository.save(logEntry);
        }
    }
}