//package org.firstinspires.ftc.teamcode.Autonomous;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.graphics.Bitmap.createBitmap;
import static android.graphics.Bitmap.createScaledBitmap;
import com.qualcomm.robotcore.util.RobotLog;

public class VuforiaStuff {

    VuforiaLocalizer vuforia;

    public VuforiaStuff(VuforiaLocalizer vuforia) {
        this.vuforia = vuforia;
    }

    public enum skystonePos {
        LEFT, CENTER, RIGHT;
    }

    public skystonePos vuforiascan(boolean saveBitmaps, boolean red) {
        Image rgbImage = null;
        int rgbTries = 0;
        /*
        double colorcountL = 0;
        double colorcountC = 0;
        double colorcountR = 0;
        */
        double yellowCountL = 1;        // declares variables for counting pixels
        double yellowCountC = 1;
        double yellowCountR = 1;

        double blackCountL = 1;
        double blackCountC = 1;
        double blackCountR = 1;

        // grabs image from the camera
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        VuforiaLocalizer.CloseableFrame closeableFrame = null;
        this.vuforia.setFrameQueueCapacity(1);
        while (rgbImage == null) {
            try {
                closeableFrame = this.vuforia.getFrameQueue().take();
                long numImages = closeableFrame.getNumImages();

                for (int i = 0; i < numImages; i++) {
                    if (closeableFrame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                        rgbImage = closeableFrame.getImage(i);
                        if (rgbImage != null) {
                            break;
                        }
                    }
                }
            } catch (InterruptedException exc) {

            } finally {
                if (closeableFrame != null) closeableFrame.close();
            }
        }  // finishes grabbing image

        if (rgbImage != null) {

            // copy the bitmap from the Vuforia frame
            Bitmap bitmap = Bitmap.createBitmap(rgbImage.getWidth(), rgbImage.getHeight(), Bitmap.Config.RGB_565);
            bitmap.copyPixelsFromBuffer(rgbImage.getPixels());

            String path = Environment.getExternalStorageDirectory().toString();
            FileOutputStream out = null;

            String bitmapName;          // declare file names for storing images
            String croppedBitmapName;

            //  set file names for image storage based on side
            if (red) {
                bitmapName = "BitmapRED.png";
                croppedBitmapName = "BitmapCroppedRED.png";
            } else {
                bitmapName = "BitmapBLUE.png";
                croppedBitmapName = "BitmapCroppedBLUE.png";
            }

            //Save bitmap to file
            if (saveBitmaps) {
                try {
                    File file = new File(path, bitmapName);
                    out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.flush();
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            int cropStartX;                //declare variables for cropping size
            int cropStartY;
            int cropWidth;
            int cropHeight;

            if (red) {
                cropStartX = (int) ((280.0 / 1280.0) * bitmap.getWidth());
                cropStartY = (int) ((30.0 / 720.0) * bitmap.getHeight());
                cropWidth =  (int) ((690.0 / 1280.0) * bitmap.getWidth());
                cropHeight = (int) ((111.0 / 720.0) * bitmap.getHeight());

            } else {
                cropStartX = (int) ((390.0 / 1280.0) * bitmap.getWidth());
                cropStartY = (int) ((66.0 / 720.0) * bitmap.getHeight());
                cropWidth = (int) ((750.0 / 1280.0) * bitmap.getWidth());
                cropHeight = (int) ((115.0 / 720.0) * bitmap.getHeight());
            }

            RobotLog.d("8620WGW skystonePos"           //saves info to debug log in file on phone
                    + " cropStartX: " + cropStartX
                    + " cropStartY: " + cropStartY
                    + " cropWidth: " + cropWidth
                    + " cropHeight: " + cropHeight
                    + " Width: " + bitmap.getWidth()
                    + " Height: " + bitmap.getHeight()
            );


            bitmap = createBitmap(bitmap, cropStartX, cropStartY, cropWidth, cropHeight); //Cropped Bitmap to show only stones

            // Save cropped bitmap to file
            if (saveBitmaps) {
                try {
                    File file = new File(path, croppedBitmapName);
                    out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.flush();
                            out.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            bitmap = createScaledBitmap(bitmap, 110, 20, true); //Compress bitmap to reduce scan time


            int height;
            int width;
            int pixel;
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
            int colWidth = (int) ((double) bitmapWidth / 6.0);
            int colorLStartCol = (int) ((double) bitmapWidth * (1.0 / 6.0) - ((double) colWidth / 2.0));
            int colorCStartCol = (int) ((double) bitmapWidth * (3.0 / 6.0) - ((double) colWidth / 2.0));
            int colorRStartCol = (int) ((double) bitmapWidth * (5.0 / 6.0) - ((double) colWidth / 2.0));

            for (height = 0; height < bitmapHeight; ++height) {
                for (width = colorLStartCol; width < colorLStartCol + colWidth; ++width) {
                    pixel = bitmap.getPixel(width, height);
                    if (Color.red(pixel) < 200 || Color.green(pixel) < 200 || Color.blue(pixel) < 200) {
                        yellowCountL += Color.red(pixel);
                        blackCountL += Color.blue(pixel);
                    }

                    /*
                    if (Color.red(pixel) > 120 && Color.green(pixel) > 80 && Color.blue(pixel) < 20) {
                        yellowCountL += 1;
                    } else if (Color.red(pixel) < 120 && Color.green(pixel) < 120 && Color.blue(pixel) < 120) {
                        blackCountL += 1;
                    }
                     */

                    //colorcountL += Color.red(pixel) + Color.green(pixel) + Color.blue(pixel);
                }
                for (width = colorCStartCol; width < colorCStartCol + colWidth; ++width) {
                    pixel = bitmap.getPixel(width, height);

                    if (Color.red(pixel) < 200 || Color.green(pixel) < 200 || Color.blue(pixel) < 200) {
                        yellowCountC += Color.red(pixel);
                        blackCountC += Color.blue(pixel);
                    }
                    /*
                    if (Color.red(pixel) > 120 && Color.green(pixel) > 80 && Color.blue(pixel) < 20) {
                        yellowCountC += 1;
                    } else if (Color.red(pixel) < 120 && Color.green(pixel) < 120 && Color.blue(pixel) < 120) {
                        blackCountC += 1;
                    }
                    */
                    //colorcountC += Color.red(pixel) + Color.green(pixel) + Color.blue(pixel);
                }

                for (width = colorRStartCol; width < colorRStartCol + colWidth; ++width) {
                    pixel = bitmap.getPixel(width, height);

                    if (Color.red(pixel) < 200 || Color.green(pixel) < 200 || Color.blue(pixel) < 200) {
                        yellowCountR += Color.red(pixel);
                        blackCountR += Color.blue(pixel);
                    }
                    /*
                    if (Color.red(pixel) > 120 && Color.green(pixel) > 80 && Color.blue(pixel) < 20) {
                        yellowCountR += 1;
                    } else if (Color.red(pixel) < 120 && Color.green(pixel) < 120 && Color.blue(pixel) < 120) {
                        blackCountR += 1;
                    }
                    */
                    //colorcountR += Color.red(pixel) + Color.green(pixel) + Color.blue(pixel);
                }
            }
        }

        double blackYellowRatioL = blackCountL / yellowCountL;
        double blackYellowRatioC = blackCountC / yellowCountC;
        double blackYellowRatioR = blackCountR / yellowCountR;


        skystonePos pos;
        /*
       RobotLog.d("color L: " + Double.toString(colorcountL));
       RobotLog.d("color C: " + Double.toString(colorcountC));
       RobotLog.d("color R: " + Double.toString(colorcountR));

        if (colorcountL < colorcountC && colorcountL < colorcountR) {
            pos = skystonePos.LEFT;
        } else if (colorcountC < colorcountL && colorcountC < colorcountR) {
            pos = skystonePos.CENTER;
        } else {
            pos = skystonePos.RIGHT;
        }
*/
        if (blackYellowRatioL > blackYellowRatioC && blackYellowRatioL > blackYellowRatioR) {
            pos = skystonePos.LEFT;
        } else if (blackYellowRatioC > blackYellowRatioL && blackYellowRatioC > blackYellowRatioR) {
            pos = skystonePos.CENTER;
        } else {
            pos = skystonePos.RIGHT;
        }

       RobotLog.d("8620WGW skystonePos black/yellow L: " + blackCountL + "/" + yellowCountL);
       RobotLog.d("8620WGW skystonePos black/yellow C: " + blackCountC + "/" + yellowCountC);
       RobotLog.d("8620WGW skystonePos black/yellow R: " + blackCountR + "/" + yellowCountR);
       RobotLog.d("8620WGW skystonePos position = " + pos);

        return pos;
    }
}