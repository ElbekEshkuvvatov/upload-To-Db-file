package com.example.demo.repository;


import com.example.demo.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentContentRepository  extends JpaRepository<AttachmentContent, Integer> {

    // JPA query  yozib attachmentcontent ni ichidan attachment id ni topib oldim
   Optional<AttachmentContent> findByAttachmentId(Integer attachment_id);


  //  @Query("update AttachmentContent a set a.attachment =null  where  a.attachment =:attachment ")
  // Optional<Attachment> findByAttachmentId(Integer attachment_id);

}
