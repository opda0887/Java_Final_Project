import javax.swing.*;        
import java.awt.*;             
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;


public class startGameScreen {
    // constant 
    private static int frameWidth = 1280;
    private static int frameHeight = 720;
    private static int timePerGame = 60; // 60
    private static int targetWideh = 200;
    private static int targetHeight = 120;
    private static Rectangle TimeLabelPositionAndSize = new Rectangle(750, 5, 400,100);
    private static Rectangle ScoreLabelPositionAndSize = new Rectangle(1050, 5, 400,100);
    private static Rectangle pauseButtonPositionAndSize = new Rectangle(10, 10, 150,150);
    private static Font labelFont = new Font("Microsoft YaHei", Font.BOLD, 40);
    private static float lighteningFactoe = 0.5f;
    private static float pauseIconScaleRate = 2.0f;

    private static String pathIWin = "./src/img/iwin.png";
    private static ImageIcon backgroundImage = new ImageIcon("./src/img/battle-background.png"); 
    private static ImageIcon darkerBackgroundImage = new ImageIcon(decreaseBrightness(backgroundImage.getImage(), lighteningFactoe));  
    private static ImageIcon iWin = new ImageIcon(pathIWin); 
    private static ImageIcon pauseIcon = new ImageIcon("./src/img/pause.png"); 
    private static ImageIcon biggerPauseIcon = getScaledImageIcon(pauseIcon, pauseIconScaleRate);
    private static ImageIcon aronaIcon = new ImageIcon("./src/img/arona.png");
    
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

    // function to scale icon
    private static ImageIcon getScaledImageIcon(ImageIcon theImageIcon, float ScaledRate) {
        Image theImage = theImageIcon.getImage();
        int newWidth = theImageIcon.getIconWidth() * 2;
        int newHeight = theImageIcon.getIconHeight() * 2;
        Image scaledImage = theImage.getScaledInstance(newWidth,newHeight,Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
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
                                                        //  JLayeredPane maybe?
        private int timeLeft;
        private int score;
        private JLabel waitText = new JLabel();
        private JLabel waitText2 = new JLabel();
        private JLabel background = new JLabel();
        private JLabel timeLabel = new JLabel();
        private JLabel scoreLabel = new JLabel();
        private JButton target = new JButton(iWin);
        private JButton pauseButton = new JButton();
        private JLabel arona = new JLabel();
        
        private Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timeLabel.setText("剩餘時間 : " + (timeLeft+9)/10);
                if(timeLeft <= 0) {
                    pausing = true;
                    timer.stop();
                    background.setIcon(darkerBackgroundImage);

                     // change if you think not remove better better
                    background.remove(target); 
                    background.remove(pauseButton); 
                    background.remove(timeLabel);
                    background.remove(scoreLabel);

                    arona.setVisible(true);                    
                    showEndMenu();
                }
            }
        });

        public NewGame()    
        {
            pre();
            init();
            
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Set game icon
            ImageIcon mainIcon = new ImageIcon("./src/img/main_icon.jpg");
            this.setIconImage(mainIcon.getImage());
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
            timeLeft = timePerGame * 10;
            score = 0;
            
            // Background Image 
            background = new JLabel(darkerBackgroundImage);
            background.setSize(frameWidth,frameHeight);
            // this is weird I know
            background.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(waitEnd == false)
                    {
                        waitEnd = true;
                        addComponents();
                        timer.start();
                        background.setIcon(backgroundImage);
                        background.remove(waitText);
                        background.remove(waitText2);
                    }
                }
            });

            // wait Text
            waitText.setText("點擊滑鼠左鍵");
            waitText.setBounds(new Rectangle(460,60,500,500));
            waitText.setFont(new Font("Microsoft YaHei", Font.BOLD, 65));
            waitText.setForeground(Color.WHITE);
            background.add(waitText, BorderLayout.PAGE_START);

            waitText2.setText("   開始遊戲  ");
            waitText2.setBounds(new Rectangle(460,200,500,500));
            waitText2.setFont(new Font("Microsoft YaHei", Font.BOLD, 65));
            waitText2.setForeground(Color.WHITE);
            background.add(waitText2, BorderLayout.PAGE_START);


            this.add(background);
        }

        private void addComponents()
        {
            // time Label
            timeLabel.setText("剩餘時間 : " + (timeLeft+9)/10);
            timeLabel.setBounds(TimeLabelPositionAndSize);
            timeLabel.setFont(labelFont);
            timeLabel.setForeground(Color.WHITE); 
            background.add(timeLabel, BorderLayout.PAGE_START);

            // Score Label 
            scoreLabel.setText("分數 : " + score);
            scoreLabel.setBounds(ScoreLabelPositionAndSize);
            scoreLabel.setFont(labelFont);
            scoreLabel.setForeground(Color.WHITE); 
            background.add(scoreLabel, BorderLayout.PAGE_START);


            // target 
            // JButton target = new JButton(iWin);
            target.setSize(targetWideh, targetHeight);
            target.setLocation((frameWidth - targetWideh) / 2, (frameHeight - targetHeight) / 2); // set to mid
            target.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            // 設置按鈕外框與背景不可見，只保留圖片
            target.setBorderPainted(false);
            target.setContentAreaFilled(false);
            target.setFocusPainted(false);
            target.setOpaque(false);
            target.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // shoot audio
                    Tools.shootClick_SE();
                    if (timeLeft == 0 || pausing == true) return;
                    int x = (int) (Math.random() * (frameWidth - targetWideh));
                    int y = (int) (Math.random() * (frameHeight - targetHeight));
                    target.setLocation(x, y);
                    score++;
                    scoreLabel.setText("分數 : " + score);
                }
            });
            background.add(target, BorderLayout.PAGE_START);

            // Pause Button
            pauseButton = new JButton(biggerPauseIcon);
            pauseButton.setBounds(pauseButtonPositionAndSize);
            pauseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            // 設置按鈕外框與背景不可見，只保留圖片
            pauseButton.setBorderPainted(false);
            pauseButton.setContentAreaFilled(false);
            pauseButton.setFocusPainted(false);
            pauseButton.setOpaque(false);
            pauseButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Tools.basicClick_SE();
                    if(timeLeft == 0 || pausing ==true) return;

                    // 隱藏組件
                    timeLabel.setVisible(false);
                    scoreLabel.setVisible(false);
                    target.setVisible(false);
                    pauseButton.setVisible(false);

                    timer.stop();
                    pausing = true;
                    background.setIcon(darkerBackgroundImage);
                    showPauseMenu();
                    pausing = false;

                    // 顯示組件
                    timeLabel.setVisible(true);
                    scoreLabel.setVisible(true);
                    target.setVisible(true);
                    pauseButton.setVisible(true);

                    timer.start();
                }
            });
            background.add(pauseButton, BorderLayout.PAGE_START);


            // arona.png
            arona.setIcon(aronaIcon);
            arona.setBounds(200,50,300,300);
            arona.setVisible(false);             
            background.add(arona, BorderLayout.PAGE_START);
        }

        //! ///////////////////////////////////////////////////////////////////////////////////////

        JDialog pauseDialog = new JDialog();    
        JPanel pauseMenu = new JPanel();
        JDialog scoreDialog = new JDialog();    
        JPanel scoreMenu = new JPanel();
        // Set the preferred size for the pause menu
        int menuWidth = 450;
        int menuHeight = 450;
        // Button dimensions
        int buttonWidth = 300;
        int buttonHeight = 70;
        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);

        private void pre()
        {
            // Set the layout manager to BoxLayout with vertical alignment
            pauseMenu.setLayout(new BoxLayout(pauseMenu, BoxLayout.Y_AXIS));
            pauseMenu.setPreferredSize(new Dimension(menuWidth, menuHeight));

            // label
            JLabel theLabel = new JLabel();
            theLabel.setText("暫停");
            theLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            theLabel.setFont(labelFont);
            theLabel.setForeground(Color.BLACK); 

            // button 1
            JButton button1 = new JButton("繼續遊戲");
            Color customColor = new Color(0, 150, 255);
            button1.setAlignmentX(Component.CENTER_ALIGNMENT);
            button1.setPreferredSize(buttonSize); 
            button1.setMaximumSize(buttonSize);   
            button1.setFont(labelFont);  
            button1.setForeground(Color.WHITE);  
            button1.setBackground(customColor);   
            button1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Tools.basicClick_SE();
                    background.setIcon(backgroundImage);
                    pauseDialog.setVisible(false);
                    timer.start();
                    pausing = false;
                }
            });

            // button 2
            JButton button2 = new JButton("回到標題");
            button2.setAlignmentX(Component.CENTER_ALIGNMENT);
            button2.setPreferredSize(buttonSize); 
            button2.setMaximumSize(buttonSize);    
            button2.setFont(labelFont); 
            button2.setForeground(Color.WHITE); 
            button2.setBackground(Color.DARK_GRAY);  
            button2.setBorder(BorderFactory.createLineBorder(Color.WHITE));  
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Tools.basicClick_SE();
                    //! this is to fix a weird bug if game.java use timer or sth
                    timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {}
                    });
                    pauseDialog.setVisible(false);
                    gameEnd();
                }
            });
        
            // Add components with glue for spacing
            pauseMenu.add(Box.createVerticalGlue());
            pauseMenu.add(theLabel);
            pauseMenu.add(Box.createVerticalGlue());
            pauseMenu.add(button1);
            pauseMenu.add(Box.createVerticalGlue());
            pauseMenu.add(button2);
            pauseMenu.add(Box.createVerticalGlue());

            // Add the pauseMenu to a dialog or another container to show it
            pauseDialog.setUndecorated(true);  // Remove the title bar
            pauseDialog.setModal(true);        // Block input to other windows
            pauseDialog.getContentPane().add(pauseMenu);
            pauseDialog.pack();
            pauseDialog.setLocationRelativeTo(null); // Center the dialog
            pauseDialog.setVisible(false);
        }

        private void build()
        {
            // Set the layout manager to BoxLayout with vertical alignment
            scoreMenu.setLayout(new BoxLayout(scoreMenu, BoxLayout.Y_AXIS));
            scoreMenu.setPreferredSize(new Dimension(menuWidth, menuHeight));

            // label
            JLabel theLabel = new JLabel("分數:" + score);
            theLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            theLabel.setFont(labelFont);
            theLabel.setForeground(Color.BLACK); 

            // button 1
            JButton button1 = new JButton("再玩一次");
            Color customColor = new Color(0, 150, 255);
            button1.setAlignmentX(Component.CENTER_ALIGNMENT);
            button1.setPreferredSize(buttonSize); 
            button1.setMaximumSize(buttonSize);   
            button1.setFont(labelFont);  
            button1.setBackground(customColor);   
            button1.setForeground(Color.WHITE);  
            button1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Tools.basicClick_SE();
                    scoreDialog.dispose();
                    dispose();
                    startGameScreen.startGame();
                }
            });

            // button 2
            JButton button2 = new JButton("回到標題");
            button2.setAlignmentX(Component.CENTER_ALIGNMENT);
            button2.setPreferredSize(buttonSize); 
            button2.setMaximumSize(buttonSize);    
            button2.setFont(labelFont); 
            button2.setForeground(Color.WHITE); 
            button2.setBackground(Color.DARK_GRAY);  
            button2.setBorder(BorderFactory.createLineBorder(Color.WHITE));  
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Tools.basicClick_SE();
                    //! this is to fix a weird bug if game.java use timer or sth
                    timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {}
                    });
                    scoreDialog.setVisible(false);
                    gameEnd();
                }
            });

            // Add components with glue for spacing
            scoreMenu.add(Box.createVerticalGlue());
            scoreMenu.add(theLabel);
            scoreMenu.add(Box.createVerticalGlue());
            scoreMenu.add(button1);
            scoreMenu.add(Box.createVerticalGlue());
            scoreMenu.add(button2);
            scoreMenu.add(Box.createVerticalGlue());
            
            // Add the pauseMenu to a dialog or another container to show it
            scoreDialog.setUndecorated(true);  // Remove the title bar
            scoreDialog.setModal(true);        // Block input to other windows
            scoreDialog.getContentPane().add(scoreMenu);
            scoreDialog.pack();
            scoreDialog.setLocationRelativeTo(null); // Center the dialog
            scoreDialog.setVisible(false);
        }



        // show Pause menu
        private void showPauseMenu() {
            pauseDialog.setVisible(true);
        }

        //show Score
        public void showEndMenu() {
            build();
            scoreDialog.setVisible(true);
        }

        private void gameEnd()
        {
            timer.stop();
            Tools.stopBGM();
            dispose();
            Game game = new Game();
        }
    }
}