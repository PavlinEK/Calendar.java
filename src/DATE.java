import java.util.Scanner;

public class DATE {
    static int dd;
    static int mm;
    static int yyyy;

    static String[] monthShort = {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov", "Dec"};
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

    public static void formatDate(int format) {
        switch (format) {
            case 1 -> System.out.println(dd + "/" + mm + "/" + yyyy);
            case 2 -> System.out.println(mm + "/" + dd + "/" + yyyy);
            case 3 -> System.out.println(dd + "-" + monthShort[mm] + "-" + yyyy);
        }
    }

    // 1. DATE INFO:
    public static void findDayOfTheWeek() {
        boolean flag_for_leap = isLeapYear(yyyy);

        String[] day = {"Sunday", "Monday", "Tuesday",
                "Wednesday", "Thursday", "Friday",
                "Saturday"};
        int[] m_no = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};
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
        /*THE FINAL FORMULA*/
        int total = (yyyy % 100) + ((yyyy % 100) / 4) + dd
                + m_no[mm - 1] + j;
        if (flag_for_leap) {
            if ((total % 7) > 0)
                System.out.println(day[(total % 7) - 1]);
            else
                System.out.println(day[6]);
        } else
            System.out.println(day[(total % 7)]);
    }

    // 3. PRINT CALENDAR FOR A MONTH
    static void printMonth(int year, int month) {
        printMonthTitle(year, month);
        printMonthBody(year, month);
    }

    static void printMonthTitle(int year, int month) {
        if (year < 0) {
            System.out.println(getMonthName(month) + " " + year + " " + "BC");
        } else if (year > 0) {
            System.out.println(getMonthName(month) + " " + year + " " + "AD");
        } else
            System.out.println(getMonthName(month) + " " + year);
        System.out.println(" Sun Mon Tue Wed Thu Fri Sat");
    }

    static String getMonthName(int month) {
        return switch (month) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> null;
        };
    }

    static void printMonthBody(int year, int month) {
        int startDay = getStartDay(year, month);
        int numberOfDaysInMonth = getNumberOfDaysInMonth(year, month);
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
        int startDay0 = 0;
        int totalNumberOfDays = getTotalNumberOfDays(year, month);
        return (totalNumberOfDays + startDay0) % 7;
    }

    static int getTotalNumberOfDays(int year, int month) {
        int total = 5;
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
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
        }
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        if (month == 2)
            return isLeapYear(year) ? 29 : 28;
        return 0;
    }

    static boolean isLeapYear(int year) {
        return year % 4 == 0;
    }

    // 1. DATE INFO:
    public static void dateInfo() {
        System.out.println("-DATE INFO-");
        System.out.print("Enter a year: ");
        yyyy = sc.nextInt();
        System.out.print("Enter a month (from 1-12)  : ");
        mm = sc.nextByte();
        if (mm > 0 && mm < 12) {
            System.out.println("Enter date:");
            dd = sc.nextByte();
//                    can be better!!!
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

    // 2. FIND DATE
    public static double findNthWeekDayOfTheMonth(int nth, int weekDay, int month, int year) {
        int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] daysOfMonthLeapYear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (nth > 0)
            return (nth - 1) * 7 + 1 + (7 + weekDay - findDayOfTheWeek((nth - 1) * 7 + 1, month, year)) % 7;
        int days = 0;
        if (isLeapYear(year)) {
            days = daysOfMonthLeapYear[month - 1];
        } else {
            days = daysOfMonth[month - 1];
        }
        return (days - (findDayOfTheWeek(days, month, year) - weekDay + 7) % 7);
    }

    /**
     * This method returns the Day Of the Week ////How??!!>
     */

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
        Scanner sc = new Scanner(System.in);
        System.out.println("-FIND DATE-");
        System.out.println("Choose an occurence from 1-4:");
        int nth = sc.nextInt();
        System.out.println("Choose a weekday from 1-7:");
        int weekDay = sc.nextInt();
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
        System.out.print("Enter a year: ");
        yyyy = sc.nextInt();
        System.out.println("Enter the weekday that the year starts: ");
        int day = sc.nextInt();
        int dayCounter = day;
        int nbrOfDays = 0;
        String monthx = "";
        for (int month = 1; month <= 12; month++) {

            switch (month) {
                case 1 -> {
                    monthx = "January";
                    nbrOfDays = 31;
                }
                case 2 -> {
                    monthx = "February";
                    if (yyyy % 4 == 0 && yyyy % 100 != 0 || yyyy % 400 == 0) {
                        nbrOfDays = 29;
                    } else {
                        nbrOfDays = 28;
                    }
                }
                case 3 -> {
                    monthx = "March";
                    nbrOfDays = 31;
                }
                case 4 -> {
                    monthx = "April";
                    nbrOfDays = 30;
                }
                case 5 -> {
                    monthx = "May";
                    nbrOfDays = 31;
                }
                case 6 -> {
                    monthx = "June";
                    nbrOfDays = 30;
                }
                case 7 -> {
                    monthx = "July";
                    nbrOfDays = 31;
                }
                case 8 -> {
                    monthx = "August";
                    nbrOfDays = 31;
                }
                case 9 -> {
                    monthx = "September";
                    nbrOfDays = 30;
                }
                case 10 -> {
                    monthx = "October";
                    nbrOfDays = 31;
                }
                case 11 -> {
                    monthx = "November";
                    nbrOfDays = 30;
                }
                case 12 -> {
                    monthx = "December";
                    nbrOfDays = 31;
                }
            }

            System.out.printf("%15s %d  \n", monthx, yyyy);
            System.out.println("----------------------------");
            System.out.printf("%s %s %s %s %s %s %s\n ", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");

            for (int space = 1; space <= day; space++) {
                System.out.printf("%4s", "    ");
            }
            for (int i = 1; i <= nbrOfDays; i++) {
                dayCounter++;
                if (dayCounter % 7 == 0)
                    System.out.printf("%- 4d\n", i);
                else
                    System.out.printf("%-4d", i);
            }
            day = (day + nbrOfDays) % 7;

            System.out.println();
        }
    }
}


