package com.freshfood.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review_replies", uniqueConstraints = @UniqueConstraint(columnNames = "review_id"))
public class ReviewReply extends AbsEntity<Integer> {
    @OneToOne
    @JoinColumn(name = "review_id", nullable = false)
    @JsonBackReference
    private Review review;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @Column(name = "reply_text", nullable = false, columnDefinition = "TEXT")
    private String replyText;
}
