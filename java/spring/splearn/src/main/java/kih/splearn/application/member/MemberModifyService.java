package kih.splearn.application.member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kih.splearn.application.member.provided.MemberRegister;
import kih.splearn.application.member.required.EmailSender;
import kih.splearn.application.member.required.MemberRepository;
import kih.splearn.domain.member.*;
import kih.splearn.domain.shared.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest request) {
        checkDuplicateEmail(request);

        Member member = Member.create(request, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberRepository.findById(memberId ).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. "+memberId));

        member.activate();
        return memberRepository.save(member);
    }

    @Override
    public Member deactivate(Long memberId) {
        Member member = memberRepository.findById(memberId ).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. "+memberId));

        member.deactivate();
        return memberRepository.save(member);
    }

    @Override
    public Member updateInfo(Long memberId, MemberInfoUpdateRequest memberInfoUpdateRequest) {
        Member member = memberRepository.findById(memberId ).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. "+memberId));

        checkDuplicateProfile(member, memberInfoUpdateRequest.profileAddress());

        member.updateInfo(memberInfoUpdateRequest);
        return memberRepository.save(member);
    }

    private void checkDuplicateProfile(Member member, String profileAddress) {
        if(profileAddress.isEmpty()) return;

        Profile currentProfile = member.getDetail().getProfile();
        if(currentProfile != null && currentProfile.address().equals(profileAddress)) return;

        if(memberRepository.findByProfile(new Profile(profileAddress)).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 프로필 주소입니다.");
        }
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해 주세요.", "아래 링크를 클릭해서 등록을 완료해 주세요.");
    }

    private void checkDuplicateEmail(MemberRegisterRequest request) {
        if(memberRepository.findByEmail(new Email(request.email())).isPresent()){
            throw new DuplicateEmailException("이미 사용중인 이메일입니다. " + request.email());
        }
    }

}
