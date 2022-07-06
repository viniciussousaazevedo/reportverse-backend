package com.es.reportverse.model;

import com.es.reportverse.enums.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Midia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String code;

    private Long publicationId;

    // @ManyToOne(fetch = FetchType.EAGER)
    // private Publication publication;

    public Midia(String code, Long publicationId) {
        this.code = code;
        this.publicationId = publicationId;
        // this.publication= publication;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // public Publication getPublicacaoId() {
    //     return this.publication;
    // }

    // public void setPublicacaoId(Publication publication) {
    //     this.publication = publication;
    // }
}
