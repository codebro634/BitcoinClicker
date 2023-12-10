 package game;
 
 import animations.Animation;
 import animations.AnimationSupervisor;
 import animations.Firework;
 import control.EventManager;
 import graphics.AchievementScreen;
 import graphics.ContentPane;
 import java.awt.Rectangle;
 import java.awt.event.MouseEvent;
 
 public class AchievementScreenControl
 {
   public static void onClick(MouseEvent e) {
     Rectangle mouse = new Rectangle(EventManager.getMouseX(), EventManager.getMouseY(), 5, 5);
     if (mouse.intersects(AchievementScreen.getBackButton())) {
       StateSupervisor.setGamestate((byte)1, true);
     }
   }
   
   public static void render() {
     HoverUpdate.update();
     if ((int)(Math.random() * 50.0D) == 0)
       AnimationSupervisor.animate((Animation)new Firework(0L, -1, (int)(Math.random() * ContentPane.convertToScreenX(100.0F)), (int)(Math.random() * ContentPane.convertToScreenY(100.0F)))); 
   }
 }
