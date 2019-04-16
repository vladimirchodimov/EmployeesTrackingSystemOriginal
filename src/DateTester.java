import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTester {

    public static void main(String[] args) {
        Date date;
        date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(date);
        System.out.println(dateString);
    }
}
