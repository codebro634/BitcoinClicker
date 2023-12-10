 package animations;
 
 import control.Resources;
 import graphics.ContentPane;
 import java.awt.Graphics;
 
 public class BitcoinRain
   extends Animation
 {
   private static final int SIZE = ContentPane.convertToScreenX(3.0F);
   
   private final int x;
   
   private final float falling_speed;
   
   private float y = (-2 * SIZE);
   
   public BitcoinRain(long delay, int prio, int x_min, int x_max, float falling_speed) {
     super(delay, prio);
     this.x = (int)(Math.random() * (x_max - x_min) + x_min);
     this.falling_speed = falling_speed;
   }
   
   public void draw(Graphics g) {
     g.drawImage(Resources.transparent_bitcoin, this.x, (int)this.y, SIZE, SIZE, null);
   }
   
   public void render() {
     this.y += this.falling_speed;
     if (this.y - SIZE > ContentPane.convertToScreenY(100.0F))
       setActive(false); 
   }
 }