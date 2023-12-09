package Watermarking;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageWatermarking {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        String inputImagePath = "src/dataSet/image_to_watermark_2.jpeg";
        String outputImagePath = "src/Watermarking/output/image_2.jpeg";
        String watermarkText = "OUSSEMA GHAOUI";

        addTextWatermark(inputImagePath, outputImagePath, watermarkText);

        System.out.println("Watermarking complete.");
    }

    private static void addTextWatermark(String inputImagePath, String outputImagePath, String watermarkText) {
        // Load the image
        Mat image = Imgcodecs.imread(inputImagePath);

        // Add text watermark with reduced font size
        Scalar textColor = new Scalar(255, 255, 255); // White text color
        Point textPosition = new Point(image.cols() - 150, image.rows() - 20);

        // Adjust the font size parameter (e.g., set it to 0.5 for half the size)
        double fontSize = 0.5;
        int thickness = 2;
        int fontType = Imgproc.FONT_HERSHEY_SIMPLEX;

        Imgproc.putText(image, watermarkText, textPosition, fontType, fontSize, textColor, thickness);

        // Save the watermarked image
        Imgcodecs.imwrite(outputImagePath, image);
    }
}
