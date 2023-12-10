 package animations;
 import java.awt.Graphics;
 import java.awt.image.BufferedImage;
 
 public class ImageAnimation extends Animation {
   private final long time;
   
   public ImageAnimation(long delay, long time, int priority, int x, int y, int width, int height, BufferedImage image) {
     super(delay, priority);
     this.x = x;
     this.y = y;
     this.width = width;
     this.height = height;
     this.image = image;
     this.time = time;
   }
   private final int x; private final int y;
   private final int width;
   private final int height;
   private final BufferedImage image;
   
   public void draw(Graphics g) {
     g.drawImage(this.image, this.x, this.y, this.width, this.height, null);
   }
   
   public void render() {
     if (getAge() > this.time)
       setActive(false); 
   }
 }
