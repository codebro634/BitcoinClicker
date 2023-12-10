 package control;
 
 import game.Save;
 import game.StateSupervisor;
 import graphics.ContentPane;
 import java.awt.Component;
 import java.awt.Point;
 import java.awt.Toolkit;
 import java.util.Calendar;
 import java.util.Timer;
 import java.util.TimerTask;
 import javax.swing.JFrame;
 
 
 
 
 public class Setup
 {
   public static final float VERSION = 1.3F;
   private static final int TICKS_PER_SECOND = 50;
   private static final int INIT_WIDTH = 1250;
   private static final int INIT_HEIGHT = 772;
   static final JFrame FRAME = new JFrame("BitcoinClicker v1.3, written by Blum3nt0pf");
   
   private static ContentPane screen = new ContentPane();
   
   public static int getTitleBarHeight() {
     return 772 - screen.getHeight();
   }
   
   public static int getFrameBoundaryWidth() {
     return 1250 - screen.getWidth();
   }
   
   public static int getPanelHeight() {
     return screen.getHeight();
   }
   
   public static int getPanelWidth() {
     return screen.getWidth();
   }
   
   public static int getTicksPerSecond() {
     return 50;
   }
   
   public static void startGame() {
     if (Save.loadData()) {
       FRAME.setSize(1250, 772);
       FRAME.setResizable(false);
       FRAME.setDefaultCloseOperation(3);
       FRAME.setLocationRelativeTo((Component)null);
       FRAME.getContentPane().add((Component)screen);
       FRAME.addMouseWheelListener(EventManager.events);
       FRAME.addMouseListener(EventManager.events);
       FRAME.addMouseMotionListener(EventManager.events);
       FRAME.addKeyListener(EventManager.events);
       FRAME.setIconImage(Resources.icon_image);
       FRAME.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Resources.empty, new Point(0, 0), "default"));
       FRAME.setVisible(true);
       (new Timer()).scheduleAtFixedRate(new TimerTask() {
             public void run() {
               StateSupervisor.render();
               Setup.screen.repaint();
               System.out.println(Calendar.getInstance().getTimeInMillis() - StateSupervisor.getTime());
             }
           },  0L, 20L);
       Runtime.getRuntime().addShutdownHook(
           new Thread() {
             public void run() {
               Save.saveData();
             }
           });
     } 
   }
 }
