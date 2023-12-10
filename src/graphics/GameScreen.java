 package graphics;
 
 import animations.Animation;
 import animations.AnimationSupervisor;
 import animations.BitcoinRain;
 import animations.CookieClicked;
 import control.EventManager;
 import control.Resources;
 import control.Setup;
 import control.Soundboard;
 import game.Achievements;
 import game.BittieBitcoin;
 import game.Game;
 import game.GoldenBitcoin;
 import game.HoverUpdate;
 import game.StateSupervisor;
 import game.Upgrades;
 import java.awt.BasicStroke;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.Rectangle;
 import java.awt.image.BufferedImage;
 import java.math.BigDecimal;
 import java.util.LinkedList;
 
 
 
 
 
 public class GameScreen
 {
   public static final String BASIC_TEXTSTYLE = "Cambria";
   public static final byte GAMESTATE_INDEX = 1;
   
   public static void render(Graphics g) {
     g.drawImage(Resources.background, 0, 0, ContentPane.convertToScreenX(100.0F), ContentPane.convertToScreenY(100.0F), null);
     AnimationSupervisor.drawLowPrio(g);
     drawBitcoinRain(g);
     drawCookie(g);
     drawBitcoinGraph(g);
     drawMiscellaneous(g);
     drawUpgradesVisualization(g);
     drawUpgrades(g);
     drawAchievement(g);
     GoldenBitcoin.draw(g);
     BittieBitcoin.paint(g);
   }
   
   private static void drawUpgradesVisualization(Graphics g) {
     Rectangle pos = getUpgradeVisualizationPos();
     g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
     g.fillRect(pos.x, pos.y, pos.width, pos.height);
     for (int i = 0; i < Upgrades.getTotalUpgrades(); i++) {
       Rectangle area = new Rectangle(pos.x, (int)(pos.y + i * pos.height / 9.98F), pos.width, pos.height / 10);
 
       
       for (int j = 0; j < Upgrades.getUpgradeBought(i); j++) {
         int width = (int)(area.height / 2.0F);
         int height = width;
         int y = (j % 2 == 0) ? area.y : (area.y + height / 2);
         y += height / 4;
         int x = area.x + width / 2 * j;
         if (x + width >= area.width + area.x)
           break; 
         g.drawImage(Upgrades.getUpgradeImage(i), x, y, width, height, null);
       } 
     } 
     drawCoinSurrounding(g, Resources.cursor_default, Upgrades.getUpgradeBought(0));
   }
   
   public static float shift = 0.0F;
   private static long[] draw_in = new long[1];
   public static long ticks = 0L;
   
   private static void drawCoinSurrounding(Graphics g, BufferedImage img, int count) {
     if (count > 75)
       count = 75; 
     Rectangle middle = getCookiePos();
     int size = ContentPane.convertToScreenX(2.0F);
     int max_draw_in = ContentPane.convertToScreenX(0.5F);
     float draw_in_time = 10.0F, rest_time_min = 100.0F;
     if (draw_in.length != count) {
       draw_in = new long[count];
       for (int j = 0; j < draw_in.length; j++)
         draw_in[j] = ticks + (int)(Math.random() * rest_time_min); 
     } 
     double radius = (middle.width / 2 + size / 2 + ContentPane.convertToScreenX(0.5F));
     double gaps = 6.283185307179586D / count;
     int middlepoint_x = middle.x + middle.width / 2;
     int middlepoint_y = middle.y + middle.height / 2;
     for (int i = 0; i < count; i++) {
       int x = (int)(middlepoint_x + Math.cos(i * gaps + shift) * radius);
       int y = (int)(middlepoint_y + Math.sin(i * gaps + shift) * radius);
       long cT = ticks, lT = draw_in[i];
       if (cT - lT > 0L)
         if ((float)(cT - lT) <= draw_in_time) {
           x = (int)(x + ((float)(cT - lT) / draw_in_time * (middlepoint_x - x)) * max_draw_in / radius);
           y = (int)(y + ((float)(cT - lT) / draw_in_time * (middlepoint_y - y)) * max_draw_in / radius);
         }
         else if ((float)(cT - lT) <= draw_in_time * 2.0F) {
           double xM = (middlepoint_x - x) * max_draw_in / radius;
           x = (int)(x + xM - (((float)(cT - lT) - draw_in_time) / draw_in_time * (middlepoint_x - x)) * max_draw_in / radius);
           double yM = (middlepoint_y - y) * max_draw_in / radius;
           y = (int)(y + yM - (((float)(cT - lT) - draw_in_time) / draw_in_time * (middlepoint_y - y)) * max_draw_in / radius);
         }
         else if ((float)(cT - lT) > draw_in_time * 2.0F + rest_time_min) {
           draw_in[i] = ticks;
         }  
       g.drawImage(Resources.cursor_angles.get((int)Math.toDegrees(i * gaps + shift) % 360), x - size / 2, y - size / 2, size, size, null);
     } 
   }
   
   private static void drawMiscellaneous(Graphics g) {
     Rectangle ach = getAchievementIconPos();
     Rectangle frame = getTopGrid();
     Rectangle options = getOptionsPos();
     Rectangle credits = getCreditsPos();
     g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
     g.fillRect(frame.x, frame.y, frame.width, frame.height);
     g.setColor(new Color(0.5F, 0.5F, 0.5F, 0.25F));
     g.fillRect(ach.x, ach.y, ach.width, ach.height);
     g.fillRect(options.x, options.y, options.width, options.height);
     g.fillRect(credits.x, credits.y, credits.width, credits.height);
     if (HoverUpdate.isAchievementHovered() || HoverUpdate.isCreditsHovered() || HoverUpdate.isOptionsHovered())
       g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F)); 
     if (HoverUpdate.isAchievementHovered()) {
       g.fillRect(ach.x, ach.y, ach.width, ach.height);
     } else if (HoverUpdate.isOptionsHovered()) {
       g.fillRect(options.x, options.y, options.width, options.height);
     } else if (HoverUpdate.isCreditsHovered()) {
       g.fillRect(credits.x, credits.y, credits.width, credits.height);
     }  g.drawImage(Resources.pokal, ach.x, ach.y, ach.width, ach.height, null);
     if (Soundboard.isMuted()) {
       g.drawImage(Resources.muted, options.x, options.y, options.width, options.height, null);
     } else {
       g.drawImage(Resources.soundbutton, options.x, options.y, options.width, options.height, null);
     }  g.drawImage(Resources.blumentopf, credits.x, credits.y, credits.width, credits.height, null);
   }
 
 
 
 
 
 
   
   private static void drawAchievement(Graphics g) {
     Rectangle bounds = getAchievementPos();
     for (int i = 0; i < Achievements.showing.size(); i++) {
       BufferedImage img = Achievements.getImage(((Integer)Achievements.showing.get(i)).intValue());
       String name = Achievements.getName(((Integer)Achievements.showing.get(i)).intValue());
       g.setColor(Color.ORANGE);
       g.fillRect(bounds.x, bounds.y - bounds.height * i, bounds.width, bounds.height);
       g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
       g.fillRect(bounds.x + bounds.height / 20, bounds.y - bounds.height * i + bounds.height / 20, bounds.width - bounds.height / 10, bounds.height - bounds.height / 10);
       g.drawImage(img, bounds.x + bounds.height / 6, bounds.y - bounds.height * i + bounds.height / 6, (int)(bounds.height / 1.5D), (int)(bounds.height / 1.5D), null);
       g.setFont(new Font("Cambria", 1, (int)(bounds.height / 4.0F)));
       g.setColor(Color.WHITE);
       drawYCenteredString(g, "Erfolg freigeschaltet:", (int)(bounds.x + bounds.height * 1.25F), bounds.y - bounds.height * i, bounds.height / 2);
       g.setFont(new Font("Cambria", 0, (int)(bounds.height / 4.5D)));
       g.setColor(Color.GREEN);
       
       Rectangle area = new Rectangle(bounds.x + bounds.height / 2, bounds.y - bounds.height * i + bounds.height / 2, bounds.width - bounds.height / 2, bounds.height / 3);
       drawCenteredString(g, name, area);
       g.setColor(new Color(255, 165, 0));
       Rectangle areaClose = getCloseInAchievement(new Rectangle(bounds.x, bounds.y - bounds.height * i, bounds.width, bounds.height));
       g.fillRoundRect(areaClose.x, areaClose.y, areaClose.width, areaClose.height, 5, 5);
       g.setColor(Color.BLACK);
       ((Graphics2D)g).setStroke(new BasicStroke(1.0F));
       g.drawLine(areaClose.x, areaClose.y, areaClose.x + areaClose.width, areaClose.y + areaClose.height);
       g.drawLine(areaClose.x + areaClose.width, areaClose.y, areaClose.x, areaClose.y + areaClose.height);
       if (HoverUpdate.getHoverIndexAchievements()[0] == i) {
         g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
         if (HoverUpdate.getHoverIndexAchievements()[1] == 0) {
           g.fillRect(bounds.x, bounds.y - bounds.height * i, bounds.width, bounds.height);
         } else if (HoverUpdate.getHoverIndexAchievements()[1] == 1) {
           g.fillRoundRect(areaClose.x, areaClose.y, areaClose.width, areaClose.height, 5, 5);
         } 
       } 
     } 
   }
   
   private static LinkedList<Double> values = new LinkedList<>();
   public static int shift_x_graph = 0;
   
   private static void drawBitcoinGraph(Graphics g) {
     Rectangle bounds = getGraphPos();
     int vertex_count = 160;
     int vertex_size = bounds.height / 20;
     float vertex_dist = bounds.width / vertex_count;
     if (shift_x_graph++ >= vertex_dist) {
       shift_x_graph = 0;
       values.remove(0);
     } 
     while (values.size() < vertex_count) {
       double factor = Game.f_bps_upper_scaling_limit();
       if (factor < 0.05D)
         factor = 0.05D; 
       values.add(Double.valueOf(Math.random() * factor));
     } 
     g.setColor(Color.BLACK);
     g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
     g.setColor(Color.WHITE);
     g.fillRect(bounds.x + vertex_size, bounds.y + vertex_size, bounds.width - 2 * vertex_size, bounds.height - 2 * vertex_size);
     g.setColor(Color.BLACK);
     Graphics2D g2 = (Graphics2D)g;
     int line_size = 5;
     g2.setStroke(new BasicStroke(line_size));
     for (int i = 0; i < values.size() - 1; i++) {
       int x1 = (int)(bounds.x + vertex_dist - shift_x_graph + line_size + vertex_dist * i);
       int x2 = (int)(x1 + vertex_dist);
       if (x2 + line_size <= bounds.x + bounds.width)
       {
         g2.drawLine(x1, bounds.y + bounds.height - (int)(((Double)values.get(i)).doubleValue() * (bounds.height - vertex_size - vertex_size)) - vertex_size, 
             x2, bounds.y + bounds.height - (int)(((Double)values.get(i + 1)).doubleValue() * (bounds.height - vertex_size - vertex_size)) - vertex_size); } 
     } 
   }
   
   private static void drawBitcoinRain(Graphics g) {
     Rectangle cookie = getCookiePos();
     double factor = Game.f_bps_upper_scaling_limit();
     float falling_speed = (float)(5.0D * factor);
     float frequency = (factor == 0.0D) ? 1000000.0F : (float)(99.0D - factor * 99.0D + 1.0D);
     if (frequency > (Setup.getTicksPerSecond() * 30))
       frequency = (Setup.getTicksPerSecond() * 30); 
     if (Game.getBitcoins_per_second() > 0.0F && (int)(Math.random() * (frequency / falling_speed * 2.0F)) == 0)
       AnimationSupervisor.animate((Animation)new BitcoinRain(0L, -1, cookie.x - cookie.width, cookie.x + cookie.width + cookie.width, falling_speed)); 
   }
   
   public static void rainBitcoin() {
     Rectangle cookie = getCookiePos();
     AnimationSupervisor.animate((Animation)new BitcoinRain(0L, -1, cookie.x - cookie.width, cookie.x + cookie.width + cookie.width, 0.5F));
   }
   
   private static void drawUpgrades(Graphics g) {
     g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
     Rectangle area = getRightRectPos();
     g.fillRect(area.x, area.y, area.width, area.height);
     for (int i = 0; i < Upgrades.getTotalUpgrades(); i++)
       drawSpecificUpgrade(g, new Rectangle(area.x, (int)(area.y + i * area.height / 9.98F), area.width, area.height / 10), i, (i == HoverUpdate.getHoverIndexRightRect())); 
   }
   
   public static int getRightRectIndex(Rectangle intersection) {
     Rectangle area = getRightRectPos();
     for (int i = 0; i < Upgrades.getTotalUpgrades(); i++) {
       if ((new Rectangle(area.x, (int)(area.y + i * area.height / 9.98F), area.width, area.height / 10)).intersects(intersection))
         return i; 
     } 
     return -1;
   }
   
   public static int[] getAchievementIndex(Rectangle intersection) {
     Rectangle area = getAchievementPos();
     for (int i = 0; i < Achievements.showing.size(); i++) {
       Rectangle ach = new Rectangle(area.x, area.y - area.height * i, area.width, area.height);
       if (ach.intersects(intersection)) {
         Rectangle cross = getCloseInAchievement(new Rectangle(ach));
         if (cross.intersects(intersection))
           return new int[] { i, 1 }; 
         return new int[] { i };
       } 
     } 
     return new int[] { -1 };
   }
   
   public static int getStringHeight(Graphics g, String s) {
     return 
       (int)g.getFont().createGlyphVector(g.getFontMetrics().getFontRenderContext(), s).getVisualBounds().getHeight();
   }
   
   public static int getStringWidth(Graphics g, String s) {
     return g.getFontMetrics().stringWidth(s);
   }
   
   public static float round(float d, int decimalPlace) {
     BigDecimal bd = new BigDecimal(Float.toString(d));
     bd = bd.setScale(decimalPlace, 4);
     return bd.floatValue();
   }
   private static void drawSpecificUpgrade(Graphics g, Rectangle area, int index, boolean hovered_over) {
     String str1;
     g.drawImage(Upgrades.getUpgradeImage(index), area.x, area.y, area.height, area.height, null);
     g.setColor(Color.WHITE);
     g.setFont(new Font("Cambria", 1, (int)(area.height / 3.2F)));
     String name = Upgrades.getUpgradeName(index);
     int string_height = getStringHeight(g, name);
     g.drawString(name, area.x + area.height + area.height / 8, area.y + string_height + (area.height / 2 - string_height) / 2);
     long cost = Upgrades.getUpgradeCost(index);
     long l1 = cost;
     if (EventManager.isCtrDown()) {
       cost = Upgrades.getUpgradeCost(index, 10);
       str1 = String.valueOf(cost) + " [x10]";
     }
     else if (EventManager.isShiftDown()) {
       cost = Upgrades.getUpgradeCost(index, 100);
       str1 = String.valueOf(cost) + " [x100]";
     } 
     if (Game.getBitcoins() >= (float)cost) {
       g.setColor(Color.GREEN);
     } else {
       g.setColor(Color.RED);
     }  g.setFont(new Font("Cambria", 1, area.height / 4));
     string_height = getStringHeight(g, str1);
     g.drawString(str1, area.x + area.height + area.height / 8 + area.height / 4 + area.height / 14, area.y + area.height / 2 + string_height + (area.height / 4 - string_height) / 2);
     g.drawImage(Resources.bitcoin, area.x + area.height + area.height / 8, area.y + area.height / 2, area.height / 4, area.height / 4, null);
     g.setColor(Color.WHITE);
     g.setFont(new Font("Cambria", 1, area.height / 4));
     drawCenteredString(g, Upgrades.getUpgradeBought(index), new Rectangle(area.x + area.width - area.height + area.height / 8, area.y - area.height / 8, area.height - area.height / 6, area.height));
     if (hovered_over) {
       g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
       g.fillRect(area.x, area.y, area.width, area.height);
       Upgrades.drawUpgradeInfoText(g, area.x - area.width * 3, area.y, area.width * 3, index);
     } 
   }
   
   public static Rectangle getRightRectPos() {
     int width = ContentPane.convertToScreenX(20.0F);
     int height = ContentPane.convertToScreenY(90.0F);
     int xpos = ContentPane.convertToScreenX(78.0F);
     int ypos = ContentPane.convertToScreenY(5.0F);
     return new Rectangle(xpos, ypos, width, height);
   }
   
   public static Rectangle getUpgradeVisualizationPos() {
     int width = ContentPane.convertToScreenX(20.0F);
     int height = ContentPane.convertToScreenY(90.0F);
     int xpos = ContentPane.convertToScreenX(2.0F);
     int ypos = ContentPane.convertToScreenY(5.0F);
     return new Rectangle(xpos, ypos, width, height);
   }
   
   public static Rectangle getCookiePos() {
     int size = ContentPane.convertToScreenX(20.0F);
     int xpos = (ContentPane.convertToScreenX(100.0F) - size) / 2;
     int ypos = (ContentPane.convertToScreenY(104.0F) - size) / 2;
     return new Rectangle(xpos, ypos, size, size);
   }
   
   public static Rectangle getGraphPos() {
     int width = ContentPane.convertToScreenX(50.0F);
     int height = ContentPane.convertToScreenY(20.0F);
     int x = ContentPane.convertToScreenX(25.0F);
     int y = ContentPane.convertToScreenY(74.0F);
     return new Rectangle(x, y, width, height);
   }
   
   public static Rectangle getAchievementPos() {
     int width = ContentPane.convertToScreenX(25.0F);
     int height = ContentPane.convertToScreenY(10.0F);
     int x = ContentPane.convertToScreenX(37.5F);
     int y = ContentPane.convertToScreenY(90.0F);
     return new Rectangle(x, y, width, height);
   }
   
   public static Rectangle getCloseInAchievement(Rectangle ach) {
     int x = ach.x + ach.width - ach.height / 4 - ach.height / 20 - 3;
     int y = ach.y + ach.height - ach.height / 20 - ach.height / 4 - 3;
     int width = ach.height / 4, height = ach.height / 4;
     return new Rectangle(x, y, width, height);
   }
   
   public static Rectangle getAchievementIconPos() {
     Rectangle grid = getTopGrid();
     int width = grid.height - 2 * getTopGridFrameWidth;
     int height = grid.height - 2 * getTopGridFrameWidth;
     int x = grid.x + getTopGridFrameWidth;
     int y = getTopGridFrameWidth;
     return new Rectangle(x, y, width, height);
   }
   
   public static Rectangle getOptionsPos() {
     Rectangle grid = getTopGrid();
     Rectangle achievements = getAchievementIconPos();
     int width = grid.height - 2 * getTopGridFrameWidth;
     int height = grid.height - 2 * getTopGridFrameWidth;
     int x = getTopGridFrameWidth + achievements.x + achievements.width;
     int y = getTopGridFrameWidth;
     return new Rectangle(x, y, width, height);
   }
   
   public static Rectangle getCreditsPos() {
     Rectangle grid = getTopGrid();
     Rectangle options = getOptionsPos();
     int width = grid.height - 2 * getTopGridFrameWidth;
     int height = grid.height - 2 * getTopGridFrameWidth;
     int x = getTopGridFrameWidth + options.x + options.width;
     int y = getTopGridFrameWidth;
     return new Rectangle(x, y, width, height);
   }
   
   private static final int getTopGridFrameWidth = ContentPane.convertToScreenX(0.65F); private static long last_cookie_click;
   
   public static Rectangle getTopGrid() {
     int width = ContentPane.convertToScreenX(13.0F);
     int height = ContentPane.convertToScreenY(8.0F);
     int x = ContentPane.convertToScreenX(100.0F) / 2 - width / 2;
     int y = 0;
     return new Rectangle(x, y, width, height);
   }
 
   
   private static void drawCookie(Graphics g) {
     Rectangle pos = getCookiePos();
     int size = pos.width;
     int xpos = pos.x;
     int ypos = pos.y;
     
     g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
     g.fillRect(xpos - size / 2, (int)(ypos - size / 1.75F), size * 2, size / 3);
     g.setColor(Color.WHITE);
     g.setFont(new Font("Cambria", 1, size / 5));
     drawCenteredString(g, String.valueOf(round(Game.getBitcoins(), 1)) + " Bitcoins", new Rectangle(xpos, (int)(ypos - size / 1.75F), size, size / 6));
     g.setFont(new Font("Cambria", 1, size / 8));
     drawCenteredString(g, "pro Sekunde: " + round(Game.getBitcoins_per_second(), 1), 
         new Rectangle(xpos, (int)(ypos - size / 1.75F) + size / 6, size, size / 6));
     if (StateSupervisor.getTime() - last_cookie_click < 100L) {
       int size_diff = ContentPane.convertToScreenX(3.0F);
       size -= size_diff;
       xpos += size_diff / 2;
       ypos += size_diff / 2;
     } 
     g.drawImage(Resources.bitcoin, xpos, ypos, size, size, null);
   }
   
   public static void drawCenteredString(Graphics g, String text, Rectangle area) {
     int string_length = g.getFontMetrics().stringWidth(text);
     int string_height = getStringHeight(g, text);
     g.drawString(text, area.x + (area.width - string_length) / 2, 
         area.y + string_height + (area.height - string_height) / 2);
   }
   
   public static void drawYCenteredString(Graphics g, String text, int x, int y, int height) {
     int string_height = getStringHeight(g, text);
     g.drawString(text, x, 
         y + string_height + (height - string_height) / 2);
   }
 
 
   
   public static void onCookieClicked() {
     last_cookie_click = StateSupervisor.getTime();
     AnimationSupervisor.animate((Animation)new CookieClicked(0L, 1, EventManager.getMouseX() + (int)(Math.random() * ContentPane.convertToScreenX(2.0F)), EventManager.getMouseY(), round(Game.getBitCoinsPerClick(), 1)));
   }
 }
