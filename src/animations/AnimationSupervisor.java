 package animations;
 
 import game.StateSupervisor;
 import java.awt.Graphics;
 import java.util.ConcurrentModificationException;
 import java.util.HashSet;
 
 public class AnimationSupervisor {
   private static HashSet<Animation> animation_list_top = new HashSet<>();
   private static HashSet<Animation> animation_list_top_temp = new HashSet<>();
   
   private static HashSet<Animation> animation_list_low = new HashSet<>();
   private static HashSet<Animation> animation_list_low_temp = new HashSet<>();
   
   public static void animate(Animation animation) {
     if (animation.getPriority() >= 0) {
       animation_list_top_temp.add(animation);
     } else {
       animation_list_low_temp.add(animation);
     } 
   }
   public static void clear() {
     animation_list_top.clear();
     animation_list_top_temp.clear();
     animation_list_low.clear();
     animation_list_low_temp.clear();
   }
   
   public static void drawTopPrio(Graphics g) {
     for (Animation a : new HashSet(animation_list_top)) {
       if (StateSupervisor.getTime() - a.getStart() >= a.getDelay() && a.isActive())
         a.draw(g); 
     } 
   }
   
   public static void drawLowPrio(Graphics g) {
     for (Animation a : new HashSet(animation_list_low)) {
       if (StateSupervisor.getTime() - a.getStart() >= a.getDelay() && a.isActive())
         a.draw(g); 
     } 
   }
   
   public static void renderTopPrio() {
     
     try { animation_list_top.addAll(animation_list_top_temp);
       animation_list_top_temp.clear(); }
     catch (ConcurrentModificationException concurrentModificationException) {  }
     finally { for (Animation a : new HashSet(animation_list_top)) {
         if (StateSupervisor.getTime() - a.getStart() >= a.getDelay()) {
           if (a.isActive()) {
             a.render(); continue;
           } 
           animation_list_top.remove(a);
         } 
       }  }
   
   }
   public static void renderLowPrio() {
     
     try { animation_list_low.addAll(animation_list_low_temp);
       animation_list_low_temp.clear(); }
     catch (ConcurrentModificationException concurrentModificationException) {  }
     finally { for (Animation a : new HashSet(animation_list_low)) {
         if (StateSupervisor.getTime() - a.getStart() >= a.getDelay()) {
           if (a.isActive()) {
             a.render(); continue;
           } 
           animation_list_low.remove(a);
         } 
       }  }
   
   }
 }