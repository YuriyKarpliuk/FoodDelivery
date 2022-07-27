package yurii.karpliuk.foodDelivery.service;

import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.foodDelivery.entity.Dish;
import yurii.karpliuk.foodDelivery.entity.DishPhoto;

import java.util.List;

public interface DishPhotoService {

    void saveImgs(List<MultipartFile> multipartFiles, Long dishId);

    public void deleteImgs(Long dishId);
}
