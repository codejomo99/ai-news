# 📰 AI 뉴스 요약 메일링 서비스

> 주요 뉴스 요약을 매일 아침 이메일로 받아보는 자동화 서비스



## 📌 프로젝트 개요

**AI 뉴스 요약 메일링 서비스**는 사용자가 이메일을 구독하면, 매일 아침 AI가 최신 뉴스를 요약하여 메일로 발송해주는 백엔드 중심의 프로젝트입니다.

<p align="center">
  <img src="images/project.png" alt="프로젝트 전체 흐름" width="600"/>
</p>

## 🛠️ 기술 스택
- **Frontend**: HTML5, CSS3, JavaScript
- **Backend**: Spring Boot 3, Java 17
- **Database**: H2 (개발환경), JPA
- **Scheduler**: Spring Scheduled
- **AI 요약**: Google Gemini API (향후 GPT API로 개선 예정)
- **메일 발송**: Spring Mail
- **빌드**: Gradle
- **테스트**: JUnit 5


## ✅ 주요 기능

### 1. ✉️ 이메일 구독 & 인증
- 사용자가 이메일을 입력하면 인증 메일 전송
- 링크 클릭 시 인증 완료 → 구독 등록

<p align="center">
  <img src="images/access1.png" alt="인증" width="600"/>
</p>


<p align="center">
  <img src="images/access2.png" alt="인증" width="600"/>
</p>

<p align="center">
  <img src="images/access3.png" alt="인증" width="600"/>
</p>

### 2. ✉️ 이메일 구독 해제 & 인증
- 사용자가 이메일을 입력하면 인증 메일 전송
- 링크 클릭 시 인증 완료 → 구독 해제

<p align="center">
  <img src="images/unsubscriber.png" alt="인증" width="600"/>
</p>

### 3. 📰 뉴스 요약 생성
- 매일 오전 8시, 스케줄러가 실행
- Gemini API에 프롬프트를 보내어 뉴스 요약 생성
- 뉴스 요약은 `summary` 테이블에 하루 1건 저장 (중복 방지)

### 4. 📬 요약 메일 전송
- 인증된 사용자 목록을 조회
- 저장된 요약 본문을 각 사용자에게 메일로 발송

<p align="center">
  <img src="images/mail.png" alt="프로젝트 전체 흐름" width="600"/>
</p>


## 🔁 향후 개선 계획

- [ ] **GPT API로 전환**하여 더 정확한 요약 및 실제 기사 링크 포함
- [ ] 이메일 인증 만료 및 재전송 로직 추가
- [ ] 실제 배포를 위한 PostgreSQL + EC2 환경 구성


## 📁 디렉토리 구조

```
📂 subscriber-news
├── scheduler                # 뉴스 요약 및 메일 전송 스케줄러
├── subscriber              # 구독자 도메인 (엔티티, 서비스, 레포지토리)
├── summary                 # 요약 정보 저장 도메인
├── util
│   ├── GeminiClient.java   # Gemini API 호출 로직
│   └── MailService.java    # 이메일 전송 로직
│       └── resources
│           ├── static
│           │   └── index.html          # 프론트 파일들 저장 js,css 등
│           └── application.yml          # API 키, 메일 설정 등
```




## 👨‍💻 개발자 노트

- Gemini API는 실시간 웹 검색 기능이 없어 링크 생성이 부정확할 수 있음
- 추후 GPT API로 전환 시 기사 링크 포함이 정확해질 예정


## ©️ License

본 프로젝트는 개인 학습 및 사이드 프로젝트 목적으로 개발되었습니다.
