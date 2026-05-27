const form = document.getElementById("resetPasswordForm");

/* PASSWORD TOGGLE */

toggleNewPassword.addEventListener("click", function () {

    const type =
        newPassword.getAttribute("type") === "password"
        ? "text": "password";

    newPassword.setAttribute("type", type);

    this.classList.toggle("bi-eye");
    this.classList.toggle("bi-eye-slash-fill");
});

toggleConfirmPassword.addEventListener("click", function () {

        const type = confirmPassword.getAttribute("type") === "password"
            ? "text" : "password";

        confirmPassword.setAttribute("type",type);

        this.classList.toggle("bi-eye");
        this.classList.toggle("bi-eye-slash-fill");
});

form.addEventListener(
    "submit",
    function(event) {

        event.preventDefault();

        clearErrors();

        let valid = true;

        const passwordRegex =
            /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&]).{8,18}$/;

        const newPassword =
            document.getElementById(
                "newPassword"
            ).value;

        const confirmPassword =
            document.getElementById(
                "confirmPassword"
            ).value;

        if (!passwordRegex.test(
                newPassword
        )) {

            showError(
                "newPassword",
                "Weak Password"
            );

            valid = false;
        }

        if (
            newPassword !==
            confirmPassword
        ) {

            showError(
                "confirmPassword",
                "Passwords do not match"
            );

            valid = false;
        }

        if (valid) {

            const requestBody = {

                userId:
                    sessionStorage.getItem(
                        "verifiedUserId"
                    ),

                newPassword: newPassword
            };

            fetch(
                "http://localhost:5050/auth/reset-password",
                {

                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body: JSON.stringify(
                        requestBody
                    )
                }
            )

            .then(async response => {

                const data =
                    await response.json();

                if (response.ok) {

                    alert(
                        data.message
                    );

                    window.location.href =
                        "/";
                }

                else {

                    alert(
                        data.errorMessage
                    );
                }
            });
        }
    }
);

function showError(field, message) {

    const input =
        document.getElementById(field);

    input.classList.add(
        "invalid-field"
    );

    document.getElementById(
        field + "Error"
    ).innerText = message;
}

function clearErrors() {

    const fields = [
        "newPassword",
        "confirmPassword"
    ];

    fields.forEach(field => {

        document.getElementById(field)
            .classList.remove(
                "invalid-field"
            );

        document.getElementById(
            field + "Error"
        ).innerText = "";
    });
}