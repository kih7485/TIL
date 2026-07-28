package kih.splearn.application.member.required;

import kih.splearn.domain.member.Profile;
import kih.splearn.domain.shared.Email;
import kih.splearn.domain.member.Member;
import org.springframework.boot.autoconfigure.container.ContainerImageMetadata;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);

    @Query("select m from Member m where m.detail.profile = :profile")
    Optional<Member> findByProfile(Profile profile);
}
