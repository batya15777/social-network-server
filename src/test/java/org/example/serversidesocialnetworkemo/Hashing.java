package org.example.serversidesocialnetworkemo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Hashing {
    private String username;
    private String password;


 public static void main(String[] args) {
     System.out.println( hash("batya", "123"));

}




    public static String hash(String username, String password) {
            try {
                byte[] digest = MessageDigest.getInstance("SHA-256")
                        .digest((username + ":" + password).getBytes(StandardCharsets.UTF_8));

                StringBuilder sb = new StringBuilder(digest.length * 2);
                for (byte b : digest) sb.append(String.format("%02x", b));
                return sb.toString();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    public Hashing(String username, String password) {
        this.username = username;
        this.password = password;
    }
}






