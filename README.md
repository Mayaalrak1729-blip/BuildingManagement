# Building Rent Management System (Java, console)

Run:  `javac -d out $(find src -name '*.java')`  then  `java -cp out main.MainApp`
(Data is saved in the `data/` folder: CSV files, receipts, backup.)

## 8 packages
| Package | Classes | Owner |
|---|---|---|
| interfaces | Payable, CsvConvertible | Member 1 |
| model | Person (abstract), Tenant, Property (abstract), Flat, Shop, Building, Payment | Member 1 |
| exception | InvalidInputException, PropertyNotFoundException, TenantNotFoundException, PropertyNotAvailableException, PaymentException | Member 1 |
| util | InputHelper, Validator, DateUtil | Member 2 |
| storage | FileStorage | Member 2 |
| thread | AutoSaveThread, RentReminderTask | Member 2 |
| service | PropertyService, TenantService, RentService | Member 3 |
| main | MainApp | Member 3 |

## Where each required topic is used
| Topic | Where |
|---|---|
| Classes, objects, constructor | every class in `model`; `Tenant` has two constructors |
| Array of objects | `Building` (Property[]), `TenantService` (Tenant[]) |
| Encapsulation, packages, access modifiers | private fields + getters/setters; 8 packages; `Building.getAll()` returns a copy |
| Method overloading | `Building.find(int)/find(String)`, `RentService.calculateTotal(...)` x3, `collectRent(...)` x2, `Validator.isPositive(int/double)` |
| Method overriding | `calculateMonthlyRent()` in Flat/Shop, `getRole()` in Tenant, `toString()` |
| Abstract class | `Person`, `Property` |
| Interface | `Payable`, `CsvConvertible` |
| In-built exceptions | NumberFormatException, ArrayIndexOutOfBoundsException, DateTimeParseException, InterruptedException, IOException, NoSuchElementException |
| User defined exceptions | package `exception` |
| Multithreading | `AutoSaveThread` (extends Thread), `RentReminderTask` (implements Runnable), `synchronized` methods |
| Files and streams | `FileStorage`: BufferedReader/Writer, PrintWriter (receipts), FileInput/OutputStream (backup) |

## Team plan (3 members)
- Member 1: interfaces, model, exception  (do first, share by day 2; others depend on it)
- Member 2: util, storage, thread
- Member 3: service, main, final testing and report/PPT

## JavaFX GUI (new)
A desktop interface built on the same model/service/storage classes. The console version (`main.MainApp`) still works.

| Page | What you can do |
|---|---|
| Dashboard | Totals for the selected month, occupancy bar, pending dues, send reminders (background thread), load sample data |
| Properties | Add flat/shop/office (live rent preview), search/filter, allot, vacate, edit base rent |
| Tenants | Add, search, remove |
| Rent | Collect rent (part payment, late fine preview, receipt file), payment history, pending dues, rent calculator |
| Workers | Add, remove, salary totals |
| Owner & Data | Edit owner, save now, backup |

The month in the top bar controls dues, summaries and rent collection. Data is saved after every change and by the 30 s AutoSaveThread.

**Run** (Java 17+):
- With Maven: `mvn javafx:run` (downloads JavaFX automatically)
- Without Maven: download the JavaFX SDK from https://openjfx.io, then `PATH_TO_FX=/path/to/javafx-sdk/lib ./run.sh` (Windows: `set PATH_TO_FX=C:\javafx-sdk\lib` then `run.bat`)
- IDE (IntelliJ/Eclipse): add the JavaFX SDK as a library, mark `src` as sources and `resources` as resources, main class `gui.Launcher`.

New package `gui`: Launcher, RentApp (window), AppContext (shared services), Page + DashboardPage/PropertiesPage/PeoplePages/RentPage/SettingsPage, Ui (helpers).
