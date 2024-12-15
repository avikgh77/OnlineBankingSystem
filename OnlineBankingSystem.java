import java.util.*;

class OnlineBankingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Account> accounts = new HashMap<>();
    private static Account loggedInAccount = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Online Banking System ===");
            if (loggedInAccount == null) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> register();
                    case 2 -> login();
                    case 3 -> {
                        System.out.println("Thank you for using Online Banking System. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Logout");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> checkBalance();
                    case 2 -> depositMoney();
                    case 3 -> withdrawMoney();
                    case 4 -> logout();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (accounts.containsKey(username)) {
            System.out.println("Username already exists. Please try another.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter initial deposit: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        accounts.put(username, new Account(username, password, balance));
        System.out.println("Account registered successfully.");
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (!accounts.containsKey(username)) {
            System.out.println("No account found with this username.");
            return;
        }

        Account account = accounts.get(username);
        if (!account.getPassword().equals(password)) {
            System.out.println("Incorrect password. Please try again.");
            return;
        }

        loggedInAccount = account;
        System.out.println("Login successful. Welcome, " + username + "!");
    }

    private static void checkBalance() {
        System.out.println("Your current balance is: $" + loggedInAccount.getBalance());
    }

    private static void depositMoney() {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }

        loggedInAccount.deposit(amount);
        System.out.println("Deposit successful. New balance: $" + loggedInAccount.getBalance());
    }

    private static void withdrawMoney() {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }

        if (loggedInAccount.withdraw(amount)) {
            System.out.println("Withdrawal successful. New balance: $" + loggedInAccount.getBalance());
        } else {
            System.out.println("Insufficient funds. Withdrawal failed.");
        }
    }

    private static void logout() {
        System.out.println("Logged out successfully. Goodbye, " + loggedInAccount.getUsername() + "!");
        loggedInAccount = null;
    }
}

class Account {
    private String username;
    private String password;
    private double balance;

    public Account(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }
}