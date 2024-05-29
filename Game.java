import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

public class Game extends JFrame {
  static int current_score = 0;
  private settingScreen settingScreenInstance;
  private JPanel mainPanel;

  public Game() {
    this.setTitle("Blue Shooting!");

    this.setSize(1280, 720);
    // * code below it's for Full screen (but the align will be reassembled)
    // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // this.setSize(screenSize);

    this.setUndecorated(true);
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set game icon
    ImageIcon mainIcon = new ImageIcon("./src/img/main_icon.jpg");
    this.setIconImage(mainIcon.getImage());

    // main page background music (loop)
    Tools.playMainBGM();

    // After loading, switch to main screen (public)
    showMainScreen();
  }

  // Method to show main screen
  // ! some todos need to deal with
  public void showMainScreen() {
    mainPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Enable antialiasing and high quality rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        ImageIcon background1 = new ImageIcon("./src/img/main-page.jpg");
        g2d.drawImage(background1.getImage(), 0, 0, getWidth(), getHeight(), null);

        // Set text color with alpha value
        g2d.setColor(new Color(245, 241, 210, 255));
        g2d.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));

        String text = "結束遊戲";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 10;
        int y = getHeight() / 9 * 8;
        g2d.drawString(text, x, y);

        String text2 = "使用素材";
        FontMetrics fm2 = g2d.getFontMetrics();
        int textWidth2 = fm2.stringWidth(text2);
        int x2 = (getWidth() - textWidth2) / 11 * 10;
        int y2 = getHeight() / 9 * 8;
        g2d.drawString(text2, x2, y2);
      }
    };

    mainPanel.setLayout(null); // set mainPanel to 絕對布局

    // ? start button
    JButton startButton = new JButton("開始遊戲");
    Color customColor = new Color(100, 149, 237);
    startButton.setBackground(customColor);
    startButton.setForeground(Color.WHITE);
    startButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
    startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Tools.basicClick_SE();
        Tools.stopBGM();
        Tools.playGameBGM(); // if has problem while switching pannel, then you can delete it
        // * enterGameScreen;
        startGameScreen.startGame();
        dispose();
      }
    });
    mainPanel.add(startButton);
    Dimension size_startButton = startButton.getPreferredSize();
    startButton.setBounds((int) (getWidth() * 0.42), getHeight() / 2, (int) (getWidth() * 0.18),
        size_startButton.height * 2);

    // ? setting button
    JButton settingButton = new JButton("設定");
    Color customColor2 = new Color(90, 94, 95);
    settingButton.setBackground(customColor2);
    settingButton.setForeground(Color.WHITE);
    settingButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
    settingButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    settingButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Tools.basicClick_SE();
        showSettingScreen();
        // * enterSettingScreen;
      }
    });
    mainPanel.add(settingButton);
    Dimension size_settingButton = settingButton.getPreferredSize();
    settingButton.setBounds((int) (getWidth() * 0.42), getHeight() / 3 * 2, (int) (getWidth() * 0.18),
        size_settingButton.height * 2);

    // ? end game icon
    ImageIcon imageIcon = new ImageIcon("./src/img/shutdown.png");
    JLabel label = new JLabel(imageIcon);
    label.setBounds((int) (getWidth() * 0.095), getHeight() / 3 * 2, imageIcon.getIconWidth(),
        imageIcon.getIconWidth());
    label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    label.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.exit(0);
      }
    });
    mainPanel.add(label);

    // ? source icon
    // todo: edit the "openURL" to our source file
    ImageIcon sourceIcon = new ImageIcon("./src/img/open-source.png");
    JLabel label_sourceIcon = new JLabel(sourceIcon);
    label_sourceIcon.setBounds((int) (getWidth() * 0.81), getHeight() / 3 * 2, sourceIcon.getIconWidth(),
        sourceIcon.getIconWidth());
    label_sourceIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    label_sourceIcon.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Tools.basicClick_SE();
        // open an url
        String url = "https://drive.google.com/file/d/1-kNjh3whzagxxpjxfTtkTj2KJjX3YMYX/view?usp=sharing";
        try {
          URI uri = new URI(url);

          if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(uri);
          } else
            System.out.println("Desktop is not supported.");

        } catch (IOException | URISyntaxException ex) {
          ex.printStackTrace();
        }
      }
    });
    mainPanel.add(label_sourceIcon);

    // ? (windows/mac) menu bar icon
    ImageIcon guileIcon = new ImageIcon("./src/img/guile.png");
    JLabel label_guileIcon = new JLabel(guileIcon);
    label_guileIcon.setBounds((int) (getWidth() * 0.72), (int) (getHeight() * 0.23), guileIcon.getIconWidth(),
        guileIcon.getIconWidth());
    mainPanel.add(label_guileIcon);
    
    if(settingScreenInstance != null) {
      settingScreenInstance.setVisible(false);
    }
    mainPanel.setVisible(true);
    this.add(mainPanel);
    this.revalidate();
    this.repaint();
  }

  public void showSettingScreen() {
    if (settingScreenInstance == null) {
      settingScreenInstance = new settingScreen(this);
      settingScreenInstance.setBounds(0, 0, 1280, 720); // 確保設定頁面大小正確
      this.add(settingScreenInstance);
    }
    settingScreenInstance.setVisible(true);
    mainPanel.setVisible(false); // 隱藏主畫面
    this.revalidate();
    this.repaint();
  }

  public static void main(String[] args) {
    Game game = new Game();
  }
}
