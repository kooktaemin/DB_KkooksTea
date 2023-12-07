import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class BackgroundPanel_S extends JPanel {
    private Image background;

    public BackgroundPanel_S(Image background) {
        this.background = background;
        this.setPreferredSize(new Dimension(background.getWidth(this), background.getHeight(this)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class SignUp extends JFrame {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "c##dbproject";
    private static final String PASS = "kkookstea";

    private JTextField userText;
    private JPasswordField passwordText;
    private JLabel messageLabel;
    private JPanel panel;

    public SignUp() {
        initUI();
        initDatabase();
    }

    private void initUI() {
        setTitle("Kkook's Tea 회원가입");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 618);
        setLocationRelativeTo(null);

        ImageIcon background = new ImageIcon("img/SignUp_bg.png");
        BackgroundPanel_S backgroundPanel = new BackgroundPanel_S(background.getImage());
        backgroundPanel.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setOpaque(false);
        backgroundPanel.add(panel, BorderLayout.CENTER);
        placeComponents(panel);
        addLoginButton();  // 새로 추가한 메서드 호출

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("사용자 이름 (영어 20자 내):");
        userLabel.setBounds(360, 228, 200, 30);
        userText = new JTextField(20);
        userText.setBounds(518, 230, 200, 30);

        JLabel passwordLabel = new JLabel("비밀번호 (숫자 4자리):");
        passwordLabel.setBounds(387, 268, 200, 30);
        passwordText = new JPasswordField(5);
        passwordText.setBounds(518, 270, 200, 30);

        JButton registerButton = new JButton("회원가입");
        registerButton.setBounds(630, 310, 85, 50);
        messageLabel = new JLabel();
        messageLabel.setBounds(400, 320, 400, 30);

        ImageIcon icon_logo = new ImageIcon("img/logo.png");
        JButton btn_logo = new JButton(icon_logo);
        btn_logo.setBorderPainted(false);
        btn_logo.setContentAreaFilled(false);
        btn_logo.setFocusPainted(false);
        btn_logo.setBounds(5, 5, 150, 100);
        btn_logo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Home();
            }
        });


        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(registerButton);
        panel.add(messageLabel);
        panel.add(btn_logo);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = String.valueOf(passwordText.getPassword());

                if (!isValidUsername(username) || !isValidPassword(password)) {
                    messageLabel.setText("이름 : 알파벳 20자 내, 비밀번호 : 숫자 4자");
                } else {
                    try {
                        registerUser(username, password);
                        messageLabel.setText("Kkook's Tea는 당신을 기다렸어요!");
                    } catch (SQLException ex) {
                        messageLabel.setText("회원가입에 실패했어요..");
                    }
                }
            }
        });
    }

    private void addLoginButton() {
        JButton loginButton = new JButton("로그인하러가기");
        loginButton.setBounds(600, 388, 140, 30);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LogIn();
            }
        });
    }

    private void initDatabase() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidUsername(String username) {
        return username.matches("[a-zA-Z]{1,20}");
    }

    private boolean isValidPassword(String password) {
        return password.matches("[0-9]{4}");
    }

    private void registerUser(String username, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String insertUserQuery = "INSERT INTO USERS (USERID, USERNAME, PASSWORD) VALUES (USER_SEQUENCE.NEXTVAL, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignUp();
            }
        });
    }
}
