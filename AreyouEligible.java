package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class AreyouEligible {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage());
    }
}

class WelcomePage extends JFrame {
    public WelcomePage() {
        setTitle("Welcome");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel background = new JLabel(ImageLoader.loadImage("/project/OOP/4PsCalc.gif"));
        setLayout(new BorderLayout());
        add(background, BorderLayout.CENTER);

        Timer timer = new Timer(2000, e -> {
            new HomePage().setVisible(true);
            dispose();
        });
        timer.setRepeats(false);
        timer.start();
        
        setVisible(true);
    }
}

class HomePage extends JFrame {
    public HomePage() {
        setTitle("Are you Eligible?");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(7, 36, 123));

        // Main panel with image background
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel backgroundPanel = new JLabel(ImageLoader.loadImage("/project/OOP/4PsCalc.gif"));
        mainPanel.add(backgroundPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Instructions panel
        JPanel instructionPanel = createInstructionPanel();
        mainPanel.add(instructionPanel, BorderLayout.WEST);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        Color buttonColor = new Color(7, 36, 123);
        
        JButton startButton = createButton("Start", buttonColor, e -> {
            dispose();
            new EligibilityCalculator();
        });
        
        JButton creditsButton = createButton("Credits", buttonColor, e -> showCredits());
        
        JButton exitButton = createButton("Exit", buttonColor, e -> showExitDialog());

        panel.add(startButton);
        panel.add(creditsButton);
        panel.add(exitButton);

        return panel;
    }

    private JButton createButton(String text, Color bgColor, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.addActionListener(listener);
        return button;
    }

    private JPanel createInstructionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("INSTRUCTIONS:", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JTextArea instructions = new JTextArea(
            "1. Enter your income and family size.\n" +
            "2. The maximum number of children for monitoring is 3 (ages 0-18).\n" +
            "3. Enter number of beneficiaries for each education level.\n" +
            "4. Enter the number of months for calculation.\n" +
            "5. Click Calculate to determine eligibility and assistance amount."
        );
        instructions.setFont(new Font("Arial", Font.PLAIN, 16));
        instructions.setEditable(false);
        panel.add(new JScrollPane(instructions), BorderLayout.CENTER);

        return panel;
    }

    private void showCredits() {
        JTextArea credits = new JTextArea(
            "Developed by Merjilla, Alyssa Jade P.\n\n" +
            "Photos by: Official Gazette | Department of Social Welfare and Development\n" +
            "Information by: Department of Social Welfare and Development"
        );
        credits.setEditable(false);
        JOptionPane.showMessageDialog(this, credits, "Credits", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showExitDialog() {
        JLabel meme = new JLabel(ImageLoader.loadImage("/project/OOP/e.gif"));
        String[] options = {"Exit", "Back"};
        int choice = JOptionPane.showOptionDialog(this, meme, "Thank you!", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        if (choice == 0) {
            System.exit(0);
        }
    }
}

class EligibilityCalculator extends JFrame {
    private JTextField incomeField, familySizeField, daycareField, elementaryField, 
                      juniorHighField, seniorHighField, monthsField;
    private JTextArea resultArea;
    private JCheckBox[] categoryCheckboxes;

    public EligibilityCalculator() {
        setTitle("4Ps Eligibility Calculator");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenuBar();
        initUI();
        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Back menu
        JMenu backMenu = new JMenu("Back");
        backMenu.add(createMenuItem("Close", "/project/OOP/close.png", e -> {
            new HomePage();
            dispose();
        }));
        
        // Benefits menu
        JMenu benefitsMenu = new JMenu("Benefits");
        benefitsMenu.add(createMenuItem("Benefits", "/project/OOP/benefit.png", e -> showBenefits()));
        
        // Info menu
        JMenu infoMenu = new JMenu("Info");
        infoMenu.add(createMenuItem("About", "/project/OOP/information.png", e -> showInfo()));
        
        // Conditions menu
        JMenu conditionsMenu = new JMenu("Conditions");
        conditionsMenu.add(createMenuItem("Conditions", "/project/OOP/rules-book.png", e -> showConditions()));

        menuBar.add(backMenu);
        menuBar.add(benefitsMenu);
        menuBar.add(infoMenu);
        menuBar.add(conditionsMenu);
        
        setJMenuBar(menuBar);
    }

    private JMenuItem createMenuItem(String text, String iconPath, ActionListener listener) {
        JMenuItem item = new JMenuItem(text, ImageLoader.loadImage(iconPath));
        item.addActionListener(listener);
        return item;
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Background image
        JLabel background = new JLabel(ImageLoader.loadImage("/project/OOP/4Ps.jpg"));
        mainPanel.add(background, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.EAST);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Family category checkboxes
        String[] categories = {
            "Farmers", "Fisherfolks", "Homeless families", 
            "Indigenous peoples", "Informal settlers",
            "Families in geographically isolated areas",
            "None of these"
        };
        
        panel.add(new JLabel("Does your family identify as:", JLabel.LEFT));
        categoryCheckboxes = new JCheckBox[categories.length];
        
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < categories.length; i++) {
            categoryCheckboxes[i] = new JCheckBox(categories[i]);
            group.add(categoryCheckboxes[i]);
            panel.add(categoryCheckboxes[i]);
        }

        // Input fields
        addInputField(panel, "Income:", incomeField = new JTextField());
        addInputField(panel, "Family Size:", familySizeField = new JTextField());
        addInputField(panel, "Daycare Beneficiaries:", daycareField = new JTextField());
        addInputField(panel, "Elementary Beneficiaries:", elementaryField = new JTextField());
        addInputField(panel, "Junior High Beneficiaries:", juniorHighField = new JTextField());
        addInputField(panel, "Senior High Beneficiaries:", seniorHighField = new JTextField());
        addInputField(panel, "Number of Months:", monthsField = new JTextField());

        // Calculate button
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> calculateEligibility());
        calculateButton.setBackground(new Color(7, 36, 123));
        calculateButton.setForeground(Color.WHITE);
        panel.add(calculateButton);

        return panel;
    }

    private void addInputField(JPanel panel, String label, JTextField field) {
        panel.add(new JLabel(label));
        panel.add(field);
    }

    private void calculateEligibility() {
        try {
            if (categoryCheckboxes[6].isSelected()) { // "None of these" selected
                showNotEligible();
                return;
            }

            boolean categorySelected = false;
            for (int i = 0; i < 6; i++) {
                if (categoryCheckboxes[i].isSelected()) {
                    categorySelected = true;
                    break;
                }
            }

            if (!categorySelected) {
                resultArea.setText("Please select a family category");
                JOptionPane.showMessageDialog(this, resultArea, "Error", 
                    JOptionPane.ERROR_MESSAGE, ImageLoader.loadImage("/project/OOP/check.gif"));
                return;
            }

            double income = Double.parseDouble(incomeField.getText());
            int familySize = Integer.parseInt(familySizeField.getText());
            int daycare = Integer.parseInt(daycareField.getText());
            int elementary = Integer.parseInt(elementaryField.getText());
            int juniorHigh = Integer.parseInt(juniorHighField.getText());
            int seniorHigh = Integer.parseInt(seniorHighField.getText());
            int months = Integer.parseInt(monthsField.getText());
            int totalChildren = daycare + elementary + juniorHigh + seniorHigh;

            if (totalChildren > 3) {
                JOptionPane.showMessageDialog(this, 
                    "Maximum number of children (3) exceeded", 
                    "Warning", JOptionPane.WARNING_MESSAGE, 
                    ImageLoader.loadImage("/project/OOP/MAX.gif"));
                return;
            }

            if (income > 9000) {
                showNotEligible();
                return;
            }

            double assistance = calculateAssistance(income, familySize, 
                daycare, elementary, juniorHigh, seniorHigh, months);
            
            resultArea.setText(String.format(
                "You are Eligible!\nFinancial Assistance for %d months: PHP %.2f", 
                months, assistance));
            
            JOptionPane.showMessageDialog(this, resultArea, "Results", 
                JOptionPane.INFORMATION_MESSAGE, 
                ImageLoader.loadImage("/project/OOP/yes.gif"));

        } catch (NumberFormatException ex) {
            resultArea.setText("Please enter valid numbers in all fields");
            JOptionPane.showMessageDialog(this, resultArea, "Input Error", 
                JOptionPane.ERROR_MESSAGE, 
                ImageLoader.loadImage("/project/OOP/error.gif"));
        }
    }

    private void showNotEligible() {
        resultArea.setText("Sorry, you are not eligible for 4Ps assistance");
        JOptionPane.showMessageDialog(this, resultArea, "Sorry", 
            JOptionPane.INFORMATION_MESSAGE, 
            ImageLoader.loadImage("/project/OOP/not.gif"));
        
        String[] options = {"New", "Back"};
        int choice = JOptionPane.showOptionDialog(this, 
            "Do you want to try again?", "Try Again", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
            null, options, options[0]);
        
        if (choice == 0) {
            new EligibilityCalculator();
            dispose();
        } else {
            new HomePage();
            dispose();
        }
    }

    private double calculateAssistance(double income, int familySize, 
            int daycare, int elementary, int juniorHigh, 
            int seniorHigh, int months) {
        
        final double DAYCARE_AMOUNT = 300.00;
        final double ELEMENTARY_AMOUNT = 300.00;
        final double JUNIOR_HIGH_AMOUNT = 500.00;
        final double SENIOR_HIGH_AMOUNT = 700.00;
        final double HEALTH_AMOUNT = 750.00;
        final double RICE_SUBSIDY = 600.00;

        double assistance = 0;
        
        // Education grants
        assistance += DAYCARE_AMOUNT * daycare;
        assistance += ELEMENTARY_AMOUNT * elementary;
        assistance += JUNIOR_HIGH_AMOUNT * juniorHigh;
        assistance += SENIOR_HIGH_AMOUNT * seniorHigh;
        
        // Other benefits
        assistance += HEALTH_AMOUNT + RICE_SUBSIDY;
        
        return assistance * months;
    }

    private void showBenefits() {
        JTextArea benefits = new JTextArea(
            "Education grants (per month per child):\n" +
            "- Daycare/Elementary: PHP 300\n" +
            "- Junior High: PHP 500\n" +
            "- Senior High: PHP 700\n\n" +
            "Health grants: PHP 750 per household\n" +
            "PhilHealth coverage: Automatic enrollment\n" +
            "Rice subsidy: PHP 600"
        );
        benefits.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(benefits), 
            "Benefits", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInfo() {
        JTextArea info = new JTextArea(
            "The 4Ps Assistance Calculator determines eligibility for the\n" +
            "Pantawid Pamilyang Pilipino Program (4Ps).\n\n" +
            "It considers income, family size, and other criteria\n" +
            "to calculate potential financial assistance."
        );
        info.setEditable(false);
        JOptionPane.showMessageDialog(this, info, 
            "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showConditions() {
        JTextArea conditions = new JTextArea(
            "Conditions to continue receiving grants:\n\n" +
            "1. Pregnant women: 4 pre-natal + 2 post-natal checkups\n" +
            "2. Children 0-5: Regular checkups and vaccinations\n" +
            "3. Children 6-14: 85% school attendance\n" +
            "4. Parents: Attend family development sessions"
        );
        conditions.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(conditions), 
            "Conditions", JOptionPane.INFORMATION_MESSAGE);
    }
}

class ImageLoader {
    public static ImageIcon loadImage(String path) {
        try {
            URL imgURL = ImageLoader.class.getResource(path);
            if (imgURL != null) {
                return new ImageIcon(imgURL);
            } else {
                System.err.println("Couldn't find image: " + path);
                return new ImageIcon(); // Return empty icon
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
            return new ImageIcon(); // Return empty icon
        }
    }
}