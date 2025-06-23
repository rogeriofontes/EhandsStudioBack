package com.maosencantadas.utils;

import com.maosencantadas.model.domain.media.MediaType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The type Date util.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MediaUtil {

    public static String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    public static  MediaType determineMediaType(String extension) {
        switch (extension) {
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
            case "bmp":
                return MediaType.IMAGE;

            case "mp4":
            case "avi":
            case "mov":
            case "mkv":
                return MediaType.VIDEO;

            case "mp3":
            case "wav":
            case "ogg":
                return MediaType.AUDIO;

            case "pdf":
            case "doc":
            case "docx":
            case "xls":
            case "xlsx":
                return MediaType.DOCUMENT;

            default:
                return MediaType.DOCUMENT;  // Ou criar um tipo UNKNOWN se quiser
        }
    }

}
