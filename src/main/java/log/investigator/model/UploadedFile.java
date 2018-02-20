package log.investigator.model;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;


public class UploadedFile {

    private String originalFileName = "";
    private String fileType = "";
    private MultipartFile file;
    private String contentType = "";

    public UploadedFile(MultipartFile file){
        setOriginalFileName(file.getOriginalFilename());
        setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
        setFile(file);
        setContentType(file.getContentType());
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
