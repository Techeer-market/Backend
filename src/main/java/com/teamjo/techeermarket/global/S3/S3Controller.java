package com.teamjo.techeermarket.global.S3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class S3Controller {

    private final S3ServiceImpl s3ServiceImpl;

//    @PostMapping(value = "/upload/{product_id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public String uploadImage(@RequestPart List<MultipartFile> imagelist,
//                              @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable String product_id) throws IOException {
//        String user = userDetailsImpl.getUsername();
//        String product = ProductRepository.getProductByProductId(product_id, user);
//        s3Service.uploadProductImage(BucketDir.PHOTO, imagelist, );
//
//    }
}
