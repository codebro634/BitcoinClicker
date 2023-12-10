 package animations;
 
 import control.Resources;
 import graphics.ContentPane;
 import java.awt.Graphics;
 import java.awt.image.BufferedImage;
 
 public class PictureRain
   extends Animation
 {
   private static final int SIZE = ContentPane.convertToScreenX(7.0F);
   
   private static final BufferedImage[] imgs = new BufferedImage[] { Resources.bittie, Resources.golden_bitcoin, Resources.fragezeichen, Resources.gammelpc, Resources.pokal, Resources.asci };
   
   private final int x;
   
   private final float falling_speed;
   
   private float y = (-2 * SIZE);
   
   private final BufferedImage img = imgs[(int)(Math.random() * imgs.length)];
   
   public PictureRain(long delay, int prio, int x_min, int x_max, float falling_speed) {
     super(delay, prio);
     this.x = (int)(Math.random() * (x_max - x_min) + x_min);
     this.falling_speed = falling_speed;
   }
   
   public void draw(Graphics g) {
     g.drawImage(this.img, this.x, (int)this.y, SIZE, SIZE, null);
   }
   
   public void render() {
     this.y += this.falling_speed;
     if (this.y - SIZE > ContentPane.convertToScreenY(100.0F))
       setActive(false); 
   }
 }