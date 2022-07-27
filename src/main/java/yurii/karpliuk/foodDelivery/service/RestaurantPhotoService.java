package yurii.karpliuk.foodDelivery.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RestaurantPhotoService {
    void saveImgs(List<MultipartFile> multipartFiles, Long restaurantId);

    void deleteImgs(Long restaurantId);
}
