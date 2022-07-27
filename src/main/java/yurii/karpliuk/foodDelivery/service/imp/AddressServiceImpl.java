package yurii.karpliuk.foodDelivery.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yurii.karpliuk.foodDelivery.dto.request.AddressAddRequest;
import yurii.karpliuk.foodDelivery.dto.response.AddressResponse;
import yurii.karpliuk.foodDelivery.dto.response.OrderResponse;
import yurii.karpliuk.foodDelivery.entity.Address;
import yurii.karpliuk.foodDelivery.entity.Order;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;
import yurii.karpliuk.foodDelivery.repository.AddressRepository;
import yurii.karpliuk.foodDelivery.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Override
    public void createAddress(AddressAddRequest addressAddRequest) {
        Address address = new Address();
        address.setNameOfCountry(addressAddRequest.getNameOfCountry());
        address.setNameOfCity(addressAddRequest.getNameOfCity());
        address.setNameOfStreet(addressAddRequest.getNameOfStreet());
        address.setNumberOfStreet(addressAddRequest.getNumberOfStreet());
        addressRepository.save(address);
    }

    @Override
    public Address findAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Address with id:" + addressId));
    }

    @Override
    public AddressResponse buildAddressResponse(Address address) {
        AddressResponse response = new AddressResponse();
        response.setNameOfCountry(address.getNameOfCountry());
        response.setNameOfCity(address.getNameOfCity());
        response.setNameOfStreet(address.getNameOfStreet());
        response.setNumberOfStreet(address.getNumberOfStreet());
        return response;
    }
}
