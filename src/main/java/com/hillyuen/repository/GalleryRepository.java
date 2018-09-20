package com.hillyuen.repository;

import com.hillyuen.model.domain.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     图库持久层
 * </pre>
 *
 * @author : Hill_Yuen
 * @date : 2018/2/26
 */
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
