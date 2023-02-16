package com.company.dtos;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class ResizeImage {
    private static final int IMG_WIDTH = 413;
    private static final int IMG_HEIGHT = 413;

    public static void write(BufferedImage bufferedImage, Path target) throws IOException {
        ImageIO.write(bufferedImage, "png", target.toFile());
    }

    public static boolean write(InputStream input, Path target, int width, int height) throws IOException {
        BufferedImage bufferedImage = crop(input, width, height);
        return ImageIO.write(bufferedImage, "png", target.toFile());
    }
    public static boolean write(InputStream input, Path target) throws IOException {
        BufferedImage bufferedImage = crop(input);
        return ImageIO.write(bufferedImage, "png", target.toFile());
    }

    public static BufferedImage crop(InputStream input, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(input);
        Image newResizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return convertToBufferedImage(newResizedImage);
    }
    public static BufferedImage crop(InputStream input) throws IOException {
        BufferedImage originalImage = ImageIO.read(input);
        Image newResizedImage = originalImage.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH);
        return convertToBufferedImage(newResizedImage);
    }

    private static BufferedImage convertToBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();
        return bufferedImage;
    }
}