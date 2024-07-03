package com.smart.core.utils;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.file.CloudFile;
import com.microsoft.azure.storage.file.CloudFileClient;
import com.microsoft.azure.storage.file.CloudFileDirectory;
import com.microsoft.azure.storage.file.CloudFileShare;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * <p>파일을 다룰때 필요한 기능을 모아 놓은 유틸성 클래스. </p>
 */
@Slf4j
public class FileUtil {


    public static final String AZURE_SHARE_FOLDER = "blueplug";
    /**
     * 1 KB
     */
    public static final int ONE_KB = 1024;

    /**
     * 1 MB.
     */
    public static final int ONE_MB = ONE_KB * ONE_KB;

    /**
     * 1 GB
     */
    public static final int ONE_GB = ONE_KB * ONE_MB;

    public static final String TEMP_FILE_DIR = "./downFile/";

    /**
     * 디렉토리인경우에 하위의 모든파일을 삭제하고 자기자신도 삭제 됨. 파일일경우에는 자기자신이 삭제 됨.
     *
     * @param file the file (or directory) to delete
     * @return 파일이나 디렉토리의 삭제여부.
     */
    public static boolean delete(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                // 디렉토리가 다 삭제되는 것은 위험하기 때문에 return false 시켜 버림
                // 솔루션내에서 디렉토리를 삭제하는 부분은 없을것으로 판단됨
                return false;
            } else {
                try {
                    return file.delete();
                } catch (Exception e) {
                    log.debug("delete error ===" + e.getMessage());
                }
            }
        }
        return true;
    }

    /**
     * 디렉토리인경우에 하위의 모든파일을 삭제하고 true를 넘겨줌. 파일일경우에는 그냥 true를 넘김.
     *
     * @param file the directory to clean
     */
    public static boolean clean(File file) {
        if (file.isDirectory()) {
            String filen[] = file.list();

            if (filen != null) {
                for (String s : filen) {
                    File subfile = new File(file, s);

                    if ((subfile.isDirectory()) && (!clean(subfile))) {
                        return false;
                    } else if (!subfile.delete()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 디렉토리인경우 하위의 파일들의 리스트를 만들어서 넘겨줌.
     *
     * @param dir 디렉토리인 파일객체
     * @return 파일리스트 File[] <-- 파일의배열 존재하지않으면 null이 return됨.
     */
    public static File[] listFiles(File dir) {
        String[] ss = dir.list();

        if (ss == null) {
            return null;
        }

        int n = ss.length;
        File[] fs = new File[n];

        for (int i = 0; i < n; i++) {
            fs[i] = new File(dir.getPath(), ss[i]);
        }

        return fs;
    }

    /**
     * 디렉토리인경우 하위의 디렉토리들의 리스트를 만들어서 넘겨줌.
     *
     * @param dir 디렉토리인 파일객체
     * @return 디렉토리 리스트 File[] <-- 디렉토의배열 존재하지않으면 null이 return됨.
     */
    public static File[] listDirectories(File dir) {
        File[] temp = listFiles(dir);
        int count = 0;

        for (File value : temp) {
            if (value.isDirectory()) {
                count++;
            }
        }

        /* 디렉토리가 존재치 않은경우 null을 리턴한다. */
        if (count == 0) {
            return null;
        }

        File[] fs = new File[count];
        int insCount = 0;

        for (File file : temp) {
            if (file.isDirectory()) {
                fs[insCount] = file;
                insCount++;
            }
        }

        return fs;
    }

    /**
     * <p>디렉토리인경우 하위의 파일들의 리스트를 디렉토리->파일 순으로 정렬하여 넘겨줌.</p>
     *
     * @param dir 디렉토리인 파일객체
     * @return 파일리스트 File[] <-- 파일의배열 존재하지않으면 null이 return됨.
     */
    public static File[] sortListFiles(File dir) {
        File[] fs = dir.listFiles();

        if (fs != null && fs.length > 1) {
            Arrays.sort(fs, new Comparator() {
                public int compare(Object a, Object b) {

                    File filea = (File) a;
                    File fileb = (File) b;

                    // 파일 소트전 디렉토리 소트
                    // 대소문자는 구분하지 않음

                    if (filea.isDirectory() && !fileb.isDirectory()) {
                        return -1;
                    } else if (!filea.isDirectory() && fileb.isDirectory()) {
                        return 1;
                    } else {
                        return filea.getName().compareToIgnoreCase(fileb.getName());
                    }
                }
            });
        }

        return fs;
    }

    /**
     * 파라미터로 넘겨진 경로가 디렉토리인지아닌지의 여부를 넘겨줌
     *
     * @param path 파일의 경로
     * @return 디렉토리인지의 여부
     */
    public static boolean isDirectory(String path) {
        boolean dir = false;

        if (path != null) {
            File file = new File(path);
            dir = file.isDirectory();
        }

        return dir;
    }

    /**
     * 파라미터로 넘겨진 경로가 디렉토리인지아닌지의 여부를 넘겨줌 디렉토리가 아니면 디렉토리를 만든다.
     *
     * @param path 파일의 경로
     */
    public static void makeDirectory(String path) {
        boolean dir = false;

        if (path != null) {
            File file = new File(path);

            //  디렉토리가 아니면 새로 만든다.
            if (!file.isDirectory()) {
                file.mkdirs();
            }
        }
    }

    /**
     * 파라미터로 넘겨진 경로가 파일인지아닌지의 여부를 넘겨줌
     *
     * @param path 파일의 경로
     * @return boolean 파일인지의 여부
     */
    public static boolean isFile(String path) {
        boolean file = false;

        if (path != null) {
            File f = new File(path);
            file = f.isFile();
        }

        return file;
    }

    /**
     * 시스템에 관계없고 절대경로이거나 상대경로로 들어와도 관계없이 현재 시스템에 맞는 정규경로를 반환함.
     *
     * @param in "파일까지포함된 경로 윈도우나 유닉스경로인지는 상관하지않음
     * @return string 현재 시스템에 맞는 정규경로
     */
    public static String toCanonicalPath(final String in) {
        final String DOT = new String() + '.';
        String current = FileUtil.toCurrentPath(in);
        String out = new String(current);
        int index = -1;

        index = in.indexOf(DOT + DOT);
        if (index < 0) {
            index = current.indexOf(File.separator + '.');
        }

        if (index < 0) {
            index = current.indexOf('.' + File.separator);
        }

        if ((index > -1) || in.startsWith(DOT) || in.endsWith(DOT)) {
            File file = new File(current);

            try {
                out = file.getCanonicalPath();
            } catch (Exception e) {
                out = current;
                log.debug(e.getMessage());
            }
        }

        return out;
    }

    /**
     * 자바에서 인식가능한 경로로 변환하여줌.
     *
     * @param in 파일에 대한 경로
     */
    public static String toJavaPath(final String in) {
        String path = new String(in);
        path = FileUtil.toCurrentPath(path);

        return path.replace('\\', '/');
    }

    /**
     * 현재 시스템에 맞는 경로로 변환하여줌
     *
     * @param path 파일에 대한 경로
     * @return string 현재 시스템에 맞는 경로
     */
    public static String toCurrentPath(String path) {
        String cPath = path;
        File file;

        if (File.separatorChar == '/') {
            cPath = FileUtil.toShellPath(cPath);
        } else {
            cPath = FileUtil.toWindowsPath(cPath);
        }

        file = new File(cPath);

        // Add default drive
        file = new File(file.getAbsolutePath());
        if (file.exists()) {
            cPath = file.getAbsolutePath();
        }

        return cPath.trim();
    }

    /**
     * 파일까지 포함된는경로에서 포함되어진 file seperators를 유닉스에 맞게 바꾸어줌. 또한 윈도우경로인 경우 시작부분이 드라이브 문자에서이면 유닉스의 루트로 바꾸어줌.
     *
     * @param inPath 파일까지포함된 경로 윈도우나 유닉스경로인지는 상관하지않음
     * @return string Cygnus shell에 맞는 경로
     */
    public static String toShellPath(String inPath) {
        StringBuffer path = new StringBuffer();
        int index = -1;

        inPath = inPath.trim();
        index = inPath.indexOf(":\\");    // nores
        inPath = inPath.replace('\\', '/');

        if (index > -1) {
            path.append("//");    // nores
            path.append(inPath.substring(0, index));
            path.append('/');
            path.append(inPath.substring(index + 2));

        } else {
            path.append(inPath);
        }

        return path.toString();
    }

    /**
     * 파일까지 포함된는경로에서 포함되어진 file seperators를 윈도우에 맞게 바꾸어줌. 또한 경로의 시작이 '\\'이면 윈도우 드라이브 문자로 바꾸어줌.
     *
     * @param path 파일까지포함된 경로 윈도우나 유닉스경로인지는 상관하지않음.
     * @return 유닉스 맞는 경로.
     */
    public static String toWindowsPath(String path) {
        String winPath = path;
        int index = winPath.indexOf("//");    // nores

        if (index > -1) {
            winPath = winPath.substring(0, index) + ":\\"    // nores
                + winPath.substring(index + 2);
        }

        index = winPath.indexOf(':');
        if (index == 1) {
            winPath = winPath.substring(0, 1).toUpperCase() + winPath.substring(1);
        }

        winPath = winPath.replace('/', '\\');
        return winPath;
    }

    /**
     * <p>파일크기를 사람이 읽기 쉽게 바꾸어줌.</p>
     *
     * @param file 파일사이즈
     * @return string 읽기쉽게 변환된 파일사이즈.
     */
    public static String byteCountToDisplaySize(File file) {
        int size = new BigDecimal(file.length()).intValue();

        return byteCountToDisplaySize(size);
    }

    /**
     * <p>파일크기를 사람이 읽기 쉽게 바꾸어줌.</p>
     *
     * @param size 파일사이즈
     * @return string 읽기쉽게 변환된 파일사이즈.
     */
    public static String byteCountToDisplaySize(int size) {
        String displaySize;

        if (size / ONE_GB > 0) {
            displaySize = String.valueOf(size / ONE_GB) + " GB";
        } else if (size / ONE_MB > 0) {
            displaySize = String.valueOf(size / ONE_MB) + " MB";
        } else if (size / ONE_KB > 0) {
            displaySize = String.valueOf(size / ONE_KB) + " KB";
        } else {
            displaySize = String.valueOf(size) + " bytes";
        }

        return displaySize;
    }

    /**
     * <p>문자형식의 파일 길이를 숫자로 바꿈.</p>
     *
     * @param strSize     파일사이즈
     * @param defaultSize 파일사이즈
     * @return int 변환된 파일사이즈
     */
    public static int displaySizeToByteInt(String strSize, int defaultSize) {
        int byteCount = 0;
        String size = StringUtils.replace(strSize.trim().toUpperCase(), ",", "");

        try {
            if (size.endsWith("G") || size.endsWith("GB")) {
                byteCount = ONE_GB * Integer.parseInt(size.substring(0, size.indexOf("G")).trim());
            } else if (size.endsWith("M") || size.endsWith("MB")) {
                byteCount = ONE_MB * Integer.parseInt(size.substring(0, size.indexOf("M")).trim());
            } else if (size.endsWith("K") || size.endsWith("KB")) {
                byteCount = ONE_KB * Integer.parseInt(size.substring(0, size.indexOf("K")).trim());
            } else {
                byteCount = Integer.parseInt(size);
            }

        } catch (Exception e) {
            byteCount = defaultSize;
        }

        return byteCount;
    }

    /**
     * <p>문자형식의 파일 길이를 숫자로 바꿈.</p>
     *
     * @param strSize     파일사이즈
     * @param defaultSize 파일사이즈
     * @return long 변환된 파일사이즈
     */
    public static long displaySizeToByteCount(String strSize, long defaultSize) {
        long byteCount = 0;
        String size = StringUtils.replace(strSize.trim().toUpperCase(), ",", "");
        try {
            if (size.endsWith("G") || size.endsWith("GB")) {
                byteCount = (long) ONE_GB * Integer.parseInt(size.substring(0, size.indexOf("G")).trim());
            } else if (size.endsWith("M") || size.endsWith("MB")) {
                byteCount = (long) ONE_MB * Integer.parseInt(size.substring(0, size.indexOf("M")).trim());
            } else if (size.endsWith("K") || size.endsWith("KB")) {
                byteCount = (long) ONE_KB * Integer.parseInt(size.substring(0, size.indexOf("K")).trim());
            } else {
                byteCount = Integer.parseInt(size);
            }

        } catch (Exception e) {
            byteCount = defaultSize;
        }

        return byteCount;
    }

    /**
     * <p>파일 구분자를 제거한 디렉토리명을 얻음.</p>
     *
     * @param filename 디렉토리명
     * @return string 파일 구분자를 제거한 디렉토리명.
     */
    public static String dirname(String filename) {
        int i = filename.lastIndexOf(File.separator);
        return (i >= 0 ? filename.substring(0, i) : "");
    }

    /**
     * <p>파일 구분자를 제거한 파일명을 얻음.</p>
     *
     * @param filename 파일명
     * @return string 파일 구분자를 제거한 파일명
     */
    public static String filename(String filename) {
        int i = filename.lastIndexOf(File.separator);
        return (i >= 0 ? filename.substring(i + 1) : filename);
    }

    /**
     * <p>확장자를 제외한 파일명을 얻음.</p>
     *
     * @param filename 파일명
     * @return string 확장자를 제외한 파일명
     */
    public static String basename(String filename) {
        return basename(filename, getExtention(filename));
    }

    /**
     * <p>확장자를 제외한 파일명을 얻음.</p>
     * <p>
     * return 값에  '.' 구분자가 포함되어 return되던 부분을 구분자 빼고 return함으로 수정!
     *
     * @param filename 파일명
     * @param suffix   확장자
     * @return string 파일명
     */
    public static String basename(String filename, String suffix) {
        int i = filename.lastIndexOf(File.separator) + 1;
        int lastDot = ((suffix != null) && (suffix.length() > 0)) ? filename.lastIndexOf(suffix) : -1;

        if (lastDot >= 0) {
            return filename.substring(i, lastDot - 1);
        } else if (i > 0) {
            return filename.substring(i - 1);
        } else {
            return filename; // else returns all (no path and no extension)
        }
    }

    /**
     * <p>파일 확장자를 얻음.</p>
     *
     * @param filename 파일명
     * @return string 확장자
     */
    public static String getExtention(String filename) {
        int lastDot = filename.lastIndexOf('.');

        if (lastDot >= 0) {
            return filename.substring(lastDot + 1);
        } else {
            return "";
        }
    }


    /**
     * <p>파일 존재여부 확인.</p>
     *
     * @param fileName 파일명
     * @return boolean 존재하면 TRUE, 아니면 FALSE
     */
    public static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * <p>파일에 내용을 기록함.</p>
     *
     * @param fileName 파일명
     * @param data     데이터
     * @param charset  문자셋
     */
    public static void fileWrite(String fileName, String data, String charset) throws Exception {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(fileName);
            out.write(data.getBytes(charset));
        } catch (Exception e) {
            log.debug(e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static void fileWrite(String fileName, byte[] data) throws Exception {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(fileName);
            out.write(data);

        } catch (Exception e) {
            log.debug(e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <p>지정된 파일명을 삭제함.</p>
     *
     * @param fileName 파일명
     */
    public static void fileDelete(String fileName) {
        File file = new File(fileName);
        file.delete();
    }

    /**
     * 파일을 변경한다.
     *
     * @param old_file 변경할 파일
     * @param new_file 변경될 파일F
     */
    public static void rename(String old_file, String new_file) throws Exception {
        File o_file = new File(old_file);
        o_file.renameTo(new File(new_file));
    }

    /**
     * 파일을 변경한다. return 값이 필요한 경우 사용 - 20070411(yhkim)
     *
     * @param old_file 변경할 파일
     * @param new_file 변경될 파일
     */
    public static boolean renameTo(String old_file, String new_file) throws Exception {
        boolean retVal = false;

        File o_file = new File(old_file);
        retVal = o_file.renameTo(new File(new_file));

        return retVal;
    }

    /**
     * <p>지정된 파일명에 대한 파일객체를 얻음.</p>
     *
     * @param fileName 파일명
     */
    public static File getFile(String fileName) {
        return new File(fileName);
    }

    /**
     * <p>지정된 시간까지 파일이 존재하는지 확인함.</p>
     *
     * @param fileName 확인할 파일명
     * @param seconds  확인할 시간
     * @return 지정 시간까지 파일이 존재하면 TRUE, 아니면 FALSE.
     */
    public static boolean waitFor(String fileName, int seconds) {
        File file = new File(fileName);
        int timeout = 0;
        int tick = 0;

        while (!file.exists()) {
            if (tick++ >= 10) {
                tick = 0;

                if (timeout++ > seconds) {
                    return false;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                log.debug(ie.getMessage());
            } catch (Exception e) {
                log.debug(e.getMessage());
                break;
            }
        }

        return true;
    }


    /**
     * 상위 디렉토리 가져온다.
     *
     * @param dirPath 확인할 파일명
     */
    public static String getParentDir(String dirPath) {
        String retVal = new File(dirPath).getParent();

        if (retVal == null) {
            retVal = "./";
        }

        return retVal;
    }

    /**
     * 파일_업로드
     *
     * @param azureFileStorageConnectionString
     * @param multipartFile
     * @param sysFileNm
     * @return
     */
    public static String fileUpload(String azureFileStorageConnectionString, MultipartFile multipartFile, String sysFileNm) {

        String temporaryFilePath = "./";
        String filePath = "";

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        String today = formatter.format(now);

        try {
            CloudStorageAccount cloudStorageAccount = CloudStorageAccount.parse(azureFileStorageConnectionString);
            CloudFileClient cloudFileClient = cloudStorageAccount.createCloudFileClient();
            CloudFileShare cloudFileShare = cloudFileClient.getShareReference(AZURE_SHARE_FOLDER);

            if (cloudFileShare.createIfNotExists()) {
                log.debug("{} share folder created", AZURE_SHARE_FOLDER);
            } else {
                log.debug("{} share folder already exists", AZURE_SHARE_FOLDER);
            }

            CloudFileDirectory shareRootDirectory = cloudFileShare.getRootDirectoryReference();
            // yyyyMMdd 디렉토리 생성
            CloudFileDirectory directory = shareRootDirectory.getDirectoryReference(today);

            if (directory.createIfNotExists()) {
                log.debug("{} directory created", today);
            } else {
                log.debug("{} directory  already exists", today);
            }

            CloudFile cloudFile = directory.getFileReference(sysFileNm);
            cloudFile.upload(multipartFile.getInputStream(), multipartFile.getSize());

            filePath = directory.getUri().getPath();
        } catch (InvalidKeyException | URISyntaxException invalidKey) {
            // Handle the exception
        } catch (StorageException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filePath;
    }



    /**
     * 파일_업로드_엑셀데이터 업로드 시뮬레이션과 배치에서 엑셀파일 만든후
     *
     * @param azureFileStorageConnectionString
     * @param xssfWorkbook
     * @param sysFileNm
     * @return
     */
    public static String[] fileUpload(String azureFileStorageConnectionString, XSSFWorkbook xssfWorkbook, String sysFileNm, String azureShareDirectory) {

        String[] rtnArray = {"", ""};

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        String today = formatter.format(now);

        if(StringUtils.isEmpty(azureShareDirectory)){
            azureShareDirectory = AZURE_SHARE_FOLDER;
        }

        try {
            CloudStorageAccount cloudStorageAccount = CloudStorageAccount.parse(azureFileStorageConnectionString);
            CloudFileClient cloudFileClient = cloudStorageAccount.createCloudFileClient();
            CloudFileShare cloudFileShare = cloudFileClient.getShareReference(azureShareDirectory);

            if (cloudFileShare.createIfNotExists()) {
                log.debug("{} share folder created", azureShareDirectory);
            } else {
                log.debug("{} share folder already exists", azureShareDirectory);
            }

            CloudFileDirectory shareRootDirectory = cloudFileShare.getRootDirectoryReference();
            // yyyyMMdd 디렉토리 생성
            CloudFileDirectory directory = shareRootDirectory.getDirectoryReference(today);

            if (directory.createIfNotExists()) {
                log.debug("{} directory created", today);
            } else {
                log.debug("{} directory  already exists", today);
            }

            CloudFile cloudFile = directory.getFileReference(sysFileNm);

            makeDirectory(TEMP_FILE_DIR);

            OutputStream fos = new FileOutputStream(TEMP_FILE_DIR + sysFileNm);
            xssfWorkbook.write(fos);
            cloudFile.uploadFromFile(TEMP_FILE_DIR + sysFileNm);
            File file = new File(TEMP_FILE_DIR + sysFileNm);
            rtnArray[0] = directory.getUri().getPath();
            rtnArray[1] = file.length() + "";
            fos.close();
            fileDelete(TEMP_FILE_DIR + sysFileNm);

        } catch (InvalidKeyException | URISyntaxException invalidKey) {
            // Handle the exception
        } catch (StorageException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rtnArray;
    }

    /**
     * 파일_다운로드2
     *
     * @param azureFileStorageConnectionString
     * @param filePath
     * @param sysFileNm
     * @return
     */
    public static CloudFile getFileCloud(String azureFileStorageConnectionString, String filePath, String sysFileNm) throws Exception {
        int resultCode = 0;
        String[] filePaths = filePath.split("/");

        CloudStorageAccount cloudStorageAccount = CloudStorageAccount.parse(azureFileStorageConnectionString);
        CloudFileClient cloudFileClient = cloudStorageAccount.createCloudFileClient();
        CloudFileShare cloudFileShare = cloudFileClient.getShareReference(filePaths[1]);

        if (cloudFileShare.createIfNotExists()) {
            log.info("{} share folder created", filePaths[1]);
        } else {
            log.info("{} share folder already exists", filePaths[1]);
        }

        CloudFileDirectory shareRootDirectory = cloudFileShare.getRootDirectoryReference();
        CloudFileDirectory directory = shareRootDirectory.getDirectoryReference(filePaths[2]);

        if (directory.createIfNotExists()) {
            log.info("{} directory created", filePaths[2]);
        } else {
            log.info("{} directory  already exists", filePaths[2]);
        }

        CloudFile cloudFile = directory.getFileReference(sysFileNm);
        log.info("cloudFile get ===================================>" + cloudFile.getName());
        return cloudFile;
    }


    /**
     * blob파일_다운로드 파일 형식 "2023_05_18_13_22_45-2023_05_18_13_40_36-1-1000.csv
     *
     * @param azureBlobStorageConnectionString
     * @param filePath
     * @param sysFileNm
     * @return
     */
    public static CloudBlockBlob getBlobCloud(String azureBlobStorageConnectionString, String containerName, String filePath, String sysFileNm) throws Exception {
        CloudStorageAccount cloudStorageAccount = CloudStorageAccount.parse(azureBlobStorageConnectionString);
        CloudBlobClient blobClient = cloudStorageAccount.createCloudBlobClient();
        CloudBlobContainer container = blobClient.getContainerReference(containerName);
        container.createIfNotExists();
        CloudBlockBlob blob = container.getBlockBlobReference(filePath + "/" + sysFileNm);
        log.debug(blob.exists()+"");
        return blob;
    }

    /**
     * 파일_다운로드
     *
     * @param azureFileStorageConnectionString
     * @param filePath
     * @param sysFileNm
     * @return
     */
    public static CloudFile fileDownload(String azureFileStorageConnectionString, String filePath, String sysFileNm) throws Exception {
        //filePath = "chgdeviot001/ESP230215KECH99";
        //sysFileNm = "2023_04_11_10_57_07-2023_04_11_11_22_08-1-100.csvn";

        String[] filePaths = filePath.split("/");

        CloudStorageAccount cloudStorageAccount = CloudStorageAccount.parse(azureFileStorageConnectionString);
        CloudFileClient cloudFileClient = cloudStorageAccount.createCloudFileClient();
        CloudFileShare cloudFileShare = cloudFileClient.getShareReference(filePaths[1]); // chgdeviot001

        if (cloudFileShare.createIfNotExists()) {
            log.debug("{} share folder created", filePaths[1]);
        } else {
            log.debug("{} share folder already exists", filePaths[1]);
        }

        CloudFileDirectory shareRootDirectory = cloudFileShare.getRootDirectoryReference();
        CloudFileDirectory directory = shareRootDirectory.getDirectoryReference(filePaths[2]);

        if (directory.createIfNotExists()) {
            log.debug("{} directory created", filePaths[2]);
        } else {
            log.debug("{} directory  already exists", filePaths[2]);
        }

        return directory.getFileReference(sysFileNm);
    }

    /**
     * Azure 파일 삭제
     *
     * @param azureFileStorageConnectionString
     * @param dirNm
     * @param fileNm
     * @return
     * @throws Exception
     */
    public static boolean cloudFileDelete(String azureFileStorageConnectionString, String dirNm, String fileNm) {

        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(azureFileStorageConnectionString);
            CloudFileClient fileClient = storageAccount.createCloudFileClient();
            CloudFileShare share = fileClient.getShareReference(AZURE_SHARE_FOLDER);

            if (share.createIfNotExists()) {
                log.debug("New share created");
            }

            CloudFileDirectory rootDir = share.getRootDirectoryReference();
            CloudFileDirectory containerDir = rootDir.getDirectoryReference(dirNm);

            CloudFile file = containerDir.getFileReference(fileNm);

            if (file.deleteIfExists()) {
                log.debug(fileNm + " was deleted");
            } else {
                return false;
            }
        } catch (InvalidKeyException | URISyntaxException invalidKey) {
            // Handle the exception
        } catch (StorageException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
