package main;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import java.security.SecureRandom;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Too few arguments");
            return;
        } else if ((args.length & 1) != 1) {
            System.out.println("Even number of arguments");
            return;
        }
        for (int i = 0; i < args.length; i++) {
            for (int j = i + 1; j < args.length; j++) {
                if (args[i].equals(args[j])) {
                    System.out.println("There are repeating arguments");
                    return;
                }
            }
        }

        SecureRandom r = new SecureRandom();
        long v = Math.abs(r.nextLong());
        String key = Long.toHexString(v) + Long.toHexString(r.nextLong());   //random key
        int cp_mv = (int) (v % args.length);    // computer move

        SHA3.DigestSHA3 S3 = new SHA3.Digest256();
        System.out.println("HMAC:");
        System.out.println(Hex.toHexString(S3.digest(key.getBytes())));    //hash from move

        boolean $ = true;
        while ($) {
            System.out.println("Available moves:");
            for (int i = 0; i < args.length; i++) {
                System.out.print(i + 1);
                System.out.println( '-' + args[i]);
            }
            System.out.println("0-exit");
            System.out.print("Enter your move: ");
            Scanner in = new Scanner(System.in);
            String us_mv = in.nextLine();
            if (us_mv.matches("[0-" + args.length + ']')) {
                int u_m = Integer.parseInt(us_mv);
                if (u_m == 0) return;
                System.out.println("Your move: " + args[u_m - 1]);
                System.out.println("Computer move: " + args[cp_mv]);
                cp_mv++;
                if (u_m == cp_mv) {
                    System.out.println("Draw");
                } else {
                    int k = cp_mv - u_m;
                    if ((k > 0 && k < args.length / 2) || k < -args.length / 2) {
                        System.out.println("You win!");
                    } else {
                        System.out.println("Computer win :(");
                    }
                }
                $ = false;
            } else {
                System.out.println("Invalid data\n--------------------");
            }
        }
        System.out.println("HMAC key:" + key);
    }
}

