package com.ecommerce.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class DefaultTemplates {

    @Column(name = "invoice_template_id")
    private String invoiceTemplateId;

    @Column(name = "creditnote_template_id")
    private String creditnoteTemplateId;

    // Getters and Setters
    // ...
}
