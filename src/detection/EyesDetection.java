package detection;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.Rect;

import java.util.Objects;

import static org.opencv.highgui.HighGui.*;

public class EyesDetection {
    public static void main(String[] args) {
        // Load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the pre-trained eyes cascade classifier
        CascadeClassifier eyesCascade = new CascadeClassifier("src/dataSet/haarcascade_eye.xml");

        // Open the camera
        VideoCapture capture = new VideoCapture(0);

        // Check if the camera is opened successfully
        if (!capture.isOpened()) {
            System.out.println("Error: Camera not opened!");
            return;
        }

        // Set the frame width and height
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);

        // Create a window to display the camera feed
// Create a window to display the camera feed
        namedWindow("Eyes Detection", WINDOW_NORMAL);

// Process frames from the camera
        Mat frame = new Mat();
        while (true) {
            // Capture a frame from the camera
            capture.read(frame);

            // Detect eyes in the frame
            MatOfRect eyes = detectEyes(frame, eyesCascade);

            // Check if the frame is not empty before displaying
            if (!frame.empty()) {
                // Draw rectangles around the detected eyes
                for (Rect rect : Objects.requireNonNull(eyes.toArray())) {
                    Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 255, 0), 2);

                    // Add a text overlay for each detected eye
                    Imgproc.putText(frame, "Eye", new Point(rect.x, rect.y - 10),
                            Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 255, 0), 2);
                }


                // Display the frame with detected eyes
                imshow("Eyes Detection", frame);
            }

            // Break the loop if 'Esc' key is pressed
            if (waitKey(30) == 27) {
                System.out.println("\n*** Eyes Detection is stopped from running ***\n");
                break;
            }
        }

        // Release the camera
        capture.release();
    }

    private static MatOfRect detectEyes(Mat frame, CascadeClassifier eyesCascade) {
        // Convert the frame to grayscale
        Mat grayFrame = new Mat();
        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

        // Equalize the histogram to improve the contrast
        Imgproc.equalizeHist(grayFrame, grayFrame);

        // Detect eyes in the frame
        MatOfRect eyes = new MatOfRect();
        eyesCascade.detectMultiScale(grayFrame, eyes, 1.1, 3, 0, new Size(30, 30), new Size());

        return eyes;
    }
}
