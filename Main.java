import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();
        
        while (true) {
            System.out.println("\nTo-Do List:");
            int index = 1;
            for (Task task : manager.getTasks()) {
                System.out.println(index++ + ". " + task);
            }
            System.out.println("\n1. Add Task  2. Mark Completed  3. Remove Task  4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String desc = scanner.nextLine();
                    manager.addTask(desc);
                    break;
                case 2:
                    System.out.print("Enter task number to mark completed: ");
                    int completeIndex = scanner.nextInt() - 1;
                    manager.markTaskCompleted(completeIndex);
                    break;
                case 3:
                    System.out.print("Enter task number to remove: ");
                    int removeIndex = scanner.nextInt() - 1;
                    manager.removeTask(removeIndex);
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
