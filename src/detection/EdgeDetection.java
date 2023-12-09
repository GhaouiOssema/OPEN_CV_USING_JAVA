package detection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.CvType;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class EdgeDetection {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        // Load the input image
        String inputImagePath = "src/dataSet/image.jpg";
        Mat src = Imgcodecs.imread(inputImagePath);

        // Convert the image to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // Apply GaussianBlur to reduce noise and improve edge detection
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        // Use Canny edge detector
        double lowerThreshold = 50.0;
        double upperThreshold = 150.0;
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, lowerThreshold, upperThreshold);

        // Save the resulting image with edges
        String outputImagePath = "src/dataSet/imagesFromEdgeDetection/image.jpg";
        Imgcodecs.imwrite(outputImagePath, edges);

        System.out.println("Edge detection completed. Result saved at: " + outputImagePath);
    }
}
