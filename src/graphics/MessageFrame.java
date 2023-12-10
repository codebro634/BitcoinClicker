 package graphics;
 
 import java.awt.Color;
 import java.awt.Component;
 import java.awt.Font;
 import java.awt.Graphics;
 import java.awt.Rectangle;
 import java.awt.image.BufferedImage;
 import javax.swing.JFrame;
 import javax.swing.JPanel;
 
 
 
 public class MessageFrame
   extends JPanel
 {
   private static final long serialVersionUID = 1L;
   private static JFrame frame;
   private static String[] display;
   private static BufferedImage img;
   private static boolean shutdown_on_close;
   
   public static void display(String[] display, BufferedImage img, boolean shutdown) {
     if (frame != null)
       close(); 
     MessageFrame.display = display;
     MessageFrame.img = img;
     shutdown_on_close = shutdown;
     frame = new JFrame(display[2]);
     if (shutdown)
       frame.setDefaultCloseOperation(3); 
     frame.getContentPane().add(new MessageFrame());
     frame.setResizable(false);
     frame.setVisible(true);
     frame.addMouseListener(Event.e);
     frame.setSize(500, 200);
     frame.setIconImage(img);
     frame.setLocationRelativeTo((Component)null);
   }
   
   public static void close() {
     frame.dispose();
     if (shutdown_on_close)
       System.exit(0); 
   }
   
   public static Rectangle getConfirmPos() {
     return new Rectangle(420, 25, 50, 50);
   }
   
   public void paintComponent(Graphics g) {
     g.clearRect(0, 0, getWidth(), getHeight());
     g.fillRect(0, 0, getWidth(), getHeight());
     g.setColor(Color.WHITE);
     g.setFont(new Font("Arial", 1, 30));
     g.drawImage(img, 15, 15, 75, 75, null);
     g.drawString(display[0], 120, 60);
     g.setFont(new Font("Arial", 0, 15));
     g.drawString(display[1], 15, 135);
     Rectangle pos = getConfirmPos();
     g.setColor(Color.black);
     g.fillRect(pos.x, pos.y, pos.width, pos.height);
     g.setColor(Color.gray);
     g.fillRect(pos.x + 5, pos.y + 5, pos.width - 10, pos.height - 10);
     g.setColor(Color.WHITE);
     g.drawString("OK", pos.x + pos.width / 5, pos.y + pos.height / 2 + 5);
   }
 }
