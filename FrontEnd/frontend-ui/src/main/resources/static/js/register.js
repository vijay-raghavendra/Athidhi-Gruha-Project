const form = document.getElementById("registerForm");

const password =
    document.getElementById("password");

const togglePassword =
    document.getElementById("togglePassword");

/* PASSWORD TOGGLE */

togglePassword.addEventListener("click", function () {

    const type =
        password.getAttribute("type") === "password"
        ? "text"
        : "password";

    password.setAttribute("type", type);

    this.classList.toggle("bi-eye");
    this.classList.toggle("bi-eye-slash-fill");
});

const confirmPassword =
    document.getElementById(
        "confirmPassword"
    );

const toggleConfirmPassword =
    document.getElementById(
        "toggleConfirmPassword"
    );

toggleConfirmPassword.addEventListener("click", function () {

        const type = confirmPassword.getAttribute("type") === "password"
            ? "text" : "password";

        confirmPassword.setAttribute("type",type);

        this.classList.toggle("bi-eye");
        this.classList.toggle("bi-eye-slash-fill");
    }
);

/* DOB MAX DATE */

const today =
    new Date().toISOString().split("T")[0];

document.getElementById("dob").max = today;

/* REAL TIME VALIDATIONS */

document.getElementById("firstName")
    .addEventListener("input", validateFirstName);

document.getElementById("middleName")
    .addEventListener("input", validateMiddleName);

document.getElementById("lastName")
    .addEventListener("input", validateLastName);

document.getElementById("mobileNumber")
    .addEventListener("input", validateMobile);

document.getElementById("email")
    .addEventListener("input", validateEmail);

document.getElementById("password")
    .addEventListener("input", validatePassword);

document.getElementById("confirmPassword")
    .addEventListener("input", validateConfirmPassword);


function validateFirstName() {

    const value =
        document.getElementById(
            "firstName"
        ).value.trim();

    const regex = /^[A-Za-z]+$/;

    if (!regex.test(value)) {

        showError(
            "firstName",
            "Only alphabets allowed"
        );

    } else {

        clearFieldError("firstName");
    }
}

function validateMiddleName() {

    const value =
        document.getElementById(
            "middleName"
        ).value.trim();

    const regex = /^[A-Za-z]+$/;

    if (value && !regex.test(value)) {

        showError(
            "middleName",
            "Only alphabets allowed"
        );

    } else {

        clearFieldError("middleName");
    }
}

function validateLastName() {

    const value =
        document.getElementById(
            "lastName"
        ).value.trim();

    const regex = /^[A-Za-z]+$/;

    if (!regex.test(value)) {

        showError(
            "lastName",
            "Only alphabets allowed"
        );

    } else {

        clearFieldError("lastName");
    }
}

function validateMobile() {

    const value =
        document.getElementById(
            "mobileNumber"
        ).value.trim();

    const regex = /^[0-9]{10}$/;

    if (!regex.test(value)) {

        showError(
            "mobileNumber",
            "Enter valid 10 digit number"
        );

    } else {

        clearFieldError("mobileNumber");
    }
}

function validateEmail() {

    const value =
        document.getElementById(
            "email"
        ).value.trim();

    const regex =
        /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!regex.test(value)) {

        showError(
            "email",
            "Invalid email format"
        );

    } else {

        clearFieldError("email");
    }
}

function validatePassword() {

    const value =
        document.getElementById(
            "password"
        ).value;

    const lengthRule =
        value.length >= 8 &&
        value.length <= 18;

    const upperRule =
        /[A-Z]/.test(value);

    const lowerRule =
        /[a-z]/.test(value);

    const numberRule =
        /[0-9]/.test(value);

    const specialRule =
        /[@$!%*?&]/.test(value);

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

        clearFieldError("password");

    } else {

        showError(
            "password",
            "Password does not meet requirements"
        );
    }
}

function validateConfirmPassword() {

    const password =
        document.getElementById(
            "password"
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

    const input =
        document.getElementById(field);

    input.classList.remove(
        "invalid-field"
    );

    document.getElementById(
        field + "Error"
    ).innerText = "";
}

/* FORM SUBMIT */

form.addEventListener("submit", function (event) {

    event.preventDefault();

    clearErrors();

    let valid = true;

    const nameRegex = /^[A-Za-z]+$/;

    const mobileRegex = /^[0-9]{10}$/;

    const passwordRegex =
        /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&]).{8,18}$/;

    const emailRegex =
        /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    const firstName =
        document.getElementById("firstName").value.trim();

    const middleName =
        document.getElementById("middleName").value.trim();

    const lastName =
        document.getElementById("lastName").value.trim();

    const role =
            document.getElementById("userRole").value;

    const dob =
        document.getElementById("dob").value;

    const gender =
        document.getElementById("gender").value;

    const mobile =
        document.getElementById("mobileNumber").value.trim();

    const email =
        document.getElementById("email").value.trim();

    const confirmPassword =
        document.getElementById("confirmPassword").value;

    /* FIRST NAME */

    if (!firstName || !nameRegex.test(firstName)) {

        showError(
            "firstName",
            "Only alphabets allowed"
        );

        valid = false;
    }

    /* MIDDLE NAME */

    if (middleName &&
        !nameRegex.test(middleName)) {

        showError(
            "middleName",
            "Only alphabets allowed"
        );

        valid = false;
    }

    /* LAST NAME */

    if (!lastName ||
        !nameRegex.test(lastName)) {

        showError(
            "lastName",
            "Only alphabets allowed"
        );

        valid = false;
    }

    /* Role */

    if (!role) {

            showError(
                "userRole",
                "Please select Role"
            );

            valid = false;
        }

    /* DOB */

    if (!dob) {

        showError(
            "dob",
            "Please select DOB"
        );

        valid = false;
    }

    /* GENDER */

    if (!gender) {

        showError(
            "gender",
            "Please select gender"
        );

        valid = false;
    }

    /* MOBILE */

    if (!mobileRegex.test(mobile)) {

        showError(
            "mobileNumber",
            "Mobile number must be 10 digits"
        );

        valid = false;
    }

    /* EMAIL */

    if (!emailRegex.test(email)) {

        showError(
            "email",
            "Please enter valid email address"
        );

        valid = false;
    }

    /* PASSWORD */

    if (!passwordRegex.test(password.value)) {

        showError(
            "password",
            "Password must contain uppercase, lowercase, number & special character"
        );

        valid = false;
    }

    /* CONFIRM PASSWORD */

    if (password.value !== confirmPassword) {

        showError(
            "confirmPassword",
            "Passwords do not match"
        );

        valid = false;
    }

    /* SUCCESS */

    if (valid) {

           const requestBody = {

            firstName: firstName,

            middleName: middleName,

            lastName: lastName,

            userRole: role,

            dob: dob,

            gender: gender,

            mobileNumber: mobile,

            email: email,

            password: password.value
        };

        fetch("http://localhost:5050/auth/register", {

            method: "POST",

            headers: {
              "Content-Type": "application/json"
            },

            body: JSON.stringify(requestBody)

        })
        .then(async response => {

            const data = await response.json();

            /* SUCCESS */

            if (response.ok) {

                showPopup(
                    data.status,
                    "Registration Status",
                    data.message + " with User ID : " +data.userId,
                    () =>
                        {
                            window.location.href = "/login";
                        }
                );
            }
            /* FAILURE */
            else
            {
                /*alert(data.errorMessage ||"Invalid Credentials");*/
                showPopup(
                    data.statusType,
                    "Registration Status",
                    data.errorMessage
                );
            }
        })
        .catch(error => {

            /*alert(
                "Registration Failed : " +
                error
            );*/
            showPopup(
                "error",
                "Registration status",
                error
            );
        });
    }
});

/* SHOW ERROR */

function showError(field, message) {

    const input =
        document.getElementById(field);

    input.classList.add("invalid-field");

    document.getElementById(
        field + "Error"
    ).innerText = message;
}

/* CLEAR ERRORS */

function clearErrors() {

    const fields = [
        "firstName",
        "middleName",
        "lastName",
        "userRole",
        "dob",
        "gender",
        "mobileNumber",
        "email",
        "password",
        "confirmPassword"
    ];

    fields.forEach(field => {

        const input =
            document.getElementById(field);

        if (input) {

            input.classList.remove(
                "invalid-field"
            );
        }

        const errorElement =
            document.getElementById(
                field + "Error"
            );

        if (errorElement) {

            errorElement.innerText = "";
        }
    });
}