//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Board extends JComponent implements KeyListener {
    int X;
    int Y;
    String actualPng = "hero-down.png";
    PositionedImage image = null;
    int[][] map = this.mapGen();
    int[][] mapOfMonsters = this.monsterGen();
    Stats hero = new Stats("Hero");
    Stats boss;
    Stats skeleton1;
    Stats skeleton2;
    Stats skeleton3;
    boolean space;

    public boolean isTrueStep(int distance, String xOry) {
        if (xOry.equals("X")) {
            if (this.X + distance < 0 || this.X + distance >= 720) {
                return false;
            }

            if (this.map[this.X / 72 + distance / 72][this.Y / 72] == 0) {
                return false;
            }
        }

        if (xOry.equals("Y")) {
            if (this.Y + distance < 0 || this.Y + distance >= 720) {
                return false;
            }

            if (this.map[this.X / 72][this.Y / 72 + distance / 72] == 0) {
                return false;
            }
        }

        return true;
    }

    public int[][] mapGen() {
        int[][] arrayMap = new int[10][10];

        for(int i = 0; i < 10; ++i) {
            for(int j = 0; j < 10; ++j) {
                int wally = this.random(1, 8);
                int wallx = this.random(1, 8);
                if (wallx >= i && wally <= j && i != 0 && j != 0) {
                    arrayMap[i][j] = 0;
                } else {
                    arrayMap[i][j] = 1;
                }
            }
        }

        return arrayMap;
    }

    public int[][] monsterGen() {
        int numberOfSkeletons = 3;
        int numberOfBoss = 1;
        int[][] mapOfMonsters = new int[10][10];

        int i;
        int bossX;
        int bossY;
        for(i = 0; i < numberOfSkeletons; ++i) {
            bossX = this.random(1, 8);
            bossY = this.random(1, 8);
            if (this.map[bossX][bossY] == 1 && mapOfMonsters[bossX][bossY] == 0) {
                mapOfMonsters[bossX][bossY] = 1;
            } else {
                --i;
            }
        }

        for(i = 0; i < numberOfBoss; ++i) {
            bossX = this.random(1, 8);
            bossY = this.random(1, 8);
            if (this.map[bossX][bossY] == 1 && mapOfMonsters[bossX][bossY] == 0) {
                mapOfMonsters[bossX][bossY] = 2;
            } else {
                --i;
            }
        }

        return mapOfMonsters;
    }

    public int random(int min, int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max);
        return randomNum;
    }

    public Board() {
        this.boss = new Stats("Boss", this.hero.getLevel());
        this.skeleton1 = new Stats("Monster", this.hero.getLevel());
        this.skeleton2 = new Stats("Monster", this.hero.getLevel());
        this.skeleton3 = new Stats("Monster", this.hero.getLevel());
        this.space = false;
        this.X = 0;
        this.Y = 0;
        this.setPreferredSize(new Dimension(720, 780));
        this.setVisible(true);
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        PositionedImage imageHero = new PositionedImage("img/" + this.actualPng, this.X, this.Y);

        for(int i = 0; i < 10; ++i) {
            for(int j = 0; j < 10; ++j) {
                if (this.map[i][j] == 0) {
                    this.image = new PositionedImage("img/wall.png", i * 72, j * 72);
                } else {
                    this.image = new PositionedImage("img/floor.png", i * 72, j * 72);
                }

                this.image.draw(graphics);
                if (this.mapOfMonsters[i][j] == 1) {
                    this.image = new PositionedImage("img/skeleton.png", i * 72, j * 72);
                    if (this.X == i * 72 && this.Y == j * 72 && this.space) {
                        if (this.hero.isSuccessfulStrike(this.hero.getSp(), this.skeleton1.getDp())) {
                            this.skeleton1.setHpActual(this.skeleton1.getHpActual() - (this.skeleton1.getSv() - this.hero.getDp()));
                            System.out.println(this.skeleton1.getHpActual());
                            if (this.skeleton1.getHpActual() < 0) {
                                this.mapOfMonsters[i][j] = 0;
                            }
                        } else {
                            this.hero.setHpActual(this.hero.getHpActual() - (this.hero.getSv() - this.boss.getDp()));
                            if (this.hero.getHpActual() < 0) {
                                System.out.printf("GAME OVER");
                                System.exit(0);
                            }
                        }
                    }
                }

                if (this.mapOfMonsters[i][j] == 2) {
                    this.image = new PositionedImage("img/boss.png", i * 72, j * 72);
                    if (this.X == i * 72 && this.Y == j * 72 && this.space) {
                        if (this.hero.isSuccessfulStrike(this.hero.getSp(), this.boss.getDp())) {
                            this.boss.setHpActual(this.boss.getHpActual() - (this.boss.getSv() - this.hero.getDp()));
                            if (this.boss.getHpActual() < 0) {
                                this.mapOfMonsters[i][j] = 0;
                            }
                        } else {
                            this.hero.setHpActual(this.hero.getHpActual() - (this.hero.getSv() - this.boss.getDp()));
                            if (this.hero.getHpActual() < 0) {
                                System.out.printf("GAME OVER");
                                System.exit(0);
                            }
                        }
                    }
                }

                this.image.draw(graphics);
            }
        }

        imageHero.draw(graphics);
        this.hero.createImg(this.hero);
        PositionedImage stats = new PositionedImage("img/stats.png", 0, 720);
        stats.draw(graphics);
        this.space = false;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RPG Game");
        Board board = new Board();
        frame.add(board);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        frame.pack();
        frame.addKeyListener(board);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 38 && this.isTrueStep(-72, "Y")) {
            this.Y -= 72;
            this.actualPng = "hero-up.png";
        } else if (e.getKeyCode() == 40 && this.isTrueStep(72, "Y")) {
            this.Y += 72;
            this.actualPng = "hero-down.png";
        } else if (e.getKeyCode() == 37 && this.isTrueStep(-72, "X")) {
            this.X -= 72;
            this.actualPng = "hero-left.png";
        } else if (e.getKeyCode() == 39 && this.isTrueStep(72, "X")) {
            this.X += 72;
            this.actualPng = "hero-right.png";
        }

        if (e.getKeyCode() == 32) {
            this.space = true;
        }

        this.repaint();
    }
}
