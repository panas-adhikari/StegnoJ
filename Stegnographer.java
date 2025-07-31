import java.io.*;
import java.util.Scanner;

class Steganographer {
    private byte[] marker = {(byte)0xDE, (byte)0xAD, (byte)0xBE, (byte)0xEF,
                     (byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE};

    private File cloner(File source) {
        try {
            File destination = new File("cpy_" + source.getName());
            if (!source.isFile()) {
                System.out.println("Source must be a file");
                return null;
            }

            if (!destination.exists()) {
                System.out.println("Destination not found. Creating one.");
                destination.createNewFile(); 
            }

            System.out.println("Creating streams and copying data...");
            FileInputStream fis = new FileInputStream(source);
            FileOutputStream fos = new FileOutputStream(destination);

            byte[] bytes = new byte[1024];
            int data;
            while ((data = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, data);
            }

            fis.close();
            fos.close();
            return destination;

        } catch (IOException e) {
            System.out.println("Error detected: ");
            e.printStackTrace();
            return null;
        }
    }

    private void Appender(String message, File destination) {
        if (message == null || message.isEmpty()) {
            System.out.println("Bad Message detected");
            return;
        }
        if (destination == null || !destination.isFile()) {
            System.out.println("The destination file is invalid.");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(destination, true)) {
            byte[] bytes = message.getBytes();
            int length = bytes.length;

            fos.write(bytes);
            fos.write(new byte[]{
                    (byte) (length >> 24),
                    (byte) (length >> 16),
                    (byte) (length >> 8),
                    (byte) (length)
            });

            fos.write(marker);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractMessage(File file) throws IOException {
        int markerLen = marker.length;
        int lengthFieldSize = 4;

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long fileLength = raf.length();
            long pointer = fileLength - markerLen - lengthFieldSize;

            if (pointer < 0) throw new IOException("File too small for hidden data.");

            raf.seek(pointer);

            byte[] lengthBytes = new byte[lengthFieldSize];
            raf.readFully(lengthBytes);

            int messageLength = ((lengthBytes[0] & 0xFF) << 24) |
                                ((lengthBytes[1] & 0xFF) << 16) |
                                ((lengthBytes[2] & 0xFF) << 8) |
                                (lengthBytes[3] & 0xFF);

            long messageStart = pointer - messageLength;
            if (messageStart < 0) throw new IOException("Invalid message length.");

            raf.seek(messageStart);
            byte[] messageBytes = new byte[messageLength];
            raf.readFully(messageBytes);

            return new String(messageBytes);
        }
    }

    public void stegnoRunner(Scanner sc){
        // Scanner sc = new Scanner(System.in);
        System.out.println("Enter the source file : ");
        String fullpath = sc.nextLine();
        try{
            File sourcFile = new File(fullpath);
            if(!sourcFile.isFile()){
                System.out.println("This File seems not to exist");
                return;
            }
            System.out.println("Enter your message : ");
            String message = sc.nextLine();
            Steganographer stegno = new Steganographer();
            File dest = stegno.cloner(sourcFile);
            stegno.Appender(message, dest);
            System.out.println("Message hidden successfully");
        }catch(Exception e){
            return;
        }
    }
    public void stegnoExtract(Scanner scanner){
        // Scanner scanner = new Scanner(System.in);
        Steganographer stegno = new Steganographer();
        System.out.print("Enter the Stegno File : ");
        String path = scanner.nextLine();
        try{
            File dest = new File(path);
            if(!dest.isFile()){
                System.out.println("Provided path is not a file . ");
                return;
            }
            String message = stegno.extractMessage(dest);
            System.out.println(message);
        }catch(IOException e){
            System.out.println("Something went wrong");
        }
    }

    public void menu() {
    Scanner scanner = new Scanner(System.in); 

    while (true) {
        try {
            System.out.println("\n===== Steganography Menu =====");
            System.out.println("1. Hide Data");
            System.out.println("2. Get Data");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextLine()) {
                System.out.println("❌ No input available. Exiting.");
                break;
            }

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("🔐 Hiding data...");
                    stegnoRunner(scanner);
                    break;

                case "2":
                    System.out.println("🔍 Retrieving data...");
                    stegnoExtract(scanner);
                    break;

                case "3":
                    System.out.println("👋 Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Choose wisely. Please select 1, 2, or 3.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            break;
        }
    }
}


    public static void main(String[] args) {
        new Steganographer().menu();
    }
}
