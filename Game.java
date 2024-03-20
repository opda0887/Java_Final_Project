import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

public class Game extends JFrame
{
  static int current_score = 0;
  
  public Game() {
    this.setTitle("Blue Shooting!");

    this.setSize(1280, 720);
    //* it's for Full screen, if you want
    // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // this.setSize(screenSize);

    this.setUndecorated(true);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    
    // Display loading screen
    showLoadingScreen();
    
    // After loading, switch to main screen
    showMainScreen();
  }


  // Method to display loading screen with fade-in effect
  private void showLoadingScreen() {
    JPanel loadingPanel = new JPanel() {
      float alpha = 0; // Initial alpha value for fade-in effect
      private boolean fadeInComplete = false; // for fade-in, fade-out

      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // Set background color and fill the panel
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        // Set text color with alpha value
        g2d.setColor(new Color(255, 0, 0, (int) (255 * alpha)));
        g2d.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));

        String text = "本遊戲無商業用途，僅供盡情遊玩！";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = getHeight() / 2;
        g2d.drawString(text, x, y);

        g2d.dispose(); // Dispose of the graphics context
      }

      @Override
      public void paint(Graphics g) {
        super.paint(g);
        // fade-in : 漸變淡入
        if (!fadeInComplete && alpha < 1) {
          alpha += 0.02f;
          if (alpha >= 1) {
            alpha = 1;
            fadeInComplete = true;
            try {
              Thread.sleep(1500);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        } else if (fadeInComplete) {
          // fade-out : 漸變淡出
          alpha -= 0.02f;
          if (alpha <= 0) {
            alpha = 0;
            try {
              Thread.sleep(500);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            return;
          }
        }
        
        try {
          Thread.sleep(10); // for a smoother effect
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        repaint(); // repaint to adjust the alpha value
      }
    };

    // Add the loadingPanel to the JFrame
    this.add(loadingPanel);
    this.revalidate();
    this.repaint();
    try {
      Thread.sleep(4000); // Wait for 4 seconds
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.remove(loadingPanel);
  }


  // Method to show main screen
  //! add background music (loop)
  private void showMainScreen() {
    JPanel mainPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Enable antialiasing and high quality rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        ImageIcon background1 = new ImageIcon("./src/img/main-page.jpg");
        g2d.drawImage(background1.getImage(), 0, 0, getWidth(), getHeight(), null);
      }
    };
    this.add(mainPanel);

    mainPanel.setLayout(null);  // set mainPanel to 絕對布局

    //? start button
    //! adjust the button: style
    //! decide the text size
    JButton startButton = new JButton("開始遊戲");
    Color customColor = new Color(100, 150, 200);
    startButton.setBackground(customColor);
    startButton.setForeground(Color.WHITE);
    startButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
    startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          //* enterGameScreen();
      }
    });
    mainPanel.add(startButton);
    Dimension size_startButton = startButton.getPreferredSize();
    startButton.setBounds((int)(getWidth()*0.42), getHeight()/2, (int)(getWidth()*0.18), size_startButton.height*2);
    
    //? setting button
    //! adjust the button: style
    //! decide the text size
    JButton settingButton = new JButton("設定");
    settingButton.setBackground(Color.GRAY);
    settingButton.setForeground(Color.WHITE);
    settingButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
    settingButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    settingButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          //* enterSettingScreen();
      }
    });
    mainPanel.add(settingButton);
    Dimension size_settingButton = settingButton.getPreferredSize();
    settingButton.setBounds((int)(getWidth()*0.42), getHeight()/3*2, (int)(getWidth()*0.18), size_settingButton.height*2);


    //? end game icon
    //! add description text
    ImageIcon imageIcon = new ImageIcon("./src/img/shutdown.png");
    JLabel label = new JLabel(imageIcon);
    label.setBounds((int)(getWidth()*0.095), getHeight()/3*2, imageIcon.getIconWidth(), imageIcon.getIconWidth());
    label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    label.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.exit(0);
      }
    });
    mainPanel.add(label);


    //? source icon
    //! add description text
    //! edit the "openURL" to our source file
    ImageIcon sourceIcon = new ImageIcon("./src/img/open-source.png");
    JLabel label_sourceIcon = new JLabel(sourceIcon);
    label_sourceIcon.setBounds((int)(getWidth()*0.81), getHeight()/3*2, sourceIcon.getIconWidth(), sourceIcon.getIconWidth());
    label_sourceIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    label_sourceIcon.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // open an url
        String url = "https://www.youtube.com";
        try {
          URI uri = new URI(url);

          if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(uri);
          } else System.out.println("Desktop is not supported.");

        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
       }
    });
    mainPanel.add(label_sourceIcon);


    //? end game icon
    //! add description text
    ImageIcon guileIcon = new ImageIcon("./src/img/guile.png");
    JLabel label_guileIcon = new JLabel(guileIcon);
    label_guileIcon.setBounds((int)(getWidth()*0.72), (int)(getHeight()*0.23), guileIcon.getIconWidth(), guileIcon.getIconWidth());
    mainPanel.add(label_guileIcon);


    this.revalidate();
    this.repaint();
  }


  public static void main(String[] args)
  {
    Game game = new Game();
  }
}
