package TP_Final.devhire.Services;

import TP_Final.devhire.Repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CommentRepository commentRepository;
}
