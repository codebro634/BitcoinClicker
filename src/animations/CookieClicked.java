 package animations;
 
 import control.Resources;
 import graphics.ContentPane;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics;
 
 
 public class CookieClicked
   extends Animation
 {
   private static final int SIZE = ContentPane.convertToScreenX(5.0F);
   
   private final float amount_gained;
   
   private int cookieX;
   private int cookieY;
   private final boolean leftOrRight = ((int)(Math.random() * 2.0D) == 0);
   private int x;
   private int y;
   
   public CookieClicked(long delay, int prio, int x, int y, float amount_gained) {
     super(delay, prio);
     this.x = x;
     this.y = y;
     this.cookieX = x - SIZE / 2;
     this.cookieY = y - SIZE / 2;
     this.amount_gained = amount_gained;
   }
   
   public void draw(Graphics g) {
     g.drawImage(Resources.bitcoin, this.cookieX, this.cookieY, SIZE, SIZE, null);
     if (getAge() >= 1000L) {
       g.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F - (float)(getAge() - 1000L) / 2000.0F));
     } else {
       g.setColor(Color.WHITE);
     }  g.setFont(new Font("Cambria", 1, ContentPane.convertToScreenX(3.0F)));
     g.drawString("+" + this.amount_gained, this.x, this.y);
   }
   
   public void render() {
     if (getAge() >= 3000L) {
       setActive(false);
       return;
     } 
     if (this.leftOrRight) {
       this.cookieX += ContentPane.convertToScreenX(0.2F);
     } else {
       this.cookieX -= ContentPane.convertToScreenX(0.2F);
     }  this.cookieY -= ContentPane.convertToScreenY(1.0F);
     this.cookieY = (int)(this.cookieY + Math.pow(ContentPane.convertToScreenX(1.0F), ((float)getAge() / 2000.0F * 3.0F)));
     this.y -= ContentPane.convertToScreenY(0.45F);
   }
 }
