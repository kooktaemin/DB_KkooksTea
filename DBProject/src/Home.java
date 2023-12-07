import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Home extends JFrame {
    public Home() {
    	 setTitle("Kkook's Tea");
         setSize(1100, 618);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // 배경 이미지를 로드
         final BufferedImage backgroundImage;
         try {
             backgroundImage = ImageIO.read(new File("img/SignUp_bg.png"));
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
         panel.setLayout(null); // 레이아웃을 null로 설정하여 위치와 크기를 직접 지정할 수 있도록 합니다.

        // Sign up 버튼
         ImageIcon icon_signup_off = new ImageIcon("img/signup_off.png");
         ImageIcon icon_signup_on = new ImageIcon("img/signup_on.png");
        JButton signUpButton = new JButton(icon_signup_off);
        signUpButton.setBounds(370, 250, 160, 90);
        signUpButton.setBorderPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setRolloverIcon(icon_signup_on);
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SignUp();
            }
        });


        // Log in 버튼
        ImageIcon icon_login_off = new ImageIcon("img/login_off.png");
        ImageIcon icon_login_on = new ImageIcon("img/login_on.png");
        JButton logInButton = new JButton(icon_login_off);
        logInButton.setBounds(560, 250, 160, 90);
        logInButton.setBorderPainted(false);
        logInButton.setContentAreaFilled(false);
        logInButton.setFocusPainted(false);
        logInButton.setRolloverIcon(icon_login_on);
        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new LogIn();
                System.out.println("Log in 버튼이 눌렸습니다.");
            }
        });
        ImageIcon icon_logo = new ImageIcon("img/logo.png");
        JButton btn_logo = new JButton(icon_logo);
        btn_logo.setBounds(5, 5, 150, 100); // 버튼 위치와 크기 설
        btn_logo.setBorderPainted(false);
        btn_logo.setContentAreaFilled(false);
        btn_logo.setFocusPainted(false);
          
        btn_logo.addActionListener(new ActionListener() {
           @Override
              public void actionPerformed(ActionEvent e) {
                 new Home();
              }
        });

        // 패널에 버튼 추가
        panel.add(btn_logo);
        panel.add(signUpButton);
        panel.add(logInButton);

        // 프레임에 패널 추가
        add(panel);

        // 프레임을 화면 가운데에 위치
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Home();
            }
        });
    }
}
