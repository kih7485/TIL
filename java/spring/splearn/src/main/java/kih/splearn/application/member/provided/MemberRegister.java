package kih.splearn.application.member.provided;

import jakarta.validation.Valid;
import kih.splearn.domain.member.Member;
import kih.splearn.domain.member.MemberInfoUpdateRequest;
import kih.splearn.domain.member.MemberRegisterRequest;

/**
 * 회원의 등록과 관련된 기능을 제공한다.
 * */
public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest request);

    Member activate(Long memberId);

    Member deactivate(Long memberId);

    Member updateInfo(Long memberId, @Valid MemberInfoUpdateRequest memberInfoUpdateRequest);
}
