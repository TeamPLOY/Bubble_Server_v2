import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.springframework.security.authentication.BadCredentialsException
import java.io.PrintWriter
import com.ploy.bubble_server_v2.common.exception.CustomAuthenticationEntryPoint


class CustomAuthenticationEntryPointTest {

    private lateinit var entryPoint: CustomAuthenticationEntryPoint
    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse
    private lateinit var writer: PrintWriter

    @BeforeEach
    fun setup() {
        entryPoint = CustomAuthenticationEntryPoint()
        request = mock(HttpServletRequest::class.java)
        response = mock(HttpServletResponse::class.java)

        // PrintWriter를 Mock 객체로 생성
        writer = mock(PrintWriter::class.java)
        `when`(response.writer).thenReturn(writer)
    }

    @Test
    fun `test commence when authentication fails`() {
        val authException = BadCredentialsException("Authentication Failed")

        `when`(request.requestURI).thenReturn("/test-url")

        entryPoint.commence(request, response, authException)

        // Verify response properties
        verify(response).characterEncoding = "UTF-8"
        verify(response).contentType = "application/json"
        verify(response).status = HttpServletResponse.SC_UNAUTHORIZED

        // Capture the written string
        val writtenString = captureWrittenString()

        // Verify the content of the written string
        assert(writtenString.contains("사용자 인증에 실패하였습니다.")) { "Expected message not found!" }
        assert(writtenString.startsWith("{") && writtenString.endsWith("}")) { "Expected JSON format not found!" }

        // Verify writer interactions
        verify(writer).flush()
        verify(writer).close()
    }

    private fun captureWrittenString(): String {
        // Capture the arguments used in the write method for further inspection
        val captureArgument = ArgumentCaptor.forClass(String::class.java)
        verify(writer).write(captureArgument.capture(), eq(0), anyInt())
        return captureArgument.value
    }
}
