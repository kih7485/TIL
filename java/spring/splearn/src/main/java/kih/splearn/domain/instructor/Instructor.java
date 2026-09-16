package kih.splearn.domain.instructor;

import jakarta.persistence.*;
import kih.splearn.domain.AbstractEntity;
import kih.splearn.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.el.parser.AstSetData;
import org.springframework.util.Assert;

import static org.springframework.util.Assert.*;

@Entity
@Getter
@ToString(callSuper = true, exclude = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Instructor extends AbstractEntity {
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private InstructorStatus status;

    public static Instructor apply(Member member){
        //null 허용 x
        //status =
        state(member.isActive(), "등록완료 상태가 아닌 회원 강사 신청을 할 수 없습니다.");

        Instructor instructor = new Instructor();
        instructor.member = member;
        instructor.status = InstructorStatus.PENDING;

        return instructor;
    }

    public void approve() {
        state(status == InstructorStatus.PENDING, "강사의 상태가 PENDING이 아닙니다.");

        status = InstructorStatus.ACTIVE;
    }

    public void reject() {
        state(status == InstructorStatus.PENDING, "강사의 상태가 PENDING이 아닙니다.");

        status = InstructorStatus.REJECTED;
    }

    public boolean isActive() {
        return status == InstructorStatus.ACTIVE;
    }

    public void ensureActive() {
        state(isActive(), "ACTIVE 상태가 아닙니다.");
    }
}
