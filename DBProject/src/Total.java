import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Total extends JFrame {
	
    private JLabel greenTeaLabel, whiteTeaLabel, oolongTeaLabel, blackTeaLabel, yellowTeaLabel, redTeaLabel,total;
    String font1 = "fonts/한국기계연구원_Light.ttf";
    String font2 = "fonts/한국기계연구원_bold.ttf";
    // 폰트 파일로부터 Font 객체 생성
    Font customFont1 = loadCustomFont(font1);
    Font customFont2 = loadCustomFont(font2);

    public Total() {
    	setTitle("Total");
        setSize(1100, 618);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

     // 배경 이미지를 로드
        final BufferedImage backgroundImage;
        try {
            backgroundImage = ImageIO.read(new File("img/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return; // 이미지 로드에 실패하면 종료
        }
     // JPanel 생성, 배경 이미지 설정
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        
        Font customFont = customFont2.deriveFont(40f);
        
        
        total = new JLabel("평점평균");
        total.setBounds(450, 100, 300, 40);
        total.setFont(customFont);
        
        greenTeaLabel = new JLabel("녹차: " + getAverageRating("녹차"));
        greenTeaLabel.setBounds(450, 150, 300, 40);
        greenTeaLabel.setFont(customFont);
        
        whiteTeaLabel = new JLabel("백차: " + getAverageRating("백차"));
        whiteTeaLabel.setBounds(450, 200, 300, 40);
        whiteTeaLabel.setFont(customFont);
        
        oolongTeaLabel = new JLabel("청차: " + getAverageRating("청차"));
        oolongTeaLabel.setBounds(450, 250, 300, 40);
        oolongTeaLabel.setFont(customFont);
        
        blackTeaLabel = new JLabel("홍차: " + getAverageRating("홍차"));
        blackTeaLabel.setBounds(450, 300, 300, 40);
        blackTeaLabel.setFont(customFont);
        
        yellowTeaLabel = new JLabel("황차: " + getAverageRating("황차"));
        yellowTeaLabel.setBounds(450, 350, 300, 40);
        yellowTeaLabel.setFont(customFont);
        
        redTeaLabel = new JLabel("흑차: " + getAverageRating("흑차"));
        redTeaLabel.setBounds(450, 400, 300, 40);
        redTeaLabel.setFont(customFont);
        
        panel.add(total);
        panel.add(greenTeaLabel);
        panel.add(whiteTeaLabel);
        panel.add(oolongTeaLabel);
        panel.add(blackTeaLabel);
        panel.add(yellowTeaLabel);
        panel.add(redTeaLabel);

        setLocationRelativeTo(null);
        setVisible(true);
        add(panel);
    }

    private double getAverageRating(String tableName) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "c##dbproject";
        String password = "kkookstea";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT AVG(평점) FROM " + tableName)) {

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
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
        new Total();
    }
}
