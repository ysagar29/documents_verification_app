package org.seclore.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.seclore.service.OcrService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service("vision")
public class GoogleVisionServiceImpl implements OcrService {

    private final ImageAnnotatorClient imageAnnotatorClient;

    public GoogleVisionServiceImpl() throws IOException {
        // Load the service account JSON from the resources folder
        ClassPathResource resource = new ClassPathResource("credentials/testmapapi-270810-a847fbbe47b1.json");
        try (InputStream credentialsStream = resource.getInputStream()) {
            // Set credentials programmatically from the resource stream
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

            // Create the settings using the credentials
            ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                    .setCredentialsProvider(() -> credentials)
                    .build();

            // Initialize the Vision API client with the settings
            imageAnnotatorClient = ImageAnnotatorClient.create(settings);
        }
    }

    @Override
    public String extractTextFromImage(byte[] imageData) {
        try {
            ByteString imgBytes = ByteString.copyFrom(imageData);
            Image img = Image.newBuilder().setContent(imgBytes).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION))
                    .setImage(img)
                    .build();
            AnnotateImageResponse response = imageAnnotatorClient.batchAnnotateImages(
                    java.util.List.of(request)).getResponses(0);
            if (response.hasError()) {
                throw new RuntimeException("Error during text extraction: " + response.getError().getMessage());
            }
            return response.getTextAnnotationsList().get(0).getDescription();

        } catch (Exception e) {
            throw new RuntimeException("Error processing image data", e);
        }
    }
}
