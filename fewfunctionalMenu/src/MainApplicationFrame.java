import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApplicationFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel surveysPanel;
    private JPanel tasksPanel;
    private JPanel recipesPanel;
    private JPanel comingSoonPanel1;
    private JPanel comingSoonPanel2;
    private JPanel comingSoonPanel3;

    public MainApplicationFrame() {
        setTitle("Многофункциональное приложение");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        
        surveysPanel = createSurveysPanel();
        tasksPanel = createTasksPanel();
        recipesPanel = createRecipesPanel();
        comingSoonPanel1 = createComingSoonPanel("Раздел 1");
        comingSoonPanel2 = createComingSoonPanel("Раздел 2");
        comingSoonPanel3 = createComingSoonPanel("Раздел 3");

        tabbedPane.addTab("Опросы", surveysPanel);
        tabbedPane.addTab("Задачи", tasksPanel);
        tabbedPane.addTab("Рецепты", recipesPanel);
        tabbedPane.addTab("Скоро появится ", comingSoonPanel1);
        tabbedPane.addTab("Скоро появится ", comingSoonPanel2);
        tabbedPane.addTab("Скоро появится ", comingSoonPanel3);

        add(tabbedPane);
    }

    private JPanel createSurveysPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel surveysList = new JPanel();
        surveysList.setLayout(new BoxLayout(surveysList, BoxLayout.Y_AXIS));

        addSurvey(surveysList, "Опрос о Ижорском колледже");
        addSurvey(surveysList, "Опрос об отчислении");
        addSurvey(surveysList, "Опрос о жизни");

        panel.add(new JScrollPane(surveysList), BorderLayout.CENTER);
        return panel;
    }

    private void addSurvey(JPanel panel, String title) {
        JPanel surveyPanel = new JPanel(new BorderLayout());
        surveyPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        surveyPanel.add(new JLabel(title), BorderLayout.WEST);
        JButton startButton = new JButton("Начать");
        surveyPanel.add(startButton, BorderLayout.EAST);
        panel.add(surveyPanel);
    }

    private JPanel createTasksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel tasksList = new JPanel();
        tasksList.setLayout(new BoxLayout(tasksList, BoxLayout.Y_AXIS));

        addTask(tasksList, "Высокий приоритет: прогулять колледж", Color.RED);
        addTask(tasksList, "Высокий приоритет: поспать ", Color.RED);
        addTask(tasksList, "Средний приоритет: ходить в колледж", Color.ORANGE);
        addTask(tasksList, "Средний приоритет: что то делать на уроках", Color.ORANGE);
        addTask(tasksList, "Низкий приоритет: учиться", Color.GREEN);

        panel.add(new JScrollPane(tasksList), BorderLayout.CENTER);
        return panel;
    }

    private void addTask(JPanel panel, String title, Color priorityColor) {
        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setBorder(BorderFactory.createLineBorder(priorityColor));
        taskPanel.add(new JLabel(title), BorderLayout.WEST);
        JCheckBox doneCheckBox = new JCheckBox("Выполнено");
        taskPanel.add(doneCheckBox, BorderLayout.EAST);
        panel.add(taskPanel);
    }

    private JPanel createRecipesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel recipesList = new JPanel();
        recipesList.setLayout(new BoxLayout(recipesList, BoxLayout.Y_AXIS));

        addRecipe(recipesList, "ленивые голубцы");
        addRecipe(recipesList, "кусок курицы");
        addRecipe(recipesList, "котлетки с пюрехой на 3\5");
        addRecipe(recipesList, "какая то рыба ");
        addRecipe(recipesList, "компот");

        panel.add(new JScrollPane(recipesList), BorderLayout.CENTER);
        return panel;
    }

    private void addRecipe(JPanel panel, String title) {
        JPanel recipePanel = new JPanel(new BorderLayout());
        recipePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        recipePanel.add(new JLabel(title), BorderLayout.WEST);
        JButton viewButton = new JButton("Просмотр");
        recipePanel.add(viewButton, BorderLayout.EAST);
        panel.add(recipePanel);
    }

    private JPanel createComingSoonPanel(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel(title + " - Скоро появится!(нет)");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label);
        return panel;
    }
} 