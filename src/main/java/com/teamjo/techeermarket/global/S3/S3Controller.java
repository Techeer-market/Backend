package com.teamjo.techeermarket.global.S3;

import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class S3Controller {

    private final S3Service s3Service;

//    @PostMapping(value = "/upload/{product_id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public String uploadImage(@RequestPart List<MultipartFile> imagelist,
//                              @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable String product_id) throws IOException {
//        String user = userDetailsImpl.getUsername();
//        String product = ProductRepository.getProductByProductId(product_id, user);
//        s3Service.uploadProductImage(BucketDir.PHOTO, imagelist, );
//
//    }
}
