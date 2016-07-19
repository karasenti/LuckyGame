package com.example.user.luckygameproj;

import java.util.Random;

/**
 * Created by User on 14-Jul-16.
 */
public class Game {
    private int num1;
    private int num2;
    private int num3;

    public Game() {
        Random rnd = new Random();
        this.num1 = rnd.nextInt(9)+1;
        this.num2 = rnd.nextInt(9)+1;
        this.num3 = rnd.nextInt(9)+1;
    }

    public void Roll()
    {
        Random rnd = new Random();
        this.num1 = rnd.nextInt(9)+1;
        this.num2 = rnd.nextInt(9)+1;
        this.num3 = rnd.nextInt(9)+1;
    }
    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }

    public int getNum3() {
        return num3;
    }

    public int Result()
    {
        if(num1==num2 && num2==num3 && num1==7)
            return 50;
        if(num1==num2 && num2==num3)
            return 10;
        if (num1==num2 || num1==num3 || num2==num3)
            return 1;
        return -1;


    }
}
