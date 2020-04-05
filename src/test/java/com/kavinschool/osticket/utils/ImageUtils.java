/*
 * Kangeyan Passoubady
 * Version 1.0
 */
package com.kavinschool.osticket.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * The Class ImageUtils.
 */
public final class ImageUtils {

    /**
     * Checks if is image same.
     *
     * @param imageAFileName the image a file name
     * @param imageBFileName the image b file name
     * @return the boolean
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static Boolean isImageSame(final String imageAFileName, final String imageBFileName)
            throws IOException {

        final BufferedImage bufileInput = ImageIO.read(new File(imageAFileName));
        final BufferedImage bufileOutPut = ImageIO.read(new File(imageBFileName));

        return compare(bufileInput, bufileOutPut);

    }

    /**
     * Instantiates a new image utils.
     * intentionally created as private so that no instance will be created.
     * 
     */
    public ImageUtils() {
        super();
    }

    /**
     * Checks if is image same.
     *
     * @param imageAurl the image aurl
     * @param imageBurl the image burl
     * @return the boolean
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static Boolean isImageSame(final URL imageAurl, final URL imageBurl) throws IOException {
        final BufferedImage bufileInput = ImageIO.read(imageAurl);
        final BufferedImage bufileOutPut = ImageIO.read(imageBurl);

        return compare(bufileInput, bufileOutPut);
    }

    /**
     * Checks if is image same.
     *
     * @param imageAurl the image aurl
     * @param imageBFileName the image b file name
     * @return the boolean
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    public static Boolean isImageSame(final URL imageAurl, final String imageBFileName) throws IOException, InterruptedException {

        final BufferedImage bufileInput = ImageIO.read(imageAurl);
        final BufferedImage bufileOutPut = ImageIO.read(new File(imageBFileName));

        return compare(bufileInput, bufileOutPut);
    }

    /**
     * Checks if is image same.
     *
     * @param imageAFile the image a file
     * @param imageBFile the image b file
     * @return the boolean
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static Boolean isImageSame(final File imageAFile, final File imageBFile) throws IOException {

        final BufferedImage bufileInput = ImageIO.read(imageAFile);
        final BufferedImage bufileOutPut = ImageIO.read(imageBFile);

        return compare(bufileInput, bufileOutPut);
    }

    /**
     * Compare.
     *
     * @param imageOne the bufile input
     * @param imageSecond the bufile out put
     * @return the boolean
     */
    private static Boolean compare(final BufferedImage imageOne, final BufferedImage imageSecond) {

        final DataBuffer dafileInput = imageOne.getData().getDataBuffer();
        final DataBuffer dafileOutPut = imageSecond.getData().getDataBuffer();
        final int sizefileInput = dafileInput.getSize();
        final int sizefileOutPut = dafileOutPut.getSize();
        Boolean matchFlag = true;

        if (sizefileInput == sizefileOutPut) {
            for (int j = 0; j < sizefileInput; j++) {
                if (dafileInput.getElem(j) != dafileOutPut.getElem(j)) {
                    matchFlag = false;
                    break;
                }
            }
        } else {
            matchFlag = false;
        }

        return matchFlag;
    }

    /**
     * Save image.
     *
     * @param imageUrl the image url
     * @param destinationFile the destination file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void saveImage(final String imageUrl, final String destinationFile) throws IOException {
        final URL url = new URL(imageUrl);
        final InputStream inputStream = url.openStream();
        final OutputStream outputStream = new FileOutputStream(destinationFile);

        final byte[] buffer = new byte[2048];
        int length;

        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }

        inputStream.close();
        outputStream.close();
    }

}
