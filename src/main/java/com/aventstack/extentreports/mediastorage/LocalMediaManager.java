package com.aventstack.extentreports.mediastorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.utils.FileUtil;

class LocalMediaManager implements MediaStorage {
    
    private static final Logger logger = Logger.getLogger(LocalMediaManager.class.getName());

    private String reporterGeneratedFilePath;
    private String targetPath;
    private String relativePath;
    
    public void init(String reporterGeneratedFilePath) {
        this.reporterGeneratedFilePath = reporterGeneratedFilePath;
        mkDirs();
    }
    
    public void storeMedia(Media screenCapture) throws IOException {
        storeMediaFileLocal(screenCapture);
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
    
    private void storeMediaFileLocal(Media sc) throws IOException {
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
        
        Path p = Paths.get(copyToPath);
        Files.copy(new FileInputStream(f), p);
        sc.setPath(relativePath + mediaFileName);
    }
    
}
