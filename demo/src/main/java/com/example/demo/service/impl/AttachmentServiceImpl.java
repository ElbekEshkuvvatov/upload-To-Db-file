package com.example.demo.service.impl;



import com.example.demo.dto.AttachmentDto;
import com.example.demo.entity.Attachment;
import com.example.demo.entity.AttachmentContent;
import com.example.demo.entity.Response;
import com.example.demo.repository.AttachmentContentRepository;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.service.AttachmentService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AttachmentServiceImpl implements AttachmentService {

    final AttachmentRepository attachmentRepository;
    final AttachmentContentRepository attachmentContentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository,
                                 AttachmentContentRepository attachmentContentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }


    @Override
    public Attachment saveAttachment(Attachment attachment) {

        return attachmentRepository.save(attachment);
    }

    @Override
    public Response saveAttachmentContent(AttachmentContent content) {

        AttachmentContent content1 = attachmentContentRepository.save(content);
        return new Response("File saved", true, content1.getAttachment().getFileOriginalName());
    }

    @SneakyThrows
    @Override
    public AttachmentDto getFile(Integer attachmentId) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentId);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachmentId);
            if (optionalAttachmentContent.isPresent()) {
                AttachmentContent attachmentContent = optionalAttachmentContent.get();
                return new AttachmentDto(
                        attachment.getId(),
                        attachment.getFileOriginalName(),
                        attachment.getSize(),
                        attachment.getContentType(),
                        attachmentContent.getAsosiyContent()

                );
            }
        }
        return null;
    }

    @Override
    public Response deleteAttachment(Integer id) {
        if (attachmentRepository.existsById(id)) {
            attachmentRepository.deleteById(id);
            return new Response("Information was deleted ", true);
        }
        return new Response("Such Information not found", false);
    }


}
