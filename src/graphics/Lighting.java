 package graphics;
 
 import java.awt.AlphaComposite;
 import java.awt.Color;
 import java.awt.Graphics2D;
 import java.awt.RadialGradientPaint;
 import java.awt.geom.Point2D;
 
 public class Lighting
 {
   public static void drawStaticLightSource(Graphics2D g2d, Point2D.Float center, int initial_brightness, int radius_light) {
     if (initial_brightness >= 0) {
       float[] dist = { 0.0F, 1.0F };
       Color[] colors = { new Color(0.0F, 0.0F, 0.0F, initial_brightness), Color.BLACK };
       RadialGradientPaint p = new RadialGradientPaint(center, radius_light, dist, colors);
       g2d.setPaint(p);
       g2d.setComposite(AlphaComposite.getInstance(3, 1.0F));
       g2d.fillRect(0, 0, ContentPane.convertToScreenX(100.0F), ContentPane.convertToScreenY(100.0F));
       g2d.dispose();
     } 
   }
 }
