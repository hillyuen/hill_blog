package com.hillyuen.repository;

import com.hillyuen.model.domain.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     附件持久层
 * </pre>
 *
 * @author : Hill_Yuen
 * @date : 2018/1/10
 */
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    /**
     * 查询所有附件，分页
     *
     * @param pageable pageable
     * @return Page
     */
    @Override
    Page<Attachment> findAll(Pageable pageable);
}
