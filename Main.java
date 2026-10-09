import java.util.Scanner;

// ---------- Abstract parent class (Abstraction + Encapsulation) ----------
abstract class Person {
    private int id;
    private String name;
    private String phone;

    Person(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    int getId()        { return id; }
    String getName()   { return name; }
    String getPhone()  { return phone; }

    // every child must define how it displays itself (Polymorphism)
    abstract void displayDetails();
}

// ---------- Owner (Inheritance) ----------
class Owner extends Person {
    private String buildingName;
    private String address;

    Owner(int id, String name, String phone, String buildingName, String address) {
        super(id, name, phone);
        this.buildingName = buildingName;
        this.address = address;
    }

    String getBuildingName() { return buildingName; }

    @Override
    void displayDetails() {
        System.out.println("Owner ID  : " + getId());
        System.out.println("Name      : " + getName());
        System.out.println("Phone     : " + getPhone());
        System.out.println("Building  : " + buildingName);
        System.out.println("Address   : " + address);
        System.out.println("-----------------------------------");
    }
}

// ---------- Tenant (Inheritance) ----------
class Tenant extends Person {
    private String flatNo;
    private double monthlyRent;
    private double paidAmount;      // paid for the current month

    Tenant(int id, String name, String phone, String flatNo, double monthlyRent, double paidAmount) {
        super(id, name, phone);
        this.flatNo = flatNo;
        this.monthlyRent = monthlyRent;
        this.paidAmount = paidAmount;
    }

    String getFlatNo()       { return flatNo; }
    double getMonthlyRent()  { return monthlyRent; }
    double getPaidAmount()   { return paidAmount; }
    double getDue()          { return monthlyRent - paidAmount; }

    void payRent(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
        } else if (amount > getDue()) {
            System.out.println("Amount is more than the due (" + getDue() + "). Payment rejected.");
        } else {
            paidAmount += amount;
            System.out.println("Payment of Rs." + amount + " received from " + getName());
        }
    }

    @Override
    void displayDetails() {
        System.out.println("Tenant ID : " + getId());
        System.out.println("Name      : " + getName());
        System.out.println("Phone     : " + getPhone());
        System.out.println("Flat No   : " + flatNo);
        System.out.println("Rent      : Rs." + monthlyRent);
        System.out.println("Paid      : Rs." + paidAmount);
        System.out.println("Due       : Rs." + getDue());
        System.out.println("-----------------------------------");
    }
}

// ---------- Worker (Inheritance) ----------
class Worker extends Person {
    private String role;
    private double monthlySalary;

    Worker(int id, String name, String phone, String role, double monthlySalary) {
        super(id, name, phone);
        this.role = role;
        this.monthlySalary = monthlySalary;
    }

    double getMonthlySalary() { return monthlySalary; }

    @Override
    void displayDetails() {
        System.out.println("Worker ID : " + getId());
        System.out.println("Name      : " + getName());
        System.out.println("Phone     : " + getPhone());
        System.out.println("Role      : " + role);
        System.out.println("Salary    : Rs." + monthlySalary);
        System.out.println("-----------------------------------");
    }
}

// ---------- Office (rentable commercial unit) ----------
class Office {
    private String officeNo;
    private int floor;
    private String companyName;     // "VACANT" if nobody has rented it
    private double monthlyRent;
    private double paidAmount;

    Office(String officeNo, int floor, String companyName, double monthlyRent, double paidAmount) {
        this.officeNo = officeNo;
        this.floor = floor;
        this.companyName = companyName;
        this.monthlyRent = monthlyRent;
        this.paidAmount = paidAmount;
    }

    String getOfficeNo()     { return officeNo; }
    double getMonthlyRent()  { return monthlyRent; }
    double getPaidAmount()   { return paidAmount; }
    boolean isOccupied()     { return !companyName.equals("VACANT"); }
    double getDue()          { return isOccupied() ? monthlyRent - paidAmount : 0; }

    void payRent(double amount) {
        if (!isOccupied()) {
            System.out.println("This office is vacant.");
        } else if (amount <= 0 || amount > getDue()) {
            System.out.println("Invalid amount. Current due is Rs." + getDue());
        } else {
            paidAmount += amount;
            System.out.println("Payment of Rs." + amount + " received from " + companyName);
        }
    }

    void displayDetails() {
        System.out.println("Office No : " + officeNo);
        System.out.println("Floor     : " + floor);
        System.out.println("Company   : " + companyName);
        System.out.println("Rent      : Rs." + monthlyRent);
        System.out.println("Paid      : Rs." + paidAmount);
        System.out.println("Due       : Rs." + getDue());
        System.out.println("-----------------------------------");
    }
}

// ---------- Main class ----------
public class BuildingRentSystem {

    // Data stored permanently in the code (arrays)
    static Owner[] owners = {
        new Owner(1, "Rajesh Patil", "9876543210", "Sai Heights", "FC Road, Pune")
    };

    static Tenant[] tenants = {
        new Tenant(101, "Amit Sharma",  "9811111111", "A-101", 12000, 12000),
        new Tenant(102, "Neha Joshi",   "9822222222", "A-102", 11000, 5000),
        new Tenant(103, "Rahul Verma",  "9833333333", "B-201", 15000, 0),
        new Tenant(104, "Sneha Kulkarni","9844444444", "B-202", 14000, 14000)
    };

    static Office[] offices = {
        new Office("O-1", 1, "TechNova Solutions", 40000, 40000),
        new Office("O-2", 1, "BrightPath Consulting", 35000, 10000),
        new Office("O-3", 2, "VACANT", 30000, 0)
    };

    static Worker[] workers = {
        new Worker(201, "Ramesh Kale",  "9900000001", "Security Guard", 12000),
        new Worker(202, "Sunita More",  "9900000002", "Cleaner",         9000),
        new Worker(203, "Prakash Jadhav","9900000003", "Electrician",   15000),
        new Worker(204, "Mahesh Pawar", "9900000004", "Plumber",        14000)
    };

    // ---- helper methods ----
    static void showOwners()  { for (Owner o : owners)   o.displayDetails(); }
    static void showTenants() { for (Tenant t : tenants) t.displayDetails(); }
    static void showOffices() { for (Office o : offices) o.displayDetails(); }
    static void showWorkers() { for (Worker w : workers) w.displayDetails(); }

    static void collectTenantRent(Scanner sc) {
        System.out.print("Enter Tenant ID: ");
        int id = sc.nextInt();
        for (Tenant t : tenants) {
            if (t.getId() == id) {
                System.out.println("Due: Rs." + t.getDue());
                System.out.print("Enter amount to pay: ");
                t.payRent(sc.nextDouble());
                return;
            }
        }
        System.out.println("Tenant not found.");
    }

    static void collectOfficeRent(Scanner sc) {
        System.out.print("Enter Office No (e.g. O-1): ");
        String no = sc.next();
        for (Office o : offices) {
            if (o.getOfficeNo().equalsIgnoreCase(no)) {
                System.out.println("Due: Rs." + o.getDue());
                System.out.print("Enter amount to pay: ");
                o.payRent(sc.nextDouble());
                return;
            }
        }
        System.out.println("Office not found.");
    }

    static void showPendingDues() {
        System.out.println("---- Pending Dues ----");
        boolean any = false;
        for (Tenant t : tenants) {
            if (t.getDue() > 0) {
                System.out.println("Tenant " + t.getName() + " (" + t.getFlatNo() + ") owes Rs." + t.getDue());
                any = true;
            }
        }
        for (Office o : offices) {
            if (o.getDue() > 0) {
                System.out.println("Office " + o.getOfficeNo() + " owes Rs." + o.getDue());
                any = true;
            }
        }
        if (!any) System.out.println("No pending dues.");
    }

    static void showMonthlySummary() {
        double expected = 0, collected = 0, salaries = 0;

        for (Tenant t : tenants) {
            expected  += t.getMonthlyRent();
            collected += t.getPaidAmount();
        }
        for (Office o : offices) {
            if (o.isOccupied()) {
                expected  += o.getMonthlyRent();
                collected += o.getPaidAmount();
            }
        }
        for (Worker w : workers) {
            salaries += w.getMonthlySalary();
        }

        System.out.println("---- Monthly Summary ----");
        System.out.println("Rent expected   : Rs." + expected);
        System.out.println("Rent collected  : Rs." + collected);
        System.out.println("Rent pending    : Rs." + (expected - collected));
        System.out.println("Worker salaries : Rs." + salaries);
        System.out.println("Net after salary: Rs." + (collected - salaries));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("===== " + owners[0].getBuildingName() + " : Rent Management System =====");

        do {
            System.out.println("\n1. View Owner");
            System.out.println("2. View Tenants");
            System.out.println("3. View Offices");
            System.out.println("4. View Workers");
            System.out.println("5. Collect Tenant Rent");
            System.out.println("6. Collect Office Rent");
            System.out.println("7. Show Pending Dues");
            System.out.println("8. Monthly Summary");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: showOwners();          break;
                case 2: showTenants();         break;
                case 3: showOffices();         break;
                case 4: showWorkers();         break;
                case 5: collectTenantRent(sc); break;
                case 6: collectOfficeRent(sc); break;
                case 7: showPendingDues();     break;
                case 8: showMonthlySummary();  break;
                case 0: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);

        sc.close();
    }
}
