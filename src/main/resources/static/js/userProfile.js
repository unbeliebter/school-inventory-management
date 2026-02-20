const STATUS_OK = 200;
const PW_DIALOG = document.getElementById("dialog-new-password");
const PW_DIALOG_INPUT_OLD = document.getElementById("dialog-new-password-input-old-pw");
const PW_DIALOG_INPUT_NEW = document.getElementById("dialog-new-password-input-new-pw");
const PW_DIALOG_INPUT_NEW_CHECK = document.getElementById("dialog-new-password-input-new-pwCheck");

async function changeValue(field,fieldFrontendName, firstName) {
    let userInput = await notificationDialog.showSingleInput("Neuer " + fieldFrontendName + ":", firstName);
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
        alert(error.message);
    }
}

async function showChangePasswordDialog() {
    PW_DIALOG.setAttribute("open","true");
}

async function sendPwChangeRequest() {

}

function closePwDialog() {
    PW_DIALOG.setAttribute("open","false");
    PW_DIALOG_INPUT_OLD.value = "";
    PW_DIALOG_INPUT_NEW.value = "";
    PW_DIALOG_INPUT_NEW_CHECK.value = "";
}