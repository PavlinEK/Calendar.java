import java.util.Scanner;

public class DATE {
    static int dd;
    static int mm;
    static int yyyy;

    static String[] monthShort = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov", "Dec"};
    static String[] monthLong = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    static String[] day = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    static int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static int[] daysOfMonthLeapYear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("CHOOSE FROM BELOW:");
        System.out.println("1. DATE INFO;");
        System.out.println("2. FIND DATE;");
        System.out.println("3. PRINT CALENDAR FOR A MONTH");
        System.out.println("4. PRINT CALENDAR FOR A YEAR;");
        int choice = sc.nextInt();
        switch (choice) {
            case 1 -> dateInfo();
            case 2 -> findDate();
            case 3 -> printCalendarForMonth();
            case 4 -> printCalendarForYear();
        }
    }

    // 1. DATE INFO:
    public static void dateInfo() {
        System.out.println("-DATE INFO-");
        System.out.print("Enter a year: ");
        yyyy = sc.nextInt();
        System.out.print("Enter a month (from 1-12)  : ");
        mm = sc.nextInt();
        if (mm > 0 && mm < 12) {
            System.out.println("Enter day of month:");
            dd = sc.nextByte();
            if (dd > 0 && dd < 31) {
                System.out.println("Choose date format from below:");
                System.out.println("1: dd/MM/yyyy");
                System.out.println("2: MM/dd/yyyy");
                System.out.println("3: dd-MMM-yyyy");

                int format = sc.nextInt();

                formatDate(format);
                System.out.println();
                findDayOfTheWeek();
            } else System.out.println("Wrong input! Please restart!");
        } else System.out.println("Wrong input! Please restart!");
    }

    public static void formatDate(int format) {
        switch (format) {
            case 1 -> System.out.println(dd + "/" + mm + "/" + yyyy);
            case 2 -> System.out.println(mm + "/" + dd + "/" + yyyy);
            case 3 -> System.out.println(dd + "-" + monthShort[mm - 1] + "-" + yyyy);
        }
    }

    public static void findDayOfTheWeek() {
        int[] monthNo = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};
        int j;
        if ((yyyy / 100) % 2 == 0) {
            if ((yyyy / 100) % 4 == 0)
                j = 6;
            else
                j = 2;
        } else {
            if (((yyyy / 100) - 1) % 4 == 0)
                j = 4;
            else
                j = 0;
        }
        int total = (yyyy % 100) + ((yyyy % 100) / 4) + dd
                + monthNo[mm - 1] + j;
        if (isLeapYear(yyyy)) {
            if ((total % 7) > 0)
                System.out.println(day[(total % 7) - 1]);
            else
                System.out.println(day[6]);
        } else
            System.out.println(day[(total % 7)]);
    }

    // 3. PRINT CALENDAR FOR A MONTH
    public static void printCalendarForMonth() {
        System.out.println("-PRINT CALENDAR FOR A MONTH-");
        System.out.print("Enter a year: ");
        yyyy = sc.nextInt();
        System.out.print("Enter a month (from 1-12)  : ");
        mm = sc.nextByte();
        if ((mm > 1 || mm == 1) && mm < 12) {
            printMonth(yyyy, mm);
        } else System.out.println("Wrong input! Please restart!");
    }

    static void printMonth(int year, int month) {
        printMonthTitle(year, month - 1);
        printMonthBody();
    }

    static void printMonthTitle(int year, int month) {
        if (year < 0) {
            System.out.println(monthLong[month] + " " + year + " " + "BC");
        } else if (year > 0) {
            System.out.println(monthLong[month] + " " + year + " " + "AD");
        } else
            System.out.println(monthLong[month] + " " + year);
        System.out.println(" Sun Mon Tue Wed Thu Fri Sat");
    }

    static void printMonthBody() {
        int startDay = getStartDay(yyyy, mm);
        int numberOfDaysInMonth = getNumberOfDaysInMonth(yyyy, mm);
        int i;
        for (i = 0; i < startDay; i++)
            System.out.print("    ");
        for (i = 1; i <= numberOfDaysInMonth; i++) {
            if (i < 10)
                System.out.print("   " + i);
            else
                System.out.print("  " + i);
            if ((i + startDay) % 7 == 0)
                System.out.println();
        }
        System.out.println();
    }

    static int getStartDay(int year, int month) {
        int startDay0 = 3;
        int totalNumberOfDays = getTotalNumberOfDays(year, month);
        return (totalNumberOfDays + startDay0) % 7;
    }

    static int getTotalNumberOfDays(int year, int month) {
        int total = 3;
        for (int i = 0; i < year; i++)
            if (isLeapYear(i))
                total = total + 366;
            else
                total = total + 365;
        for (int i = 1; i < month; i++)
            total = total + getNumberOfDaysInMonth(year, i);
        return total;
    }

    static int getNumberOfDaysInMonth(int year, int month) {
        if (isLeapYear(year)) {
            return daysOfMonthLeapYear[month - 1];
        } else return daysOfMonth[month - 1];
    }

    static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    // 2. FIND DATE
    public static double findNthWeekDayOfTheMonth(int nth, int weekDay, int month, int year) {
        if (nth > 0)
            return (nth - 1) * 7 + 1 + (7 + weekDay - findDayOfTheWeek((nth - 1) * 7 + 1, month, year)) % 7;
        int days;
        if (isLeapYear(year)) {
            days = daysOfMonthLeapYear[month - 1];
        } else {
            days = daysOfMonth[month - 1];
        }
        return (days - (findDayOfTheWeek(days, month, year) - weekDay + 7) % 7);
    }

    public static double findDayOfTheWeek(int day, int month, int year) {
        double a = Math.floor((14 - month) / 12);
        double y = year - a;
        double m = month + 12 * a - 2;
        double d =
                (day + y + Math.floor(y / 4) - Math.floor(y / 100) + Math.floor(y / 400) + Math.floor((31 * m) / 12)) %
                        7;
        return d + 1;
    }

    public static void findDate() {
        System.out.println("-FIND DATE-");
        System.out.println("Choose a weekday from 1-7:");
        System.out.println("1 - Monday");
        System.out.println("7 - Sunday");
        int weekDay = sc.nextInt();
        System.out.println("Choose number of occurrences from 1-4:");
        int nth = sc.nextInt();
        System.out.println("Choose a month from 1-12:");
        int month = sc.nextInt();
        System.out.println("Choose year:");
        int year = sc.nextInt();
        int nthDay = (int) findNthWeekDayOfTheMonth(nth, weekDay, month, year);
        System.out.println(" DATE: " + nthDay + "." + month + "." + year);
    }

    // 4. PRINT CALENDAR FOR A YEAR
    public static void printCalendarForYear() {
        System.out.println("-PRINT CALENDAR FOR A YEAR-");
        System.out.println("Enter the weekday that the year starts: ");
        System.out.println("1 - Monday");
        System.out.println("7 - Sunday");
        int startDay = sc.nextInt();
        int dayCounter = startDay;
        int nbrOfDays;
        String monthX;
        for (int month = 0; month < 12; month++) {

            monthX = monthLong[month];
            nbrOfDays = daysOfMonthLeapYear[month];

            System.out.println(monthX);
            System.out.println("----------------------------");
            System.out.println("Sun Mon Tue Wed Thu Fri Sat");
            for (int space = 1; space <= startDay; space++) {
                System.out.printf("%4s", "    ");
            }
            for (int i = 1; i <= nbrOfDays; i++) {
                dayCounter++;
                if (dayCounter % 7 == 0)
                    System.out.printf("%-4d\n", i);
                else
                    System.out.printf("%-4d", i);
            }
            startDay = (startDay + nbrOfDays) % 7;
            System.out.println();
        }
    }
}