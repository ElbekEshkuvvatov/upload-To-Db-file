package com.example.demo.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "attachment")   //A
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileOriginalName;    //school.jpg , inn.pdf

    private Long size;            // 2 344 667 bytes bo'lishi mumkin

    private String contentType;    //   application.img, Ronaldo.jpg  buni faqat rasm type oladigan qilib tekshirib quyaman

    @OneToOne(mappedBy = "attachment", cascade = CascadeType.ALL)
    private AttachmentContent content;

    public Attachment(String fileOriginalName, Long size, String contentType) {
        this.fileOriginalName = fileOriginalName;
        this.size = size;
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Attachment that = (Attachment) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
