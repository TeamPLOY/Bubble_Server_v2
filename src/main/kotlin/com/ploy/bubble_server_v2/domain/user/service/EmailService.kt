package com.ploy.bubble_server_v2.domain.user.service

import com.laundering.laundering_server.domain.member.model.entity.Email
import com.ploy.bubble_server_v2.domain.user.model.entity.Email
import com.ploy.bubble_server_v2.domain.user.repository.EmailRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@lombok.extern.slf4j.Slf4j
class EmailService {
    @Autowired
    private val emailRepository: EmailRepository? = null // 이메일 정보를 저장할 리포지토리

    @org.springframework.beans.factory.annotation.Value("\${spring.mail.host}")
    private val host: String? = null

    @org.springframework.beans.factory.annotation.Value("\${spring.mail.port}")
    private val port: String? = null

    @org.springframework.beans.factory.annotation.Value("\${spring.mail.username}")
    private val senderEmail: String? = null

    @org.springframework.beans.factory.annotation.Value("\${spring.mail.password}")
    private val senderPassword: String? = null

    /**
     * 이메일로 6자리 랜덤 코드 발송 및 정보 저장
     *
     * @param email 수신자 이메일 주소
     */
    fun sendEmail(email: String) {
        // 6자리 랜덤 숫자 생성
        val random = java.util.Random()
        val randomCode = 100000 + random.nextInt(900000) // 100000 ~ 999999 범위의 숫자 생성

        // 이메일 보내기
        send(email, randomCode) // 실제 이메일 전송 로직 호출

        // 이메일 정보 저장
        val emailEntity: Email = Email.builder()
            .email(email)
            .code(randomCode)
            .date(java.time.LocalDate.now()) // 현재 날짜 설정
            .build()

        emailRepository.save(emailEntity) // 이메일 정보 데이터베이스에 저장
    }

    /**
     * 실제 이메일 전송 로직
     *
     * @param recipientEmail 수신자 이메일 주소
     * @param code 전송할 인증 코드
     */
    private fun send(recipientEmail: String, code: Int) {
        // SMTP 서버 연결을 위한 속성 설정
        val properties = java.util.Properties()
        properties["mail.smtp.host"] = host // SMTP 서버 호스트 설정
        properties["mail.smtp.port"] = port // SMTP 서버 포트 설정
        properties["mail.smtp.starttls.enable"] = "true" // STARTTLS 암호화 활성화
        properties["mail.smtp.auth"] = "true" // 인증 활성화

        // 이메일 세션 생성
        val session: Session = Session.getInstance(properties, object : Authenticator() {
            protected val passwordAuthentication: PasswordAuthentication
                get() = PasswordAuthentication(senderEmail, senderPassword) // 인증 정보 설정
        })

        try {
            // 이메일 메시지 작성
            val message: MimeMessage = MimeMessage(session)
            message.setFrom(InternetAddress(senderEmail)) // 발신자 주소 설정
            message.addRecipient(Message.RecipientType.TO, InternetAddress(recipientEmail)) // 수신자 주소 설정
            message.setSubject("버블 회원가입 인증 메일") // 이메일 제목 설정
            message.setText("인증 코드 : $code") // 이메일 본문 설정

            // 이메일 전송
            Transport.send(message) // 이메일 전송
        } catch (e: MessagingException) {
            e.printStackTrace() // 예외 발생 시 스택 트레이스 출력
        }
    }

    fun certificationEmail(code: Int, email: String?): Boolean {
        // email로 가장 최근의 코드 조회
        val verification: java.util.Optional<Email> = emailRepository.findTop1ByEmailOrderByIdDesc(email)
        EmailService.log.info(verification.toString())
        // 검증할 email 데이터가 있는지 확인
        if (verification.isPresent()) {
            val emailEntity: Email = verification.get()
            // 저장된 코드와 입력된 코드를 비교
            return emailEntity.getCode() === code
        } else {
            // 해당 이메일에 대한 코드가 없을 경우 false 반환
            return false
        }
    }
}
