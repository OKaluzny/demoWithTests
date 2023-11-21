package com.example.demowithtests.domain;

import com.example.demowithtests.util.annotations.entity.Name;
import com.example.demowithtests.util.annotations.entity.ToLowerCase;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public final class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Name
    private String name;

    private String country;

    @ToLowerCase
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id desc, country asc")
    private Set<Address> addresses = new HashSet<>();


    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne  (cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;

    @Column(name = "is_deleted")
    private Boolean is_Deleted = Boolean.FALSE;

    public Boolean getIs_Deleted() {
        if (this.is_Deleted == null) {
            return false;
        }
        return this.is_Deleted;
    }


}

