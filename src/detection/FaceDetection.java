package detection;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import static org.opencv.highgui.HighGui.*;


import static org.opencv.highgui.HighGui.*;

public class FaceDetection {
    public static void main(String[] args) {
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the pre-trained cascade classifier for face detection
        CascadeClassifier faceCascade = new CascadeClassifier("src/dataSet/haarcascade_face.xml");

        // Open the camera
        VideoCapture capture = new VideoCapture(0); // 0 represents the default camera

        // Check if the camera is opened successfully
        if (!capture.isOpened()) {
            System.out.println("Error: Camera not opened!");
            return;
        }

        // Set the frame width and height
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);

        // Create a window to display the camera feed
        namedWindow("Face Detection", WINDOW_NORMAL);

        // Process frames from the camera
        Mat frame = new Mat();
        while (true) {
            // Capture a frame from the camera
            capture.read(frame);

            // Convert the frame to grayscale
            Mat grayFrame = new Mat();
            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

            // Equalize the histogram to improve the contrast
            Imgproc.equalizeHist(grayFrame, grayFrame);

            // Detect faces in the frame
            MatOfRect faceDetections = new MatOfRect();
            faceCascade.detectMultiScale(grayFrame, faceDetections, 1.1, 3, 0, new Size(30, 30), new Size());

            // Draw rectangles around the detected faces
            for (Rect rect : faceDetections.toArray()) {
                Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(0, 255, 0), 2);

                // Add a text overlay
                Imgproc.putText(frame, "Face Detected", new Point(rect.x, rect.y - 10),
                        Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 255, 0), 2);
            }

            // Display the frame with detected faces
            imshow("Face Detection", frame);

            // Break the loop if 'Esc' key is pressed
            if (waitKey(30) == 27) {
                System.out.println("\n*** Face Detection is stoped from running ***\n");
                destroyAllWindows();
                break;
            }
        }
        // Release the camera
        capture.release();
    }
}
