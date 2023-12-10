 package game;
 
 import control.EventManager;
 import graphics.AchievementScreen;
 import graphics.CreditsScreen;
 import graphics.GameScreen;
 import java.awt.Rectangle;
 
 
 public class HoverUpdate
 {
   public static void update() {
     Rectangle mouse = new Rectangle(EventManager.getMouseX(), EventManager.getMouseY(), 5, 5);
     switch (StateSupervisor.getGamestate()) {
       case 1:
         hovered_index_rightRect = GameScreen.getRightRectIndex(mouse);
         hovered_index_achievements = GameScreen.getAchievementIndex(mouse);
         achievements_hovered = GameScreen.getAchievementIconPos().intersects(mouse);
         options_hovered = GameScreen.getOptionsPos().intersects(mouse);
         credits_hovered = GameScreen.getCreditsPos().intersects(mouse);
         break;
       case 2:
         back_button_ach_hovered = mouse.intersects(AchievementScreen.getBackButton());
         hovered_index_achievement_in_achievement_screen = AchievementScreen.getHoverIndex(mouse);
         break;
       case 3:
         back_button_credits_hovered = mouse.intersects(CreditsScreen.getBackButton());
         break;
     } 
   }
   
   private static int hovered_index_achievement_in_achievement_screen = -1;
   
   public static int getHoverIndexAchievementsInAchievementScreen() {
     return hovered_index_achievement_in_achievement_screen;
   }
   
   private static boolean back_button_ach_hovered = false;
   
   public static boolean isBackButtonAchHovered() {
     return back_button_ach_hovered;
   }
   
   private static boolean back_button_credits_hovered = false;
   
   public static boolean isBackButtonCreditsHovered() {
     return back_button_credits_hovered;
   }
   
   private static boolean back_button_options_hovered = false;
   
   public static boolean isBackButtonOptionsHovered() {
     return back_button_options_hovered;
   }
   
   private static int hovered_index_rightRect = 0;
   
   public static int getHoverIndexRightRect() {
     return hovered_index_rightRect;
   }
   
   private static int[] hovered_index_achievements = new int[] { -1 };
   
   public static int[] getHoverIndexAchievements() {
     return hovered_index_achievements;
   }
   
   private static boolean achievements_hovered = false;
   
   public static boolean isAchievementHovered() {
     return achievements_hovered;
   }
   
   private static boolean options_hovered = false;
   
   public static boolean isOptionsHovered() {
     return options_hovered;
   }
   
   private static boolean credits_hovered = false;
   
   public static boolean isCreditsHovered() {
     return credits_hovered;
   }
 }