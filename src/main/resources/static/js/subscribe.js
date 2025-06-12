document.getElementById("subscribeForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const emailInput = document.getElementById("email");
    const email = emailInput.value;
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
            messageDiv.textContent = '✅ 신청이 완료되었습니다. 메일을 확인해주세요!';
            messageDiv.style.color = 'green';
            emailInput.value = '';
        } else {
            const errorData = await response.json();
            const errorMessage = errorData.error?.message || '알 수 없는 오류가 발생했습니다.';
            messageDiv.textContent = `❌ 실패: ${errorMessage}`;
            messageDiv.style.color = 'red';
        }
    } catch (err) {
        messageDiv.textContent = '❌ 오류가 발생했습니다. 다시 시도해주세요.';
        messageDiv.style.color = 'red';
    }
});