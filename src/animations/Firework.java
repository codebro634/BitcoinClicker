 package animations;
 
 import java.awt.Graphics;
 import java.util.Stack;
 
 
 public class Firework
   extends Animation
 {
   private static final int COUNT = 45;
   private final Stack<Body> bodies;
   
   public Firework(long delay, int priority, int x, int y) {
     super(delay, priority);
 
 
 
     
     this.bodies = new Stack<>();
     for (int i = 0; i < 45; i++)
       this.bodies.push(new Body(x, y));  } public void draw(Graphics g) {
     for (Body b : this.bodies)
       b.draw(g); 
   }
   
   public void render() {
     for (Body b : this.bodies)
       b.tick(); 
     if (getAge() >= 5000L)
       setActive(false); 
   }
 }
