import java.util.InputMismatchException;
import java.util.Scanner;

public class DATE {

    private static final String[] monthShort = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static final String[] monthLong = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    private static final String[] day = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] daysOfMonthLeapYear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final Scanner sc = new Scanner(System.in);

    private static int dd;
    private static int mm;
    private static int yyyy;


    public static void main(String[] args) {
        boolean waitingForCommands = true;
        do {
            printMenu();
            switch (sc.nextLine()) {
                case "1" -> dateInfo();
                case "2" -> findDate();
                case "3" -> printCalendarForMonth();
                case "4" -> printCalendarForYear();
                case "5" -> waitingForCommands = false;
            }
        } while (waitingForCommands);
    }

    private static int chooseCommand() {
        String command;
        while (!(command = sc.nextLine()).equals("1") && !command.equals("2") && !command.equals("3")
                && !command.equals("4") && !command.equals("5")) {
            System.out.println("Invalid command! Try again!");
        }
        return Integer.parseInt(command);
    }

    private static void printMenu() {
        System.out.println("CHOOSE FROM BELOW:");
        System.out.println("1. DATE INFO");
        System.out.println("2. FIND DATE");
        System.out.println("3. PRINT CALENDAR FOR A MONTH");
        System.out.println("4. PRINT CALENDAR FOR A YEAR");
        System.out.println("5. EXIT FROM THE PROGRAM");
    }

    // 1. DATE INFO:
    private static void dateInfo() {
        System.out.println("-DATE INFO-");
        System.out.print("Enter a year: ");
        validateYear();
        System.out.print("Enter a month (from 1-12)  : ");
        validateMonth();
        System.out.println("Enter day of month:");
        validateDay();
        System.out.println("Choose date format from below:");
        System.out.println("1: dd/MM/yyyy");
        System.out.println("2: MM/dd/yyyy");
        System.out.println("3: dd-MMM-yyyy");

        int format = validateFormat();

        formatDate(format);
        System.out.println();
        findDayOfTheWeek();
    }

    private static void validateYear() {
        do {
            try {
                yyyy = Integer.parseInt(sc.nextLine());
                return;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid year! Try again!");
            }
        } while (true);
    }

    private static void validateMonth() {
        do {
            try {
                mm = Integer.parseInt(sc.nextLine());
                if (mm < 1 || mm > 12) {
                    System.out.println("Invalid month! Try again!");
                    continue;
                }
                return;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid month! Try again!");
            }
        } while (true);
    }

    private static void validateDay() {
        do {
            try {
                dd = Integer.parseInt(sc.nextLine());
                if (dd < 1 || dd > 31) {
                    System.out.println("Invalid day! Try again!");
                    continue;
                }
                return;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid day! Try again!");
            }
        } while (true);
    }

    private static int validateFormat() {
        int format;
        do {
            try {
                format = Integer.parseInt(sc.nextLine());
                if (format < 1 || format > 3) {
                    System.out.println("Invalid format! Try again!");
                    continue;
                }
                return format;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid format! Try again!");
            }
        } while (true);
    }

    private static void formatDate(int format) {
        switch (format) {
            case 1 -> System.out.println(dd + "/" + mm + "/" + yyyy);
            case 2 -> System.out.println(mm + "/" + dd + "/" + yyyy);
            case 3 -> System.out.println(dd + "-" + monthShort[mm - 1] + "-" + yyyy);
        }
    }

    private static void findDayOfTheWeek() {
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
        if (isLeapYear()) {
            if ((total % 7) > 0)
                System.out.println(day[(total % 7) - 1]);
            else
                System.out.println(day[6]);
        } else
            System.out.println(day[(total % 7)]);
    }

    // 3. PRINT CALENDAR FOR A MONTH
    private static void printCalendarForMonth() {
        System.out.println("-PRINT CALENDAR FOR A MONTH-");
        System.out.print("Enter a year: ");
        validateYear();
        System.out.print("Enter a month (from 1-12)  : ");
        validateMonth();
        printMonth(yyyy,mm);
    }

    private static void printMonth(int year, int month) {
        printMonthTitle(year, month);
        printMonthBody(month);
    }

    private static void printMonthTitle(int year, int month) {
        if (year < 0) {
            System.out.println(monthLong[month - 1] + " " + year + " " + "BC");
        } else if (year > 0) {
            System.out.println(monthLong[month - 1] + " " + year + " " + "AD");
        } else
            System.out.println(monthLong[month - 1] + " " + year);
        System.out.println(" Sun Mon Tue Wed Thu Fri Sat");
    }

    private static void printMonthBody(int month) {
        int startDay = getStartDay();
        int numberOfDaysInMonth = getNumberOfDaysInMonth(month);
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

    private static int getStartDay() {
        int startDay0 = 3;
        int totalNumberOfDays = getTotalNumberOfDays();
        return (totalNumberOfDays + startDay0) % 7;
    }

    private static int getTotalNumberOfDays() {
        int total = 4;
        for (int i = 0; i < yyyy; i++)
            if (isLeapYear())
                total = total + 366;
            else
                total = total + 365;
        for (int i = 1; i < mm; i++)
            total = total + getNumberOfDaysInMonth(i);
        return total;
    }

    private static int getNumberOfDaysInMonth(int month) {
        if (isLeapYear()) {
            return daysOfMonthLeapYear[month - 1];
        }
        return daysOfMonth[month - 1];
    }

    private static boolean isLeapYear() {
        return yyyy % 4 == 0 && yyyy % 100 != 0 || yyyy % 400 == 0;
    }

    // 2. FIND DATE
    private static double findNthWeekDayOfTheMonth(int nth) {
        if (nth > 0)
            return (nth - 1) * 7 + 1 + (7 + dd - findDayOfTheWeek((nth - 1) * 7 + 1, mm, yyyy)) % 7;
        int days;
        if (isLeapYear()) {
            days = daysOfMonthLeapYear[mm];
        } else {
            days = daysOfMonth[mm];
        }
        return (days - (findDayOfTheWeek(days, mm, yyyy) - dd + 7) % 7);
    }

    private static double findDayOfTheWeek(int day, int month, int year) {
        double a = Math.floor((14 - month) / 12);
        double y = year - a;
        double m = month + 12 * a - 2;
        double d =
                (day + y + Math.floor(y / 4) - Math.floor(y / 100) + Math.floor(y / 400) + Math.floor((31 * m) / 12)) %
                        7;
        return d + 1;
    }

    private static void findDate() {
        System.out.println("-FIND DATE-");
        System.out.println("Choose a weekday from 1-7:");
        System.out.println("1 - Monday");
        System.out.println("7 - Sunday");
        validateDayOfWeek();
        System.out.println("Choose number of occurrences from 1-4:");
        int nth = validateNumberOfOccurrences();
        System.out.println("Choose a month from 1-12:");
        validateMonth();
        System.out.println("Choose year:");
        validateYear();
        int nthDay = (int) findNthWeekDayOfTheMonth(nth);
        System.out.println(" DATE: " + nthDay + "." + mm + "." + yyyy);
    }

    private static int validateNumberOfOccurrences() {
        int numberOfOccurrences;
        do {
            try {
                numberOfOccurrences = Integer.parseInt(sc.nextLine());
                if (numberOfOccurrences < 1 || numberOfOccurrences > 5) {
                    System.out.println("Invalid number of occurrences! Try again!");
                    continue;
                }
                return numberOfOccurrences;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid number of occurrences! Try again!");
            }
        } while (true);
    }

    // 4. PRINT CALENDAR FOR A YEAR
    private static void printCalendarForYear() {
        System.out.println("-PRINT CALENDAR FOR A YEAR-");
        System.out.println("Enter the weekday that the year starts: ");
        System.out.println("1 - Monday");
        System.out.println("7 - Sunday");
        int day = validateDayOfWeek();
        int dayCounter = day;
        int nbrOfDays;
        String monthX;
        for (int month = 0; month < 12; month++) {

            monthX = monthLong[month];
            nbrOfDays = daysOfMonthLeapYear[month];

            System.out.printf("%17s  \n", monthX);
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

    private static int validateDayOfWeek() {
        int dayOfWeek;
        do {
            try {
                dayOfWeek = Integer.parseInt(sc.nextLine());
                if (dayOfWeek < 1 || dayOfWeek > 7) {
                    System.out.println("Invalid day of week! Try again!");
                    continue;
                }
                return dayOfWeek;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid day of week! Try again!");
            }
        } while (true);
    }
}

