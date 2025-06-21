package net.kkennib.house.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kkennib.house.enums.AccountType;

@Table(name = "member")
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long no;

    @Column
    private String name;

    @Column
    private String picture;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name="account_type", nullable = false)
    private String accountType = AccountType.EMAIL.getValue();

    public Member(String email, String password, String accountType, String name, String picture) {
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.name = name;
        this.picture = picture;
    }
}

