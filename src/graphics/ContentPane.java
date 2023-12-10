 package graphics;
 
 import animations.AnimationSupervisor;
 import control.EventManager;
 import control.Resources;
 import control.Setup;
 import game.StateSupervisor;
 import java.awt.Graphics;
 import java.awt.image.BufferedImage;
 import javax.swing.JPanel;
 
 public class ContentPane
   extends JPanel
 {
   private static final long serialVersionUID = -3112601278001092056L;
   public ClassLoader cl = getClass().getClassLoader();
   
   public static int convertToScreenX(float percentage) {
     return (int)(Setup.getPanelWidth() / 100.0F * percentage);
   }
   private static BufferedImage cursor;
   public static int convertToScreenY(float percentage) {
     return (int)(Setup.getPanelHeight() / 100.0F * percentage);
   }
 
 
   
   public static void pressCursor() {
     cursor = Resources.cursor_pressed_down;
   }
   
   public static void releaseCursor() {
     cursor = Resources.cursor_default;
   }
   
   private static boolean drawing = false;
   
   public static boolean isDrawing() {
     return drawing;
   }
   
   public void paintComponent(Graphics g) {
     drawing = true;
     g.clearRect(0, 0, getWidth(), getHeight());
     switch (StateSupervisor.getGamestate()) {
       case 3:
         CreditsScreen.render(g);
         break;
       case 1:
         GameScreen.render(g);
         break;
       case 2:
         AchievementScreen.render(g);
         break;
     } 
 
     
     AnimationSupervisor.drawTopPrio(g);
     if (cursor == null)
       cursor = Resources.cursor_default; 
     g.drawImage(cursor, EventManager.getMouseX(), EventManager.getMouseY(), convertToScreenX(3.0F), convertToScreenX(3.0F), null);
     drawing = false;
   }
 }
