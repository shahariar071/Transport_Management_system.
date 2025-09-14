import java.util.*;

public class DataStore {

    public static final List<Bus> buses = new ArrayList<>();
    public static final List<Driver> drivers = new ArrayList<>();
    public static final List<StudentRecord> students = new ArrayList<>(); // loaded from StudentStore on startup
    public static final List<FeePayment> feePayments = new ArrayList<>();
    public static final List<RequestComplaint> requests = new ArrayList<>();
    public static final List<SeatBooking> bookings = new ArrayList<>();


    public static class Bus {
        public String busNumber;
        public String from;
        public String to;
        public String time;
        public String assignedDriverName = "";

        public Bus(String busNumber, String from, String to, String time) {
            this.busNumber = busNumber;
            this.from = from;
            this.to = to;
            this.time = time;
        }
    }

    public static class Driver {
        public String name;
        public String license;
        public String phone;

        public Driver(String name, String license, String phone) {
            this.name = name;
            this.license = license;
            this.phone = phone;
        }
    }

    public static class StudentRecord {
        public String id;
        public String name;

        public StudentRecord(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static class FeePayment {
        public String studentId;
        public String studentName;
        public int months;
        public int amount;
        public Date date;

        public FeePayment(String sid, String sname, int months, int amount) {
            this.studentId = sid; this.studentName = sname; this.months = months; this.amount = amount; this.date = new Date();
        }
    }

    public static class RequestComplaint {
        public String studentId;
        public String studentName;
        public String text;
        public Date date;

        public RequestComplaint(String sid, String sname, String text) {
            this.studentId = sid; this.studentName = sname; this.text = text; this.date = new Date();
        }
    }

    public static class SeatBooking {
        public String busNumber;
        public int seatNumber; // 1..36
        public String studentId;
        public String studentName;

        public SeatBooking(String busNumber, int seatNumber, String studentId, String studentName) {
            this.busNumber = busNumber; this.seatNumber = seatNumber; this.studentId = studentId; this.studentName = studentName;
        }
    }


    public static Bus findBusByNumber(String busNo) {
        for (Bus b : buses) if (b.busNumber.equals(busNo)) return b;
        return null;
    }

    public static StudentRecord findStudentById(String id) {
        for (StudentRecord s : students) if (s.id.equals(id)) return s;
        return null;
    }
}
