const form = document.getElementById("registerForm");

/* REAL TIME VALIDATIONS */

document.getElementById("dob")
    .addEventListener("input", validateDOB);

document.getElementById("mobileNumber")
    .addEventListener("input", validateMobile);

document.getElementById("email")
    .addEventListener("input", validateEmail);

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

    const mobileRegex = /^[0-9]{10}$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    const dob    = document.getElementById("dob").value;
    const mobile = document.getElementById("mobileNumber").value.trim();
    const email  = document.getElementById("email").value.trim();

    /* DOB */

        if (!dob) {

            showError(
                 "dob",
                 "Please select DOB"
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

    /* SUCCESS */

        if (valid) {

               /* QUERY PARAMETERS */

               const queryParams = new URLSearchParams({
                   dob: dob,
                   mobileNumber: mobile,
                   email: email
               }).toString();

            fetch(`http://localhost:5050/auth/findUserID?${queryParams}`, {

                method: "GET",

                headers: {
                  "Content-Type": "application/json"
                },

                //body: JSON.stringify(requestBody)

            })

            .then(async response =>{

                const data = await response.json();

                if (response.ok) {

                        document.getElementById("successMessage").innerHTML = data.message +" with User ID : " +data.userId;

                         const successModal = new bootstrap.Modal(document.getElementById('successModal'));

                         successModal.show();

                         document.getElementById("okButton").onclick = function ()
                         {
                             window.location.href = "/login";
                         };
                }

                /* FAILURE */

                else
                {
                    alert(data.message ||"Invalid Credentials");
                }


            })
            .catch(error => {
                alert("Registration Failed : " +error);
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
        "dob",
        "mobileNumber",
        "email"
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
