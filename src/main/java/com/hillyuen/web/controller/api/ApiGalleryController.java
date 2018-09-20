package com.hillyuen.web.controller.api;

import com.hillyuen.model.domain.Gallery;
import com.hillyuen.model.dto.Result;
import com.hillyuen.model.enums.ResponseStatus;
import com.hillyuen.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author : Hill_Yuen
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/galleries")
public class ApiGalleryController {

    @Autowired
    private GalleryService galleryService;

    /**
     * 获取所有图片
     *
     * @return Result
     */
    @GetMapping
    public Result galleries() {
        List<Gallery> galleries = galleryService.findAllGalleries();
        if (null != galleries && galleries.size() > 0) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), galleries);
        } else {
            return new Result(ResponseStatus.EMPTY.getCode(), ResponseStatus.EMPTY.getMsg());
        }
    }

    /**
     * 获取单张图片的信息
     *
     * @param id id
     * @return Result
     */
    @GetMapping(value = "/{id}")
    public Result galleries(@PathVariable("id") Long id) {
        Optional<Gallery> gallery = galleryService.findByGalleryId(id);
        if (gallery.isPresent()) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), gallery.get());
        } else {
            return new Result(ResponseStatus.NOTFOUND.getCode(), ResponseStatus.NOTFOUND.getMsg());
        }
    }
}
