package kih.splearn.application.member.provided;

import kih.splearn.domain.member.Member;

public interface MemberFinder {
    Member find(Long memberId);
}
