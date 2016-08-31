package com.tenx.ms.retail.service;

import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.retail.domain.StoreEntity;
import com.tenx.ms.retail.repository.StoreRepository;
import com.tenx.ms.retail.rest.dto.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public Long createNewStore(Store storeDto) {
        StoreEntity store = new StoreEntity();
        store.setName(storeDto.getName());
        storeRepository.save(store);
        return store.getId();
    }

    public Store getStoreById(Long id) {
        StoreEntity entity = storeRepository.findById(id).get();
        return convertToDTO(entity);
    }

    public Paginated<Store> getAllStores(Pageable pageable, String baseLinkPath) throws ValidationException {
        Page<StoreEntity> curPage = storeRepository.findAll(pageable);
        List<Store> storesList = curPage.getContent().stream().map(this::convertToDTO).collect(Collectors.toList());
        return Paginated.wrap(curPage, storesList, baseLinkPath);
    }

    private Store convertToDTO(StoreEntity storeEntity) {
        Store storeDto = new Store();
        storeDto.setId(storeEntity.getId());
        storeDto.setName(storeEntity.getName());
        return storeDto;
    }

    public void deleteStore(Long storeId) {
        storeRepository.delete(storeId);
    }
}
