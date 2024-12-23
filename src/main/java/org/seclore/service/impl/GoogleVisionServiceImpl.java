package org.seclore.service.impl;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.seclore.service.GoogleVisionService;
import org.springframework.stereotype.Service;

@Service
public class GoogleVisionServiceImpl implements GoogleVisionService {

    @Override
    public String extractTextFromImage(byte[] imageData) {
        try {
            ByteString imgBytes = ByteString.copyFrom(imageData);
            Image img = Image.newBuilder().setContent(imgBytes).build();
            try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
                AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                        .addFeatures(Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION))
                        .setImage(img)
                        .build();
                AnnotateImageResponse response = vision.batchAnnotateImages(
                        java.util.List.of(request)).getResponses(0);
                if (response.hasError()) {
                    throw new RuntimeException("Error during text extraction: " + response.getError().getMessage());
                }
                return response.getTextAnnotationsList().get(0).getDescription();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing image data", e);
        }
    }
}
