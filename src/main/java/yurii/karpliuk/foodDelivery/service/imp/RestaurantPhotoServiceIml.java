package yurii.karpliuk.foodDelivery.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.foodDelivery.entity.Restaurant;
import yurii.karpliuk.foodDelivery.entity.RestaurantPhoto;
import yurii.karpliuk.foodDelivery.exception.CouldNotDeleteFileException;
import yurii.karpliuk.foodDelivery.exception.CouldNotStoreFileException;
import yurii.karpliuk.foodDelivery.exception.DishNotFoundException;
import yurii.karpliuk.foodDelivery.repository.RestaurantPhotoRepository;
import yurii.karpliuk.foodDelivery.repository.RestaurantRepository;
import yurii.karpliuk.foodDelivery.service.RestaurantPhotoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class RestaurantPhotoServiceIml implements RestaurantPhotoService {
    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantPhotoRepository restaurantPhotoRepository;

    private final Path root = Paths.get("D:\\MyProjects\\Images");

    @Override
    public void saveImgs(List<MultipartFile> multipartFiles, Long restaurantId) {
        try {
            for (MultipartFile file : multipartFiles) {
                saveFile(file, restaurantId);
            }
        } catch (IOException e) {
            throw new CouldNotStoreFileException("Could not store the file");
        }
    }

    private void saveFile(MultipartFile multipartFile, Long restaurantId) throws IOException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new DishNotFoundException("Restaurant is not found"));
        Path path = this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        RestaurantPhoto restaurantPhoto = new RestaurantPhoto();
        restaurantPhoto.setImgUrl(path.toAbsolutePath().toString());
        restaurantPhotoRepository.save(restaurantPhoto);
        restaurant.getRestaurantPhotoList().add(restaurantPhoto);
        restaurantRepository.save(restaurant);
        restaurantPhoto.setRestaurant(restaurant);
        restaurantPhotoRepository.save(restaurantPhoto);
    }

    @Override
    public void deleteImgs(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new DishNotFoundException("Restaurant is not found"));
        for (RestaurantPhoto restaurantPhoto : restaurant.getRestaurantPhotoList()) {
            try {
                Path path = this.root.resolve(Objects.requireNonNull(restaurantPhoto.getImgUrl()));
                restaurantPhotoRepository.delete(restaurantPhoto);
                Files.delete(path);
            } catch (IOException e) {
                throw new CouldNotDeleteFileException("Could not delete file");
            }
        }
        restaurant.getRestaurantPhotoList().clear();
        restaurantRepository.save(restaurant);
    }
}
