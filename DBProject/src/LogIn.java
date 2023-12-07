import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class BackgroundPanel_L extends JPanel {
    private Image background;

    public BackgroundPanel_L(Image background) {
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

public class LogIn extends JFrame {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "c##dbproject";
    private static final String PASS = "kkookstea";

    private JTextField userText;
    private JPasswordField passwordText;
    private JLabel messageLabel;
    private JPanel panel;

    public LogIn() {
        initUI();
    }

    private void initUI() {
        setTitle("Kkook's Tea 로그인");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 618);
        setLocationRelativeTo(null);

        ImageIcon background = new ImageIcon("img/SignUp_bg.png");
        BackgroundPanel_L backgroundPanel = new BackgroundPanel_L(background.getImage());
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
        panel.setLayout(null);  // 레이아웃 매니저를 null로 설정하여 직접 위치와 크기 지정

        JLabel userLabel = new JLabel("사용자 이름 (영어 20자 내):");
        userLabel.setBounds(360, 228, 200, 30);
        userText = new JTextField(20);
        userText.setBounds(518, 230, 200, 30);

        JLabel passwordLabel = new JLabel("비밀번호 (숫자 4자리):");
        passwordLabel.setBounds(387, 268, 200, 30);
        passwordText = new JPasswordField(5);
        passwordText.setBounds(518, 270, 200, 30);

        JButton loginButton = new JButton("로그인");
        loginButton.setBounds(630, 310, 85, 50);
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
        panel.add(loginButton);
        panel.add(messageLabel);
        panel.add(btn_logo);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUsername = userText.getText();
                String inputPassword = new String(passwordText.getPassword());

                if (checkLogin(inputUsername, inputPassword)) {
                    messageLabel.setText("로그인 성공!");
                    new MainPage();
                    dispose();
                } else {
                    messageLabel.setText("로그인에 실패했어요..");
                }
            }
        });
    }

    private void addLoginButton() {
        JButton loginButton = new JButton("회원가입하러가기");
        loginButton.setBounds(600, 388, 140, 30); // Sign up 버튼 위치와 크기 설정
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUp();
            }
        });

        panel.revalidate();
        panel.repaint();
    }

    private boolean checkLogin(String inputUsername, String inputPassword) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
        String dbUsername = "c##dbproject";
        String dbPassword = "kkookstea";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, inputUsername);
                preparedStatement.setString(2, inputPassword);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new LogIn();
    }
}
