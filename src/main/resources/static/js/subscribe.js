document.getElementById("subscribeForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const messageDiv = document.getElementById("message");

    if (!email) {
        messageDiv.innerText = "이메일을 입력해주세요.";
        return;
    }

    try {
        const response = await fetch("/api/subscribe", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email })
        });

        if (response.ok) {
            messageDiv.innerText = "인증 메일을 발송했습니다. 메일함을 확인해주세요!";
        } else {
            const error = await response.text();
            messageDiv.innerText = "오류 발생: " + error;
        }
    } catch (err) {
        messageDiv.innerText = "요청 실패: " + err.message;
    }
});