 package control;
 import java.awt.image.BufferedImage;
 import java.util.ArrayList;
 import javax.sound.sampled.Clip;
 
 public class Resources {
   public static BufferedImage icon_image;
   public static BufferedImage cursor_default;
   public static BufferedImage cursor_pressed_down;
   public static BufferedImage bitcoin;
   public static BufferedImage background;
   public static BufferedImage stockmarket;
   public static BufferedImage euro;
   public static BufferedImage kot;
   public static BufferedImage transparent_bitcoin;
   public static BufferedImage pokal;
   public static BufferedImage options;
   public static BufferedImage blumentopf;
   public static BufferedImage fragezeichen;
   public static BufferedImage back;
   public static BufferedImage golden_bitcoin;
   public static BufferedImage gammelpc;
   public static BufferedImage gpu;
   public static BufferedImage soundbutton;
   public static ArrayList<BufferedImage> cursor_angles = new ArrayList<>(); public static BufferedImage muted; public static BufferedImage error; public static BufferedImage cheats_symbol; public static BufferedImage michael_jackson; public static BufferedImage spongebozz; public static BufferedImage kollegah; public static BufferedImage empty; public static BufferedImage miningchip2; public static BufferedImage asci; public static BufferedImage cloud; public static BufferedImage sekzy; public static BufferedImage idgaf; public static BufferedImage placeholder; public static BufferedImage bittie; public static BufferedImage poolm; public static BufferedImage battery; public static BufferedImage halle; public static BufferedImage skyscraper; public static ArrayList<BufferedImage> achievement_imgs = new ArrayList<>(); public static String test_info; public static String spirituelles_laufen;
   public static String seccomom;
   public static String helloworld;
   public static String spongebozz_info;
   public static String[] achievement_texte = new String[11]; public static String[] upgrade_texte = new String[10]; public static String[] bittie_texte = new String[7]; public static Soundboard diesound; public static Soundboard hubmusic; public static Soundboard blop; public static Soundboard boughtsound;
   public static Soundboard hooray;
   public static Soundboard magic;
   public static final int MAX_RESOURCES = 84;
   
   public static void loadPreGameResources() {
     empty = new BufferedImage(16, 16, 2);
     icon_image = readImage("images/bitcoin_icon_image.png");
     background = readImage("images/background.png");
     cursor_default = readImage("images/cursor_default.png");
     cursor_pressed_down = readImage("images/cursor_pressed_down.png");
   }
 
   
   private static int resources_loaded_count = 0;
   
   public static int getResourcesLoadedCount() {
     return resources_loaded_count;
   }
 
   
   public static void loadGameResources() {
     int i;
     for (i = 0; i <= 360; i++)
       cursor_angles.add(rotate(cursor_default, (Math.toRadians(i) - 45.0D) % 360.0D)); 
     bitcoin = readImage("images/bitcoin.png");
     achievement_imgs.add(bitcoin);
     for (i = 2; i <= 11; i++)
       achievement_imgs.add(readImage("images/Bitcoin " + i + " Bild.png")); 
     stockmarket = readImage("images/stockmarket.png");
     idgaf = readImage("images/idgaf.png");
     cloud = readImage("images/cloud.png");
     halle = readImage("images/lagerhalle.png");
     poolm = readImage("images/poolm.png");
     battery = readImage("images/bat.png");
     skyscraper = readImage("images/skyscraper.png");
     miningchip2 = readImage("images/miningchip2.png");
     placeholder = readImage("images/placeHolder.png");
     bittie = readImage("images/bittie.png");
     euro = readImage("images/1euro.png");
     kot = readImage("images/kot.png");
     sekzy = readImage("images/sekzy.jpg");
     pokal = readImage("images/pokal.png");
     asci = readImage("images/asci.png");
     kollegah = readImage("images/kollegah.jpg");
     spongebozz = readImage("images/spongebozz.jpg");
     michael_jackson = readImage("images/michael_jackson.jpg");
     error = readImage("images/err.png");
     soundbutton = readImage("images/soundbutton.png");
     cheats_symbol = readImage("images/cheats_symbol.png");
     muted = readImage("images/muted.png");
     blumentopf = readImage("images/blumentopf.png");
     gammelpc = readImage("images/gammelpc.png");
     options = readImage("images/options.png");
     back = readImage("images/back.png");
     gpu = readImage("images/gpu.png");
     golden_bitcoin = readImage("images/golden_bitcoin.png");
     fragezeichen = readImage("images/fragezeichen.png");
     transparent_bitcoin = readImage("images/bitcoin.png");
     setTransparency(transparent_bitcoin, 0.25F);
     
     for (i = 0; i < bittie_texte.length; i++)
       bittie_texte[i] = readTextFile("info_texte/bittie" + (i + 1) + ".txt"); 
     test_info = readTextFile("info_texte/test.txt");
     spirituelles_laufen = readTextFile("info_texte/spirituelles_laufen.txt");
     seccomom = readTextFile("info_texte/seccomom.txt");
     helloworld = readTextFile("info_texte/helloworld.txt");
     spongebozz_info = readTextFile("info_texte/spongebozz.txt");
     for (i = 1; i <= achievement_texte.length; i++)
       achievement_texte[i - 1] = readTextFile("info_texte/achievement" + i + ".txt"); 
     for (i = 1; i <= upgrade_texte.length; i++) {
       upgrade_texte[i - 1] = readTextFile("info_texte/upgrade" + i + ".txt");
     }
     diesound = readMusic("sounds/diesound.wav");
     hubmusic = readMusic("sounds/hubmusic.wav");
     blop = readMusic("sounds/blop.wav");
     boughtsound = readMusic("sounds/boughtsound.wav");
     hooray = readMusic("sounds/hooray.wav");
     magic = readMusic("sounds/magic.wav");
   }
   
   private static Soundboard readMusic(String filename) {
     try {
       AudioInputStream audioStream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource(filename));
       AudioFormat format = audioStream.getFormat();
       DataLine.Info info = new DataLine.Info(Clip.class, format);
       Clip audioClip = (Clip)AudioSystem.getLine(info);
       Soundboard sb = new Soundboard(audioClip);
       audioClip.addLineListener(sb);
       audioClip.open(audioStream);
       resources_loaded_count++;
       return sb;
     } catch (Exception e) {
       e.printStackTrace();
       
       return null;
     } 
   }
   
   private static String readTextFile(String file) {
     StringBuilder sb = new StringBuilder();
     try {
       InputStream is = ClassLoader.getSystemResourceAsStream(file);
       BufferedReader r = new BufferedReader(new InputStreamReader(is));
       while (r.ready())
         sb.append(r.readLine()); 
     } catch (Exception e) {
       e.printStackTrace();
     } 
     resources_loaded_count++;
     return sb.toString();
   }
   
   private static BufferedImage readImage(String name) {
     try {
       resources_loaded_count++;
       return ImageIO.read(ClassLoader.getSystemResourceAsStream(name));
     } catch (Exception e) {
       e.printStackTrace();
       return null;
     } 
   }
   
   private static void setTransparency(BufferedImage img, float amount) {
     for (int x = 0; x < img.getWidth(); x++) {
       for (int y = 0; y < img.getHeight(); y++) {
         int argb = img.getRGB(x, y);
         int alpha = argb >> 24 & 0xFF;
         alpha = (int)(alpha * amount);
         alpha &= 0xFF;
         argb &= 0xFFFFFF;
         argb |= alpha << 24;
         img.setRGB(x, y, argb);
       } 
     } 
   }
 
   
   private static BufferedImage rotate(BufferedImage img, double angle) {
     AffineTransform tx = new AffineTransform();
     tx.rotate(angle, (img.getWidth() / 2), (img.getHeight() / 2));
     AffineTransformOp op = new AffineTransformOp(tx, 2);
     return op.filter(img, (BufferedImage)null);
   }
 }