package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.domain.APIKey;
import project.domain.Member;
import project.repository.APIKeyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class APIKeyService {
    private final APIKeyRepository apiKeyRepository;

    public List<APIKey> findAll(){
        return apiKeyRepository.findAll();
    }
}
