const form = document.getElementById("resetPasswordForm");

/* REAL TIME VALIDATIONS */
document.getElementById("newPassword").addEventListener("input", validateNewPassword);

document.getElementById("confirmPassword").addEventListener("input", validateConfirmPassword);

function validateNewPassword() {

    const value = document.getElementById("newPassword").value;

    const lengthRule = value.length >= 8 && value.length <= 18;

    const upperRule = /[A-Z]/.test(value);

    const lowerRule = /[a-z]/.test(value);

    const numberRule = /[0-9]/.test(value);

    const specialRule = /[@$!%*?&]/.test(value);

    updateRule("ruleLength", lengthRule);

    updateRule("ruleUpper", upperRule);

    updateRule("ruleLower", lowerRule);

    updateRule("ruleNumber", numberRule);

    updateRule("ruleSpecial", specialRule);

    if (
        lengthRule &&
        upperRule &&
        lowerRule &&
        numberRule &&
        specialRule
    ) {

        clearFieldError("newPassword");

    } else {

        showError(
            "newPassword",
            "Password does not meet requirements"
        );
    }
}

function validateConfirmPassword() {

    const password =
        document.getElementById(
            "newPassword"
        ).value;

    const confirmPassword =
        document.getElementById(
            "confirmPassword"
        ).value;

    if (password !== confirmPassword) {

        showError(
            "confirmPassword",
            "Passwords do not match"
        );

    } else {

        clearFieldError(
            "confirmPassword"
        );
    }
}

function updateRule(ruleId, valid) {

    const rule =
        document.getElementById(ruleId);

    if (valid) {

        rule.classList.add("rule-valid");

    } else {

        rule.classList.remove("rule-valid");
    }
}

function clearFieldError(field) {

    const input = document.getElementById(field);

    input.classList.remove(
        "invalid-field"
    );

    document.getElementById(
        field + "Error"
    ).innerText = "";
}

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

                    /* commenting old alert code */
                    /*alert(
                        data.message
                    );

                    window.location.href =
                        "/";*/

                    showPopup(
                        "success",
                        "Password Reset Successful",
                        data.message,
                        () =>
                            {
                                window.location.href ="/";
                            }
                    );
                }

                else {

                    showPopup(
                        "error",
                        "Password Reset Failed",
                        data.errorMessage
                    );
                }
            })

            .catch(error => {

                            console.error(error);

                            showPopup(
                                "error",
                                "Password Status",
                                error
                            );
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