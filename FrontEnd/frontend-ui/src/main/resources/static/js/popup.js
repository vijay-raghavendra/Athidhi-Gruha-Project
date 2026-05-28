function showPopup(
    type,
    title,
    message,
    callback = null
) {

    /* REMOVE OLD POPUP */

    const existingPopup =
        document.querySelector(
            ".popup-overlay"
        );

    if (existingPopup) {

        existingPopup.remove();
    }

    /* ICON */

    let icon = "";

    switch(type) {

        case "success":

            icon =
                "✔";

            break;

        case "error":

            icon =
                "✖";

            break;

        case "warning":

            icon =
                "⚠";

            break;

        default:

            icon =
                "ℹ";
    }

    /* CREATE POPUP */

    const overlay =
        document.createElement("div");

    overlay.className =
        "popup-overlay";

    overlay.innerHTML = `

        <div class="popup-box ${type}-popup">

            <div class="popup-icon">
                ${icon}
            </div>

            <div class="popup-title">
                ${title}
            </div>

            <div class="popup-message">
                ${message}
            </div>

            <button class="popup-btn"
                    id="popupOkBtn">

                OK

            </button>

        </div>
    `;

    document.body.appendChild(
        overlay
    );

    /* PREVENT OUTSIDE CLICK */

    overlay.addEventListener(
        "click",
        function(event) {

            if (
                event.target === overlay
            ) {

                event.stopPropagation();
            }
        }
    );

    /* BUTTON CLICK */

    document.getElementById(
        "popupOkBtn"
    ).addEventListener(
        "click",
        function() {

            overlay.remove();

            if (callback) {

                callback();
            }
        }
    );
}