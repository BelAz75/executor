package com.virtuallab.user;

import com.virtuallab.util.rest.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService {
    private final UserEntityRepository repository;

    public UserService(UserEntityRepository repository) {
        this.repository = repository;
    }

    public PageResponse<User> findAll(int page, int pageSize) {
        final Page<UserEntity> result = repository.findUsers(PageRequest.of(page - 1, pageSize));

        List<User> searchResponse = result.getContent().stream()
                .map(ResponseConverter::toSearchResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                page,
                pageSize,
                result.getTotalPages(),
                result.getTotalElements(),
                searchResponse
        );
    }
}
