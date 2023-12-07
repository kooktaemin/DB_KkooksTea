
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainPage extends JFrame {
    public MainPage() {
    	 setTitle("Kkook's Tea");
         setSize(1100, 618);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // 배경 이미지를 로드
         final BufferedImage backgroundImage;
         try {
             backgroundImage = ImageIO.read(new File("img/main_bg.png"));
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

        
        ImageIcon icon_cha1 = new ImageIcon("img/1off.png");
        ImageIcon icon_cha1_on = new ImageIcon("img/1on.png");
        JButton btn_cha1 = new JButton(icon_cha1);
        btn_cha1.setBounds(20, 140, 140, 300);
        btn_cha1.setBorderPainted(false);
        btn_cha1.setContentAreaFilled(false);
        btn_cha1.setFocusPainted(false);
        btn_cha1.setRolloverIcon(icon_cha1_on);
        btn_cha1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Cha1();
                dispose();
            }
        });
        ImageIcon icon_cha2 = new ImageIcon("img/2off.png");
        ImageIcon icon_cha2_on = new ImageIcon("img/2on.png");
        JButton btn_cha2 = new JButton(icon_cha2);
        btn_cha2.setBounds(198, 140, 140, 300);
        btn_cha2.setBorderPainted(false);
        btn_cha2.setContentAreaFilled(false);
        btn_cha2.setFocusPainted(false);
        btn_cha2.setRolloverIcon(icon_cha2_on);
        btn_cha2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new Cha2();
                dispose();
            }
        });
        ImageIcon icon_cha3 = new ImageIcon("img/3off.png");
        ImageIcon icon_cha3_on = new ImageIcon("img/3on.png");
        JButton btn_cha3 = new JButton(icon_cha3);
        btn_cha3.setBounds(376, 140, 140, 300);
        btn_cha3.setBorderPainted(false);
        btn_cha3.setContentAreaFilled(false);
        btn_cha3.setFocusPainted(false);
        btn_cha3.setRolloverIcon(icon_cha3_on);
        btn_cha3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new Cha3();
                dispose();
            }
        });
        ImageIcon icon_cha4 = new ImageIcon("img/4off.png");
        ImageIcon icon_cha4_on = new ImageIcon("img/4on.png");
        JButton btn_cha4 = new JButton(icon_cha4);
        btn_cha4.setBounds(554, 140, 140, 300);
        btn_cha4.setBorderPainted(false);
        btn_cha4.setContentAreaFilled(false);
        btn_cha4.setFocusPainted(false);
        btn_cha4.setRolloverIcon(icon_cha4_on);
        btn_cha4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new Cha4();
                dispose();
            }
        });
        ImageIcon icon_cha5 = new ImageIcon("img/5off.png");
        ImageIcon icon_cha5_on = new ImageIcon("img/5on.png");
        JButton btn_cha5 = new JButton(icon_cha5);
        btn_cha5.setBounds(732, 140, 140, 300);
        btn_cha5.setBorderPainted(false);
        btn_cha5.setContentAreaFilled(false);
        btn_cha5.setFocusPainted(false);
        btn_cha5.setRolloverIcon(icon_cha5_on);
        btn_cha5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new Cha5();
                dispose();
            }
        });
        ImageIcon icon_cha6 = new ImageIcon("img/6off.png");
        ImageIcon icon_cha6_on = new ImageIcon("img/6on.png");
        JButton btn_cha6 = new JButton(icon_cha6);
        btn_cha6.setBounds(910, 140, 140, 300);
        btn_cha6.setBorderPainted(false);
        btn_cha6.setContentAreaFilled(false);
        btn_cha6.setFocusPainted(false);
        btn_cha6.setRolloverIcon(icon_cha6_on);
        btn_cha6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new Cha6();
                dispose();
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
                 new MainPage();
              }
        });
        
        ImageIcon icon_total_on = new ImageIcon("img/total_on.png");
        ImageIcon icon_total = new ImageIcon("img/total.png");
        JButton btn_total = new JButton(icon_total);
        btn_total.setBounds(480, 470, 150, 100); // 버튼 위치와 크기 설
        btn_total.setBorderPainted(false);
        btn_total.setContentAreaFilled(false);
        btn_total.setFocusPainted(false);
        btn_total.setRolloverIcon(icon_total_on);
          
        btn_total.addActionListener(new ActionListener() {
           @Override
              public void actionPerformed(ActionEvent e) {
                 new Total();
                 dispose();
              }
        });

        // 패널에 버튼 추가
        panel.add(btn_logo);
        panel.add(btn_cha1);
        panel.add(btn_cha2);
        panel.add(btn_cha3);
        panel.add(btn_cha4);
        panel.add(btn_cha5);
        panel.add(btn_cha6);
        panel.add(btn_total);
        
        

        // 프레임에 패널 추가
        add(panel);

        // 프레임을 화면 가운데에 위치
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainPage();
            }
        });
    }
}
