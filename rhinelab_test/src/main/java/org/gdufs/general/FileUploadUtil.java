package org.gdufs.general;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadUtil {

    public static void saveFileToForm(MultipartFile file) {
        try {
            // 获取当前目录的路径
            String currentDirectory = System.getProperty("user.dir");

            // 创建mulFile文件夹路径
            String targetDirectory = currentDirectory + File.separator + "applications";

            // 创建mulFile文件夹
            File directory = new File(targetDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 获取文件名
            String fileName = file.getOriginalFilename();

            // 创建目标文件路径
            Path targetPath = Paths.get(targetDirectory, fileName);

            // 将文件保存到目标路径
            file.transferTo(targetPath.toFile());

            System.out.println("File successful save!：" + targetPath);
        } catch (Exception e) {
            System.out.println("File save with Failed!：" + e.getMessage());
        }
    }

    public static void saveFileToPhoto(MultipartFile file) {
        try {
            // 获取当前目录的路径
            String currentDirectory = System.getProperty("user.dir");

            // 创建mulFile文件夹路径
            String targetDirectory = currentDirectory + File.separator + "photos";

            // 创建mulFile文件夹
            File directory = new File(targetDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 获取文件名
            String fileName = file.getOriginalFilename();

            // 创建目标文件路径
            Path targetPath = Paths.get(targetDirectory, fileName);

            // 将文件保存到目标路径
            file.transferTo(targetPath.toFile());

            System.out.println("File successful save!：" + targetPath);
        } catch (Exception e) {
            System.out.println("File save with Failed!：" + e.getMessage());
        }
    }
}