package com.ploy.bubble_server_v2.domain.washingMachine.service


import com.laundering.laundering_server.common.exception.BusinessException

@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
class WashingMachineService {
    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val restTemplate: RestTemplate? = null

    @Autowired
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper? = null

    fun getStatus(id: Long?): List<WashingMachineResponse> {
        try {
            // 주어진 id로 사용자 정보를 조회
            val user: User = userRepository.findById(id)
                .orElseThrow { BusinessException(ErrorCode.UNAUTHORIZED) }

            // 세탁실 정보 가져오기
            val washingRoom: String = user.getWashingRoom()

            // URL을 구성하고 HTTP GET 요청 보내기
            val url = String.format("https://build-bubble-proxy-server.vercel.app/home/%s", washingRoom)
            val response: String = restTemplate.getForObject<String>(url, String::class.java)

            // JSON 응답을 WashingMachineResponse 리스트로 변환하기
            return objectMapper!!.readValue<List<WashingMachineResponse>>(
                response,
                object : com.fasterxml.jackson.core.type.TypeReference<List<WashingMachineResponse?>?>() {})
        } catch (e: IOException) {
            // JSON 처리 중 오류가 발생한 경우 또는 IO 오류가 발생한 경우 처리
            throw BusinessException(ErrorCode.BAD_REQUEST)
        }
    }
}


