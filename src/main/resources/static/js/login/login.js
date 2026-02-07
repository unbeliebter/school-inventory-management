
const STATUS_ACCEPTED = 202;
const STATUS_RESOURCE_NOT_FOUND = 404;

async function sendPasswordResetRequest() {
    let username = document.getElementById("username-input").value;
    try {
        const response = await fetch("/requestPasswordReset/send?username=" + username, {method:"POST"});
        if (response.status === STATUS_ACCEPTED) {alert("Ihre absicht das Passwortzurückzusetzen wurde erkenntlich gemacht." +
                "\nBitte Administrator persönlich informieren");
        } else if (response.status === STATUS_RESOURCE_NOT_FOUND) {
            alert("Username ist unbekannt!")
        }
    } catch (error) {
        alert(error.message);
    }
}


