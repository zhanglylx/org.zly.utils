package org.zly.utils.uiauto.selenium;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {
    private TakesScreenshot takesScreenshot;

    public ScreenshotUtils(TakesScreenshot takesScreenshot) {
        this.takesScreenshot = takesScreenshot;
    }

    public void getScreenshotAs(File savePath) {
        if (!getScreenshotAs().renameTo(savePath)) throw new RuntimeException("截图失败");
    }

    public File getScreenshotAs() {
        return takesScreenshot.getScreenshotAs(OutputType.FILE);
    }

    public BufferedImage getScreenshotAsBufferedImage() {
        try {
            return ImageIO.read(getScreenshotAs());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
