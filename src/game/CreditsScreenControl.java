 package game;
 
 import animations.Animation;
 import animations.AnimationSupervisor;
 import animations.PictureRain;
 import control.EventManager;
 import graphics.ContentPane;
 import graphics.CreditsScreen;
 import java.awt.Rectangle;
 import java.awt.event.MouseEvent;
 
 public class CreditsScreenControl
 {
   public static void onClick(MouseEvent e) {
     Rectangle mouse = new Rectangle(EventManager.getMouseX(), EventManager.getMouseY(), 5, 5);
     if (mouse.intersects(CreditsScreen.getBackButton())) {
       StateSupervisor.setGamestate((byte)1, true);
     }
   }
   
   public static void render() {
     HoverUpdate.update();
     if ((int)(Math.random() * 200.0D) == 0)
       AnimationSupervisor.animate((Animation)new PictureRain(0L, -1, 0, ContentPane.convertToScreenX(100.0F), ContentPane.convertToScreenY(0.25F))); 
   }
 }