package kih.splearn.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kih.splearn.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {
    @Embedded
    private Profile profile;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(nullable = false)
    private LocalDateTime registeredAt;
    private LocalDateTime activatedAt;
    private LocalDateTime deactivatedAt;

    public static MemberDetail crete() {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.registeredAt = LocalDateTime.now();
        return memberDetail;
    }

    void activate() {
        Assert.isTrue(activatedAt == null, "이미 activatedAt은 설정되었습니다.");
        this.activatedAt = LocalDateTime.now();
    }

    void deactivate() {
        Assert.isTrue(deactivatedAt == null, "이미 deactivatedAt은 설정되었습니다.");
        this.deactivatedAt = LocalDateTime.now();
    }

    void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.profile = convertToProfile(updateRequest.profileAddress());
        this.introduction = Objects.requireNonNull(updateRequest.introduction());
    }

    private Profile convertToProfile(@NotNull @Size(max = 15) String profileAddress) {
        if(profileAddress == null || profileAddress.isEmpty()) return null;
        return new Profile(profileAddress);
    }
}
