package com.example.stock_service_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_info")
@EntityListeners(AuditingEntityListener.class)
public class UserInfo implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long seq;

    @NotNull
    @Column(unique = true)
    private String id;
    private String password;
    private String name;
    private String nickName;

    @CreatedDate
    private LocalDateTime createAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    //권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    //사용자의 id를 반환(고유한 값)
    @Override
    public String getUsername() {
        return id;
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        //만료되었는지 확인하는 로직
        return true; // true - > 만료되지 않았음
    }
    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; //true -> 잠금되지 않았음
    }
    //패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;   // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true; //  true -> 사용 가능
    }
}
