package com.relevantcodes.extentreports.media;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.relevantcodes.extentreports.model.Media;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.utils.FileUtil;

public class MediaFilesManager {
    
    private static final Logger logger = Logger.getLogger(MediaFilesManager.class.getName());

    private String reporterGeneratedFilePath;
    private String targetPath;
    private String relativePath;
    
    public MediaFilesManager(String reporterGeneratedFilePath) {
        this.reporterGeneratedFilePath = reporterGeneratedFilePath;

        mkDirs();
    }
    
    private void mkDirs() {
        String fileName = FileUtil.getFileNameWithoutExtension(reporterGeneratedFilePath) + ".";
        String absolutePath = new File(reporterGeneratedFilePath).getAbsolutePath().replace("\\", "/");
        String basePath = new File(absolutePath).getParent().replace("\\", "/");
        
        mkDirs(basePath, fileName, 0);
    }
    
    private void mkDirs(String basePath, String fileName, int cnt) {
        relativePath = fileName + cnt + "/";
        targetPath = basePath + "/" + relativePath;
        
        File f = new File(targetPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        else {
            mkDirs(basePath, fileName, ++cnt);
        }
    }
    
    public void storeMediaInformationLocal(ScreenCapture screenCapture) {
        storeMediaFile(screenCapture);
    }
    
    private void storeMediaFile(Media sc) {
        File f = new File(sc.getPath());
        if (!f.exists()) {
            logger.warning("Unable to locate media file: " + sc.getPath());
            return;
        }
        
        String baseFileName = new File(sc.getPath()).getName();
        String mediaFileName = baseFileName;
        String copyToPath = targetPath + baseFileName;
        File copyToFile = new File(copyToPath);
        int cnt = 0;

        while(copyToFile.exists()) {
            String name = FileUtil.getFileNameWithoutExtension(baseFileName) + "." + cnt;
            String ext = FileUtil.getExtension(baseFileName);
            mediaFileName = name + "." + ext;
            
            copyToPath = targetPath + mediaFileName + "/";
            copyToFile = new File(copyToPath);
            
            cnt ++;
        }
        
        try {
            Path p = Paths.get(copyToPath);
            Files.copy(new FileInputStream(f), p);
            sc.setPath(relativePath + mediaFileName);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File Not Found", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "", e);
        }
    }
    
}
