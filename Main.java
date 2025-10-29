import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Money Class Demo");
            System.out.println("2. Parallelogram Class Demo");
            System.out.println("3. Binary Tree Demo");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    runMoneyDemo();
                    break;
                case 2:
                    runParallelogramDemo();
                    break;
                case 3:
                    runBinaryTreeDemo();
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ================= MONEY DEMO =================
    public static void runMoneyDemo() {
        System.out.println("\n=== MONEY CLASS DEMONSTRATION ===");

        Money m1 = new Money();
        Money m2 = new Money(125.75);
        Money m3 = new Money(m2);

        Money sum = m2.add(new Money(10.50));
        Money diff = m2.subtract(new Money(50.25));
        Money half = m2.divide(2);

        System.out.println("m1 = " + m1);
        System.out.println("m2 = " + m2);
        System.out.println("m3 (copy of m2) = " + m3);
        System.out.println("Sum (m2 + 10.50) = " + sum);
        System.out.println("Difference (m2 - 50.25) = " + diff);
        System.out.println("Half of m2 = " + half);
        System.out.println("m2 > m3 ? " + m2.greaterThan(m3));
        System.out.println("m2 == m3 ? " + m2.equals(m3));
        System.out.println("Total Money objects: " + Money.getObjectCount());
        System.out.println("m2 in USD: $" + String.format("%.2f", Money.toUSD(m2.getAmount())));
    }

    // ================= PARALLELOGRAM DEMO =================
    public static void runParallelogramDemo() {
        System.out.println("\n=== PARALLELOGRAM CLASS DEMONSTRATION ===");

        Parallelogram p1 = new Parallelogram(5, 8, 60);
        Parallelogram p2 = new Parallelogram(10, 16, 60);
        Parallelogram p3 = new Parallelogram(p1);

        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("p3 (copy of p1) = " + p3);

        System.out.println("Perimeter of p1 = " + p1.getPerimeter());
        System.out.println("Area of p1 = " + String.format("%.2f", p1.getArea()));

        System.out.println("p1 equals p3 ? " + p1.equals(p3));
        System.out.println("p1 similar to p2 ? " + p1.isSimilar(p2));
        System.out.println("p1 larger than p2 ? " + p1.greaterThan(p2));

        System.out.println("Total Parallelograms created: " + Parallelogram.getObjectCount());
    }

    // ================= BINARY TREE DEMO =================
    public static void runBinaryTreeDemo() {
        Scanner sc = new Scanner(System.in);
        BinaryTree tree = new BinaryTree();
        int choice;

        System.out.println("\n=== BINARY TREE DEMONSTRATION ===");

        do {
            System.out.println("\n--- BINARY TREE MENU ---");
            System.out.println("1. Insert elements (multiple numbers separated by space)");
            System.out.println("2. Delete element");
            System.out.println("3. Search element");
            System.out.println("4. Print tree (in-order)");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter numbers to insert (separated by space): ");
                    String line = sc.nextLine();
                    String[] numbers = line.split(" ");
                    for (String numStr : numbers) {
                        int num = Integer.parseInt(numStr);
                        tree.insert(num);
                    }
                    break;
                case 2:
                    System.out.print("Enter number to delete: ");
                    int delVal = sc.nextInt();
                    tree.delete(delVal);
                    break;
                case 3:
                    System.out.print("Enter number to search: ");
                    int findVal = sc.nextInt();
                    System.out.println(tree.search(findVal)
                            ? "Element found!"
                            : "Element not found!");
                    break;
                case 4:
                    System.out.print("Tree elements (in-order): ");
                    tree.printInOrder();
                    System.out.println();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }
}

// ======================= CLASS MONEY =======================
class Money {
    private static int objectCount = 0;
    private static double usdRate = 40.0;
    private double amount;

    { objectCount++; }

    public Money() { this.amount = 0.0; }
    public Money(double amount) { this.amount = Math.max(amount, 0); }
    public Money(Money other) { this.amount = other.amount; }

    public static int getObjectCount() { return objectCount; }
    public static double toUSD(double uah) { return uah / usdRate; }
    public static double toUAH(double usd) { return usd * usdRate; }
    public static void setUsdRate(double rate) { if (rate > 0) usdRate = rate; }

    public Money add(Money other) { return new Money(this.amount + other.amount); }
    public Money subtract(Money other) { return new Money(Math.max(this.amount - other.amount, 0)); }
    public Money divide(double number) {
        if (number == 0) throw new ArithmeticException("Division by zero!");
        return new Money(this.amount / number);
    }

    public boolean greaterThan(Money other) { return this.amount > other.amount; }
    public double getAmount() { return amount; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Money)) return false;
        Money other = (Money) obj;
        return Math.abs(this.amount - other.amount) < 0.001;
    }

    @Override
    public String toString() {
        int hryvnias = (int) amount;
        int kop = (int) Math.round((amount - hryvnias) * 100);
        return hryvnias + "," + String.format("%02d UAH", kop);
    }
}

// ======================= CLASS PARALLELOGRAM =======================
class Parallelogram {
    private static int objectCount = 0;
    private double sideA, sideB, angle;

    { objectCount++; }

    public Parallelogram() { this(1, 1, 90); }
    public Parallelogram(double sideA, double sideB, double angle) {
        this.sideA = Math.max(sideA, 0);
        this.sideB = Math.max(sideB, 0);
        this.angle = Math.max(Math.min(angle, 180), 0);
    }
    public Parallelogram(Parallelogram other) {
        this.sideA = other.sideA;
        this.sideB = other.sideB;
        this.angle = other.angle;
    }

    public static int getObjectCount() { return objectCount; }
    public double getPerimeter() { return 2 * (sideA + sideB); }
    public double getArea() { return sideA * sideB * Math.sin(Math.toRadians(angle)); }

    public boolean greaterThan(Parallelogram other) { return this.getArea() > other.getArea(); }

    public boolean isSimilar(Parallelogram other) {
        double ratio1 = this.sideA / other.sideA;
        double ratio2 = this.sideB / other.sideB;
        return Math.abs(ratio1 - ratio2) < 0.001 && Math.abs(this.angle - other.angle) < 0.001;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Parallelogram)) return false;
        Parallelogram other = (Parallelogram) obj;
        return Math.abs(this.sideA - other.sideA) < 0.001 &&
               Math.abs(this.sideB - other.sideB) < 0.001 &&
               Math.abs(this.angle - other.angle) < 0.001;
    }

    @Override
    public String toString() {
        return String.format("Parallelogram [A=%.2f, B=%.2f, angle=%.1f°]", sideA, sideB, angle);
    }
}

// ======================= CLASS BINARY TREE =======================
class BinaryTree {
    private Node root;

    private static class Node {
        int value;
        Node left, right;
        Node(int value) { this.value = value; }
    }

    public void insert(int value) { root = insertRec(root, value); }

    private Node insertRec(Node node, int value) {
        if (node == null) return new Node(value);
        if (value < node.value)
            node.left = insertRec(node.left, value);
        else if (value > node.value)
            node.right = insertRec(node.right, value);
        return node;
    }

    public void delete(int value) { root = deleteRec(root, value); }

    private Node deleteRec(Node node, int value) {
        if (node == null) return null;
        if (value < node.value)
            node.left = deleteRec(node.left, value);
        else if (value > node.value)
            node.right = deleteRec(node.right, value);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            node.value = minValue(node.right);
            node.right = deleteRec(node.right, node.value);
        }
        return node;
    }

    private int minValue(Node node) {
        int min = node.value;
        while (node.left != null) {
            min = node.left.value;
            node = node.left;
        }
        return min;
    }

    public boolean search(int value) { return searchRec(root, value); }

    private boolean searchRec(Node node, int value) {
        if (node == null) return false;
        if (value == node.value) return true;
        return value < node.value
                ? searchRec(node.left, value)
                : searchRec(node.right, value);
    }

    public void printInOrder() { printInOrderRec(root); }

    private void printInOrderRec(Node node) {
        if (node != null) {
            printInOrderRec(node.left);
            System.out.print(node.value + " ");
            printInOrderRec(node.right);
        }
    }
}
