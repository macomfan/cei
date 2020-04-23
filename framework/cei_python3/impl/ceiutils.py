import time

class CEIUtils:

    @staticmethod
    def get_now(format: 'str'):
        """
        Convert current date/time in UTC to string

        :param format The format of the output string, following below rules:
        %Y The 4 characters year code. like 2019.
        %M The 2 characters month in year. range is 01 - 12.
        %D The 2 characters day in month. range is 01 - 31.
        %h The 2 characters hour (24H) in day. range is 00 - 23.
        %m The 2 characters minute in hour. range is 00- 59.
        %s The 2 characters second in minute. range is 00 - 59.
        %ms The 3 characters millisecond. range is 000 - 999.
        Unix_s The unix format timestamp based on second.
        Unix_ms The unix format timestamp based on millisecond. (default)

        :return The time string.
        """
        if format is None or format == "":
            return str(round(time.time() * 1000))
        if format == "Unix_s":
            return str(round(time.time()))
        elif format == "Unix_ms":
            return str(round(time.time() * 1000))
        else:
            items = format.split("%")
            formatString = ""
            for i in range(0, len(items)):
                if (i == 0):
                    formatString = items[i]

        pass
