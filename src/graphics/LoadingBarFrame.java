 package graphics;
 
 import control.Resources;
 import control.Setup;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics;
 import java.util.Timer;
 import java.util.TimerTask;
 import javax.swing.JFrame;
 import javax.swing.JPanel;
 
 
 public class LoadingBarFrame
   extends JPanel
 {
   private static final long serialVersionUID = 897160929645979864L;
   public static final int INIT_WIDTH = 1250;
   public static final int INIT_HEIGHT = 772;
   public static final float VERSION = 1.3F;
   public static final String TITLE = "BitcoinClicker v1.3, written by Blum3nt0pf";
   private static final JFrame FRAME = new JFrame("BitcoinClicker v1.3, written by Blum3nt0pf");
   
   private static LoadingBarFrame instance = new LoadingBarFrame();
   
   public static void main(String[] args) {
     Resources.loadPreGameResources();
     FRAME.setSize(1250, 772);
     FRAME.setResizable(false);
     FRAME.setDefaultCloseOperation(3);
     FRAME.setLocationRelativeTo(null);
     FRAME.getContentPane().add(instance);
     FRAME.setIconImage(Resources.icon_image);
     FRAME.setVisible(true);
     (new Timer()).scheduleAtFixedRate(new TimerTask() {
           public void run() {
             LoadingBarFrame.instance.repaint();
           }
         },  0L, 25L);
     (new Thread(new Runnable() {
           public void run() {
             Resources.loadGameResources();
           }
         })).start();
   }
   
   private static boolean loading = false;
   
   public void paintComponent(Graphics g) {
     g.clearRect(0, 0, getWidth(), getHeight());
     g.drawImage(Resources.background, 0, 0, getWidth(), getHeight(), null);
     float percent_width = getWidth() / 100.0F, percent_height = getHeight() / 100.0F;
     g.setColor(Color.BLACK);
     g.fillRoundRect((int)(percent_width * 20.0F), (int)(percent_height * 45.0F), (int)(percent_width * 60.0F), (int)(percent_height * 10.0F), (int)(percent_width * 3.0F), (int)(percent_height * 3.0F));
     g.setColor(Color.GRAY);
     int border_size = 10;
     g.fillRoundRect((int)(percent_width * 20.0F + border_size), (int)(percent_height * 45.0F + border_size), (int)(percent_width * 60.0F - (2 * border_size)), (int)(percent_height * 10.0F - (2 * border_size)), (int)(percent_width * 3.0F), (int)(percent_height * 3.0F));
     g.setColor(Color.GREEN);
     float loaded = Resources.getResourcesLoadedCount() / 84.0F;
     g.fillRoundRect((int)(percent_width * 20.0F + border_size), (int)(percent_height * 45.0F + border_size), (int)((percent_width * 60.0F - (2 * border_size)) * loaded), (int)(percent_height * 10.0F - (2 * border_size)), (int)(percent_width * 3.0F), (int)(percent_height * 3.0F));
     g.setColor(Color.WHITE);
     g.setFont(new Font("Cambria", 1, 50));
     g.drawString(String.valueOf((int)(loaded * 100.0F)) + " / 100", (int)(percent_width * 42.0F), 385);
     g.setFont(new Font("Cambria", 1, 130));
     g.drawString("BitcoinClicker", (int)(percent_width * 20.0F), 300);
     g.setFont(new Font("Cambria", 1, 50));
     g.drawString("version 1.3", (int)(percent_width * 40.0F), 450);
     if (!loading && loaded >= 1.0F) {
       loading = true;
       FRAME.dispose();
       Setup.startGame();
     } 
   }
 }
