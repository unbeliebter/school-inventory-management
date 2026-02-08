const STATUS_CREATED = 201;
const STATUS_OK = 200;

async function resetPasswordOfSelectedUser(userId, username, htmlElement) {
    if (await notificationDialog.showConfirm("Passwort von nutzer mit nutzernamen '" + username + "' zurücksetzen?")) {
        try {
            const response = await fetch("/users/resetPassword?userId=" + userId, {method:"POST"});
            if (response.status === STATUS_OK) {
                await response.text().then(r => notificationDialog.showNotification("Neues einmal passwort: " + r));
                while (htmlElement && htmlElement.parentNode) {
                        htmlElement = htmlElement.parentNode;
                        if (htmlElement.tagName == "TR") {
                            htmlElement.setAttribute("pw-reset-requested", "false");
                        }
                    }
            }
            

        } catch (error) {
            alert(error.message);
        }
    }
}

async function createUser() {
    let itemId = document.getElementById("id-input").value;
    let roleId = document.getElementById("userType-input").value;
    let newUser = gatherValues()
    let requestString = "tableItemId=" + itemId
        + "&username=" + newUser.username 
        + "&firstname=" + newUser.firstname 
        + "&lastname=" + newUser.lastname 
        + "&email=" + newUser.email
        + "&roleId=" + roleId;
    try {
        const response = await fetch("/users/addWithRole?" + requestString, {method:"POST"});
        if (response.status === STATUS_CREATED) {
            await response.text().then(r => notificationDialog.showNotification("Initial password: " + r));
            location.reload();
        }
        

    } catch (error) {
        alert(error.message);
    }
}

function gatherValues() {
    let itemId = document.getElementById("id-input");
    let userNameField = document.getElementById("username-input");
    let firstNameField = document.getElementById("firstname-input");
    let lastNameField = document.getElementById("lastname-input");
    let eMailField = document.getElementById("email-input");
    let userTypeField = document.getElementById("userType-input");
    return new User(
        itemId.value, 
        userNameField.value, 
        firstNameField.value, 
        lastNameField.value, 
        eMailField.value,
        userTypeField.value,
        false,
        false);
}

function User(itemId, userName, firstName, lastName, email, role, changedPassword, requiresPasswordReset) {
    this.id = itemId;
    this.username = userName;
    this.firstname = firstName;
    this.lastname = lastName;
    this.email = email;
    this.role = role;
    this.changedPassword = changedPassword;
    this.requiresPasswordReset = requiresPasswordReset;
}