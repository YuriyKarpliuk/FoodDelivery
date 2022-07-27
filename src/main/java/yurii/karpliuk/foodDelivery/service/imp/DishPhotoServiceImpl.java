package yurii.karpliuk.foodDelivery.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.foodDelivery.entity.Dish;
import yurii.karpliuk.foodDelivery.entity.DishPhoto;
import yurii.karpliuk.foodDelivery.exception.CouldNotDeleteFileException;
import yurii.karpliuk.foodDelivery.exception.CouldNotStoreFileException;
import yurii.karpliuk.foodDelivery.exception.DishNotFoundException;
import yurii.karpliuk.foodDelivery.repository.DishRepository;
import yurii.karpliuk.foodDelivery.repository.DishPhotoRepository;
import yurii.karpliuk.foodDelivery.service.DishPhotoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DishPhotoServiceImpl implements DishPhotoService {
    @Autowired
    private DishPhotoRepository dishPhotoRepository;
    @Autowired
    private DishRepository dishRepository;
    private final Path root = Paths.get("D:\\MyProjects\\Images");

    @Override
    public void saveImgs(List<MultipartFile> multipartFiles, Long dishId) {
        try {
            for (MultipartFile file : multipartFiles) {
                saveFile(file, dishId);
            }
        } catch (IOException e) {
            throw new CouldNotStoreFileException("Could not store the file");
        }
    }

    private void saveFile(MultipartFile multipartFile, Long dishId) throws IOException {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new DishNotFoundException("Dish is not found"));
        Path path = this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        DishPhoto dishPhoto = new DishPhoto();
        dishPhoto.setImgUrl(path.toAbsolutePath().toString());
        dishPhotoRepository.save(dishPhoto);
        dish.getPhotos().add(dishPhoto);
        dishRepository.save(dish);
        dishPhoto.setDish(dish);
        dishPhotoRepository.save(dishPhoto);
    }

    @Override
    public void deleteImgs(Long dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new DishNotFoundException("Dish is not found"));
        for (DishPhoto dishPhoto : dish.getPhotos()) {
            try {
                Path path = this.root.resolve(Objects.requireNonNull(dishPhoto.getImgUrl()));
                dishPhotoRepository.delete(dishPhoto);
                Files.delete(path);
            } catch (IOException e) {
                throw new CouldNotDeleteFileException("Could not delete file");
            }
        }
        dish.getPhotos().clear();
        dishRepository.save(dish);
    }
}
