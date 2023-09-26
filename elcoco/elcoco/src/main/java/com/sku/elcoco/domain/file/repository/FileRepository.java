package com.sku.elcoco.domain.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sku.elcoco.domain.file.entity.File;


import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {

    List<File> findAllByPostId(Long id);

    void deleteAllByPostId(Long id);
}
