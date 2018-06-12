package pl.noname;

import java.io.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
    }

    private static void copyFileUsingStream(String source, String destination, String thread) throws IOException {
        Runnable newThread = () -> {
            System.out.println("Thread " + thread + " started");
            try (InputStream inputStream
                         = new FileInputStream(source);
                 OutputStream outputStream
                         = new FileOutputStream(new File(destination));) {

                float written = 0;
                long total = new File(source).length() / 1000000;
                int read = 0;
                byte[] bytes = new byte[2048];
                long start = System.currentTimeMillis();
                while ((read = inputStream.read(bytes)) != -1) {
                    written += read;
                    float writenMb = written / 1000000;
                    System.out.println("Thread " + thread + "is: " + ((writenMb / total) * 100) + "% done");
                    outputStream.write(bytes, 0, read);
                }

                System.out.println(System.currentTimeMillis() - start);

                System.out.println("Thread: " + thread + "is done!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(newThread).start();
    }
}
