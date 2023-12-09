import Watermarking.ImageWatermarking;
import detection.EdgeDetection;
import detection.EyesDetection;
import detection.FaceDetection;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Face Detection");
            System.out.println("2. Eyes Detection");
            System.out.println("3. Edge Detection");
            System.out.println("4. Image WaterMarking");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            try {
                Thread thread = null;

                switch (choice) {
                    case 1:
                        // Run FaceDetection in a separate thread
                        System.out.print("\nFace Detection ...\n");
                        thread = new Thread(() -> FaceDetection.main(args));
                        break;
                    case 2:
                        // Run EyesDetection in a separate thread
                        thread = new Thread(() -> EyesDetection.main(args));
                        break;
                    case 3:
                        thread = new Thread(() -> EdgeDetection.main(args));
                        break;
                    case 4:
                        thread = new Thread(() -> ImageWatermarking.main(args));
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please choose again.");
                }

                if (thread != null) {
                    thread.start();
                    thread.join();
                }
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
