package kih.splearn.application.member;

import kih.splearn.application.member.provided.LoginFailedException;
import kih.splearn.application.member.provided.MemberAuthenticator;
import kih.splearn.application.member.provided.MemberLoginRequest;
import kih.splearn.application.member.required.MemberRepository;
import kih.splearn.domain.member.Member;
import kih.splearn.domain.member.PasswordEncoder;
import kih.splearn.domain.shared.Email;
import kih.splearn.support.stereotype.ValidatedApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@ValidatedApplicationService
@RequiredArgsConstructor
public class MemberAuthenticationService implements MemberAuthenticator {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Member login(MemberLoginRequest memberLoginRequest) throws LoginFailedException {
        Member member = memberRepository.findByEmail(
                new Email(memberLoginRequest.email())
        ).orElseThrow(LoginFailedException::new);

        if(!member.isActive()){
            throw new LoginFailedException();
        }

        if(!member.verifyPassword(memberLoginRequest.password(), passwordEncoder)){
            throw new LoginFailedException();
        }
        return member;
    }
}
