
const STATUS_ACCEPTED = 202;
const STATUS_RESOURCE_NOT_FOUND = 404;

async function sendPasswordResetRequest() {
    let username = document.getElementById("username-input").value;
    try {
        const response = await fetch("/requestPasswordReset/send?username=" + username, {method:"POST"});
        if (response.status === STATUS_ACCEPTED) {notificationDialog.showNotification("Ihre absicht das Passwort zurückzusetzen wurde erkenntlich gemacht." +
                "\nBitte Administrator persönlich informieren");
        } else if (response.status === STATUS_RESOURCE_NOT_FOUND) {
            notificationDialog.showNotification("Nutzername ist nicht bekannt(Wenn auch vergessen dann Admin persönlich ansprechen)!")
        }
    } catch (error) {
        alert(error.message);
    }
}


