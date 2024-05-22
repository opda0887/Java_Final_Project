import javax.swing.*;        
import java.awt.*;             
import java.awt.event.*;   
import javax.swing.border.*;
import java.lang.Thread;
import java.util.concurrent.CountDownLatch;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class startGameScreen {
    // constant 
    private static int frameWidth = 1280;
    private static int frameHeight = 720;
    private static int timePerGame = 3; // 60
    private static int targetWideh = 200;
    private static int targetHeight = 120;
    private static Rectangle TimeLabelPositionAndSize = new Rectangle(500, 100, 300,100);
    private static Rectangle ScoreLabelPositionAndSize = new Rectangle(750, 100, 300,100);
    private static Rectangle pauseButtonPositionAndSize = new Rectangle(20, 20, 200,200);
    private static Font labelFont = new Font("Microsoft YaHei", Font.BOLD, 24);
    private static float lighteningFactoe = 0.5f;

    private static String pathIWin = "./src/img/iwin.png";
    private static ImageIcon backgroundImage = new ImageIcon("./src/img/battle-background.png"); 
    private static ImageIcon darkerBackgroundImage = new ImageIcon(decreaseBrightness(backgroundImage.getImage(), lighteningFactoe));  
    private static ImageIcon iWin = new ImageIcon(pathIWin); 
    private static ImageIcon pauseIcon = new ImageIcon("./src/img/pause.png"); 
    
    // Function to decrease brightness of an image
    private static BufferedImage decreaseBrightness(Image image, float factor) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // Apply brightness adjustment
        RescaleOp op = new RescaleOp(1.0f - factor, 0, null);
        BufferedImage darkerImage = op.filter(bufferedImage, null);

        return darkerImage;
    }


    // CALL to START
    public static void startGame(){
        NewGame newGame = new NewGame();
    }


    // Game frame
    private static class NewGame extends JFrame
    {  
        private boolean waitEnd = false;
        private boolean pausing = false;
        // objects in gameFrame
        // TODO: JLayeredPane maybe?
        private int timeLeft;
        private int score;
        private JLabel timeLabel = new JLabel();
        private JLabel scoreLabel = new JLabel();
        private JLabel background = new JLabel();
        //private JLabel darkerBackground = new JLabel();
        private JLabel target = new JLabel();
        private JLabel pauseButton = new JLabel();
        
        private Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timeLabel.setText("剩餘時間 : " + timeLeft);
                if(timeLeft <= 0) {
                    pausing = true;
                    timer.stop();
                    showEndMenu();
                }
            }
        });



        public NewGame()    
        {
            init();

            //waitingScene();
            // addComponents();
            // timer.start();
        }

        private void init()
        {
            // self 
            this.setTitle("Never Gonna Give You Up");
            this.setSize(frameWidth, frameHeight);
            this.setUndecorated(true);
            this.setLocationRelativeTo(null);

            this.setLayout(null); // I don't wtf is this but can't work well without this 
            this.setVisible(true);

            // init
            timeLeft = timePerGame;
            score = 0;

            
            // Background Image 
            background = new JLabel(darkerBackgroundImage);
            background.setSize(frameWidth,frameHeight);
            //! this is weird I know
            background.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(waitEnd == false)
                    {
                        waitEnd = true;
                        addComponents();
                        timer.start();
                        background.setIcon(backgroundImage);
                    }
                }
            });

            this.add(background);
        }

        private void addComponents()
        {
            // time Label
            timeLabel.setText("剩餘時間 : " + timeLeft);
            timeLabel.setBounds(TimeLabelPositionAndSize);
            timeLabel.setFont(labelFont);
            background.add(timeLabel, BorderLayout.PAGE_START);

            // Score Label 
            scoreLabel.setText("分數 : " + score);
            scoreLabel.setBounds(ScoreLabelPositionAndSize);
            scoreLabel.setFont(labelFont);
            background.add(scoreLabel, BorderLayout.PAGE_START);

            // target 
            target = new JLabel(iWin);
            target.setSize(targetWideh,targetHeight);
            target.setLocation((frameWidth-targetWideh)/2,(frameHeight-targetHeight)/2); // set to mid
            target.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //shoot audio
                    Tools.shootClick_SE();
                    if(timeLeft == 0 || pausing ==true) return;
                    int x = (int)(Math.random()*(frameWidth-targetWideh));   
                    int y = (int)(Math.random()*(frameHeight-targetHeight));
                    target.setLocation(x,y);
                    score++;
                    scoreLabel.setText("分數 : " + score);
                }
            });
            background.add(target, BorderLayout.PAGE_START);

            // Pause Button
            pauseButton = new JLabel(pauseIcon);
            pauseButton.setBounds(pauseButtonPositionAndSize);
            pauseButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Tools.basicClick_SE();
                    if(timeLeft == 0 || pausing ==true) return;
                    timer.stop();
                    pausing = true;
                    showPauseMenu();
                    pausing = false;
                    timer.start();
                }
            });
            background.add(pauseButton, BorderLayout.PAGE_START);
        }
        
        
        //private void waitingScene() 
        

        //! stiil in test
        //! need beautify
        // show Pause menu
        private void showPauseMenu() 
        {
            JDialog pauseDialog = new JDialog(this, "Pause Menu", true);
            pauseDialog.setSize(300, 150);
            pauseDialog.setLocationRelativeTo(this);

            JPanel dialogPanel = new JPanel();
            dialogPanel.setLayout(new GridLayout(3, 1));

            JLabel pauseLabel = new JLabel("Paused", SwingConstants.CENTER);
            dialogPanel.add(pauseLabel);

            JButton continueButton = new JButton("Continue");
            continueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pauseDialog.dispose();
                    timer.start();
                    pausing = false;
                }
            });
            dialogPanel.add(continueButton);

            JButton backToMenuButton = new JButton("Back to Menu");
            backToMenuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameEnd();
                }
            });
            dialogPanel.add(backToMenuButton);

            pauseDialog.add(dialogPanel);
            pauseDialog.setVisible(true);
        }


        //! stiil in test
        //! need beautify
        //show Score
        /*
        public void showEndMenu() {
            // Create a panel to hold components
            JPanel panel = new JPanel(new BorderLayout());
    
            // Add score label
            JLabel scoreLabel = new JLabel("Score: " + score);
            panel.add(scoreLabel, BorderLayout.NORTH);
    
            // Create buttons
            JButton restartButton = new JButton("Restart");
            JButton menuButton = new JButton("Back to Menu");
    
            // Add buttons to a sub-panel
            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
            buttonPanel.add(restartButton);
            buttonPanel.add(menuButton);
            panel.add(buttonPanel, BorderLayout.CENTER);
    
            // Add action listeners
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Window window = SwingUtilities.windowForComponent(restartButton);
                    if (window instanceof JDialog) {
                        ((JDialog) window).dispose();
                    }
                    timer.stop();
                    dispose();
                    NewGame newGame = new NewGame();
                }
            });
    
            menuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Window window = SwingUtilities.windowForComponent(restartButton);
                    if (window instanceof JDialog) {
                        ((JDialog) window).dispose();
                    }
                    gameEnd();
                }
            });
    
            // Show dialog
            JOptionPane.showMessageDialog(null, panel, "Game Over", JOptionPane.PLAIN_MESSAGE);
        }
    */
        public void showEndMenu() {
            // Create a panel to hold components
            JPanel panel = new JPanel(new BorderLayout());
    
            // Add score label
            JLabel scoreLabel = new JLabel("Score: " + score);
            panel.add(scoreLabel, BorderLayout.NORTH);
    
            // Create buttons
            JButton restartButton = new JButton("Restart");
            JButton menuButton = new JButton("Back to Menu");
    
            // Add buttons to a sub-panel
            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
            buttonPanel.add(restartButton);
            buttonPanel.add(menuButton);
            panel.add(buttonPanel, BorderLayout.CENTER);
    
            // Show dialog
            JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            JDialog dialog = optionPane.createDialog("Game Over");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
            // Add action listeners
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                    gameEnd();
                }
            });
    
            menuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                    timer.stop();
                    dispose();
                    NewGame newGame = new NewGame();
                }
            });
    
            dialog.setVisible(true);
    
            // Detect if the dialog is closed
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    gameEnd();
                }
            });
        }


        private void gameEnd()
        {
            timer.stop();
            Tools.stopBGM();
            Game game = new Game();
            dispose();
        }
    }
}
