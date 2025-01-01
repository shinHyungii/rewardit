package kr.rewordit.api.service;

import kr.rewordit.api.common.PaginateResponse;
import kr.rewordit.api.dto.RewardHistoryIndexRequest;
import kr.rewordit.api.dto.RewardHistoryIndexRes;
import kr.rewordit.api.repository.RewardHistoryRepository;
import kr.rewordit.api.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardHistoryService {

    private final RewardHistoryRepository repository;


    public PaginateResponse<RewardHistoryIndexRes> index(RewardHistoryIndexRequest request, CustomUserDetails userDetails) {
        List<RewardHistoryIndexRes> result = repository.findAllByMemberId(
                request.page(),
                Long.parseLong(userDetails.getUsername())
            ).stream()
            .map(RewardHistoryIndexRes::from)
            .collect(Collectors.toList());

        return new PaginateResponse<>(request, result);
    }
}
