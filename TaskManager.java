import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaskManager {
    private List<Task> tasks;
    private static final String FILE_NAME = "tasks.dat";
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.taskListModel = new DefaultListModel<>();
        this.taskList = new JList<>(taskListModel);
        loadTasks(); 
        createGUI(); 
    }

    public void addTask(String description) {
        tasks.add(new Task(description));
        taskListModel.addElement(description);
        saveTasks(); 
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void markTaskCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markCompleted();
            taskListModel.set(index, "[✔] " + tasks.get(index).getDescription());
            saveTasks(); 
        } else {
            JOptionPane.showMessageDialog(null, "Invalid task number.");
        }
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            taskListModel.remove(index);
            saveTasks(); 
        } else {
            JOptionPane.showMessageDialog(null, "Invalid task number.");
        }
    }

    private void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked") 
    private void loadTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                tasks = (List<Task>) obj;
            }
            for (Task task : tasks) {
                taskListModel.addElement(task.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved tasks found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    private void createGUI() {
        JFrame frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextField taskInput = new JTextField();
        JButton addButton = new JButton("Add Task");
        JButton completeButton = new JButton("Mark Completed");
        JButton removeButton = new JButton("Remove Task");
        
        JScrollPane scrollPane = new JScrollPane(taskList);
        
        addButton.addActionListener(e -> {
            String taskText = taskInput.getText();
            if (!taskText.isEmpty()) {
                addTask(taskText);
                taskInput.setText("");
            }
        });

        completeButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                markTaskCompleted(index);
            }
        });

        removeButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                removeTask(index);
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(removeButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.add(panel);
        frame.setVisible(true);
    }
}
