package com.example.demo.service;

import com.example.demo.dto.AttachmentDto;
import com.example.demo.entity.Attachment;
import com.example.demo.entity.AttachmentContent;
import com.example.demo.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface AttachmentService {

    Attachment saveAttachment(Attachment attachment);

    Response saveAttachmentContent(AttachmentContent content);

    AttachmentDto getFile(Integer id);

    Response deleteAttachment(Integer id);

}
