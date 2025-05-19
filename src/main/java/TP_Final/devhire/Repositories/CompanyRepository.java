package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.CompanyEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

//    CompanyEntity findByName(String name);
//    CompanyEntity UpdateById(String id,)
}
