package com.dishcraft.services;

import java.io.IOException;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dishcraft.model.Image;
import com.dishcraft.repositories.ImageRepository;

@Service
public class ImageService {

	private final ImageRepository imageRepository;
	
	public ImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}
	
    public Image uploadImage(MultipartFile file) throws IOException {
        return imageRepository.save(
                Image.builder()
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .data(ImageUtil.compressImage(file.getBytes()))
                    .build()
        );
    }
    
    @Transactional
    public Image getInfoByImageByName(String name) {
        Optional<Image> dbImage = imageRepository.findByFileName(name);

        return Image.builder()
                .fileName(dbImage.get().getFileName())
                .fileType(dbImage.get().getFileType())
                .data(ImageUtil.decompressImage(dbImage.get().getData())).build();

    }

    public byte[] downloadImage(Long id) {
        Optional<Image> imageData = imageRepository.findById(id);
        return ImageUtil.decompressImage(imageData.get().getData());
    }
    
    public byte[] downloadImage(Image image) {
//        Optional<Image> imageData = imageRepository.findById(id);
        return ImageUtil.decompressImage(image.getData());
    }
   
}
