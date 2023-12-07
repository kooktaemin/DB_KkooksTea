import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.io.File;
import java.io.IOException;


public class Cha1 extends JFrame {

    private Connection connection;
    private JTextArea textArea;
    private JTextField ratingTextField;
    private JComboBox<String> nameComboBox;

    String font1 = "fonts/한국기계연구원_Light.ttf";
    String font2 = "fonts/한국기계연구원_bold.ttf";
    // 폰트 파일로부터 Font 객체 생성
    Font customFont1 = loadCustomFont(font1);
    Font customFont2 = loadCustomFont(font2);

    public Cha1() {
        // GUI 설정
        setSize(1100, 618);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("녹차");

        // 배경 이미지 설정
        setContentPane(new JLabel(new ImageIcon("img/bg.png")));
        setLayout(null); // 레이아웃 매니저를 사용하지 않음

        // 데이터베이스 연결
        connectToDatabase();

        // 패널 생성 및 설정
        JPanel mainPanel = createMainPanel();
        add(mainPanel);

        // 프레임 표시
        setVisible(true);
        setLocationRelativeTo(null);
        
    }

    private void connectToDatabase() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "c##dbproject";
            String password = "kkookstea";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1100, 618);
        mainPanel.setLayout(null);
        mainPanel.setOpaque(false);

     // '이름' 목록 생성
        nameComboBox = new JComboBox<>();
        nameComboBox.setBounds(230, 80, 280, 30);
        populateNameComboBox(nameComboBox);
        mainPanel.add(nameComboBox);

     // 이름 선택 시 이벤트 처리
        nameComboBox.addActionListener(e -> {
            String selectedName = (String) nameComboBox.getSelectedItem();
            displayInfo(selectedName);
        });

        // 정보 출력을 위한 텍스트 영역
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBounds(130, 120, 500, 350);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 테두리 추가
        mainPanel.add(textArea);

        // 수정 버튼
        JButton modifyButton = new JButton("수정하기");
        modifyButton.setBounds(320, 480, 100, 30);
        modifyButton.addActionListener(e -> displayModifyPanel(nameComboBox.getSelectedItem().toString()));
        mainPanel.add(modifyButton);

        // 로고 버튼
        ImageIcon iconLogo = new ImageIcon("img/logo.png");
        JButton btnLogo = new JButton(iconLogo);
        btnLogo.setBorderPainted(false);
        btnLogo.setContentAreaFilled(false);
        btnLogo.setFocusPainted(false);
        btnLogo.setBounds(5, 5, 150, 100);
        btnLogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainPage();
                dispose();
            }
        });

        mainPanel.add(btnLogo);

        return mainPanel;
    }

    private void populateNameComboBox(JComboBox<String> nameComboBox) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 이름 FROM 녹차");
            nameComboBox.removeAllItems();
            while (resultSet.next()) {
                nameComboBox.addItem(resultSet.getString("이름"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayInfo(String name) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM 녹차 WHERE 이름 = '" + name + "'");
            if (resultSet.next()) {
                String info = String.format("이름: %s\n\n생산지역: %s\n\n수확시기: %s\n\n가공방법: %s\n\n기록: %s\n\n평점: %s",
                        resultSet.getString("이름"),
                        resultSet.getString("생산지역"),
                        resultSet.getString("수확시기"),
                        resultSet.getString("가공방법"),
                        resultSet.getString("기록"),
                        resultSet.getString("평점"));
                textArea.setLineWrap(true);
                Font customFont = customFont2.deriveFont(19f);
                textArea.setFont(customFont);
                textArea.setText(info);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayModifyPanel(String name) {
        // 이름, 텍스트 아레아(기록 용), 텍스트 필드(평점 용) 생성
    	Font customFont = customFont1.deriveFont(15f);
        
        JTextField nameField = new JTextField(name);
        nameField.setBounds(660, 170, 300, 30);
        nameField.setFont(customFont);
        
        JTextArea recordTextArea = new JTextArea("차에대한 기록을 남겨보세요!",10, 30 );
        recordTextArea.setLineWrap(true);
        recordTextArea.setBounds(660, 200, 300, 200);
        recordTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        recordTextArea.setFont(customFont);
        
        ratingTextField = new JTextField("평점(1~5)");
        ratingTextField.setBounds(660, 405, 100, 30);
        ratingTextField.setFont(customFont);

     // 저장 버튼
        JButton saveButton = new JButton("저장하기");
        saveButton.setBounds(860, 405, 100, 30);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToDatabase(nameField.getText(), recordTextArea.getText(), ratingTextField.getText());
                populateNameComboBox(nameComboBox); // 목록 갱신
                System.out.println("저장되었습니다");
                new Cha1();
                dispose();
            }
        });

        // 패널에 컴포넌트들 추가
        getContentPane().add(nameField);
        getContentPane().add(recordTextArea);
        getContentPane().add(ratingTextField);
        getContentPane().add(saveButton);

        // 기존 정보 출력
        displayInfo(name);
        revalidate();
        repaint();
    }

    private void saveToDatabase(String name, String record, String rating) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE 녹차 SET 기록 = ?, 평점 = ? WHERE 이름 = ?");
            preparedStatement.setString(1, record);
            preparedStatement.setString(2, rating);
            preparedStatement.setString(3, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Font loadCustomFont(String fontFilePath) {
        Font customFont = null;
        try {
            // 폰트 파일로부터 Font 객체 생성
            File fontFile = new File(fontFilePath);
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(30f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // 예외 처리
        }
        return customFont;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cha1());
    }
}
