//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

public class Stats {
    private int hpMax;
    private int successfulStrikeValue;
    private int hpActual;
    private int sv;
    private int level = 1;
    private int dp;
    private int sp;
    private String type;

    public int getHpMax() {
        return this.hpMax;
    }

    public int getHpActual() {
        return this.hpActual;
    }

    public void setSv(int sv) {
        this.sv = sv;
    }

    public int getSv() {
        return this.sv;
    }

    public int getLevel() {
        return this.level;
    }

    public int getDp() {
        return this.dp;
    }

    public int getSp() {
        return this.sp;
    }

    public String getType() {
        return this.type;
    }

    public void setSuccessfulStrikeValue(int successfulStrikeValue) {
        this.successfulStrikeValue = successfulStrikeValue;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public void setHpActual(int hpActual) {
        this.hpActual = hpActual;
    }

    public void setLevel(int level) {
        this.level = level;
        this.hpActual = this.hpActual+ThreadLocalRandom.current().nextInt(1, 5);
        this.dp=this.dp+ThreadLocalRandom.current().nextInt(1, 5);
        this.sp=this.sp+ThreadLocalRandom.current().nextInt(1, 5);
    }
    public void mapChanged(){
       int tenPercentChance = ThreadLocalRandom.current().nextInt(1, 10);
       int fortyPercentChance = ThreadLocalRandom.current().nextInt(1, 40);
       int fiftyPercentChance = ThreadLocalRandom.current().nextInt(1, 50);
       int hundredPercentChange = ThreadLocalRandom.current().nextInt(1, 100);
       if(tenPercentChance>fortyPercentChance && tenPercentChance>fiftyPercentChance){
           this.hpActual=this.getHpMax();
       }else if(fortyPercentChance>fiftyPercentChance){
           this.hpActual= (int) (this.getHpActual()*(double)1/3);
       }else if(fiftyPercentChance>hundredPercentChange){
           this.hpActual= (int) (this.getHpActual()*(double)1/10);
       }

    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    public void addOneLeve() {
        ++this.level;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSuccessfulStrike(int sp, int othersCaracterDp) {
        return 2 * ThreadLocalRandom.current().nextInt(1, 5) + sp > othersCaracterDp;
    }

    public Stats(String type) {
        this.type = type;
        if (type.equals("Hero")) {
            this.hpMax = 20 + 3 * ThreadLocalRandom.current().nextInt(1, 5);
            this.dp = 2 * ThreadLocalRandom.current().nextInt(1, 5);
            this.sp = 5 + ThreadLocalRandom.current().nextInt(1, 5);
        }

        this.hpActual = this.hpMax;
        this.sv = this.sp * ThreadLocalRandom.current().nextInt(1, 5);
        this.successfulStrikeValue = 2 * ThreadLocalRandom.current().nextInt(1, 5) + this.sp;
    }

    public Stats(String type, int level) {
        if (type.equals("Monster")) {
            this.hpMax = 2 * level * ThreadLocalRandom.current().nextInt(1, 5);
            this.dp = level / 2 * ThreadLocalRandom.current().nextInt(1, 5);
            this.sp = level * ThreadLocalRandom.current().nextInt(1, 5);
        }

        if (type.equals("Boss")) {
            this.hpMax = 2 * level * ThreadLocalRandom.current().nextInt(1, 5) + ThreadLocalRandom.current().nextInt(1, 5);
            this.dp = level / 2 * ThreadLocalRandom.current().nextInt(1, 5) + ThreadLocalRandom.current().nextInt(1, 5) / 2;
            this.sp = level * ThreadLocalRandom.current().nextInt(1, 5) + ThreadLocalRandom.current().nextInt(1, 5);
        }

        this.hpActual = this.hpMax;
        this.sv = this.sp * ThreadLocalRandom.current().nextInt(1, 5);
    }

    public void createImg(Stats stat) {
        BufferedImage img = new BufferedImage(1, 1, 2);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", 0, 36);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        String text = this.type + " (Level " + this.level + ") HP: " + this.hpActual + "/" + this.hpMax + " | DP: " + this.dp + " | SP: " + this.sp;
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();
        img = new BufferedImage(width, height, 2);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();

        try {
            ImageIO.write(img, "png", new File("img/stats.png"));
        } catch (IOException var10) {
            var10.printStackTrace();
        }

    }
}
