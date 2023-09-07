package com.shiki.koko.base

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.concurrent.TimeUnit

//https://blog.csdn.net/wangdong5678999/article/details/81159690
//SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Date())
//https://juejin.cn/post/7269013062677823528

private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
private val simpleDateFormatS = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA)
fun Long.toTime(): String {
    val date = Date(this)
    return simpleDateFormat.format(date)
}

fun Long.toTimeSSS(): String {
    val date = Date(this)
    return simpleDateFormatS.format(date)
}

/**
 * 获取转换后的时间样式。
 *
 * @return 处理后的时间样式，示例：06:50
 */
fun Int.conversionVideoDuration(): String {
    val minute = 1 * 60
    val hour = 60 * minute
    val day = 24 * hour

    if (this < hour) {
        return String.format("%02d:%02d", this / minute, this % 60)
    }
    return String.format("%02d:%02d:%02d", this / hour, (this % hour) / minute, this % 60)
}


/**
 * Long转换为字符串
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Long.toTimeString(): String {
    val ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return ftf.format(
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this),
            ZoneId.systemDefault()
        )
    )
}

/**
 * 当前系统时间转成
 * LocalDateTime 不能处理时区
 */
@SuppressLint("SimpleDateFormat")
fun getLocalTimeString(): String {
    val date = Date(System.currentTimeMillis())
//    val zoneId: ZoneId = ZoneId.systemDefault()
//    val localDateTime: LocalDateTime = date.toInstant().atZone(zoneId).toLocalDateTime()
//    println(localDateTime)

    //dd：指当月的几号  DD：指该天在全年的天数。
    val simpleDateFormat1 = SimpleDateFormat("yy:MM：dd:HH:mm.SSS")
    return simpleDateFormat1.format(date)
}

/**
 *
 * Date获取时间,Date类启始year时间1900,且月份从0开始表示的
 */
fun getDate() {
    val date = Date(114, 2, 18)//2014,3,18
}

/**
 * https://blog.csdn.net/yx0628/article/details/79317440
 * Calendar 用于代替Date,year:从0开始
 * month:从0开始
 */
fun sampleCalendar() {
    val cal = Calendar.getInstance()
    // 赋值时年月日时分秒常用的6个值，注意月份下标从0开始，所以取月份要+1
    println("年:" + cal.get(Calendar.YEAR))
    println("月:" + (cal.get(Calendar.MONTH) + 1))
    println("日:" + cal.get(Calendar.DAY_OF_MONTH))
    println("分:" + cal.get(Calendar.MINUTE))
    println("秒:" + cal.get(Calendar.SECOND))

    // 如果想设置为某个日期，可以一次设置年月日时分秒，由于月份下标从0开始赋值月份要-1
    // cal.set(year, month, date, hourOfDay, minute, second);
    cal.set(2018, 1, 15, 23, 59, 59);
}

/**
 * LocalDate类的实例是一个不 可变对象，它只提供了简单的日期，并不含当天的时间信息。
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getLocalDate() {
    val lofDate: LocalDate = LocalDate.of(2014, 3, 18) //2014-03-18
    val parseDate = LocalDate.parse("2014-03-18") //2014-03-18
}

/**
 * LocalTime用来表示一天中的时间，比如13:45:20。
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getLocalTime() {
    val ofTime: LocalTime = LocalTime.of(13, 45, 20) // 13:45:20
    val parseTime: LocalTime = LocalTime.parse("13:45:20") // 13:45:20
}

/**
 * LocalDateTime，是LocalDate和LocalTime的合体。它同时表示了日期和时间，但不带有时区信息。
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getLocalDateTime() {
    val dt1: LocalDateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20)
}

/**
 * Duration类主要用于以秒和纳秒衡量时间的长短。
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getDuration() {
    val dateTime1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
    val dateTime2 = LocalDateTime.of(2015, Month.MARCH, 18, 13, 45, 20); // 2015-03-18T13:45
    val d1 = Duration.between(dateTime1, dateTime2)
    val threeMinutes1 = Duration.ofMinutes(3)
}

/**
 * Period类以年、月或者日的方式对多个时间单位建模。
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getPeriod() {
    val localDate1 = LocalDate.of(2014, 3, 8)
    val localDate2 = LocalDate.of(2014, 3, 18)
    val tenDays = Period.between(localDate1, localDate2)
    val tenDays2 = Period.ofDays(10);
    val threeWeeks = Period.ofWeeks(3);
    val twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
}

/**
 * DateTimeFormatter 格式化以及解析日期、时间对象的。
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getDateTimeFormatter() {
    val date = LocalDate.of(2014, 3, 18)
    val s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE) //20140318
    val s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE) //2014-03-18

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val chinaFormatter2 = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
    val s3 = date.format(formatter) //18/03/2014
    val s5 = date.format(chinaFormatter2) //2014年3月18日 星期二

    val date1 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE)
    val date2 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE)
    val date3 = LocalDate.parse("18/03/2014", formatter)
    val date5 = LocalDate.parse("2014年3月18日 星期二", chinaFormatter2)
}

/**
 * Java8中的日期和时间的种类都不包含时区信息。
 * ZoneId 区的处理
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getZoneId() {
    val romeZone = ZoneId.of("Europe/Rome")
    val zoneIds = ZoneId.getAvailableZoneIds()
    for (zone in zoneIds) {
        //共计599个
        println(zone)
    }
}

/**
 *ZonedDateTime： ZoneId 结合 LocalDate、LocalDateTime、Instant
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getZoneDateTime() {
    val romeZone = ZoneId.of("Europe/Rome")
    val date = LocalDate.of(2014, Month.MARCH, 18)
    val zdt1 = date.atStartOfDay(romeZone)

    val dateTime = LocalDateTime.now()
    val zdt2 = dateTime.atZone(romeZone)

    val instant = Instant.now()
    val zdt3 = instant.atZone(romeZone)

    val dateTime2: LocalDateTime = LocalDateTime.of(2018, 7, 21, 18, 46, 0)
    val romeZone2 = ZoneId.systemDefault()
    val instantFromDateTime = dateTime2.atZone(romeZone2).toInstant()
    println(instantFromDateTime.epochSecond)
}

/**
 * LocalDate、LocalDateTime类之间转换中 均可以通过Instant作为中间类完成转换
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getInstant() {
    val now = Instant.now()
    println(now)
}

/**
 * 指定时间转时间戳
 */
fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
    SimpleDateFormat("dd.MM.yyyy").let { formatter ->
        TimeUnit.MICROSECONDS.toSeconds(formatter.parse("$day.$month.$year")?.time ?: 0)
    }