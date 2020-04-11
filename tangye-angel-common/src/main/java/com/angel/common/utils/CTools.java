package com.angel.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @description 自定义公共工具类
 * @date 2020-04-11
 * @author GilbertPan
 */
@Slf4j
public class CTools {

	public static String dateTimeMillis2String(long time,String format){
		Instant instant = Instant.ofEpochMilli(time);
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone).format(DateTimeFormatter.ofPattern(format));
	}

    public static String localDateTime2String(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static String dateTime2String(Date date, String format){
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone).format(DateTimeFormatter.ofPattern(format));
    }

    public static long object2Date(Object time){
        if(null!=time){
            String dateTime = ((String) time).replace("Z", " UTC");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            try {
                return format.parse(dateTime).getTime();
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return 0;
    }
}
