 package game;
 
 import animations.Animation;
 import animations.AnimationSupervisor;
 import animations.Transition;
 import control.Resources;
 import control.Soundboard;
 import graphics.ContentPane;
 import java.util.Calendar;
 
 
 public class StateSupervisor
 {
   private static byte gamestate = 1;
   
   static {
     setGamestate((byte)1, false);
   }
   private static long time;
   public static byte getGamestate() {
     return gamestate;
   }
   
   public static void setGamestate(byte state, boolean playBlop) {
     gamestate = state;
     AnimationSupervisor.clear();
     AnimationSupervisor.animate((Animation)new Transition(0L, 1));
     Soundboard.putOnRepeat(Resources.hubmusic);
     if (playBlop) {
       Resources.blop.play();
     }
   }
 
   
   public static long getTime() {
     return time;
   }
   
   public static void render() {
     if (ContentPane.isDrawing())
       return; 
     time = Calendar.getInstance().getTimeInMillis();
     switch (getGamestate()) {
       case 1:
         Game.render();
         break;
       case 2:
         AchievementScreenControl.render();
         break;
       case 3:
         CreditsScreenControl.render();
         break;
     } 
     AnimationSupervisor.renderTopPrio();
     AnimationSupervisor.renderLowPrio();
     Soundboard.update();
   }
 }
