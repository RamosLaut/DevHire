package TP_Final.devhire.Services;

import TP_Final.devhire.Repositories.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;
}
