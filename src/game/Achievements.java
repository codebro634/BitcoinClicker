 package game;
 import animations.Animation;
 import animations.AnimationSupervisor;
 import animations.ItemBought;
 import control.Resources;
 import java.awt.image.BufferedImage;
 import java.util.ArrayList;
 
 public class Achievements {
   private static final Object[][] achievement_data = new Object[][] { 
       { "I bims 1 Bitcoin", Resources.achievement_imgs.get(0), Resources.achievement_texte[0], Boolean.valueOf(false), Integer.valueOf(-1)
       }, { "Moneyrain", Resources.achievement_imgs.get(1), Resources.achievement_texte[1], Boolean.valueOf(false), Integer.valueOf(-1)
       }, { "Timeless mining", Resources.achievement_imgs.get(2), Resources.achievement_texte[2], Boolean.valueOf(false), Integer.valueOf(-1)
       }, { "Harcore mining", Resources.achievement_imgs.get(3), Resources.achievement_texte[3], Boolean.valueOf(false), Integer.valueOf(-1)
       }, { "A world filled with bitcoins", Resources.achievement_imgs.get(4), Resources.achievement_texte[4], Boolean.valueOf(false), Integer.valueOf(-1)
       }, { "Bitcoin monster", Resources.achievement_imgs.get(5), Resources.achievement_texte[5], Boolean.valueOf(false), Integer.valueOf(-1)
       }, { "Don't stop me now", Resources.achievement_imgs.get(6), Resources.achievement_texte[6], Boolean.valueOf(false), Integer.valueOf(-1)
       }, { "Bitcoins all the way down", Resources.achievement_imgs.get(7), Resources.achievement_texte[7], Boolean.valueOf(false), Integer.valueOf(-1)
       }, { "World-famous Miner", Resources.achievement_imgs.get(8), Resources.achievement_texte[8], Boolean.valueOf(false), Integer.valueOf(-1)
       }, { "You can stop now", Resources.achievement_imgs.get(9), Resources.achievement_texte[9], Boolean.valueOf(false), Integer.valueOf(-1) }, 
       { "Millionaer", Resources.achievement_imgs.get(10), Resources.achievement_texte[10], Boolean.valueOf(false), Integer.valueOf(-1)
       }, { "Limitless", Resources.golden_bitcoin, "Insgesamt können nicht mehr als 21 Millionen Bitcoins existieren. Du hast sie alle gesammelt!", Boolean.valueOf(false), Integer.valueOf(-1) } };
 
   
   public static ArrayList<Integer> getUnlockedAchievements() {
     ArrayList<Integer> list = new ArrayList<>();
     for (int i = 0; i < achievement_data.length; i++) {
       if (isUnlocked(i))
         list.add(Integer.valueOf(i)); 
     } 
     return list;
   }
   
   public static int getTotalAchievementCount() {
     return achievement_data.length;
   }
   
   public static int getTotalUnlockCount() {
     return getUnlockedAchievements().size();
   }
   
   public static long getTimeAtUnlock(int index) {
     return ((Long)achievement_data[index][4]).longValue();
   }
   
   public static boolean isUnlocked(int index) {
     return ((Boolean)achievement_data[index][3]).booleanValue();
   }
   
   public static String getName(int index) {
     return (String)achievement_data[index][0];
   }
   
   public static String getDescription(int index) {
     return (String)achievement_data[index][2];
   }
   
   public static BufferedImage getImage(int index) {
     return (BufferedImage)achievement_data[index][1];
   }
   
   public static final ArrayList<Integer> showing = new ArrayList<>();
   
   private static final long ACHIEVEMENT_DISPLAY_TIME = 10000L;
   
   public static void update() {
     achievement_check();
     boolean change = true;
     while (change) {
       change = false;
       for (int i = 0; i < showing.size(); i++) {
         if (StateSupervisor.getTime() - getTimeAtUnlock(((Integer)showing.get(i)).intValue()) > 10000L) {
           change = true;
           showing.remove(i);
         } 
       } 
     } 
   }
 
   
   public static float lifetime_coins = 0.0F;
   
   public static float lifetime_coins_clicked = 0.0F;
   
   public static int lifetime_upgrades = 0;
   
   private static void achievement_check() {
     if (lifetime_coins >= 10.0F) {
       unlock(0, false);
       if (lifetime_coins >= 100.0F) {
         unlock(1, false);
         if (lifetime_coins >= 990.0F) {
           unlock(2, false);
           if (lifetime_coins >= 5000.0F) {
             unlock(3, false);
             if (lifetime_coins >= 10000.0F) {
               unlock(4, false);
               if (lifetime_coins >= 50000.0F) {
                 unlock(5, false);
                 if (lifetime_coins >= 150000.0F) {
                   unlock(6, false);
                   if (lifetime_coins >= 500000.0F) {
                     unlock(7, false);
                     if (lifetime_coins >= 1000000.0F) {
                       unlock(8, false);
                       if (lifetime_coins >= 5000000.0F) {
                         unlock(9, false);
                         if (lifetime_coins >= 1.0E7F) {
                           unlock(10, false);
                           if (lifetime_coins >= 2.1E7F) {
                             unlock(11, false);
                           }
                         } 
                       } 
                     } 
                   } 
                 } 
               } 
             } 
           } 
         } 
       } 
     } 
   }
   
   public static void unlockAll() {
     for (int i = 0; i < getTotalAchievementCount(); i++)
       unlock(i, false); 
   }
   
   public static void unlock(int index, boolean silent) {
     if (index >= achievement_data.length)
       return; 
     if (!isUnlocked(index)) {
       achievement_data[index][3] = Boolean.valueOf(true);
       achievement_data[index][4] = Long.valueOf(StateSupervisor.getTime());
       if (!silent) {
         showing.add(Integer.valueOf(index));
         if (ItemBought.itembought_animation_count == 0)
           AnimationSupervisor.animate((Animation)new ItemBought(0L, 1)); 
         Resources.hooray.play();
       } 
     } 
   }
 }
