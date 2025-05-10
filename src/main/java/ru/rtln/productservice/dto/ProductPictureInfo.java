package ru.rtln.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPictureInfo {

    private Long id;

    private String fileExtension;

    private String url;

    private String name;

    private String mimeType;

    private Integer size;
}
