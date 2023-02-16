package com.company.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {
    private Integer id;
    private String originalFileName;
    private String generatedFileName;
    private String mimeType;
    private String filePath;
    private Long fileSize;
    private String extension;
}
