package org.seclore.service;

public interface OcrService {

    String extractTextFromImage(byte[] imageData);
}
