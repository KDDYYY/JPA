package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.domain.Board;
import project.domain.Image;
import project.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileUploadService {

    private final FileRepository fileRepository;

    @Value("${upload.path}") // application.properties 또는 application.yml에 파일 업로드 경로를 설정합니다.
    private String uploadPath;

    @Transactional
    public void saveFiles(Board board, List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String extension = StringUtils.getFilenameExtension(originalFilename);
                String storedFilename = UUID.randomUUID().toString() + "." + extension;
                String fullPath = uploadPath + storedFilename;
                long fileSize = file.getSize();

                // 파일을 서버에 저장
                Files.write(Paths.get(fullPath), file.getBytes());

                // Image 엔티티 생성 및 저장
                Image image = new Image(originalFilename, storedFilename, fullPath, fileSize, extension, board);

                board.getImages().add(image);

                // Image 엔티티를 저장
                fileRepository.saveFile(image);
            }
        }
    }
}