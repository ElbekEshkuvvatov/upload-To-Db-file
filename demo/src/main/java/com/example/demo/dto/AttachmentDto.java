package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDto {
    private Integer id;
    private String fileOriginalName;
    private Long size;
    private String contentType;
    private byte[] data;


}
