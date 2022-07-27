package yurii.karpliuk.foodDelivery.service;

import yurii.karpliuk.foodDelivery.dto.request.AddressAddRequest;
import yurii.karpliuk.foodDelivery.dto.response.AddressResponse;
import yurii.karpliuk.foodDelivery.entity.Address;

public interface AddressService {
    void createAddress(AddressAddRequest addressAddRequest);
    Address findAddressById(Long addressId);
    AddressResponse buildAddressResponse(Address address);
}
