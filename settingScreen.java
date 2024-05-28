import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;


public class settingScreen extends JPanel {
    private Game parentFrame;

    public settingScreen(Game parentFrame) {
        this.parentFrame = parentFrame;
        this.setLayout(null); // 使用绝对布局
        this.setSize(1280, 720); // 设置settingScreen的尺寸

        JPanel panel = createSettingPanel();
        panel.setBounds(0, 0, 1280, 720);
        this.add(panel); // 将panel添加到settingScreen
    }

    private JPanel createSettingPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Enable antialiasing and high quality rendering
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                ImageIcon background = new ImageIcon("./src/img/settingpage-background.png");
                g2d.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
                Font font = new Font("Microsoft YaHei", Font.BOLD, 55);
                int textWidth = g2d.getFontMetrics(font).stringWidth("關於我們");
                drawText("關於我們", font, g2d, getWidth() / 2 - textWidth / 2, getHeight() / 3*2, 4);

                font = new Font("Microsoft YaHei", Font.BOLD, 30);
                drawText("減小音量", font, g2d, getWidth()/18*2 , getHeight()/20*17, 3);
                textWidth = g2d.getFontMetrics(font).stringWidth("提升音量");
                drawText("提升音量", font, g2d, getWidth()/18*16 - textWidth, getHeight()/20*17, 3);
            }
        };
        panel.setLayout(null);

        JLabel backLabel = drawIcon("./src/img/back.png", getWidth()/20, getHeight()/12, getWidth()/17, getWidth()/17);
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tools.basicClick_SE();
                parentFrame.showMainScreen();
            }
        });

        int aboutIconWidth = getWidth()/18*3;
        int aboutIconHeight = getHeight()/10*3;
        JLabel aboutLabel = drawIcon("./src/img/group.png", getWidth()/2 - aboutIconWidth/2 , getHeight()/2 - aboutIconHeight/3*2, aboutIconWidth, aboutIconHeight);
        aboutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tools.basicClick_SE();
                //todo
            }
        });

        int volumeIconWidth = 122;
        int volumeIconHeight = 122;
        JLabel volume_down = drawIcon("./src/img/audio-minus.png", getWidth()/18*2, getHeight()/10*8-volumeIconHeight, volumeIconWidth, volumeIconHeight);
        volume_down.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tools.basicClick_SE();
                Tools.adjustVolume(-5.0f);
            }
        });

        JLabel volume_up = drawIcon("./src/img/audio-plus.png", getWidth()/18*16 - volumeIconWidth, getHeight()/10*8-volumeIconHeight, volumeIconWidth, volumeIconHeight);
        volume_up.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tools.basicClick_SE();
                Tools.adjustVolume(5.0f);
            }
        });
        panel.add(backLabel);
        panel.add(aboutLabel);
        panel.add(volume_down);
        panel.add(volume_up);
        return panel;
    }

    private void drawText(String text, Font font, Graphics2D g2d, int x, int y, int border_width){
        g2d.setFont(font);
        // 描邊文本（黑色）
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(border_width)); // 設置描邊寬度
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl = new TextLayout(text, font, frc);
        Shape shape = tl.getOutline(null);
        g2d.translate(x, y);
        g2d.draw(shape);

        // 填充文本（白色）
        g2d.setColor(Color.WHITE);
        g2d.fill(shape); // 填充文本
        g2d.translate(-x, -y);
    }

    private JLabel drawIcon(String path, int x, int y, int width, int height){
        ImageIcon icon = new ImageIcon(path);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); // 指定新的宽度和高度
        icon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(new ImageIcon(icon.getImage()));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setBounds(x, y, width, height);
        return label;
    }


    
}
