 package control;
 
 import game.AchievementScreenControl;
 import game.Cheats;
 import game.CreditsScreenControl;
 import game.Game;
 import game.StateSupervisor;
 import graphics.ContentPane;
 import graphics.Dialogbox;
 import java.awt.event.KeyEvent;
 import java.awt.event.KeyListener;
 import java.awt.event.MouseEvent;
 import java.awt.event.MouseListener;
 import java.awt.event.MouseMotionListener;
 import java.awt.event.MouseWheelEvent;
 import java.awt.event.MouseWheelListener;
 import java.util.HashSet;
 
 
 
 
 public class EventManager
   implements MouseWheelListener, KeyListener, MouseListener, MouseMotionListener
 {
   public static EventManager events = new EventManager();
 
   
   public void mouseWheelMoved(MouseWheelEvent e) {}
 
   
   private static final HashSet<Character> pressed_keys = new HashSet<>();
   private static boolean shift_down;
   private static boolean ctr_down;
   
   public static boolean isShiftDown() {
     return shift_down;
   }
   private static int mouseX; private static int mouseY;
   public static boolean isCtrDown() {
     return ctr_down;
   }
   
   public static boolean isPressed(char c) {
     return pressed_keys.contains(Character.valueOf(c));
   }
   
   public void keyPressed(KeyEvent e) {
     pressed_keys.add(Character.valueOf(e.getKeyChar()));
     shift_down = e.isShiftDown();
     ctr_down = e.isControlDown();
   }
   
   public void keyReleased(KeyEvent e) {
     pressed_keys.remove(Character.valueOf(e.getKeyChar()));
     shift_down = false;
     ctr_down = false;
     Cheats.keyTyped(e);
   }
   
   public void keyTyped(KeyEvent e) {
     switch (StateSupervisor.getGamestate()) {
       case 1:
         Dialogbox.close();
         break;
     } 
   }
   
   public void mouseClicked(MouseEvent e) {
     setMousePosition(e);
   }
 
 
   
   public void mouseEntered(MouseEvent arg0) {}
 
   
   public void mouseExited(MouseEvent e) {}
 
   
   public void mousePressed(MouseEvent e) {
     setMousePosition(e);
     ContentPane.pressCursor();
     switch (StateSupervisor.getGamestate()) {
       case 1:
         Game.onClick(e);
         break;
       case 2:
         AchievementScreenControl.onClick(e);
         break;
       case 3:
         CreditsScreenControl.onClick(e);
         break;
     } 
   }
   
   public void mouseReleased(MouseEvent e) {
     ContentPane.releaseCursor();
     setMousePosition(e);
   }
   
   public void mouseDragged(MouseEvent e) {
     setMousePosition(e);
   }
 
 
   
   public void mouseMoved(MouseEvent e) {
     setMousePosition(e);
   }
   
   private static void setMousePosition(MouseEvent e) {
     mouseX = e.getX() - 2;
     mouseY = e.getY() - Setup.getTitleBarHeight() + 3;
   }
   
   public static int getMouseX() {
     return mouseX;
   }
   
   public static int getMouseY() {
     return mouseY;
   }
 }
