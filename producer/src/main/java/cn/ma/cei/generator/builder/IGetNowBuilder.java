package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;

/**
 *
 * Convert current date/time in UTC to string
 * %Y The 4 characters year code. like 2019.
 * %M The 2 characters month in year. range is 01 - 12.
 * %D The 2 characters day in month. range is 01 - 31.
 * %h The 2 characters hour (24H) in day. range is 00 - 23.
 * %m The 2 characters minute in hour. range is 00- 59.
 * %s The 2 characters second in minute. range is 00 - 59.
 * %ms The 3 characters millisecond. range is 000 - 999.
 * Unix_s The unix format timestamp based on second.
 * Unix_ms The unix format timestamp based on millisecond. (default)
 *
 */
public interface IGetNowBuilder {
    String convertToStringFormat(String format);

    void getNow(Variable output, Variable format);
}
