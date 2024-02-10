package com.kimseungjin.cafe.global.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface SoftDeleteRepository<T, ID> extends JpaRepository<T, ID> {

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE #{#entityName} e SET e.deleted = true WHERE e = :entity",
            nativeQuery = true)
    void softDelete(final T entity);
}
