package org.collaborators.paymentslab.account.presentation

import org.collaborators.paymentslab.AbstractApiTest
import org.collaborators.paymentslab.account.domain.Account
import org.collaborators.paymentslab.account.domain.AccountRepository
import org.collaborators.paymentslab.account.presentation.request.LoginAccountRequest
import org.collaborators.paymentslab.account.presentation.request.RegisterAccountRequest
import org.collaborators.paymentslab.account.presentation.request.RegisterConfirmRequest
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthenticationApiTest @Autowired constructor(
    private val accountRepository: AccountRepository
) : AbstractApiTest() {
    @Test
    @DisplayName("회원가입 api 동작")
    fun registerTest() {
        val requestDto = RegisterAccountRequest("hello@gmail.com", "qwer1234", "helloUsername", "010-1234-5678")
        val reqBody = this.objectMapper.writeValueAsString(requestDto)

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
        ).andExpect(status().is2xxSuccessful)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("email")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 email 주소"),
                        fieldWithPath("password")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 password.\n " +
                                    "- 문자의 종류 2가지 이하일 경우 최소 10자 이상 50자 이하\n" +
                                    "- 문자의 종류 3가지 이상일 경우 최소 8자 이상 50자 이하"),
                        fieldWithPath("username")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 username"),
                        fieldWithPath("phoneNumber")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 phoneNumber")
                    )
                )
            )
    }

    @Test
    @DisplayName("잘못된 회원가입 폼 입력으로 인한 오류 발생 테스트")
    fun registerErrorTest() {
        val wrongRegisterForm = RegisterAccountRequest("hellogmail.com", "qwer1234", "helloUsername", "010-1234-1234")
        val reqBody = this.objectMapper.writeValueAsString(wrongRegisterForm)

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
        ).andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.isSuccess").value(false))
            .andExpect(jsonPath("$.body").exists())
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("email")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 email 주소"),
                        fieldWithPath("password")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 password.\n " +
                                    "- 문자의 종류 2가지 이하일 경우 최소 10자 이상 50자 이하\n" +
                                    "- 문자의 종류 3가지 이상일 경우 최소 8자 이상 50자 이하"),
                        fieldWithPath("username")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 username"),
                        fieldWithPath("phoneNumber")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 phoneNumber")
                    ),
                    errorResponseFieldsSnippet()
                )
            )
    }

    @Test
    @DisplayName("잘못된 휴대폰 번호 입력으로 인한 오류 발생 테스트")
    fun registerWrongPhoneNumberTest() {
        val wrongRegisterForm = RegisterAccountRequest("hello@gmail.com", "qwer1234", "helloUsername", "0101234-1234")
        val reqBody = this.objectMapper.writeValueAsString(wrongRegisterForm)

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
        ).andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.isSuccess").value(false))
            .andExpect(jsonPath("$.body").exists())
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("email")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 email 주소"),
                        fieldWithPath("password")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 password.\n " +
                                    "- 문자의 종류 2가지 이하일 경우 최소 10자 이상 50자 이하\n" +
                                    "- 문자의 종류 3가지 이상일 경우 최소 8자 이상 50자 이하"),
                        fieldWithPath("username")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 username"),
                        fieldWithPath("phoneNumber")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 phoneNumber")
                    ),
                    errorResponseFieldsSnippet()
                )
            )
    }

    @Test
    @DisplayName("회원가입 검증 api 동작 테스트")
    fun confirmTest() {
        val registered = accountRepository.save(Account.register("hello2@gmail.com",
            encrypt.encode("qqqwww123"), "hello2", "010-1234-1234"))
        val account = accountRepository.findByEmail(registered.email)

        val requestDto = RegisterConfirmRequest(account.emailCheckToken!!, account.email)
        val reqBody = this.objectMapper.writeValueAsString(requestDto)

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .get("/api/v1/auth/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
        ).andExpect(status().isSeeOther)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("token")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록 검증에 필요한 이메일 검증 token 키"),
                        fieldWithPath("email")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록 검증에 필요한 이메일 주소")
                    )
                )
            )
    }

    @Test
    @DisplayName("로그인 api 동작 테스트")
    fun loginTest() {
        val registered = testEntityForRegister("hello3@gmail.com")
        val account = accountRepository.save(registered)

        val requestDto = LoginAccountRequest(account.email, "qqqwww123")
        val reqBody = this.objectMapper.writeValueAsString(requestDto)

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
        ).andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.isSuccess").value(true))
            .andExpect(jsonPath("$.body").exists())
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("email")
                            .type(JsonFieldType.STRING)
                            .description("로그인을 시도하고자 하는 사용자의 email"),
                        fieldWithPath("password")
                            .type(JsonFieldType.STRING)
                            .description("로그인을 시도하고자 하는 사용자의 password"),
                    ),
                    responseFields(
                        fieldWithPath("isSuccess")
                            .type(JsonFieldType.BOOLEAN)
                            .description("api 성공여부 필드"),
                        fieldWithPath("body.accessToken")
                            .type(JsonFieldType.STRING)
                            .description("발급된 jwt 토큰 접근 키"),
                        fieldWithPath("body.refreshToken")
                            .type(JsonFieldType.STRING)
                            .description("발급된 jwt 토큰 리프레시 키")
                    )
                )
            )
    }

    @Test
    @DisplayName("잘못된 로그인 api 에러 테스트")
    fun loginErrorTest() {
        val registered = accountRepository.save(Account.register("invalid@gmail.com",
            encrypt.encode("qqqwww123"), "hello2", "010-1234-1234"))
        val account = accountRepository.findByEmail(registered.email)

        val requestDto = LoginAccountRequest(account.email, "wrongpassword123")
        val reqBody = this.objectMapper.writeValueAsString(requestDto)

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
        ).andExpect(status().is4xxClientError)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("email")
                            .type(JsonFieldType.STRING)
                            .description("로그인을 시도하고자 하는 사용자의 email"),
                        fieldWithPath("password")
                            .type(JsonFieldType.STRING)
                            .description("로그인을 시도하고자 하는 사용자의 password"),
                    ),
                    errorResponseFieldsSnippet()
                )
            )
    }

    @Test
    @DisplayName("토큰 재발급 api 테스트")
    fun reIssuanceTest() {
        val registered = testEntityForRegister("hello4@gmail.com")
        val account = accountRepository.save(registered)
        val tokens = tokenGenerator.generate(account.email, account.roles)

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/reIssuance")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${tokens.refreshToken}")
        ).andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.isSuccess").value(true))
            .andExpect(jsonPath("$.body").exists())
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName("Authorization").description("재발급 하고자 하는 대상의 리프레시 토큰 값")
                    ),
                    responseFields(
                        fieldWithPath("isSuccess")
                            .type(JsonFieldType.BOOLEAN)
                            .description("api 성공여부 필드"),
                        fieldWithPath("body.accessToken")
                            .type(JsonFieldType.STRING)
                            .description("발급된 jwt 토큰 접근 키"),
                        fieldWithPath("body.refreshToken")
                            .type(JsonFieldType.STRING)
                            .description("발급된 jwt 토큰 리프레시 키")
                    )
                )
            )
    }

    @Test
    @DisplayName("회원 등록이 안된 사용자의 토큰으로 재발급 실패 api 테스트")
    fun notRegisteredReIssuanceTest() {
        val registered = testEntityForRegister("nouser123@gmail.com")
        val tokens = tokenGenerator.generate(registered.email, registered.roles)

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/reIssuance")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${tokens.refreshToken}")
        ).andExpect(status().isUnauthorized)
            .andExpect(jsonPath("$.isSuccess").value(false))
            .andExpect(jsonPath("$.body").exists())
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName("Authorization").description("재발급 하고자 하는 대상의 리프레시 토큰 값")
                    ),
                    errorResponseFieldsSnippet()
                )
            )
    }

    @Test
    @DisplayName("만료된 회원의 토큰으로 재발급 실패 api 테스트")
    fun expiredReIssuanceTest() {
        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/reIssuance")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${MockAuthentication.expiredRefreshToken}")
        ).andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.isSuccess").value(false))
            .andExpect(jsonPath("$.body").exists())
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName("Authorization").description("재발급 하고자 하는 대상의 리프레시 토큰 값")
                    ),
                    errorResponseFieldsSnippet()
                )
            )
    }
}