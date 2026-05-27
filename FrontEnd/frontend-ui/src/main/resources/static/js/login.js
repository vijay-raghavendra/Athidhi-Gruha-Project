const togglePassword =
    document.querySelector("#togglePassword");

const password =
    document.querySelector("#password");

togglePassword.addEventListener("click", function () {

    const type =
        password.getAttribute("type") === "password"
        ? "text"
        : "password";

    password.setAttribute("type", type);

    this.classList.toggle("bi-eye");
    this.classList.toggle("bi-eye-slash-fill");
});

/* LOGIN */

loginForm.addEventListener("submit",function(event) {

        event.preventDefault();

        const userId = document.getElementById("userId").value;

        const password = document.getElementById("password").value;

        const requestBody =
        {
            userId: userId,
            password: password
        };

        fetch("http://localhost:5050/auth/login",
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

            /* SUCCESS */

            if (response.ok) {

                sessionStorage.setItem("token",data.token);
                sessionStorage.setItem("userId",data.userId);

                alert(data.message);

                window.location.href ="/dashboard";
            }

            /* FAILURE */

            else
            {
                alert(data.errorMessage ||"Invalid Credentials");
            }
        })

        .catch(error =>
        {
            console.error(error);
            alert("Unable to connect to server");
        });
    }
);