 package game;
 
 import control.EventManager;
 import control.Resources;
 import control.Setup;
 import control.Soundboard;
 import graphics.Dialogbox;
 import graphics.GameScreen;
 import java.awt.Rectangle;
 import java.awt.event.MouseEvent;
 
 
 
 public class Game
 {
   private static final int BPS_UPPER_SCALING_LIMIT = 1000000;
   
   public static double f_bps_upper_scaling_limit() {
     double x = getBitcoins_per_second();
     double yMax = 1.0D;
     double xMax = 1000000.0D;
     return (x >= xMax) ? yMax : (Math.log(x + 1.0D) * yMax / Math.log(xMax + 1.0D));
   }
   
   private static double bitcoins = 0.0D;
   
   private static float bitcoins_per_second = 0.0F;
   
   private static long gameticks = 0L;
   
   public static long getGameticks() {
     return gameticks;
   }
   
   public static void render() {
     if (gameticks < Long.MAX_VALUE)
       gameticks++; 
     update_screen_data();
     update_coins();
     if (!GoldenBitcoin.isActive())
       HoverUpdate.update(); 
     Achievements.update();
     GoldenBitcoin.update();
     BittieBitcoin.update();
     Dialogbox.update();
   }
 
   
   private static void update_screen_data() {
     GameScreen.shift += 0.0025F;
     if (GameScreen.shift >= 6.283185307179586D)
       GameScreen.shift = 0.0F; 
     if (GameScreen.ticks++ >= Long.MAX_VALUE) {
       GameScreen.ticks = 0L;
     }
     GameScreen.shift_x_graph++;
   }
   
   private static void update_coins() {
     float amount = bitcoins_per_second * 1.0F / Setup.getTicksPerSecond();
     addBitcoins(amount);
   }
   
   public static void onClick(MouseEvent e) {
     Rectangle mouse = new Rectangle(EventManager.getMouseX(), EventManager.getMouseY(), 5, 5);
     if (GoldenBitcoin.isActive()) {
       if (mouse.intersects(GoldenBitcoin.getArea())) {
         GoldenBitcoin.collect();
       }
     } else {
       int[] ach_index = GameScreen.getAchievementIndex(mouse);
       if (ach_index[0] != -1) {
         if (ach_index[1] == 0) {
           StateSupervisor.setGamestate((byte)2, true);
         } else if (ach_index[1] == 1) {
           Achievements.showing.remove(ach_index[0]);
         }  Resources.blop.play();
       }
       else if (mouse.intersects(GameScreen.getCookiePos())) {
         onBitCoinClicked();
       }
       else if (mouse.intersects(GameScreen.getRightRectPos())) {
         int index = GameScreen.getRightRectIndex(mouse);
         if (index != -1) {
           if (e.isControlDown()) {
             if (getBitcoins() >= (float)(Upgrades.getUpgradeCost(index) * 10L))
               for (int i = 0; i < 10; i++) {
                 Upgrades.buyUpgrade(index);
               } 
           } else if (e.isShiftDown()) {
             if (getBitcoins() >= (float)(Upgrades.getUpgradeCost(index) * 100L))
               for (int i = 0; i < 100; i++) {
                 Upgrades.buyUpgrade(index);
               } 
           } else {
             Upgrades.buyUpgrade(index);
           } 
         }
       } else if (GameScreen.getTopGrid().intersects(mouse)) {
         if (mouse.intersects(GameScreen.getAchievementIconPos())) {
           StateSupervisor.setGamestate((byte)2, true);
         }
         else if (mouse.intersects(GameScreen.getOptionsPos())) {
           Soundboard.toggleMuted();
         }
         else if (mouse.intersects(GameScreen.getCreditsPos())) {
           StateSupervisor.setGamestate((byte)3, true);
         } 
       } 
     } 
   }
   
   private static void onBitCoinClicked() {
     Resources.blop.play();
     GameScreen.onCookieClicked();
     addBitcoins(getBitCoinsPerClick());
     Achievements.lifetime_coins_clicked += getBitCoinsPerClick();
     if ((int)(Math.random() * 20.0D) == 0)
       GameScreen.rainBitcoin(); 
   }
   
   public static void addBitcoins(float amount) {
     Achievements.lifetime_coins += amount;
     bitcoins += amount;
   }
   
   public static float getBitcoins() {
     return (float)bitcoins;
   }
   
   public static void setBitcoins(float bitcoins) {
     Game.bitcoins = bitcoins;
   }
   
   public static float getBitcoins_per_second() {
     return bitcoins_per_second;
   }
   
   public static void setBitcoins_per_second(float bitcoins_per_second) {
     Game.bitcoins_per_second = bitcoins_per_second;
   }
   
   private static float bitcoins_per_click = 1.0F;
   
   public static void setBitCoinsPerClick(float bpc) {
     bitcoins_per_click = bpc;
   }
   
   public static float getBitCoinsPerClick() {
     return bitcoins_per_click;
   }
 }
