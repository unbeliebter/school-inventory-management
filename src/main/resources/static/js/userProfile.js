const STATUS_OK = 200;
const PW_DIALOG = document.getElementById("dialog-new-password");
const PW_DIALOG_ERROR_LABEL = document.getElementById("dialog-new-password-error-label");
const PW_DIALOG_INPUT_OLD = document.getElementById("dialog-new-password-input-old-pw");
const PW_DIALOG_INPUT_NEW = document.getElementById("dialog-new-password-input-new-pw");
const PW_DIALOG_INPUT_NEW_CHECK = document.getElementById("dialog-new-password-input-new-pwCheck");

async function changeValue(field,fieldFrontendName, firstName) {
    let prompt = fieldFrontendName === "E-Mail" ? "Neue " : "Neuer ";
    prompt += fieldFrontendName + ":";
    let userInput = await notificationDialog.showSingleInput(prompt, firstName);
    if (!userInput) {
        return;
    }

    try {
        let requestString = USER_ID + "&" + field + "=" + userInput;
        const response = await fetch("/userProfile/changeValue?userId=" + requestString, {method:"PATCH"});
        if (response.status === STATUS_OK) {
            if (await notificationDialog.showNotification("Erfolg!")) {
                location.reload();
            }
        }
    } catch (error) {
        await notificationDialog.showError(error.message);
    }
}

async function showChangePasswordDialog() {
    PW_DIALOG_ERROR_LABEL.setAttribute("shown", "false");
    PW_DIALOG.setAttribute("open","true");
}

async function sendPwChangeRequest() {
    let oldPw = PW_DIALOG_INPUT_OLD.value;
    let newPw = PW_DIALOG_INPUT_NEW.value;
    let newPwCheck = PW_DIALOG_INPUT_NEW_CHECK.value;

    let pwValid = true;
    if (!oldPw || !newPw || !newPwCheck) {
        PW_DIALOG_ERROR_LABEL.textContent = "Eingabe fehlt!";
        pwValid = false;
    }
    if (newPw.length < 12 || newPwCheck.length < 12) {
        PW_DIALOG_ERROR_LABEL.textContent = "Passwort zu kurz!";
        pwValid = false;
    }
    if (newPw !== newPwCheck) {
        PW_DIALOG_ERROR_LABEL.textContent = "Fehler bei Wiederholung!";
        pwValid = false;
    }
    if (!pwValid) {
        PW_DIALOG_ERROR_LABEL.setAttribute("shown", "true");
        return;
    }

    try {
        let requestString = oldPw + "&pw=" + newPw + "&pwCheck=" + newPwCheck;
        const response = await fetch("/userProfile/changePassword?oldPw=" + requestString, {method: "PATCH"});
        if (response.status === STATUS_OK) {
            closePwDialog();
            await notificationDialog.showNotification("Erfolg!");
        } else {
            await notificationDialog.showError(await response.text());
        }

    } catch (error) {
        await notificationDialog.showError(error.message);
    }
}

function closePwDialog() {
    PW_DIALOG.setAttribute("open","false");
    PW_DIALOG_INPUT_OLD.value = "";
    PW_DIALOG_INPUT_NEW.value = "";
    PW_DIALOG_INPUT_NEW_CHECK.value = "";
}