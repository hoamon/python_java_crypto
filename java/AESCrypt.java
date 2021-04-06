
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.lang.*;
import java.util.Random;

public class AESCrypt {

    // String plaintext -> Base64-encoded String ciphertext
    public static String encrypt(String key, String plaintext) {
        try {
            // Generate a random 16-byte initialization vector
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            // prep the AES Cipher
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            // Encode the plaintext as array of Bytes
            byte[] cipherbytes = cipher.doFinal(plaintext.getBytes());

            // Build the output message initVector + cipherbytes -> base64
            byte[] messagebytes = new byte[cipherbytes.length];

            System.arraycopy(cipherbytes, 0, messagebytes, 0, cipherbytes.length);

            // Return the cipherbytes as a Base64-encoded string
            return Base64.getEncoder().encodeToString(messagebytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Base64-encoded String ciphertext -> String plaintext
    public static String decrypt(String key, String ciphertext) {
        try {
            byte[] cipherbytes = Base64.getDecoder().decode(ciphertext);

            byte[] messagebytes = Arrays.copyOfRange(cipherbytes,0,cipherbytes.length);

            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);

            // Convert the ciphertext Base64-encoded String back to bytes, and
            // then decrypt
            byte[] byte_array = cipher.doFinal(messagebytes);

            // Return plaintext as String
            return new String(byte_array, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        boolean encrypt = args[0].equals("encrypt"); // encrypt | decrypt

        String key = args[1];
        String message = args[2]; // either plaintext or encrypted

        if (encrypt)
        {
            System.out.println(encrypt(key, message));
        }
        else
        {
            System.out.println(decrypt(key, message));
        }
        //String originalString = "The quick brown fox jumps over the lâzy dতg";
    }

}
