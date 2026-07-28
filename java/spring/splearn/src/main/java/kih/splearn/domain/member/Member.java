package kih.splearn.domain.member;

import jakarta.persistence.*;
import kih.splearn.domain.AbstractEntity;
import kih.splearn.domain.shared.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.util.Assert;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "MEMBER",
        uniqueConstraints =
        @UniqueConstraint(name = "UK_MEMBER_EMAIL_ADDRESS", columnNames = "email_address")
    )
@Getter
@ToString(callSuper = true, exclude = "detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
public class Member extends AbstractEntity {
    @Embedded
    @NaturalId
    private Email email;

    @Column(length = 100, nullable = false)
    private String nickname;

    @Column(length = 200, nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private MemberStatus status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberDetail detail;

    public static Member create(MemberRegisterRequest createRequest, PasswordEncoder passwordEncoder){
        Member member = new Member();

        member.email = new Email(createRequest.email());
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(createRequest.password()));
        member.status = MemberStatus.PENDING;

        member.detail = MemberDetail.crete();

        return member;
    }

    public void activate(){
        Assert.state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");
        this.status = MemberStatus.ACTIVE;
        this.detail.activate();
    }

    public boolean isActive(){
        return this.status == MemberStatus.ACTIVE;
    }

    public void deactivate() {
        Assert.state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
        this.detail.deactivate();
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void updateInfo(MemberInfoUpdateRequest updateRequest){
        Assert.state(getStatus() == MemberStatus.ACTIVE, "등록완료 상태가 아니라 정보를 수정할 수 없습니다.");

        this.nickname = Objects.requireNonNull(updateRequest.nickname());
        this.detail.updateInfo(updateRequest);

    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }
}
