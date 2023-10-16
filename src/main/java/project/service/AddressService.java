package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Address;
import project.repository.AddressRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {
    private final AddressRepository addressRepository;

    // 주소 검색 및 저장
    @Transactional
    public void saveAddress(Address address) {
        addressRepository.saveAddress(address);
    }
}
