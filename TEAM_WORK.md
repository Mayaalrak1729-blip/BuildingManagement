# Building Rent Management System: who does what

This project is your original code (Owner, Worker, Office, partial payments, monthly summary)
merged with the earlier package-based project. All code is in `src/`, 8 packages, 29 files.
Each member WRITES their own files from scratch (type them, don't copy) using the reference code here.

Run:  javac -d out $(find src -name '*.java')   then   java -cp out main.MainApp
Tip: pick menu 24 (Load sample data) to see the same data as your original program.

## HARSH (easy): the simple building blocks. Do these FIRST (2 days), others need them.
| Package | File | What to write |
|---|---|---|
| interfaces | Payable | interface with constants DUE_DAY, LATE_FINE_PERCENT and `calculateMonthlyRent()` |
| interfaces | CsvConvertible | interface with `String toCsv()` |
| model | Person | abstract class: private id, name, phone, constructor, getters/setters, abstract `getRole()`, `displayInfo()` |
| model | Property | abstract class implementing Payable + CsvConvertible: id, number, baseRent, occupied, tenantId, `allot()`, `vacate()`, abstract getType/getExtraInfo/getExtraValue, `toCsv()`, `toString()` |
| model | Flat, Shop, Office | extend Property; each overrides `calculateMonthlyRent()` (Flat: +500 per BHK, Shop: +10 per sqft, Office: +500 per floor) |
| model | Tenant, Owner, Worker | extend Person; override `getRole()`; Tenant has TWO constructors (overloading); Worker overrides `displayInfo()` and has `getYearlySalary()` |
| model | Payment | id, tenantId, propertyId, month, rent, fine, date, `getTotal()`, `toCsv()`, `toString()` |
| util | Validator | `isValidName`, `isValidPhone`, and overloaded `isPositive(int)` / `isPositive(double)` |
| util | DateUtil | `today()`, `currentMonth()`, `currentDay()`, `isValidMonth()` (catch DateTimeParseException) |

Topics Harsh covers: classes, constructors, encapsulation, abstract class, interface, overriding, overloading.

## MAYANK (medium): exceptions, input and the services that keep arrays
| Package | File | What to write |
|---|---|---|
| exception | InvalidInputException, PropertyNotFoundException, TenantNotFoundException, WorkerNotFoundException, PropertyNotAvailableException, PaymentException | 6 classes extending Exception, each calls `super(message)` |
| model | Building | name, address, owner, `Property[]` with growth using `Arrays.copyOf`; `addProperty`, `getAll` (returns a copy), overloaded `find(int)` / `find(String)` |
| util | InputHelper | Scanner; `readString`, `readInt`, `readDouble` that catch NumberFormatException and ask again |
| service | PropertyService | addFlat/addShop/addOffice with checks, `getById` (throws), `getVacant`, `allot` (throws PropertyNotAvailableException), `vacate`, `isTenantAllotted`, `addLoaded` |
| service | TenantService | `Tenant[]` array: add (validate), getById (throws), searchByName, remove (shift array left), addLoaded |
| service | WorkerService | `Worker[]` array: add, getById (throws), remove, `totalSalaries()`, addLoaded |

Topics Mayank covers: array of objects, built-in exceptions, user-defined exceptions, access modifiers and data hiding.
Needs from Harsh: model classes and Validator (agree on names first).

## HARIOM (hard): money logic, files, threads and the main menu
| Package | File | What to write |
|---|---|---|
| service | RentService | overloaded `calculateTotal` (3 versions); `getPaidRent`, `getDue`; overloaded `collectRent` (with/without day) that allows PART payment, rejects over-payment, adds 5% fine after the 10th; `getHistory`, `getPending`, `totalExpected`, `totalRentCollected`, `totalFines`, `addLoaded` (all mutators `synchronized`) |
| storage | FileStorage | `saveAll` (BufferedWriter), `loadAll` (BufferedReader, catches NumberFormatException and ArrayIndexOutOfBoundsException per line), `writeReceipt` (PrintWriter), `backup` (FileInputStream/FileOutputStream), files: properties, tenants, workers, owner, payments |
| thread | AutoSaveThread | `extends Thread`, daemon, saves every 30 s, `stopSaving()` |
| thread | RentReminderTask | `implements Runnable`, prints a reminder per tenant who still owes rent, `Thread.sleep(500)` between messages |
| main | MainApp | menu with 24 options, calls all services, try-catch around every action, loads data at start and saves at exit, sample data loader (menu 24) |

Topics Hariom covers: multithreading, file handling and streams, overloading, plus joining all packages.
Needs from Harsh and Mayank: model + services. Start with RentService and FileStorage, then MainApp last.

## Team rules
1. Day 1: all three agree on method names (use the reference code) before typing anything.
2. One GitHub repo, one person per package folder, so there are no merge fights.
3. Day 2: Harsh pushes model/util/interfaces. Day 3-4: Mayank pushes exception/services. Day 4-6: Hariom finishes rent, storage, threads.
4. Last day: everyone runs the program together with menu 24 and tests: add tenant with bad phone, pay more than due, pay in parts, backup, restart and check data is still there.
