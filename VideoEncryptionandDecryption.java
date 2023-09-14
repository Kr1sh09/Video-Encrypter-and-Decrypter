import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

public class VideoEncryptionandDecryption {

    public static void main(String[] args) {
        try {
            // Replace these values with your own secret key and input/output file paths
            String secretKey = "YourSecretKey"; // Your original key
            String videoPath = "input your path of the video file";
            String encryptedFile = "encryptedVideo.enc"; // Name after encrypting the file
            String decryptedFile = "decryptedVideo.mp4"; // Name after decrypting the file

            // Ensure the secret key is the correct length (16, 24, or 32 bytes)
            byte[] keyBytes = Arrays.copyOf(secretKey.getBytes("UTF-8"), 32); // Adjust to the required key length

            // Encrypt the video file
            encryptVideo(keyBytes, videoPath, encryptedFile);

            // Decrypt the encrypted video file
            decryptVideo(keyBytes, encryptedFile, decryptedFile);

            System.out.println("Encryption and decryption completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void encryptVideo(byte[] secretKey, String inputFile, String outputFile) throws Exception {
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            cipherOutputStream.write(buffer, 0, bytesRead);
        }

        cipherOutputStream.close();
        inputStream.close();
        outputStream.close();
    }

    public static void decryptVideo(byte[] secretKey, String inputFile, String outputFile) throws Exception {
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        cipherInputStream.close();
        inputStream.close();
        outputStream.close();
    }
}
