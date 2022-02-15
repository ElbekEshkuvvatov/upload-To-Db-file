package com.example.demo.controller;



import com.example.demo.dto.AttachmentDto;
import com.example.demo.entity.Attachment;
import com.example.demo.entity.AttachmentContent;
import com.example.demo.entity.Response;
import com.example.demo.security.CurrentUser;
import com.example.demo.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.util.Optional;


@RestController
@RequestMapping("/api/attachment")
@RequiredArgsConstructor
public class AttachmentController {
    final AttachmentService attachmentService;

    @SneakyThrows    // bu anatatsiya chiqqan exceptionlarni tutib turadi  Throws kalit so'ziga o'xshab
    @PostMapping("/upload")
 //   @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public Response uploadFileToDb(@RequestParam("file") MultipartFile file) {     // MultipartHttpServletRequest bu class clientdan kelgan  faylni tutib olib qismlarga ajratibberadi
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();

            Attachment attachment = new Attachment(
                    originalFilename,
                    size,
                    contentType         // ma'lumotlarni construktor orqali joyladim yangi object yaratib
            );

            Attachment savedAttachment = attachmentService.saveAttachment(attachment);

            // File contentini saqlaymiz
            AttachmentContent attachmentContent = new AttachmentContent(
                    savedAttachment,
                    file.getBytes()
            );
            return attachmentService.saveAttachmentContent(attachmentContent);
        }
        return new Response("Error!", false);
    }

    @GetMapping("/getFile/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {

        Optional<String> optional = CurrentUser.getCurrentUserName();

        AttachmentDto attachment = attachmentService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileOriginalName() + "\"")
                .body(attachment.getData());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Response response = attachmentService.deleteAttachment(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 500).body(response);
    }

}
