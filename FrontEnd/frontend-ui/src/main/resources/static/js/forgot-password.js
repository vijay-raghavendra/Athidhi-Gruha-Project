const form = document.getElementById("forgotPasswordForm");

const today = new Date().toISOString().split("T")[0];

document.getElementById("dob").max = today;

/* REAL TIME VALIDATIONS */

document.getElementById("userId").addEventListener("input", validateUserID);

document.getElementById("dob").addEventListener("input", validateDOB);

document.getElementById("mobileNumber").addEventListener("input", validateMobile);

document.getElementById("email").addEventListener("input", validateEmail);

function validateUserID() {

    const value = document.getElementById("userId").value.trim();

    if(value==null || value=="" || value=='')
    {
        showError("userId","Enter valid UserID or UserName");
    }
    else{
        clearFieldError("userId");
    }

}

function validateDOB() {

    const value = document.getElementById("dob").value.trim();

    if(value==null || value=="" || value=='')
    {
        showError("dob","Enter valid Date of Birth");
    }
    else{
        clearFieldError("dob");
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

function clearFieldError(field) {

    const input = document.getElementById(field);

    input.classList.remove("invalid-field");

    document.getElementById(field + "Error").innerText = "";
}

form.addEventListener("submit",function(event) {

        event.preventDefault();

        clearErrors();

        let valid = true;

        const mobileRegex = /^[0-9]{10}$/;

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        const userId = document.getElementById("userId").value.trim();

        const dob = document.getElementById("dob").value;

        const mobileNumber = document.getElementById("mobileNumber").value.trim();

        const email = document.getElementById("email").value.trim();

        if (!userId) {

            showError("userId","User ID is required");

            valid = false;
        }

        if (!dob) {

            showError("dob","DOB is required");

            valid = false;
        }

        if (!mobileRegex.test(mobileNumber)) {

            showError("mobileNumber","Invalid Mobile Number");

            valid = false;
        }

        if (!emailRegex.test(email)) {

            showError("email","Invalid Email");

            valid = false;
        }

        if (valid) {

            const requestBody = {

                userId: userId,

                dob: dob,

                mobileNumber: mobileNumber,

                email: email
            };

            fetch("http://localhost:5050/auth/verify-user",
                {
                    method: "POST",

                    headers:
                    {
                        "Content-Type":"application/json"
                    },

                    body: JSON.stringify(requestBody)
                }
            )

            .then(async response => {

                const data = await response.json();

                if (response.ok) {

                    sessionStorage.setItem("verifiedUserId",userId);

                    showPopup(
                              "success",
                              "Verification Status",
                               data.message,
                               () =>
                                     {
                                        window.location.href ="/reset-password";
                                     }
                               );
                    /* Commenting Old alert code

                    alert(data.message);

                    window.location.href = "/reset-password";*/
                }

                else
                {
                    /*alert(data.errorMessage);*/
                    showPopup(
                        "error",
                        "Verification Status",
                         data.errorMessage
                    );
                }
            })

            .catch(error => {

                console.error(error);

                /*alert("Unable to connect to server");*/
                showPopup(
                    "error",
                    "Verification Status",
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
        "userId",
        "dob",
        "mobileNumber",
        "email"
    ];

    fields.forEach(field => {

        const input =
            document.getElementById(field);

        input.classList.remove(
            "invalid-field"
        );

        document.getElementById(
            field + "Error"
        ).innerText = "";
    });
}