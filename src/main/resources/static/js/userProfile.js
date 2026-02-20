const STATUS_OK = 200;


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