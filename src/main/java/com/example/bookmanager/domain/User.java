package com.example.bookmanager.domain;

import com.example.bookmanager.domain.listener.Auditable;
import com.example.bookmanager.domain.listener.UserEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "USERS", indexes = {@Index(columnList = "name")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@EntityListeners(value = { UserEntityListener.class})
public class User extends BaseEntity{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)//(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    @Column(unique = true)
    private String email;
    /*
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(name = "created_at", insertable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;
*/
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "home_city")),
            @AttributeOverride(name = "district", column = @Column(name = "home_district")),
            @AttributeOverride(name = "detail", column = @Column(name = "home_detail")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "home_zip_code"))
    })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "company_city")),
            @AttributeOverride(name = "district", column = @Column(name = "company_district")),
            @AttributeOverride(name = "detail", column = @Column(name = "company_detail")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "company_zip_code"))
    })
    private Address companyAddress;
    @Transient //db에 반영되지 않고 entity 객체에서만 활용활 때 사용
    private String testData;

    @Enumerated(value = EnumType.STRING) //enum을 사용할 때 반드시 String을 이용
    private Gender gender;

    @OneToMany(fetch = FetchType.EAGER) //user객체를 호출하면 relation들을 모두 조회해 온다는 설정, Lazy는 persistance context가 해당 entity를 관리하는 시점에만 동작
    @JoinColumn(name = "user_id", insertable = false, updatable = false) //entity가 어떤 컬럼으로 조회할지 지정해주는 어노테이션
    @ToString.Exclude
    private List<UserHistory> userHistories = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();
    /*

    @OneToMany(fetch = FetchType.EAGER)
    private List<Address> address;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PostPersist
    public void postPersist(){
        System.out.println(">>> postPersist");
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    @PostUpdate
    public void postUpdate(){
        System.out.println(">>>postUpdate");
    }

    @PreRemove
    public void preRemove(){
        System.out.println(">>>preRemove");
    }

    @PostRemove
    public void postRemove(){
        System.out.println(">>>postRemove");
    }

    @PostLoad
    public void postLoad(){
        System.out.println(">>>postLoad");
    }

     */
}
